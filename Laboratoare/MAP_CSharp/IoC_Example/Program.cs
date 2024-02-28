// See https://aka.ms/new-console-template for more information

Student student = new Student();
student.CumMaSimt();

public class Phone
{
    public string GetMessage()
    {
        var month = DateTime.Now.Month;

        if (month < 4)
        {
            return "liber";
        }

        if (month < 9)
        {
            return "ocupat";
        }

        return "panik!";
    }
}