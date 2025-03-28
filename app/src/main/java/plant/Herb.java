package plant;

public class Herb extends Plant 
{
    protected String taste;

    public Herb(PlantInfo info, String taste)
    {
        super(info);
        this.taste = taste;
    }

    @Override
    public void Stake() 
    {
        System.out.println("Not required");
    }

    @Override
    public void Prune() 
    {
        System.out.println("Snip off the top sets of the leaves/stems");
    }
}
