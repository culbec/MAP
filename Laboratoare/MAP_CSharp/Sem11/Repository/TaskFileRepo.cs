using Sem11.Delegates;
using Sem11.Domain;

namespace Sem11.Repository;

public class TaskFileRepo() : FileRepository<string, Domain.Task>(Constants.TaskFilename, EntityFileMapping.LineToTask, EntityFileMapping.TaskToLine)
{
}