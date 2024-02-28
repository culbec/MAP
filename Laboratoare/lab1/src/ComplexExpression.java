public abstract class ComplexExpression {
    protected ComplexNumber[] args;
    protected Operation operation;

    public ComplexExpression(Operation _operation, ComplexNumber[] _args) {
        this.operation = _operation;
        this.args = _args;
    }

    public ComplexNumber execute() {
        ComplexNumber result = this.args[0];
        for(int i = 1; i < this.args.length; i++) {
            result = this.executeOneOperation(result, this.args[i]);
        }
        return result;
    }

    protected abstract ComplexNumber executeOneOperation(ComplexNumber op1, ComplexNumber op2);
}
