using Seminar11MAP.Domain;

namespace Seminar11MAP.Repository;

public class EmployeeFileRepo:FileRepository<string,Employee>
{
    public EmployeeFileRepo(string filename) : base(filename)
    {
    }

    protected override Employee EntityFromLine(string line)
    {
        string[] field = line.Split(',');
        Employee employee=new Employee
        {
            Id = field[0],
            Name = field[1],
            Wage = Double.Parse(field[2]),
            Level = (KnowledgeLevel) Enum.Parse(typeof(KnowledgeLevel),field[3])
        };
        return employee;
    }

    protected override string LineFromEntity(Employee entity)
    {
        return entity.Id + "," + entity.Name + "," + entity.Wage + "," + entity.Level;
    }
}