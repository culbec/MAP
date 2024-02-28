internal class Program
{
    // Value types are stored on the stack
    // Reference types are stored on the heap (objects)
    public static void Main()
    {
        int x = 5;
        int y = x;
        y = 7;
        System.Console.WriteLine(x); // 5
        System.Console.WriteLine(y); // 7

        int[] a = new int[1];
        int[] b = a;
        b[0] = 42;
        System.Console.WriteLine(a[0]); // 42
        System.Console.WriteLine(b[0]); // 42
    }
}