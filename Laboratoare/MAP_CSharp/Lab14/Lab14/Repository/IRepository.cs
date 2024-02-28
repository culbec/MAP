using Lab14.Domain;

namespace Lab14.Repository;

public interface IRepository<TId, out TE> where TE : Entity<TId>
{
    public IEnumerable<TE> FindAll();
}