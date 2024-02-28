public class AdditionOperation extends ComplexExpression {
    public AdditionOperation(Operation _operation, ComplexNumber[] _args) {
        super(_operation, _args);
    }

    @Override
    public ComplexNumber executeOneOperation(ComplexNumber op1, ComplexNumber op2) {
        return op1.add(op2);
    }
}
