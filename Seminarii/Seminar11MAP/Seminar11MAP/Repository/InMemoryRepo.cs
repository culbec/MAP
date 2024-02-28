using System.Collections.ObjectModel;
using Seminar11MAP.Domain;

namespace Seminar11MAP.Repository;

public class InMemoryRepo<TId,TE>:IRepository<TId,TE> where TE:Entity<TId>
{
    private Dictionary<TId, TE> _dictionary = new();
    public TE? FindOne(TId id)
    {
        return _dictionary.GetValueOrDefault(id);
    }

    public ICollection<TE> FindAll()
    {
        return _dictionary.Values.ToList();
    }

    public virtual TE? Save(TE entity)
    {
        ArgumentNullException.ThrowIfNull(entity);
        return _dictionary.TryAdd(entity.Id, entity) ? entity : null;
    }

    public virtual TE? Update(TE entity)
    {
        ArgumentNullException.ThrowIfNull(entity);
        if (!_dictionary.ContainsKey(entity.Id))
            return null;
        _dictionary[entity.Id]=entity;
        return entity;
    }

    public virtual TE? Delete(TId id)
    {
        _dictionary.Remove(id, out TE? entity);
        return  entity;
    }
}

