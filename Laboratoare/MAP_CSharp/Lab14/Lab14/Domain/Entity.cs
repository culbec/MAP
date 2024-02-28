namespace Lab14.Domain;

public class Entity<TId>
{
    public TId Id { get; init; } = default!;
}