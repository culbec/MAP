import java.text.DecimalFormat;

public class ComplexNumber {
    public double re, img;

    public ComplexNumber(double _re, double _img) {
        this.re = _re;
        this.img = _img;
    }

    public ComplexNumber(String number) {
        if (isValidNumber(number)) {
            String[] numberArr = number.split("\\+", 2);
            double real = Double.parseDouble(numberArr[0]);
            double imaginary = Double.parseDouble(numberArr[1].split("\\*", 2)[0]);

            this.re = real;
            this.img = imaginary;
        }
    }

    public static boolean isValidNumber(String number) {
        String[] numberArr = number.split("\\+", 2);
        try {
            double real = Double.parseDouble(numberArr[0]);
            double imaginary = Double.parseDouble(numberArr[1].split("\\*", 2)[0]);
            return true;
        } catch (NumberFormatException nFE) {
            return false;
        }
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.re + other.re, this.img + other.img);
    }

    public ComplexNumber sub(ComplexNumber other) {
        return new ComplexNumber(this.re - other.re, this.img - other.img);
    }

    public ComplexNumber mul(ComplexNumber other) {
        // (a+ib)*(c+id) = a*c - b*d + i(ad +bc)
        double newRe = this.re * other.re - this.img * other.img;
        double newImg = this.re * other.img + this.img * other.re;
        return new ComplexNumber(newRe, newImg);
    }

    public ComplexNumber div(ComplexNumber other) {
        // (a+ib) / (c+id) = (a + ib) * (c - id) / (c^2 + d^2)
        double denominator = other.re * other.re + other.img * other.img;
        ComplexNumber partialResult = this.mul(other.conjugate());
        return new ComplexNumber(partialResult.re / denominator, partialResult.img / denominator);
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(this.re, -this.img);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        return df.format(this.re) + "+" + df.format(this.img) + "*i";
    }
}
