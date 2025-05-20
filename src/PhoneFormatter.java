import java.io.*;
import java.util.regex.*;

public class PhoneFormatter {
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("(\\+7|8)(\\d{10})");
    
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
    
    public static void processFile(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                String formatted = formatPhone(line);
                writer.write(formatted + "\n");
                System.out.println("Обработано: " + line + " -> " + formatted);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            // Создаем тестовый input.txt если не существует
            File input = new File("input.txt");
            if (!input.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(input))) {
                    writer.write("+79001112233\n");
                    writer.write("89155556677\n");
                }
            }
            
            processFile("input.txt", "output.txt");
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
    }
}
