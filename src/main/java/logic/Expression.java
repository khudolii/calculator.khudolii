package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Expression {
    /*Метод реализует чтение строки из файла. После чего убирает все буквенные символы и пробелы с полученной строки*/

    public List<String> getExpressionFromFile() {
        List<String> expressions = new ArrayList<>();
        try (FileReader f = new FileReader("testData/input.txt")) {
            StringBuffer sb = new StringBuffer();
            while (f.ready()) {
                char c = (char) f.read();
                if (c == '\n') {
                    expressions.add(sb.toString());
                    sb = new StringBuffer();
                } else {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                expressions.add(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expressions;
    }

    public boolean writeResultToFile(List<String> results) {
        try (FileWriter writer = new FileWriter("testData/output.txt", false)) {
            for (String result : results) {
                writer.write(result + "\n");
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
}
