using Sem11.Domain;

namespace Sem11.Delegates;

public class EntityFileMapping
{
    public static Employee LineToEmployee(string line)
    {
        String[] fields = line.Split(',');

        Employee employee = new Employee
        {
            Id = fields[0],
            Name = fields[1],
            Wage = Double.Parse(fields[2]),
            Level = (KnowledgeLevel)Enum.Parse(typeof(KnowledgeLevel), fields[3])
        };

        return employee;
    }

    public static string EmployeeToLine(Employee employee)
    {
        return employee.Id + "," + employee.Name + "," + employee.Wage + "," + employee.Level;
    }

    public static Domain.Task LineToTask(string line)
    {
        String[] fields = line.Split(',');

        Domain.Task task = new Domain.Task
        {
            Id = fields[0],
            TaskComplexity = (Complexity)Enum.Parse(typeof(Complexity), fields[1]),
            EstimatedHours = int.Parse(fields[2])
        };

        return task;
    }

    public static string TaskToLine(Domain.Task task)
    {
        return task.Id + "," + task.EstimatedHours + "," + task.TaskComplexity;
    }

    public static TaskAssignment LineToTaskAssignment(string line)
    {
        String[] fields = line.Split(',');

        TaskAssignment taskAssignment = new TaskAssignment
        {
            Id = new Tuple<string, string>(fields[0], fields[1]),
            AssignmentDate =
                DateTime.ParseExact(fields[2], "d/M/yyyy", System.Globalization.CultureInfo.InvariantCulture)
        };

        return taskAssignment;
    }

    public static string TaskAssignmentToLine(TaskAssignment taskAssignment)
    {
        return taskAssignment.Id.Item1 + " " + taskAssignment.Id.Item2 + " " + taskAssignment.AssignmentDate;
    }
}