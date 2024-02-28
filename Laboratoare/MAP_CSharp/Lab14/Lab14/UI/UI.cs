using System.Globalization;

namespace Lab14.UI;

public class Ui
{
    private readonly Service.Service _service;
    private readonly Dictionary<string, Delegate> _methods;

    public Ui(Service.Service service)
    {
        _service = service;
        _methods = new Dictionary<string, Delegate>
        {
            ["show_cmds"] = ShowCmds,
            ["documente_2023"] = this.DocumenteEmise_2023,
            ["facturi_scadente"] = this.FacturiScadente,
            ["facturi_3"] = this.Facturi_3,
            ["achizitii_utilities"] = this.AchizitiiUtilities,
            ["categoria_max_expense"] = this.CategoriaMaxExpense
        };
    }

    private static void ShowCmds()
    {
        Console.Write("\nCOMENZI\n\n" +
                      "show_cmds - afiseaza lista de comenzi\n" +
                      "documente_2023 - afiseaza toate documentele emise in 2023\n" +
                      "facturi_scadente - afiseaza facturile scadente din luna curenta\n" +
                      "facturi_3 - afiseaza facturile care au cel putin 3 produse achizitionate\n" +
                      "achizitii_utilities - afiseaza toate facturile care au categoria 'Utilities'\n" +
                      "categoria_max_expense - afiseaza categoria cu cele mai multe cheltuieli\n" +
                      "exit - iesire din aplicatie");
    }

    private void DocumenteEmise_2023()
    {
        var documents = _service.DocumenteEmise2023().ToList();
        if (documents.Count == 0)
        {
            Console.WriteLine("\nNu exista documente emise in 2023!");
            return;
        }

        Console.WriteLine("\nDocumente emise in 2023\n");
        foreach (var document in documents)
        {
            Console.WriteLine(document);
        }
    }

    private void FacturiScadente()
    {
        var facturi = _service.FacturiScadenteLunaCurenta().ToList();
        if (facturi.Count == 0)
        {
            Console.WriteLine("\nNu exista facturi scadente in luna curenta!");
            return;
        }

        Console.WriteLine("\nFacturi scadente in luna " + CultureInfo.CurrentCulture.DateTimeFormat.GetMonthName(DateTime.Now.Month) + "\n");
        foreach (var factura in facturi)
        {
            Console.WriteLine(factura);
        }
    }

    private void Facturi_3()
    {
        var facturi = _service.FacturiCelPutin3().ToList();
        if (facturi.Count == 0)
        {
            Console.WriteLine("\nNu exista facturi cu cel putin 3 produse achizitionate!");
            return;
        }

        Console.WriteLine("\nFacturi cu cel putin 3 produse achizitionate\n");
        foreach (var factura in facturi)
        {
            Console.WriteLine(factura);
        }
    }

    private void AchizitiiUtilities()
    {

        var achizitii = _service.AchizitiiUtilities().ToList();
        if (achizitii.Count == 0)
        {
            Console.WriteLine("\nNu exista achizitii din categoria Utilities!");
            return;
        }

        Console.WriteLine("\nAchizitii din categoria Utilities\n");
        foreach (var achizitie in achizitii)
        {
            Console.WriteLine(achizitie);
        }
    }

    private void CategoriaMaxExpense()
    {
        var categorie = _service.CategorieCeleMaiMulteCheltuieli();

        Console.WriteLine("\nCategoria cu cele mai multe cheltuieli: " + categorie);
    }

    public void Run()
    {
        ShowCmds();

        while (true)
        {
            Console.Write("\nAlegeti comanda: ");
            var input = Console.ReadLine();

            if (input?.ToLower() is "exit") {
                Console.WriteLine("Bye!");
                return;
            }

            if (input != null && _methods.TryGetValue(input.ToLower(), out var value)) {
                value.DynamicInvoke();
                continue;
            }

            Console.WriteLine("Comanda invalida!");
        }
    }
}