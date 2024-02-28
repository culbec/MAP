namespace Lab14.Domain;

public class Achizitie : Entity<string>
{
    public string? Produs { get; init; }
    public int Cantitate { get; init; }
    public double PretProdus { get; init; }
    public Factura? Factura { get; init; }

    private bool Equals(Achizitie other)
    {
        return Produs! == other.Produs && Cantitate == other.Cantitate && PretProdus.Equals(other.PretProdus) &&
               Factura!.Equals(other.Factura);
    }

    public override bool Equals(object? obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        return obj.GetType() == GetType() && Equals((Achizitie)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(Produs, Cantitate, PretProdus, Factura);
    }

    public override string ToString()
    {
        return
            $"{nameof(Produs)}: {Produs}, {nameof(Cantitate)}: {Cantitate}, {nameof(PretProdus)}: {PretProdus}, {nameof(Factura)}: {Factura}";
    }
}