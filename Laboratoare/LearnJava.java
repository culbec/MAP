/**
	JavaDoc comments (documentation)

	@param
	@return
	...	and others
*/

// importing ArrayList from the package 'java.util'
import java.util.ArrayList;

// importing the whole 'java.security' package
import java.security.*;

// for reading input, separate object
import java.util.Scanner;

public class LearnJava {
	/*
		Every java program, to run, must have a main method as an entry point.
		The signature is as follows.
	*/

	public static void main(String[] args) {
		// System.out.* -> for output commands
		System.out.println("Hello, World!");
		System.out.println(
			"Integer: " + 10 +
			" Double: " + 3.14 +
			" Boolean: " + true);

		System.out.print("Hello"); System.out.print("World!\n");

		// printf for formatted strings
		System.out.printf("pi = %.5f", Math.PI);

		// Scanner -> for reading input
		Scanner scanner = new Scanner(System.in);

		// String input
		//String name = scanner.next();

		// byte input
		//byte numByte = scanner.nextByte();

		// integer input
		//int numInt = scanner.nextInt();

		// and so on

		// variable declaration
		int fooInt;
		int foo1, foo2, foo3;

		// variable initialization
		int bar1 = 1;

		// supports multiple initialization, but the variables need to be the same type
		int bar2, bar3, bar4;
		bar2 = bar3 = bar4 = 2;

		// final keyword => constants
		// cannot be reassigned, but can be initialized later
		final int HOURS_PER_DAY = 24;
		// HOURS_PER_DAY = 25 -> error
		final int E;
		E = 2; // permitted

		// BigInteger -> integers longer than 64 bytes
		// stored as an array of bytes or a string
		// separate object

		// String builders
		// method 1) with '+' <=> creates intermediate strings
		String plusConcatenated = "This is a string" + " concatenated " + " with '+'.";

		// method 2) with StringBuilder object
		// no intermediate strings, ties the strings together
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("You ");
		stringBuilder.append("mere!");
		System.out.println(stringBuilder.toString());


		// method 3) with string format
		String str = String.format("%s may have %s", "Ana", "mere");
		System.out.println(str);

		// Primitive arrays (vectors)
		// <data_type>[] <name> = new <data_type>[<size>]
		int[] intArray = new int[100];
		String[] stringArray = new String[1000];
		boolean[] boolArray = new boolean[10];

		int[] y = {9000, 1000, 1337};
		String[] names = {"Bob", "Alice", "John"};
		boolean bools[] = {true, true, false, true}; // C-style declaration

		// behaves like C arrays

		// Other data types that store values
		// SDA types -> ArrayList, LinkedList, Map, HashMap, TreeMap...

		// same operators

		/* 	for loops 
			Classic (by indexing): for(int i = 0; i < size; i++)
			For-each: for(<data_type> <object> : <iterable>)
		*/

		// try, catch -> exception/error handling
		// ternary operator
		/*
			switch

			can return values starting with Java 21

			example: return (switch(month){
				case FEBRUARY -> 1;
				case JANUARY -> 2;
				...
				default -> null;
			})
		*/

		// converting data
		// built-in methods for each data types
		Integer.parseInt("123"); // integer version of...
		Integer.toString(123); // string version of...
	}
}

/*
		Classes

	Signature: <visibility> <abstract> <final> <return_type> <name>(<params>);
	Visibility: public (everyone can use it)
				private (only the owner can use it)
				protected (only the owner and inherited classes can use it)
	Abstract: abstraction of a class for polymorphism and inheritance
	Final: non-changeable behaviour, constant, impossible to inherit
*/ 

/*
	Other, non-public outer-level classes can be included in a .java file
	but it isn't good practice and problematic for the compiler.
	So, we can instead split the public classes into separate files.
*/

class Bicycle {
	// Fields, attributes, variables
	public int cadence; // can be accessed from anywhere
	private int speed; // can be accessed by the owner
	protected int gear; // can be accessed by the owner and subclasses

	static String className; // independent from the class instance (object), can be use by the name of the class and is specific to the class, not an instance of it

	// static block -> way of initializing static fields when the class is loaded
	static {
		className = "Bicycle";
	}

	// Constructors -> way of creating objects (instantiating a class)
	public Bicycle() {
		gear = 1;
	}

	// We can have multiple constructors
	public Bicycle(int startCadence, int startSpeed, int startGear) {
		this.gear = startGear;
		this.cadence = startCadence;
		this.speed = startSpeed;
	}

	// Methods -> trivial

	// @Override -> overriding the behaviour of method in a superclass
	// Every object in Java is an instance of the superclass Object
	@Override
	public String toString() {
		return "gear: " + gear + " cadence: " + cadence + " speed: " + speed;
	}
}

// Inheritance: by the extends keyword
// A class can inherit only a superclass (design choice due to the complexity of multiple inheritance)
// C++ allows such behaviour (programmer should handle the complexity if she/him needs such behaviour)

class SmallBicycle extends Bicycle {
	public SmallBicycle(int startCadence, int startSpeed) {
		// 'super' keyword determines the usage of the superclass in the subclass
		// example: constructor
		super(startCadence, startSpeed, 0);
	}
}

// Object casting (Liskov substitution principle)
// Superclass objects can be replaced by subclass objects without breaking the app
// So, we can declare a superclass object by being an instance of subclass

// example: Bicycle bicycle = new SmallBicycle();

// Interfaces: abstract classes in which every method is by default PUBLIC and ABSTRACT
// Advantage: a class can 'inherit' (implement) multiple interfaces

public interface Edible {
	void eat(); // by default public and abstract
}

public interface Digestable {
	void digest();

	// default methods in interfaces
	// since all methods are abstract, they can not have an implementation
	// but since Java 8, they can have a default behavior

	default void defaultMethod() {
		System.out.println("Something default.");
	}
}

// multiple implementing
public class Fruit implements Edible, Digestable {
	@Override
	public void eat() {
		// ...
	}

	@Override
	public void digest() {
		// ...
	}

	// default methods can be overwritten
	@Override
	public void defaultMethod() {
		// ...
	}

}

/*
	Abstract Classes

	- cannot be instantiated;
	- must have at least one abstract method in them;
	- used for polymorphism;
	- each class that inherits from the abstract class must implement the abstract method(s), otherwise those classes are also abstract;
	- violates 'Composition over inheritance' when using trying to use classes with the same logic but customizable behavior;
*/

public abstract class Animal {
	private int age;

	public abstract void makeSound(); // the abstract method

	public void printAge() {
		System.out.println(this.age);
	}

	// Abstract classes can have a main method
	public static void(String[] args) {
		System.out.println("I am a abstract class!");
	}
}

public class Dog extends Animal {
	// must be implemented to allow being instantiated
	@Override
	public void makeSound() {
		System.out.println("Bark!");
	}
}

/*
	Final classes
	- opposite from the abstract classes in such manner that they cannot be inherited from;
	- so, they are a final child and their implementation is non-extendable

	Final methods
	- almost the same as final classes, but they cannot be overriden;
*/

/*
	Enum Type
	- collection of predefined constants;
	- by convention, the contents of an enum must be uppercase words, because they are constants;
*/

public enum Week {
	MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY
}

/*
	Lambda expressions
	- typical in functional programming languages;
	- almost like C++;
	- in C++ they were like this:
		[captured_variables](parameters){body}
	- in Java:
		(zero or more variables) -> <expression body or statement block>
*/