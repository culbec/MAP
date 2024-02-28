using Sem11.Domain;

namespace Sem11.Repository;

public delegate TE LineToEntity<TE>(string line);

public delegate string EntityToLine<TE>(TE te);

public abstract class FileRepository<TId, TE> : InMemoryRepository<TId, TE> where TE : Entity<TId>
{
    private readonly string _fileName;
    private readonly LineToEntity<TE> _lineToEntity;
    private readonly EntityToLine<TE> _entityToLine;

    public FileRepository(string fileName, LineToEntity<TE> lineToEntity, EntityToLine<TE> entityToLine)
    {
        _fileName = fileName;
        _lineToEntity = lineToEntity;
        _entityToLine = entityToLine;
        ReadAll();
    }

    private void ReadAll()
    {
        using StreamReader streamReader = new StreamReader(_fileName);
        string? line;

        while ((line = streamReader.ReadLine()) != null)
        {
            TE entity = _lineToEntity(line);
            Save(entity);
        }
    }

    private void WriteAll()
    {
        using StreamWriter streamWriter = new StreamWriter(_fileName);

        foreach (var entity in FindAll())
        {
            streamWriter.WriteLine(_entityToLine(entity));
        }
    }



    public override TE? Save(TE te)
    {
        var saved = base.Save(te);

        if (saved == null)
        {
            WriteAll();
        }

        return saved;
    }

    public override TE? Update(TE te)
    {
        var updated = base.Update(te);

        if (updated == null)
        {
            WriteAll();
        }

        return updated;
    }

    public override TE? Delete(TId tid)
    {
        var deleted = base.Delete(tid);

        if (deleted == null)
        {
            WriteAll();
        }

        return deleted;
    }
}