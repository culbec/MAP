public class Student
{
    // Cum mai exact putem testa metoda de mai jos?
    // Avem nevoie de un mod de a abstractiza modul de operare al metodei pentru a fii testabila.

    public void CumMaSimt()
    {
        // Abstractizare?
        var device = new Phone();
        var message = device.GetMessage();
        Console.WriteLine(message);
    }
}