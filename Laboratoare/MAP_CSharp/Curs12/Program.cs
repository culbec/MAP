// See https://aka.ms/new-console-template for more information

namespace Curs12;

public class Program
{
    public static void Main(string[] args)
    {
        int[] numbers = { -20, 13, -100, 50, 101, 16, 99 };

        // Numbers which their absolute value >= 50.
        
        // LINQ with methods.
        Console.WriteLine("LINQ with METHODS");
        var linqWithMethods = numbers.Where(number => int.Abs(number) >= 50).OrderBy(x => x);
        foreach (var number in linqWithMethods)
        {
            Console.WriteLine(number);
        }
        
        Console.WriteLine();
        
        // LINQ with SQL style interrogations.
        Console.WriteLine("LINQ with INTERROGATIONS");
        var linqSql = from nr in numbers
            where int.Abs(nr) >= 50
            orderby nr
            select nr;
        foreach (var nr in linqSql)
        {
            Console.WriteLine(nr);
        }
    }
}