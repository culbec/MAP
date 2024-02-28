// See https://aka.ms/new-console-template for more information

using Seminar11MAP.Domain;
using Seminar11MAP.Repository;

namespace Seminar11MAP
{
    class Program
    {
        public static void Main(string[] args)
        {
            EmployeeFileRepo repo = new EmployeeFileRepo("Data/Employees.txt");
            foreach (var emp in repo.FindAll())
            {
                System.Console.WriteLine(emp.ToString());
            }
        }
    }
}

