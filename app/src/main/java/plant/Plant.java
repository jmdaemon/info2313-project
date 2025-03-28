public abstract class Plant implements IPlant 
{
    protected PlantInfo info;    

    public Plant(PlantInfo info) 
    {
        this.info = info;
    }

    @Override
    public void Fertilize() 
    {
        System.out.println("Using fertilizer on " + info.name);
    }

    @Override
    public void Water() 
    {
        System.out.println("Watering " + info.name);
    }
}