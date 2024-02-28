using System.Xml.Serialization;
using Seminar11MAP.Domain;

namespace Seminar11MAP.Repository;

public abstract class FileRepository<TId,TE>:InMemoryRepo<TId,TE> where TE:Entity<TId>
{
    private string _filename;

    public FileRepository(string filename)
    {
        _filename = filename;
        ReadAll();
    }

    private void ReadAll()
    {
        using (StreamReader r = new StreamReader(_filename))
        {
            string line;
            while ((line = r.ReadLine()) != null)
            {
                TE entity = EntityFromLine(line);
                Save(entity);
            }
        }
    }

    private void WriteAll()
    {
        using (StreamWriter w = new StreamWriter(_filename))
        {
            foreach (var entity in FindAll())
            {
                w.WriteLine(LineFromEntity(entity));
            }
        }
    }

    public override TE? Save(TE entity)
    {
        var rez = base.Save(entity);
        if (rez!=null)
            WriteAll();
        return rez;
    }

    public override TE? Delete(TId id)
    {
        var rez = base.Delete(id);
        if (rez!=null)
            WriteAll();
        return rez;
    }

    public override TE? Update(TE entity)
    {
        var rez = base.Update(entity);
        if (rez!=null)
            WriteAll();
        return rez;
    }

    protected abstract TE EntityFromLine(string line);
    protected abstract string LineFromEntity(TE entity);
}