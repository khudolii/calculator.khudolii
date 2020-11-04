package logic;

import java.io.*;
import java.util.stream.Collectors;

public class Expression {
    /*Метод реализует чтение строки из файла. После чего убирает все буквенные символы и пробелы с полученной строки*/
    public String getExpressionFromFile() {
        String text = null;
        try (FileInputStream file = new FileInputStream("testData/input.txt")) {
            byte[] str = new byte[file.available()];
            file.read(str);
            text = new String(str);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
        String collect = text.chars()
                .filter(c -> !Character.isLetter(c))
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
        return collect.replaceAll("\\s+", "");
    }

    public static void main(String[] args) throws IOException {
        Expression expression = new Expression();
        System.out.println(expression.getExpressionFromFile());
    }

    public boolean writeResultToFile(double result) {
        try (FileWriter writer = new FileWriter("testData/output.txt", false)) {
            writer.write(String.valueOf(result));
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
}
