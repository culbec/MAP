using System.Collections.ObjectModel;
using Sem11.Domain;

namespace Sem11.Repository;

public class InMemoryRepository<TId, TE> : IRepository<TId, TE> where TE:Entity<TId>
{
    private Dictionary<TId, TE> _dictionary = new();

    public TE? FindOne(TId tid)
    {
        return _dictionary.GetValueOrDefault(tid);
    }

    public ICollection<TE> FindAll()
    {
        return _dictionary.Values.ToList();
    }

    public virtual TE? Save(TE te)
    {
        ArgumentNullException.ThrowIfNull(te);

        return _dictionary.TryAdd(te.Id, te) ? te : null;
    }

    public virtual TE? Update(TE te)
    {
        ArgumentNullException.ThrowIfNull(te);

        if (!_dictionary.ContainsKey(te.Id))
        {
            return null;
        }

        _dictionary[te.Id] = te;
        return te;
    }

    public virtual TE? Delete(TId tid)
    {
        ArgumentNullException.ThrowIfNull(tid);

        return _dictionary.Remove(tid, out var te) ? te : null;
    }
}