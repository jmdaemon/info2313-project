enum Season 
{
    SUMMER, 
    SPRING,
    WINTER,
    FALL;
}

public interface IPlant 
{
    void Stake();
    void Prune();
    void Fertilize();
    void Water();
}