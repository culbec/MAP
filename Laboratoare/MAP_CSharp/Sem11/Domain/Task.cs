namespace Sem11.Domain;

public enum Complexity
{
    Low,
    Medium,
    High
}

public class Task : Entity<string>
{
    public int EstimatedHours { get; set; }
    public Complexity TaskComplexity { get; set; }

    public override string ToString()
    {
        return Id + " " + TaskComplexity + " " + EstimatedHours;
    }
}