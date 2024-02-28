public interface TaskRunner {
    void executeOneTask(); // executa un task din colectia de task-uri de executat
    void executeAll(); // executa toate task-urile

    void addTask(Task t); // adauga un task

    boolean hasTask(); // verifica daca mai exista task-uri de executat

}
