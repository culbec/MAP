public class Patrat extends Shape {
    double latura;

    Patrat(double latura) {
        this.latura = latura;
    }

    @Override
    public double area() {
        return this.latura * this.latura;
    }
}
