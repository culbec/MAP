using System.Collections.ObjectModel;
using Seminar11MAP.Domain;

namespace Seminar11MAP.Repository;

public interface IRepository<TId, TE> where TE : Entity<TId>
{
    TE? FindOne(TId id);
    ICollection<TE> FindAll(); 
    TE? Save(TE entity);
    TE? Update(TE entity);
    TE? Delete(TId id);
    
}