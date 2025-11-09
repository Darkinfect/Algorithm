package Treees;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Автотестер для проверки производительности и корректности алгоритма Хаффмана (specSD3).
 * Генерирует тестовые файлы, запускает specSD3.start(), измеряет время, валидирует результаты.
 */
public class HuffmanAutoTester {

    // Конфигурация тестера
    private final String templatePath;
    private final int iterations;
    private final int minN;
    private final int maxN;
    private final long seed;
    private final boolean validateResults;

    // Результаты тестов
    private final List<TestResult> results = new ArrayList<>();

    /**
     * Конструктор автотестера.
     * @param templatePath путь к шаблонному файлу (не используется, но для расширения)
     * @param iterations количество итераций для каждого теста
     * @param minN минимальное n для генерации
     * @param maxN максимальное n для генерации
     * @param seed seed для генератора случайных чисел
     * @param validateResults включить валидацию результатов
     */
    public HuffmanAutoTester(String templatePath, int iterations, int minN, int maxN,
                             long seed, boolean validateResults) {
        this.templatePath = templatePath;
        this.iterations = iterations;
        this.minN = minN;
        this.maxN = maxN;
        this.seed = seed;
        this.validateResults = validateResults;
    }

    /**
     * Главный метод запуска тестов.
     * @param configCsv путь к CSV-конфигурации (null для дефолтных тестов)
     */
    public void runTests(String configCsv) {
        List<TestConfig> configs;

        if (configCsv != null && Files.exists(Paths.get(configCsv))) {
            configs = loadConfigFromCsv(configCsv);
            System.out.println("Loaded " + configs.size() + " test configs from " + configCsv);
        } else {
            // Дефолтные тесты: 5 типов с разными n
            configs = getDefaultConfigs();
            System.out.println("Using default test configurations (5 tests)");
        }

        int testId = 1;
        for (TestConfig config : configs) {
            System.out.println("\n=== Running test: " + config.testType + ", n=" + config.n +
                    ", iterations=" + config.iterations + " ===");

            for (int iter = 1; iter <= config.iterations; iter++) {
                try {
                    // Генерация входного файла
                    List<Long> frequencies = FileGenerator.generateInputFile(
                            "huffman.in", config.testType, config.n, config.seed + iter
                    );

                    // Вычисление ожидаемого результата (если включена валидация)
                    long expectedTotal = validateResults ? computeExpectedTotal(frequencies) : -1;

                    // Замер времени выполнения specSD3.start()
                    long startTime = System.nanoTime();
                    SpecSD.specSD3.main(null);  // Запуск статического метода
                    long endTime = System.nanoTime();
                    double timeMsec = (endTime - startTime) / 1_000_000.0;

                    // Чтение результата из huffman.out
                    long totalFromOut = readOutputFile("huffman.out");

                    // Валидация результата
                    boolean passed = !validateResults || (totalFromOut == expectedTotal);
                    String status = passed ? "PASS" : "FAIL";

                    // Сохранение результата
                    TestResult result = new TestResult(
                            testId++, config.testType, config.n, iter,
                            timeMsec, totalFromOut, expectedTotal, status
                    );
                    results.add(result);

                    System.out.printf("  Iteration %d: %.2f ms, total=%d, expected=%d, %s\n",
                            iter, timeMsec, totalFromOut, expectedTotal, status);

                } catch (Exception e) {
                    System.err.println("  Iteration " + iter + " FAILED: " + e.getMessage());
                    results.add(new TestResult(
                            testId++, config.testType, config.n, iter,
                            -1, -1, -1, "FAIL_ERROR: " + e.getMessage()
                    ));
                }
            }
        }

        // Вывод результатов
        exportResultsToCsv("results.csv");
        printSummary();
    }

    /**
     * Вычисление ожидаемого total с использованием стандартного алгоритма Хаффмана (min-heap).
     */
    private long computeExpectedTotal(List<Long> frequencies) {
        if (frequencies.size() <= 1) return 0;

        PriorityQueue<Long> pq = new PriorityQueue<>(frequencies);
        long total = 0;

        while (pq.size() > 1) {
            long l = pq.poll();
            long r = pq.poll();
            long sum = l + r;
            total += sum;
            pq.offer(sum);
        }

        return total;
    }

    /**
     * Чтение результата из выходного файла huffman.out.
     */
    private long readOutputFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                throw new IOException("Output file is empty");
            }
            return Long.parseLong(line.trim());
        }
    }

    /**
     * Загрузка конфигураций тестов из CSV-файла.
     */
    private List<TestConfig> loadConfigFromCsv(String csvPath) {
        List<TestConfig> configs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            String line = reader.readLine(); // Пропускаем заголовок

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String testType = parts[0].trim();
                    int n = Integer.parseInt(parts[1].trim());
                    int iters = Integer.parseInt(parts[2].trim());
                    long seedVal = Long.parseLong(parts[3].trim());
                    configs.add(new TestConfig(testType, n, iters, seedVal));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading CSV config: " + e.getMessage());
        }

        return configs;
    }

    /**
     * Дефолтные конфигурации тестов.
     */
    private List<TestConfig> getDefaultConfigs() {
        return Arrays.asList(
                new TestConfig("sorted", 100, 3, seed),
                new TestConfig("random", 1000, 3, seed),
                new TestConfig("sorted", 10000, 3, seed),
                new TestConfig("duplicates", 100000, 2, seed),
                new TestConfig("random", 500000, 1, seed)
        );
    }

    /**
     * Экспорт результатов в CSV-файл.
     */
    private void exportResultsToCsv(String csvPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvPath))) {
            writer.println("testId,type,n,iteration,time_ms,total_from_out,expected_total,status");

            for (TestResult result : results) {
                writer.printf("%d,%s,%d,%d,%.2f,%d,%d,%s\n",
                        result.testId, result.type, result.n, result.iteration,
                        result.timeMsec, result.totalFromOut, result.expectedTotal, result.status);
            }

            System.out.println("\nResults exported to " + csvPath);
        } catch (IOException e) {
            System.err.println("Error exporting results: " + e.getMessage());
        }
    }

    /**
     * Вывод статистики в консоль.
     */
    private void printSummary() {
        System.out.println("\n=== SUMMARY ===");

        Map<String, List<Double>> timesByType = new HashMap<>();
        int totalTests = results.size();
        int failedTests = 0;

        for (TestResult result : results) {
            if (result.status.startsWith("FAIL")) {
                failedTests++;
            }
            if (result.timeMsec >= 0) {
                timesByType.computeIfAbsent(result.type, k -> new ArrayList<>())
                        .add(result.timeMsec);
            }
        }

        System.out.println("Total tests: " + totalTests);
        System.out.println("Failed tests: " + failedTests + " (" +
                String.format("%.1f%%", 100.0 * failedTests / totalTests) + ")");
        System.out.println("\nAverage times by type:");

        for (Map.Entry<String, List<Double>> entry : timesByType.entrySet()) {
            List<Double> times = entry.getValue();
            double avgTime = times.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            double minTime = times.stream().mapToDouble(Double::doubleValue).min().orElse(0);
            double maxTime = times.stream().mapToDouble(Double::doubleValue).max().orElse(0);

            System.out.printf("  %s: avg=%.2f ms, min=%.2f ms, max=%.2f ms\n",
                    entry.getKey(), avgTime, minTime, maxTime);
        }
    }

    // === ВНУТРЕННИЕ КЛАССЫ ===

    /**
     * Генератор тестовых файлов.
     */
    static class FileGenerator {
        /**
         * Генерация входного файла с частотами.
         * @param outputPath путь к выходному файлу (huffman.in)
         * @param testType тип теста: random, sorted, duplicates
         * @param n количество частот
         * @param seed seed для генератора
         * @return список сгенерированных частот
         */
        public static List<Long> generateInputFile(String outputPath, String testType,
                                                   int n, long seed) throws IOException {
            Random random = new Random(seed);
            List<Long> frequencies = new ArrayList<>(n);

            // Генерация частот в зависимости от типа
            switch (testType.toLowerCase()) {
                case "sorted":
                    for (int i = 1; i <= n; i++) {
                        frequencies.add((long) i);
                    }
                    break;

                case "duplicates":
                    int half = n / 2;
                    for (int i = 0; i < half; i++) frequencies.add(1L);
                    for (int i = half; i < n; i++) frequencies.add(2L);
                    break;

                case "random":
                default:
                    for (int i = 0; i < n; i++) {
                        frequencies.add(1L + (long)(random.nextDouble() * 1_000_000_000));
                    }
                    break;
            }

            // Запись в файл
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
                writer.println(n);
                for (int i = 0; i < frequencies.size(); i++) {
                    if (i > 0) writer.print(" ");
                    writer.print(frequencies.get(i));
                }
                writer.println();
            }

            return frequencies;
        }
    }

    /**
     * Конфигурация теста.
     */
    static class TestConfig {
        String testType;
        int n;
        int iterations;
        long seed;

        TestConfig(String testType, int n, int iterations, long seed) {
            this.testType = testType;
            this.n = n;
            this.iterations = iterations;
            this.seed = seed;
        }
    }

    /**
     * Результат теста.
     */
    static class TestResult {
        int testId;
        String type;
        int n;
        int iteration;
        double timeMsec;
        long totalFromOut;
        long expectedTotal;
        String status;

        TestResult(int testId, String type, int n, int iteration, double timeMsec,
                   long totalFromOut, long expectedTotal, String status) {
            this.testId = testId;
            this.type = type;
            this.n = n;
            this.iteration = iteration;
            this.timeMsec = timeMsec;
            this.totalFromOut = totalFromOut;
            this.expectedTotal = expectedTotal;
            this.status = status;
        }
    }

    // === MAIN ===

    /**
     * Точка входа для запуска автотестера.
     * Аргументы командной строки:
     *   --config <path>: путь к CSV-конфигурации (опционально)
     *   --iterations <n>: количество итераций (по умолчанию 3)
     *   --validate: включить валидацию результатов
     */
    public static void main(String[] args) {
        // Парсинг аргументов
        String configPath = null;
        int iterations = 3;
        boolean validate = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--config") && i + 1 < args.length) {
                configPath = args[++i];
            } else if (args[i].equals("--iterations") && i + 1 < args.length) {
                iterations = Integer.parseInt(args[++i]);
            } else if (args[i].equals("--validate")) {
                validate = true;
            }
        }

        // Создание и запуск тестера
        HuffmanAutoTester tester = new HuffmanAutoTester(
                "template.in",  // Не используется в текущей реализации
                iterations,
                100,            // minN
                1000000,        // maxN
                42L,            // seed
                validate
        );

        tester.runTests(configPath);

        System.out.println("\n=== Testing completed ===");
    }
}
