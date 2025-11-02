import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

public class PerformanceTester {

    private static List<List<String>> data = new ArrayList<>();
    private static final String FileInPutName = "in.txt";
    private static final String FileOutPutName = "out.txt";

    /**
     * Результат выполнения одного теста
     */
    static class TestResult {
        String testName;
        Object result;
        long executionTimeNano;    // в наносекундах
        long executionTimeMicro;   // в микросекундах
        long executionTimeMilli;   // в миллисекундах
        boolean success;
        String errorMessage;

        TestResult(String testName) {
            this.testName = testName;
            this.success = true;
            this.executionTimeNano = 0;
            this.executionTimeMicro = 0;
            this.executionTimeMilli = 0;
        }
    }

    /**
     * Парсит CSV файл
     * @param csvFile путь к CSV файлу
     * @param delimiter разделитель (запятая, точка с запятой и т.д.)
     * @return список списков значений
     */
    public static void parseCSV(String csvFile, String delimiter) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(csvFile));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(delimiter, -1);
            List<String> row = new ArrayList<>();
            for (String value : values) {
                row.add(value.trim());
            }
            data.add(row);
        }
        reader.close();
    }

    /**
     * Парсит CSV с разделителем запятой по умолчанию
     */
    public static void parseCSV(String csvFile) throws IOException {
        parseCSV(csvFile, ",");
    }

    /**
     * Вставляет строки в файл
     * @param lines строки для вставки
     */
    public static void writeToFile(List<String> lines) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(FileInPutName));
        for (String line : lines) {
            writer.println(line);
        }
        writer.close();
    }

    /**
     * Вставляет строки в файл (перегрузка для массива)
     */
    public static void writeToFile( String... lines) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(FileInPutName));
        for (String line : lines) {
            writer.println(line);
        }
        writer.close();
    }

    /**
     * Запускает тест метода с замером времени
     * @return результат с информацией о времени выполнения
     */
    public static TestResult runTest(String testName,Object object,String methodName, List<String> dataForTesting) {
        TestResult result = new TestResult(testName);
        Class<?> clazz = object.getClass();
        try {
            writeToFile(dataForTesting);

            // Замеряем время выполнения
            long startTime = System.nanoTime();
            Method method = clazz.getMethod(methodName);
            result.result = method.invoke(null);
            long endTime = System.nanoTime();

            // Рассчитываем время в разных единицах
            result.executionTimeNano = endTime - startTime;
            result.executionTimeMicro = result.executionTimeNano / 1_000;
            result.executionTimeMilli = result.executionTimeNano / 1_000_000;

        } catch (Exception e) {
            result.success = false;
            result.errorMessage = e.getMessage();
        }

        return result;
    }
    private static List<String> readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FileInPutName));
        String line;
        List<String> list = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }


    /**
     * Выводит результаты тестирования
     */
    public static void printResults(List<TestResult> results) {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                       РЕЗУЛЬТАТЫ ТЕСТИРОВАНИЯ ПРОИЗВОДИТЕЛЬНОСТИ              ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Тест                         │ Результат  │ Время (мс) │ Время (мкс) │ Статус ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════╣");

        long totalTime = 0;
        int successCount = 0;

        for (TestResult result : results) {
            totalTime += result.executionTimeMilli;
            if (result.success) successCount++;

            String status = result.success ? "✓ OK" : "✗ ERROR";
            String resultStr = result.success ?
                    (result.result != null ? result.result.toString() : "void") :
                    result.errorMessage;

            if (resultStr.length() > 10) {
                resultStr = resultStr.substring(0, 10);
            }

            System.out.printf("║ %-28s │ %-10s │ %10d │ %11d │ %-6s ║%n",
                    result.testName,
                    resultStr,
                    result.executionTimeMilli,
                    result.executionTimeMicro,
                    status);
        }

        System.out.println("╠════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ ИТОГО: %d успешных, %d ошибок | Общее время: %d мс %40s║%n",
                successCount, results.size() - successCount, totalTime, "");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════╝");
    }

    /**
     * Выводит подробные результаты
     */
    public static void printDetailedResults(List<TestResult> results) {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                            ПОДРОБНЫЙ АНАЛИЗ РЕЗУЛЬТАТОВ                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════╝");

        long minTime = Long.MAX_VALUE;
        long maxTime = 0;
        long totalTime = 0;

        for (TestResult result : results) {
            if (result.executionTimeMilli < minTime) minTime = result.executionTimeMilli;
            if (result.executionTimeMilli > maxTime) maxTime = result.executionTimeMilli;
            totalTime += result.executionTimeMilli;

            System.out.println();
            System.out.println("┌────────────────────────────────────────────────────────────────────────────────┐");
            System.out.printf("│ Тест: %-72s │%n", result.testName);
            System.out.println("├────────────────────────────────────────────────────────────────────────────────┤");
            System.out.printf("│ Статус:            %-68s │%n", result.success ? "✓ УСПЕШНО" : "✗ ОШИБКА");
            System.out.printf("│ Результат:         %-68s │%n",
                    result.success ? (result.result != null ? result.result.toString() : "void") : result.errorMessage);
            System.out.printf("│ Время (наносек):   %-68d │%n", result.executionTimeNano);
            System.out.printf("│ Время (микросек):  %-68d │%n", result.executionTimeMicro);
            System.out.printf("│ Время (миллисек):  %-68d │%n", result.executionTimeMilli);
            System.out.println("└────────────────────────────────────────────────────────────────────────────────┘");
        }

        // Статистика
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ СТАТИСТИКА                                                                     │");
        System.out.println("├────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Количество тестов:         %-57d │%n", results.size());
        System.out.printf("│ Минимальное время (мс):    %-57d │%n", minTime);
        System.out.printf("│ Максимальное время (мс):   %-57d │%n", maxTime);
        System.out.printf("│ Среднее время (мс):        %-57.2f │%n", (double) totalTime / results.size());
        System.out.printf("│ Общее время (мс):          %-57d │%n", totalTime);
        System.out.println("└────────────────────────────────────────────────────────────────────────────────┘");
    }

}
