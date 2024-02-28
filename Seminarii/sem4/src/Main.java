import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {
    private static <E> void printArea(List<E> l, Area<E> area) {
        for (E e : l) {
            System.out.println(area.compute(e));
        }
    }

    // filtrare numere pare + sortarea descrescatoare
    private static <E> List<E> filterGeneric(List<E> list, Predicate<E> pred, Comparator<E> comp) {
        return list.stream().filter(pred).sorted(comp.reversed() ).collect(Collectors.toList());
    }

    private static void testStream() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 1, 10, 5, 1, -2, 3, 20);
//        integers = filterGeneric(integers, x -> x % 2 == 0, (o1, o2) -> (o2 - o1));
        integers = filterGeneric(integers, x -> x % 2 == 0, Integer::compare);
        System.out.println(integers);
    }

    public static void main(String[] args) {
        Circle circle1 = new Circle(2.0f);
        Circle circle2 = new Circle(4.6f);
        Circle circle3 = new Circle(20.1f);
        Circle circle4 = new Circle(3.5f);

        List<Circle> list = Arrays.asList(circle1, circle2, circle3, circle4);

        Area<Circle> circleAreaAnonymous = new Area<Circle>() {
            @Override
            public Double compute(Circle circle) {
                return Math.PI * circle.getRadius() * circle.getRadius();
            }
        };

        Area<Circle> circleAreaLambda = (circle) -> Math.PI * circle.getRadius() * circle.getRadius();

        /*printArea(list, circleAreaAnonymous);
        printArea(list, circleAreaLambda);*/

        Predicate<Circle> smallCirclePredicate = (circle) -> {
            return circle.getRadius() < 10f;
        };
        Predicate<Circle> largeCirclePredicate = smallCirclePredicate.negate();
        Predicate<Circle> smallEvenCirclePredicate = smallCirclePredicate.and((circle) -> {
            return circle.getRadius() % 2 == 0;
        });

        for (Circle circle : list) {
            if (smallEvenCirclePredicate.test(circle)) {
                System.out.println(circle);
            }
        }

        Function<String, Integer> stringToInteger = Integer::valueOf; // Integer::parseInt
        Function<String, Integer> squaredInteger = stringToInteger.andThen(x -> x * x);
        System.out.println(stringToInteger.apply("123") + stringToInteger.apply("1"));
        System.out.println(squaredInteger.apply("123"));

        Supplier<LocalDateTime> localDateTimeSupplier = LocalDateTime::now;
        System.out.println(localDateTimeSupplier.get());

        Comparator<Integer> integerComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        };
        System.out.println(integerComparator.compare(1, 2));

        List<Integer> integerList = Arrays.asList(1, 5, 2, 1, -1, 3);
        Comparator<Integer> descComparator = (o1, o2) -> (o2 - o1);
        Comparator<Integer> ascComparator = Integer::compare;

        List<Integer> integerList1 = new ArrayList<>();
        integerList.forEach(integerList1::add);

        List<Integer> integerList2 = new ArrayList<>();
        integerList.forEach(integerList2::add);

        integerList1.sort(ascComparator.reversed());
        integerList2.sort(ascComparator);

        System.out.println(integerList1);
        System.out.println(integerList2);

        testStream();
    }
}
