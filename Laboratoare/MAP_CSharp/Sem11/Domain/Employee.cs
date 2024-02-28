namespace Sem11.Domain;

public enum KnowledgeLevel
{
    Junior,
    Medium,
    Senior
}

public class Employee : Entity<string>
{
    public string Name { get; set; }
    public double Wage { get; set; }
    public KnowledgeLevel Level { get; set; }

    public override string ToString()
    {
        return base.ToString() + " " + Name + " " + Wage + " " + Level;
    }
}