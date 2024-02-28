package Entities;

import java.util.Objects;

public class Student implements Comparable<Student> {
    private String name;
    private float grade;

    public Student(String name, float grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return this.name;
    }

    public float getGrade() {
        return this.grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + this.name + '\'' +
                ", media=" + this.grade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Float.compare(this.grade, student.grade) == 0 && Objects.equals(this.name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.grade);
    }


    @Override
    public int compareTo(Student o) {
        return Float.compare(o.grade, this.grade);
    }
}
