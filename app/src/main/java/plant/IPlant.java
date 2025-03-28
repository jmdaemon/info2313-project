enum Season 
{
    SUMMER, 
    SPRING,
    WINTER,
    FALL;
}

public interface IPlant 
{
    Season GetSeason();

    void Stake();
    void Prune();
    void Fertilize();
    void Water();
}