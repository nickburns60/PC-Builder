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

    public String getModel() {
        return model;
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
