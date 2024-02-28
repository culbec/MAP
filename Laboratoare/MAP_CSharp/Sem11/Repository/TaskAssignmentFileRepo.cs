using Sem11.Delegates;
using Sem11.Domain;

namespace Sem11.Repository;

public class TaskAssignmentFileRepo() : FileRepository<Tuple<string, string>, TaskAssignment>(
    Constants.TaskAssignmentsFilename,
    EntityFileMapping.LineToTaskAssignment, EntityFileMapping.TaskAssignmentToLine)
{
    private void FindSubEntities(TaskAssignment taskAssignment)
    {
        Employee? employee = FileUtil.FindWithId(Constants.EmployeeFilename, taskAssignment.Id.Item1,
            EntityFileMapping.LineToEmployee);
        Domain.Task? task =
            FileUtil.FindWithId(Constants.TaskFilename, taskAssignment.Id.Item2, EntityFileMapping.LineToTask);

        if (employee == null || task == null) return;

        taskAssignment.Employee = employee;
        taskAssignment.Task = task;
    }

    public void initTaskAssignmentFileRepo()
    {
        /*foreach (var taskAssignment in FindAll())
        {
            FindSubEntities(taskAssignment);
        }*/

        var employees =
            FileUtil.FindEntities(Constants.EmployeeFilename, EntityFileMapping.LineToEmployee!)
                .Where(employee => FindAll().Select(assignment => assignment.Id.Item1).Contains(employee.Id));
        var tasks =
            FileUtil.FindEntities(Constants.TaskFilename, EntityFileMapping.LineToTask!)
                .Where(task => FindAll().Select(assignment => assignment.Id.Item2).Contains(task.Id));

        foreach (var taskAssignment in FindAll())
        {
            taskAssignment.Employee = employees.First(employee => employee.Id.Equals(taskAssignment.Id.Item1));
            taskAssignment.Task = tasks.First(task => task.Id.Equals(taskAssignment.Id.Item2));
        }
    }
}