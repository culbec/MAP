package ex2;


import ex2.domain.Assignment;
import ex2.domain.Grade;
import ex2.domain.Student;
import ex2.dto.GradeDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Ex2 {
    private static List<Student> getStudents() {
        Student s1 = new Student("andrei", 221);
        s1.setId(2l);
        Student s2 = new Student("dan", 222);
        s2.setId(2l);
        Student s3 = new Student("gigi", 221);
        s3.setId(2l);
        Student s4 = new Student("costel", 222);
        s4.setId(2l);
        return Arrays.asList(s1, s2, s3, s4);
    }

    private static List<Assignment> getAssignments() {
        return Arrays.asList(
                new Assignment("a1", "desc1"),
                new Assignment("a2", "desc2"),
                new Assignment("a3", "desc3"),
                new Assignment("a4", "desc4")
        );
    }

    private static List<Grade> getGrades(List<Student> stud, List<Assignment> assignments) {
        return Arrays.asList(
                new Grade(stud.get(0), assignments.get(0), 10d, LocalDate.of(2022, 11, 2), "teacher1"),
                new Grade(stud.get(1), assignments.get(0), 9d, LocalDate.of(2022, 11, 2).minusWeeks(1), "teacher1"),
                new Grade(stud.get(1), assignments.get(1), 10d, LocalDate.of(2022, 10, 20), "teacher2"),
                new Grade(stud.get(1), assignments.get(2), 10d, LocalDate.of(2022, 10, 20), "teacher2"),
                new Grade(stud.get(2), assignments.get(1), 7d, LocalDate.of(2022, 10, 28), "teacher1"),
                new Grade(stud.get(2), assignments.get(3), 9d, LocalDate.of(2022, 10, 27), "teacher2"),
                new Grade(stud.get(1), assignments.get(3), 10d, LocalDate.of(2022, 10, 29), "teacher2")
        );
    }

    public static void main(String[] args) {
        List<Grade> grades = getGrades(getStudents(), getAssignments());
        // print the list of GradeDTOs given by teacher "teacher1" to group "221"
        filterGrades(grades, "teacher1", 221);
        // print the final grade for each student
        computeFinalGradeForEachStudent(grades);
    }

    private static void filterGrades(List<Grade> grades, String teacher, int group) {
        Predicate<Grade> byTeacher = grade -> grade.getTeacher().equals(teacher);
        Predicate<Grade> byGroup = grade -> grade.getStudent().getGroup() == group;

        // sau inlantuim 2 filtre
        grades.stream()
                .filter(byTeacher.and(byGroup))
                .map(grade -> new GradeDTO(grade.getStudent().getName(), grade.getAssignment().getId(), grade.getValue(), grade.getTeacher()))
                .forEach(System.out::println);
    }

    private static void computeFinalGradeForEachStudent(List<Grade> grades) {
        Map<Student, List<Grade>> studentGrades;
        studentGrades = grades.stream().collect(Collectors.groupingBy(Grade::getStudent));

        Map<Double, List<Grade>> gradeStudent = grades.stream().collect(Collectors.groupingBy(Grade::getValue));

        studentGrades.entrySet().forEach(entry -> {
            Optional<Double> gradeSum = entry.getValue().stream()
                                                            .map(Grade::getValue)
                                                            .reduce(Double::sum);
            if (gradeSum.isPresent()) {
                System.out.println("Final grade for " + entry.getKey().getName() + ": " + gradeSum.get() / entry.getValue().size());
            }
        });

        studentGrades.forEach((student, gradeList) -> {
            Optional<Grade> grade = gradeList.stream().reduce((grade1, grade2) -> grade1.setValue(grade1.getValue() + grade2.getValue()));
        });
    }
}
