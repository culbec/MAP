using Lab14.Domain;
using Lab14.Utilities;

namespace Lab14.Repository;

public class AchizitieFileRepository()
    : FileRepository<string, Achizitie>(Constants.AchizitiiFile, EntityFileMapping.LineToAchizitie);