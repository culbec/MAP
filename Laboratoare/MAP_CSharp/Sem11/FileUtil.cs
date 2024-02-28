using Sem11.Domain;

namespace Sem11;

public class FileUtil
{
    public delegate TE LineToEntity<TE>(string? line);

    public delegate string EntityToLine<TE>(TE te);

    public static TE? FindWithId<TId, TE>(string filename, TId id, LineToEntity<TE> lineToEntity) where TE : Entity<TId>
    {
        using var reader = new StreamReader(filename);
        string? line;
        while ((line = reader.ReadLine()) != null)
        {
            var entity = lineToEntity(line);
            if (entity.Id != null && entity.Id.Equals(id))
            {
                return entity;
            }
        }

        return null;
    }

    public static List<TE> FindEntities<TE>(string filename, LineToEntity<TE> lineToEntity)
    {
        List<TE> entities = new List<TE>();
        using var reader = new StreamReader(filename);
        string? line;
        while ((line = reader.ReadLine()) != null)
        {
            var entity = lineToEntity(line);
            entities.Add(entity);
        }

        return entities;
    }
}