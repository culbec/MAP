namespace Sem11.Domain;

public class TaskAssignment : Entity<Tuple<string, string>>
{
    public Employee Employee { get; set; }
    public Task Task { get; set; }
    public DateTime AssignmentDate { get; set; }

    public override string ToString()
    {
        return Id + " " + Employee + " " + Task + " " + AssignmentDate.ToString("d/M/yyyy");
    }
}