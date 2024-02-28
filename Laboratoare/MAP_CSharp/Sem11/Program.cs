// See https://aka.ms/new-console-template for more information

using Sem11.Domain;
using Sem11.Repository;

namespace Sem11
{
    class Program
    {
        public static void Main(string[] args)
        {
            EmployeeFileRepo employeeFileRepo = new EmployeeFileRepo();
            TaskFileRepo taskFileRepo = new TaskFileRepo();

            TaskAssignmentFileRepo taskAssignmentFileRepo = new TaskAssignmentFileRepo();
            taskAssignmentFileRepo.initTaskAssignmentFileRepo();

            foreach (var employee in employeeFileRepo.FindAll())
            {
                Console.WriteLine(employee);
            }
            foreach (var task in taskFileRepo.FindAll())
            {
                Console.WriteLine(task);
            }
            foreach (var taskAssignment in taskAssignmentFileRepo.FindAll())
            {
                Console.WriteLine(taskAssignment);
            }
        }
    }
}