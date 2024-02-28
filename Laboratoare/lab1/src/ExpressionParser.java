import java.util.Stack;

public class ExpressionParser {
    private static ExpressionParser instance = new ExpressionParser();

    private ExpressionParser() {};

    public static ExpressionParser getInstance() {
        return instance;
    }

    public ComplexNumber parseExpression(String[] args) throws Exception {
        if (args.length % 2 == 0) {
            throw new Exception("Not a valid expression.");
        }

        Stack<String> orderStack = new Stack<>();
        boolean multiplication = false;
        boolean division = false;
        boolean negative = false;
        ComplexNumber result = new ComplexNumber(0, 0);
        ComplexNumber[] operands = new ComplexNumber[2];

        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0 && (args[i].equals("+") || args[i].equals("-") || args[i].equals("*") || args[i].equals("/"))) {
                throw new ArithmeticException("Operator expected.");
            }

            if (i % 2 != 0 && ComplexNumber.isValidNumber(args[i])) {
                throw new ArithmeticException("Number at invalid position.");
            }

            switch (args[i]) {
                // multiplication and division will work on the whole stack
                case "*" -> {
                    multiplication = true;
                    continue;
                }
                case "/" -> {
                    division = true;
                    continue;
                    // adding and substracting will work in a similar matter
                }
                case "-" -> {
                    negative = true;
                    continue;
                }
                case "+" -> {
                    negative = false;
                    continue;
                }
            }

            // array with a partial expression
            operands[0] = new ComplexNumber(args[i]);

            // daca operatia este inmultire sau impartire, facem direct operatia
            // daca operatorul este '-', atunci introducem pe stiva numarul negat
            if (multiplication) {
                operands[1] = new ComplexNumber(orderStack.pop());
                ComplexNumber partialResult = ExpressionFactory.getInstance().createExpression(Operation.MULTIPLICATION, operands).execute();
                orderStack.push(partialResult.toString());
                multiplication = false;
            } else if (division) {
                operands[1] = new ComplexNumber(orderStack.pop());
                ComplexNumber partialResult = ExpressionFactory.getInstance().createExpression(Operation.DIVISION, operands).execute();
                orderStack.push(partialResult.toString());
                division = false;
            } else if (negative) {
                operands[0].re = -operands[0].re;
                operands[0].img = -operands[0].img;
                orderStack.push(operands[0].toString());
                negative = false;
            } else {
                orderStack.push(operands[0].toString());
            }
        }

        while (!orderStack.empty()) {
            operands[0] = result;
            operands[1] = new ComplexNumber(orderStack.pop());
            result = ExpressionFactory.getInstance().createExpression(Operation.ADDITION, operands).execute();
        }

        return result;
    }
}
