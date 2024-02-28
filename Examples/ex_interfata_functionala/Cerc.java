public class Cerc extends Shape {
    private double raza;

    Cerc(double raza) {
        this.raza = raza;
    }

    @Override
    public double area() {
        return this.raza * this.raza * Math.PI;
    }
}
