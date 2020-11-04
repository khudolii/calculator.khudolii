package logic;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        Expression expression = new Expression();
        String exp = expression.getExpressionFromFile();
        ReversePolishNotationCalculator calculator = new ReversePolishNotationCalculator(exp);
        double result;
        try {
            result = calculator.toCalculateExpression();
        } catch (NullExpressionException e) {
            System.err.println(e.toString());
            result = 0;
        }
        if (expression.writeResultToFile(result))
            System.out.println("The result is written to a file");
        else
            System.out.println("The result is  NOT written to a file");

    }
}
