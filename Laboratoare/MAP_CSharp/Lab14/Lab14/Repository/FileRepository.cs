using Lab14.Domain;
using Lab14.Utilities;

namespace Lab14.Repository;

public class FileRepository<TId, TE> : IRepository<TId, TE> where TE : Entity<TId>
{
    private readonly string _fileName;
    private readonly FileUtils.LineToEntity<TE> _lineToEntity;
    private readonly List<TE> _entities = [];

    protected FileRepository(string fileName, FileUtils.LineToEntity<TE> lineToEntity)
    {
        _fileName = fileName;
        _lineToEntity = lineToEntity;
        ReadAll();
    }

    private void ReadAll()
    {
        using var streamReader = new StreamReader(_fileName);


        while (streamReader.ReadLine() is { } line)
        {
            var entity = _lineToEntity(line);
            _entities.Add(entity);
        }
    }

    public IEnumerable<TE> FindAll()
    {
        return _entities;
    }
}