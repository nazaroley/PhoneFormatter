import java.io.*;
import java.util.regex.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Класс для форматирования телефонных номеров с тестами
 */
public class PhoneFormatterWithTests {
    // Шаблон для распознавания российских номеров
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("(\\+7|8)(\\d{10})");
    
    /**
     * Форматирует телефонный номер в международный формат
     * @param phone Исходный номер
     * @return Отформатированный номер или исходная строка если не номер
     */
    public static String formatPhone(String phone) {
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        if (matcher.find()) {
            String digits = matcher.group(2);
            return String.format("+1 (%s) %s-%s-%s",
                digits.substring(0, 3),
                digits.substring(3, 6),
                digits.substring(6, 8),
                digits.substring(8));
        }
        return phone;
    }
    
    /**
     * Обрабатывает файл с номерами
     * @param inputFile Входной файл
     * @param outputFile Выходной файл
     */
    public static void processFile(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(formatPhone(line) + "\n");
            }
        }
    }
    
    /**
     * Точка входа в программу
     */
    public static void main(String[] args) {
        try {
            processFile("input.txt", "output.txt");
            System.out.println("Форматирование завершено. Результат в output.txt");
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
    
    // ==================== ТЕСТЫ ====================
    
    /**
     * Тест форматирования номеров
     */
    @Test
    public void testFormatPhone() {
        assertEquals("+1 (900) 111-22-33", formatPhone("+79001112233"));
        assertEquals("+1 (915) 555-66-77", formatPhone("89155556677"));
        assertEquals("invalid", formatPhone("invalid"));
    }
    
    /**
     * Тест обработки файла
     */
    @Test
    public void testFileProcessing() throws IOException {
        // Подготовка тестовых файлов
        String testInput = "test_input.txt";
        String testOutput = "test_output.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testInput))) {
            writer.write("+79001112233\n");
            writer.write("89155556677\n");
        }
        
        // Выполнение тестируемого метода
        processFile(testInput, testOutput);
        
        // Проверка результатов
        BufferedReader reader = new BufferedReader(new FileReader(testOutput));
        assertEquals("+1 (900) 111-22-33", reader.readLine());
        assertEquals("+1 (915) 555-66-77", reader.readLine());
        assertNull(reader.readLine()); // Проверка конца файла
        reader.close();
        
        // Очистка
        new File(testInput).delete();
        new File(testOutput).delete();
    }
    
    /**
     * Запуск тестов из командной строки
     */
    public static void runTests() {
        System.out.println("Запуск тестов...");
        org.junit.runner.JUnitCore.main("PhoneFormatterWithTests");
    }
}
