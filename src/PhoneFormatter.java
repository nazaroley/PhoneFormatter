import java.io.*;
import java.util.regex.*;

public class PhoneFormatter {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String line;
        Pattern pattern = Pattern.compile("(\\+7|8)(\\d{10})");
        
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                System.out.println("Найден номер: " + matcher.group());
            }
        }
        reader.close();
    }
}
