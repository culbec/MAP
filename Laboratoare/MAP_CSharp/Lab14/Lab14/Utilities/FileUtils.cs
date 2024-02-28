using Lab14.Domain;

namespace Lab14.Utilities;

public static class FileUtils
{
    public delegate TE LineToEntity<out TE>(string line);

    public static TE? FindWithId<TId, TE>(string fileName, TId tid, LineToEntity<TE> lineToEntity)
        where TE : Entity<TId>
    {
        using var reader = new StreamReader(fileName);
        string? line;

        while ((line = reader.ReadLine()) != null)
        {
            var entity = lineToEntity(line.Trim());
            if (entity.Id != null && entity.Id.Equals(tid))
            {
                return entity;
            }
        }

        return null;
    }
}