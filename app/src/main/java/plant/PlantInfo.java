package plant;

import java.util.Date;

public class PlantInfo
{
    public String name;
    public String otherNames;

    public Season pottingTime;
    public Date pottingDate;

    public double priceCAD;
    public int lifespanDays;

    public String growingMethod;
    public String growingInstructions;

    public PlantInfo(String name, String otherNames, 
    Season pottingTime, Date pottingDate, double priceCAD, 
    int lifespanDays, String growingMethod, String growingInstructions)
    {
        this.name = name;
        this.otherNames = (otherNames != null && !otherNames.isEmpty()) ? otherNames : "None";
        this.pottingTime = pottingTime;
        this.pottingDate = pottingDate;
        this.priceCAD = priceCAD;
        this.lifespanDays = lifespanDays;
        this.growingMethod = growingMethod;
        this.growingInstructions = growingInstructions;
    }
}
