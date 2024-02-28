package Tasks;

import java.util.Objects;

public abstract class Task {
    private String taskID;
    private String descriere;

    public Task(String _id, String _descriere) {
        this.taskID = _id;
        this.descriere = _descriere;
    }

    public String getTaskID() {
        return this.taskID;
    }

    public void setTaskID(String newID) {
        this.taskID = newID;
    }

    public String getDescriere() {
        return this.descriere;
    }

    public void setDescriere(String newDescriere) {
        this.descriere = newDescriere;
    }

    public abstract void execute();

    @Override
    public String toString() {
        return "Tasks.Task{" +
                "id" + this.taskID + "\n" +
                "descriere: " + this.descriere + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskID, task.taskID) && Objects.equals(descriere, task.descriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskID, descriere);
    }

    /*
        Daca obj1.equals(obj2) => obj1.hashCode() = obj2.hashCode()
        Daca avem clasele A si B, iar B mosteneste din A, avand metoda equals suprascrisa fata de cea din A,
        atunci:
            A a; B b;
            b = a, dar a = b;

            * b poate fi considerat ca fiind o instanta a unui obiect de tip A, fiind o extindere a acestei clase
            * a nu poate fi considerat ca fiind o instanta a unui obiect ede tip B deoarece nu are functionalitatile pe care B le-a adaugat
     */
}
