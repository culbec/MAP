using Sem11.Delegates;
using Sem11.Domain;

namespace Sem11.Repository;

public class EmployeeFileRepo() : FileRepository<string, Employee>(Constants.EmployeeFilename,
    EntityFileMapping.LineToEmployee, EntityFileMapping.EmployeeToLine);