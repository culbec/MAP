using System.Globalization;
using Lab14.Domain;

namespace Lab14.Utilities;

public static class EntityFileMapping
{
    public static Achizitie LineToAchizitie(string line)
    {
        var fields = line.Split(",");
        var factura = FileUtils.FindWithId(Constants.FacturiFile, fields[4], LineToFactura);
        var achizitie = new Achizitie
        {
            Id = fields[0],
            Produs = fields[1],
            Cantitate = int.Parse(fields[2]),
            PretProdus = double.Parse(fields[3]),
            Factura = factura
        };
        return achizitie;
    }

    public static Document LineToDocument(string line)
    {
        var fields = line.Split(",");
        return new Document
        {
            Id = fields[0],
            Nume = fields[1],
            DataEmitere = DateTime.ParseExact(fields[2], "dd-MM-yyyy", CultureInfo.InvariantCulture)
        };
    }

    public static Factura LineToFactura(string line)
    {
        var fields = line.Split(",");

        var id = fields[0];
        var dataScadenta = DateTime.ParseExact(fields[1], "dd-MM-yyyy", CultureInfo.InvariantCulture);
        var categorie = (Categorie) Enum.Parse(typeof(Categorie), fields[2]);

        var document = FileUtils.FindWithId(Constants.DocumentFile, fields[0], LineToDocument);
        var nume = document!.Nume;
        var dataEmitere = document.DataEmitere;

        var factura = new Factura
        {
            Id = id,
            Nume = nume,
            DataEmitere = dataEmitere,
            DataScadenta = dataScadenta,
            Categorie = categorie
        };
        return factura;
    }
}