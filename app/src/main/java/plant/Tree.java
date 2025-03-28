public class Tree extends Plant 
{
    protected int height;

    public Tree(PlantInfo info, int height)
    {
        super(info);
        this.height = height;
    }

    @Override
    public void Stake() 
    {
        System.out.println("Place 2 metal/wood stakes of 5 ft near the branches");
        System.out.println("Tie up stakes with the branch of a tree for wind security");
    }

    @Override
    public void Prune() 
    {
        System.out.println("Cut out dead or deseased branches, without leaving stubs");
    }
}