public class DivisionOperation extends ComplexExpression {
    public DivisionOperation(Operation _operation, ComplexNumber[] _args) {
        super(_operation, _args);
    }

    @Override
    public ComplexNumber executeOneOperation(ComplexNumber op1, ComplexNumber op2) {
        return op1.div(op2);
    }
}
