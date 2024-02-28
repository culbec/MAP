import java.util.Objects;

public class Equals {
    public static class A {
        protected int a;
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            A a1 = (A) o;
            return a == a1.a;
        }
        @Override
        public int hashCode() {
            return Objects.hash(a);
        }
    }
    public static class B extends A {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            // suprascriem pentru a accepta si clase de tip B si clase de tip A
            if (o == null || (o.getClass() != B.class && o.getClass() != A.class)) return false;
            A a1 = (A) o;
            return a == a1.a;
        }
    }
    public static void main(String[] args) {
        A a = new A();
        A b = new B();
        System.out.println("b's class is " + b.getClass()); // polimorfism
        System.out.println("a.equals(b) is " + a.equals(b)); // se accepta doar clase de tip A in implementare
        System.out.println("b.equals(a) is " + b.equals(a)); // se accepta si clase de tip A si clase de tip B
    }
}