public class Main {
    public static void main(String[] args) {
        CalculatorArea calculatorArea = (shape -> shape.area());
        Shape shape1 = new Cerc(2.0);
        Shape shape2 = new Patrat(5.67);

        System.out.println("Aria pentru cerc: " + calculatorArea.area(shape1));
        System.out.println("Aria pentru patrat: " + calculatorArea.area(shape2));
    }
}
