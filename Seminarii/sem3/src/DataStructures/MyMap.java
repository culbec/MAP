package DataStructures;

import Entities.Student;

import java.util.*;

public class MyMap {
    private TreeMap<Integer, List<Student>> treeMap = new TreeMap<>((k1, k2) -> k1.compareTo(k2));

    public void add(Student student) {
        Integer grade = Math.round(student.getGrade());
        if (this.treeMap.get(grade) == null) {
            this.treeMap.put(grade, new ArrayList<>());
        }
        this.treeMap.get(grade).add(student);
    }

    public Set<Map.Entry<Integer, List<Student>>> getEntries() {
        return this.treeMap.entrySet();
    }
}
