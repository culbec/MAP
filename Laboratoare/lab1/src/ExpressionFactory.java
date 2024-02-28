public class ExpressionFactory {
    private static ExpressionFactory instance = null;

    private ExpressionFactory() {
    }

    public static ExpressionFactory getInstance() {
        if (instance == null) {
            instance = new ExpressionFactory();
        }
        return instance;
    }

    public ComplexExpression createExpression(Operation operation, ComplexNumber[] args) {
        return switch(operation){
            case ADDITION -> new AdditionOperation(Operation.ADDITION, args);
            case SUBSTRACTION -> new SubstractionOperation(Operation.SUBSTRACTION, args);
            case MULTIPLICATION -> new MultiplicationOperation(Operation.MULTIPLICATION, args);
            case DIVISION -> new DivisionOperation(Operation.DIVISION, args);
        };
    }
}
