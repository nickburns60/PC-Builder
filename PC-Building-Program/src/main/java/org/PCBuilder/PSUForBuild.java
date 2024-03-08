package org.PCBuilder;

public class PSUForBuild implements Purchasable {
    private String model;
    private int wattage;
    private double price;


    public PSUForBuild(String model, int wattage, double price) {
        this.model = model;
        this.wattage = wattage;
        this.price = price;
    }

    public void compatible(GraphicsCardForBuild gpu) throws PartCompatibilityException{
        if (wattage < (gpu.getRecommendedPSU())){
            throw new PartCompatibilityException("Selected part " + "'" + model + "' does not have a minimum 'Wattage' " +
                    "required for the 'Graphics Card' you selected. \n Please make sure the 'Wattage' on the power supply you choose " +
                    "meets the minimum requirement for your selected graphics card." +
                    "\n Your power supply must have a Wattage of at least: " + gpu.getRecommendedPSU() + " watts");
        }
    }

    public String getModel() {
        return model;
    }

    public int getWattage() {
        return wattage;
    }

    @Override
    public String toString(){
        return model + "    $" + price;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
