using Lab14.Utilities;

namespace Lab14.Domain;

public class Factura : Document
{
    public DateTime DataScadenta { get; init; }
    public List<Achizitie> Achizitii { get; } = [];
    public Categorie Categorie { get; init; }


    private bool Equals(Factura other)
    {
        return base.Equals(other) && DataScadenta.Equals(other.DataScadenta) && Achizitii.Equals(other.Achizitii) && Categorie == other.Categorie;
    }

    public override bool Equals(object? obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        return obj.GetType() == GetType() && Equals((Factura)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(base.GetHashCode(), DataScadenta, Achizitii, (int)Categorie);
    }


}