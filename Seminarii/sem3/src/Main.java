import DataStructures.MyMap;
import Entities.Student;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Dan", 4.5f);
        Student s2 = new Student("Ana", 8.5f);
        Student s3 = new Student("Dan", 4.5f);

        System.out.println("Using HashSet.");
        HashSet<Student> studentHashSet = new HashSet<>();
        studentHashSet.add(s1);
        studentHashSet.add(s2);
        studentHashSet.add(s3);
        for (Student student : studentHashSet) {
            System.out.println(student);
        }

        System.out.println("\nUsing TreeSet.");
        TreeSet<Student> studentTreeSet = new TreeSet<>((o1, o2) -> o1.getName().compareTo(o2.getName()));
        studentTreeSet.add(s1);
        studentTreeSet.add(s2);
        studentTreeSet.add(s3);
        for (Student student : studentTreeSet) {
            System.out.println(student);
        }

        System.out.println("\nUsing HashMap.");
        HashMap<String, Student> studentHashMap = new HashMap<>();
        studentHashMap.put(s1.getName(), s1);
        studentHashMap.put(s2.getName(), s2);
        studentHashMap.put(s3.getName(), s3);
        System.out.println("\nIterating through values.");
        for (Student student : studentHashMap.values()) {
            System.out.println(student);
        }
        System.out.println("\nIterating through keys.");
        for (String studentName : studentHashMap.keySet()) {
            System.out.println(studentName);
        }
        System.out.println("\nIterating through entries.");
        for (HashMap.Entry<String, Student> entry : studentHashMap.entrySet()) {
            System.out.println(entry);
        }

        System.out.println("\nUsing TreeMap.");
        TreeMap<String, Student> studentTreeMap = new TreeMap<>(String::compareTo);
        studentTreeMap.put(s1.getName(), s1);
        studentTreeMap.put(s2.getName(), s2);
        studentTreeMap.put(s3.getName(), s3);
        for (Map.Entry<String, Student> entry : studentTreeMap.entrySet()) {
            System.out.println(entry);
        }

        System.out.println("\nUsing custom map.");
        MyMap myMap = new MyMap();

        myMap.add(s1);
        myMap.add(s2);
        myMap.add(s3);
        for (Map.Entry<Integer, List<Student>> entry : myMap.getEntries()) {
            System.out.println(entry);
        }
        System.out.println("\nSorted lists.");
        for(Map.Entry<Integer, List<Student>> entry: myMap.getEntries()) {
            Collections.sort(entry.getValue());
            System.out.println(entry);
        }

    }
}
