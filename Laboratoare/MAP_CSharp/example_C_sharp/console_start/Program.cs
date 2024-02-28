// See https://aka.ms/new-console-template for more information

namespace console_start;

public abstract class Animal
{
    private string name;

    protected Animal(string _name)
    {
        this.name = _name;
    }

    public abstract void Speak();
}

public class Dog : Animal
{
    public Dog(string _name) : base(_name) {}

    public override void Speak()
    {
        Console.WriteLine("Woof!");
    }
}

public class Cat : Animal
{
    private bool isAsleep;

    public Cat(string _name, bool isAsleep) : base(_name)
    {
        this.isAsleep = isAsleep;
    }

    public override void Speak()
    {
        switch (isAsleep)
        {
            case true: Console.WriteLine("Purr...");
                break;
            case false: Console.WriteLine("Meow!");
                break;
        }
    }
}

class MainClass
{
    public static void Main()
    {
        Dog marius = new Dog("Marius");
        Cat marinela = new Cat("Marinela", true);
        Cat mirela = new Cat("Mirela", false);

        marius.Speak();
        marinela.Speak();
        mirela.Speak();
    }
}