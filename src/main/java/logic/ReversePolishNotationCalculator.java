package logic;

import java.util.EmptyStackException;
import java.util.Stack;

public class ReversePolishNotationCalculator implements Calculator {

    private final String expression;

    public ReversePolishNotationCalculator(String expression) {
        this.expression = expression;
    }

    /*
    Метод разбирает строку с заданным выражением на польскую нотацию. Происходит посимвольный
    перебор с определением типа символа по его условному приоритету. Если текущий символ это цифра - то добавляем ее сразу в результирующую строку:
    Если символ - знак операции (+, -, *, / ), то проверяем приоритет данной операции. Операции умножения и деления имеют наивысший приоритет
    (равен 3). Операции сложения и вычитания имеют меньший приоритет (равен 2). Наименьший приоритет (равен 1) имеет открывающая скобка.
    Если текущий символ - открывающая скобка, то помещаем ее в стек. Если текущий символ - закрывающая скобка, то извлекаем символы из стека в результирующую строку
    до тех пор, пока не встретим в стеке открывающую скобку, которая удаляется. Закрывающая скобка также удаляется. Если вся входная строка разобрана,
    а в стеке еще остаются знаки операций, извлекаем из стека в результирующую строку.
    */
    private String expressionToReversePolishNotation() throws EmptyStackException {
        Stack<Character> stack = new Stack<>();
        StringBuilder rpnExpression = new StringBuilder();
        int currentSymbolPriority = 0;
        for (char c : expression.toCharArray()) {
            currentSymbolPriority = getPriority(c);
            if (currentSymbolPriority == 0) {
                rpnExpression.append(c);
                System.out.println("Add new num to RPN Expression -> " + c);
            } else if (currentSymbolPriority == 1) {
                stack.push(c);
                System.out.println("Found " + c + " - add to stack");
            } else if (currentSymbolPriority > 1) {
                rpnExpression.append(" ");
                while (!stack.empty()) {
                    if (getPriority((stack.peek())) >= currentSymbolPriority) {
                        rpnExpression.append(stack.pop());
                        System.out.println("Pop  " + c + " from stack - and add to RPM expression");
                    } else
                        break;
                }
                stack.push(c);
            } else {
                rpnExpression.append(" ");
                while (getPriority(stack.peek()) != 1)
                    rpnExpression.append(stack.pop());
                stack.pop();
            }
        }
        while (!stack.empty())
            rpnExpression.append(stack.pop());
        return rpnExpression.toString();
    }

    /*Данный метод возвращает результат вычисления выражения. В стек добавляются числа по очереди по принципу: предыдущие числа плюс текущее число.
    Если текущий символ попадает на арифметический знак то берем два последних символа с стека и выполняем эту операцию над ними, результат помещаем в стек.
    * */
    private double getResult(String rpnExpression) throws EmptyStackException {
        StringBuilder operand = new StringBuilder();
        Stack<Double> stack = new Stack<>();
        char[] charArray = rpnExpression.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == ' ')
                continue;
            if (getPriority(charArray[i]) == 0) {
                while (getPriority(charArray[i]) == 0
                        && charArray[i] != ' ') {
                    operand.append(charArray[i++]);
                    if (i == charArray.length) break;
                }
                stack.push(Double.parseDouble(operand.toString()));
                operand = new StringBuilder();
            }
            if (getPriority(charArray[i]) > 1) {
                double a = stack.pop();
                double b = stack.pop();
                double result = 0;
                switch (charArray[i]) {
                    case '+':
                        result = sum(a, b);
                        break;
                    case '-':
                        result = subtract(a, b);
                        break;
                    case '*':
                        result = multiply(a, b);
                        break;
                    case '/': {
                        try {
                            result = divide(a, b);
                        } catch (ArithmeticException e) {
                            System.err.println(e.toString());
                            return 0;
                        }
                    }
                }
                stack.push(result);
            }

        }
        return stack.pop();
    }

    public double sum(double a, double b) {
        System.out.println(a + " + " + b);
        return a + b;
    }

    public double subtract(double a, double b) {
        System.out.println(a + " - " + b);
        return a - b;
    }

    public double multiply(double a, double b) {
        System.out.println(a + " * " + b);
        return a * b;
    }

    public double divide(double a, double b) throws ArithmeticException {
        System.out.println(a + " / " + b);
        if (b == 0)
            throw new ArithmeticException("b=0");
        return a / b;
    }

    private int getPriority(char symbol) {
        switch (symbol) {
            case '*':
            case '/':
                return 3;
            case '+':
            case '-':
                return 2;
            case '(':
                return 1;
            case ')':
                return -1;
            default:
                return 0;
        }
    }
    /*
    Метод проверяет корректность выражения. Проверки на то, что после открытой скобки должна быть закрытая, то что не может повторятся символ.
    */
    private boolean isCorrectExpression() {
        char previousChar;
        char[] charArray = expression.toCharArray();
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++) {
            char c = charArray[i];
            if (getPriority(c) == 0) continue;

            if (getPriority(c) == 1) { //Если текущий элемент (
                int elementNum = i + 1;
                boolean result = false;
                while (elementNum != charArrayLength) {
                    if (getPriority(charArray[elementNum]) != -1) //Если элемент не ), идем дальше
                        elementNum++;
                    else if (getPriority(charArray[elementNum]) == 1) { //Если элемент снова ( - это ошибка, вовзращаем false
                        System.err.println("Double '('");
                        break;
                    } else if (getPriority(charArray[elementNum]) == -1) { //Если элемент ) то заканчиваем цикл
                        result = true;
                        break;
                    }
                }
                if (!result) {
                    System.err.println("No such )");
                    return false;
                }
            }
            if (getPriority(c) == 2 || getPriority(c) == 3) {
                if (getPriority(charArray[i + 1]) > 1 || getPriority(charArray[i + 1]) < 0) {
                    System.err.println("Error! After " + c + " - " + charArray[i + 1]);
                    return false;
                }
            }
        }
        return true;
    }

    public double toCalculateExpression() throws NullExpressionException {
        if (expression == null || expression.equals(""))
            throw new NullExpressionException("Expression is null! Calculate impossible.");
        if (!isCorrectExpression())
            return 0;
        else {
            try {
                String rpnExpression = expressionToReversePolishNotation();
                return getResult(rpnExpression);
            } catch (EmptyStackException e) {
                System.err.println(e.toString());
                return 0;
            }
        }
    }
}
