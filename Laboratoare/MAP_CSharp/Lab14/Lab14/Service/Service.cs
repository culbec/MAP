using Lab14.Domain;
using Lab14.Utilities;
using Lab14.Repository;

namespace Lab14.Service;

public class Service(
    IRepository<string, Document> documentRepository,
    IRepository<string, Factura> facturaRepository,
    IRepository<string, Achizitie> achizitieRepository)
{
    private IRepository<string, Document> DocumentRepository { get; } = documentRepository;
    private IRepository<string, Factura> FacturaRepository { get; } = facturaRepository;
    private IRepository<string, Achizitie> AchizitieRepository { get; } = achizitieRepository;

    public IEnumerable<Document> DocumenteEmise2023()
    {
        var documents = new List<Document>();
        foreach (var document in DocumentRepository.FindAll())
        {
            if (document.DataEmitere.Year == 2023)
            {
                documents.Add(document);
            }
        }

        /*var documents =
            from document in DocumentRepository.FindAll()
            where document.DataEmitere.Year == 2023
            select document;*/

        return documents;
    }

    public IEnumerable<Factura> FacturiScadenteLunaCurenta()
    {
        var facturi =
            from factura in FacturaRepository.FindAll()
            where factura.DataScadenta.Month == DateTime.Now.Month && factura.DataScadenta.Year == DateTime.Now.Year
            select factura;

        return facturi;
    }

    public IEnumerable<Factura> FacturiCelPutin3()
    {
        var facturi = FacturaRepository.FindAll()
            .Where(factura => factura.Achizitii.Select(achizitie => achizitie.Cantitate).Sum() >= 3);
        return facturi;
    }

    public IEnumerable<Achizitie> AchizitiiUtilities()
    {
        var achizitii =
            from achizitie in AchizitieRepository.FindAll()
            where achizitie.Factura.Categorie == Categorie.Utilities
            select achizitie;
        return achizitii;
    }

    public Categorie CategorieCeleMaiMulteCheltuieli()
    {
        var categorie = FacturaRepository.FindAll()
            .GroupBy(factura => factura.Categorie)
            .Select(grouping => new
            {
                Categorie = grouping.Key,
                TotalExpenses = grouping.Sum(factura =>
                    factura.Achizitii.Sum(achizitie => achizitie.Cantitate * achizitie.PretProdus))
            })
            .OrderByDescending(item => item.TotalExpenses)
            .First().Categorie;

        return categorie;
    }
}