package org.PCBuilder;

public class CPUCoolerForBuild implements Purchasable{
    private String model;
    private int size;
    private double price;

    public CPUCoolerForBuild(String model, int size, double price) {
        this.model = model;
        this.size = size;
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
