using Lab14.Domain;
using Lab14.Utilities;

namespace Lab14.Repository;

public class FacturaFileRepository()
    : FileRepository<string, Factura>(Constants.FacturiFile, EntityFileMapping.LineToFactura)
{
    public void ReadSubEntities(IEnumerable<Achizitie> achizitii)
    {
        foreach (var achizitie in achizitii)
        {
            var factura =
                from f in FindAll()
                where Equals(achizitie.Factura.Id, f.Id)
                select f;
            var facturi = factura.ToList();
            if (facturi.Count != 0)
            {
                facturi.First().Achizitii.Add(achizitie);
            }
        }
    }
}