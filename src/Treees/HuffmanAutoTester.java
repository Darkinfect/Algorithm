package Treees;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Обновленный автотестер для алгоритма Хаффмана под новый формат файлов.
 * Поддерживает файлы huffman_test_1.in ... huffman_test_30.in с отсортированными частотами.
 */
public class HuffmanAutoTester {

    private final String testDirectory;
    private final boolean validateResults;
    private final boolean verbose;
    private final List<TestResult> results = new ArrayList<>();

    /**
     * Конструктор.
     * @param testDirectory директория с тестовыми файлами
     * @param validateResults включить валидацию (может быть медленно для больших n)
     * @param verbose выводить детальную информацию
     */
    public HuffmanAutoTester(String testDirectory, boolean validateResults, boolean verbose) {
        this.testDirectory = testDirectory;
        this.validateResults = validateResults;
        this.verbose = verbose;
    }

    /**
     * Главный метод: находит все тестовые файлы и запускает их.
     */
    public void runAllTests() {
        List<File> testFiles = findTestFiles();

        if (testFiles.isEmpty()) {
            System.err.println("No test files found matching pattern huffman_test_*.in");
            return;
        }

        testFiles.sort(Comparator.comparing(File::getName));
        System.out.println("Found " + testFiles.size() + " test files\n");

        int testId = 1;
        for (File testFile : testFiles) {
            runSingleTest(testFile, testId++);
        }

        exportResultsToCsv("results.csv");
        printSummary();
    }

    /**
     * Находит все файлы huffman_test_*.in в директории.
     */
    private List<File> findTestFiles() {
        List<File> files = new ArrayList<>();

        try {
            File dir = new File(testDirectory);
            File[] allFiles = dir.listFiles((d, name) ->
                    name.matches("huffman_test_\\d+\\.in"));

            if (allFiles != null) {
                files.addAll(Arrays.asList(allFiles));
            }
        } catch (Exception e) {
            System.err.println("Error finding test files: " + e.getMessage());
        }

        return files;
    }

    /**
     * Запуск одного теста.
     */
    private void runSingleTest(File testFile, int testId) {
        try {
            // Чтение входного файла
            System.out.print("Test " + testId + " (" + testFile.getName() + "): ");

            TestInput input = readTestInput(testFile.getAbsolutePath());
            int n = input.n;
            List<Long> frequencies = input.frequencies;

            // Проверка отсортированности
            boolean isSorted = isSorted(frequencies);
            if (!isSorted && verbose) {
                System.out.println("WARNING: frequencies not sorted");
            }

            // Копирование в стандартный входной файл
            File standardInput = new File("huffman.in");
            Files.copy(testFile.toPath(), standardInput.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // Вычисление ожидаемого результата
            long expectedTotal = validateResults ? computeExpectedTotal(frequencies) : -1;

            // Замер времени выполнения
            long startTime = System.nanoTime();
            SpecSD.specSD3.main(null);
            long endTime = System.nanoTime();
            double timeMsec = (endTime - startTime) / 1_000_000.0;

            // Чтение результата
            long totalFromOut = readOutputFile("huffman.out");

            // Валидация
            boolean passed = !validateResults || (totalFromOut == expectedTotal);
            String status = passed ? "PASS" : "FAIL";

            // Сохранение результата
            TestResult result = new TestResult(
                    testId, n, timeMsec, totalFromOut, expectedTotal, status, isSorted
            );
            results.add(result);


            // Вывод
            double timePerSymbol = n > 0 ? timeMsec / n * 1000 : 0;  // мкс на элемент
            System.out.printf("n=%7d, time=%.2f ms (%.3f µs/elem), total=%,d, %s\n",
                    n, timeMsec, timePerSymbol, totalFromOut, status);

        } catch (Exception e) {
            System.err.println("Test " + testId + " ERROR: " + e.getMessage());
            results.add(new TestResult(testId, -1, -1, -1, -1, "FAIL_ERROR: " + e.getMessage(), false));
        }
    }

    /**
     * Чтение входного файла.
     */
    private TestInput readTestInput(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path), 131072)) {
            int n = Integer.parseInt(reader.readLine().trim());
            String line = reader.readLine().trim();
            String[] parts = line.split("\\s+");

            List<Long> frequencies = new ArrayList<>(n);
            for (String part : parts) {
                frequencies.add(Long.parseLong(part));
            }

            return new TestInput(n, frequencies);
        }
    }

    /**
     * Проверка отсортированности массива.
     */
    private boolean isSorted(List<Long> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < list.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Вычисление ожидаемого total с использованием стандартного Хаффмана (min-heap).
     * ВНИМАНИЕ: Медленно для n > 100000!
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
     * Чтение результата из huffman.out.
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
     * Экспорт результатов в CSV.
     */
    private void exportResultsToCsv(String csvPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvPath))) {
            writer.println("testId,n,time_ms,total_from_out,expected_total,status,sorted");

            for (TestResult result : results) {
                writer.printf("%d,%d,%.2f,%d,%d,%s,%b\n",
                        result.testId, result.n, result.timeMsec,
                        result.totalFromOut, result.expectedTotal,
                        result.status, result.isSorted);
            }

            System.out.println("\n✓ Results exported to " + csvPath);
        } catch (IOException e) {
            System.err.println("Error exporting results: " + e.getMessage());
        }
    }

    /**
     * Вывод статистики и прогноза.
     */
    private void printSummary() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SUMMARY");
        System.out.println("=".repeat(80));

        int totalTests = results.size();
        int passedTests = 0;
        long totalTimeMs = 0;
        double maxTimePerSymbol = 0;

        for (TestResult result : results) {
            if (result.status.equals("PASS")) passedTests++;
            if (result.timeMsec >= 0) {
                totalTimeMs += (long) result.timeMsec;
                if (result.n > 0) {
                    double timePerSymbol = result.timeMsec / result.n;
                    maxTimePerSymbol = Math.max(maxTimePerSymbol, timePerSymbol);
                }
            }

        }

        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed: " + passedTests + " (" + String.format("%.1f%%", 100.0 * passedTests / totalTests) + ")");
        System.out.println("Total time: " + totalTimeMs + " ms");
        System.out.println("Average time per test: " + String.format("%.2f ms", (double) totalTimeMs / totalTests));

        // Прогноз для n=2.5млн
        System.out.println("\nForecast for n=2,500,000:");
        double forecastTimeMs = maxTimePerSymbol * 2_500_000 / 1000;
        System.out.println("  Estimated time: " + String.format("%.2f ms (%.2f seconds)", forecastTimeMs, forecastTimeMs / 1000));

        // Группировка по размерам
        System.out.println("\nPerformance by size category:");
        Map<String, List<TestResult>> byCategory = categorizeBySize();
        for (Map.Entry<String, List<TestResult>> entry : byCategory.entrySet()) {
            List<TestResult> category = entry.getValue();
            double avgTime = category.stream()
                    .filter(r -> r.timeMsec >= 0)
                    .mapToDouble(r -> r.timeMsec)
                    .average()
                    .orElse(0);
            int avgN = (int) category.stream()
                    .filter(r -> r.n > 0)
                    .mapToInt(r -> r.n)
                    .average()
                    .orElse(0);
            System.out.printf("  %s (avg n=%,d): %.2f ms avg\n", entry.getKey(), avgN, avgTime);
        }
    }

    /**
     * Категоризация тестов по размеру.
     */
    private Map<String, List<TestResult>> categorizeBySize() {
        Map<String, List<TestResult>> categories = new LinkedHashMap<>();
        categories.put("Tiny (n<100)", new ArrayList<>());
        categories.put("Small (100-1k)", new ArrayList<>());
        categories.put("Medium (1k-100k)", new ArrayList<>());
        categories.put("Large (100k-1M)", new ArrayList<>());
        categories.put("Huge (>1M)", new ArrayList<>());

        for (TestResult result : results) {
            if (result.n < 100) {
                categories.get("Tiny (n<100)").add(result);
            } else if (result.n < 1000) {
                categories.get("Small (100-1k)").add(result);
            } else if (result.n < 100_000) {
                categories.get("Medium (1k-100k)").add(result);
            } else if (result.n < 1_000_000) {
                categories.get("Large (100k-1M)").add(result);
            } else {
                categories.get("Huge (>1M)").add(result);
            }
        }

        return categories;
    }

    // === ВСПОМОГАТЕЛЬНЫЕ КЛАССЫ ===

    static class TestInput {
        int n;
        List<Long> frequencies;

        TestInput(int n, List<Long> frequencies) {
            this.n = n;
            this.frequencies = frequencies;
        }
    }

    static class TestResult {
        int testId;
        int n;
        double timeMsec;
        long totalFromOut;
        long expectedTotal;
        String status;
        boolean isSorted;

        TestResult(int testId, int n, double timeMsec, long totalFromOut,
                   long expectedTotal, String status, boolean isSorted) {
            this.testId = testId;
            this.n = n;
            this.timeMsec = timeMsec;
            this.totalFromOut = totalFromOut;
            this.expectedTotal = expectedTotal;
            this.status = status;
            this.isSorted = isSorted;
        }
    }

    // === MAIN ===

    /**
     * Точка входа.
     * Аргументы:
     *   --dir <path>: директория с тестами (по умолчанию текущая)
     *   --validate: включить валидацию (медленно!)
     *   --verbose: подробный вывод
     */
    public static void main(String[] args) {
        String testDir = "tests";
        boolean validate = false;
        boolean verbose = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--dir") && i + 1 < args.length) {
                testDir = args[++i];

            } else if (args[i].equals("--validate")) {
                validate = true;
            } else if (args[i].equals("--verbose")) {
                verbose = true;
            }
        }

        HuffmanAutoTester tester = new HuffmanAutoTester(testDir, validate, verbose);
        tester.runAllTests();
    }
}

