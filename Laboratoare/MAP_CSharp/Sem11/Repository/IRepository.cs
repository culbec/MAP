namespace Sem11.Repository;

public interface IRepository<TId, TE>
{
    TE? FindOne(TId tid);
    ICollection<TE> FindAll();
    TE? Save(TE te);
    TE? Update(TE te);
    TE? Delete(TId tid);
}