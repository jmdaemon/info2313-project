public class Creepers extends Plant 
{
    String color;

    public Creepers(PlantInfo info, String color)
    {
        super(info);
        this.color = color;
    }

    @Override
    public void Stake() 
    {
        System.out.println("Install trellises/arbors 20-30 cm away from the walls to allow air citculation");
    }

    @Override
    public void Prune() 
    {
        System.out.println("Cut overgrown or unwanted stems");
    }
}
