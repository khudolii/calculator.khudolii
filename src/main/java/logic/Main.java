package logic;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        Expression expressionFile = new Expression();
        List<String> expressionsList = expressionFile.getExpressionFromFile();
        List <String> resultsList = new ArrayList<>();
        for (String expression : expressionsList){
            ReversePolishNotationCalculator calculator = new ReversePolishNotationCalculator(expression);
            String resultText = null;
            try {
                double res = calculator.toCalculateExpression();
                resultText = String.valueOf(res);

            } catch (Exception e) {
                System.err.println(e.toString());
                resultText = e.toString();
            } finally {
                resultsList.add(resultText);
            }

        }
        if (expressionFile.writeResultToFile(resultsList))
            System.out.println("The result is written to a file");
        else
            System.out.println("The result is  NOT written to a file");

    }
}
