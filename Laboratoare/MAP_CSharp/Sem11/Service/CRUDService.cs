using Sem11.Domain;
using Sem11.Repository;

namespace Sem11.Service;

public class CRUDService<TId, TE> where TE : Entity<TId>
{
    protected IRepository<TId, TE> Repository;

    public CRUDService(IRepository<TId, TE> repository)
    {
        Repository = repository;
    }

    TE? FindOne(TId tid)
    {
        return Repository.FindOne(tid);
    }

    ICollection<TE> FindAll()
    {
        return Repository.FindAll();
    }

    TE? Save(TE te)
    {
        return Repository.Save(te);
    }

    TE? Update(TE te)
    {
        return Repository.Update(te);
    }

    TE? Delete(TId tid)
    {
        return Repository.Delete(tid);
    }
}