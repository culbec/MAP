using Lab14.Domain;
using Lab14.Utilities;

namespace Lab14.Repository;

public class DocumentFileRepository()
    : FileRepository<string, Document>(Constants.DocumentFile, EntityFileMapping.LineToDocument);