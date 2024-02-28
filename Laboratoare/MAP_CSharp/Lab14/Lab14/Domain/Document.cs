namespace Lab14.Domain;

public class Document : Entity<string>
{
    public string? Nume { get; init; }
    public DateTime DataEmitere { get; init; }

    protected bool Equals(Document other)
    {
        return Nume == other.Nume && DataEmitere.Equals(other.DataEmitere);
    }

    public override bool Equals(object? obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        return obj.GetType() == GetType() && Equals((Document)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(Nume, DataEmitere);
    }

    public override string ToString()
    {
        return $"{nameof(Nume)}: {Nume}, {nameof(DataEmitere)}: {DataEmitere}";
    }
}