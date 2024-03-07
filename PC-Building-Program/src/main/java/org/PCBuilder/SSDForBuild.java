package org.PCBuilder;

public class SSDForBuild implements Purchasable{
    String model;
    double price;

    public SSDForBuild(String model, double price) {
        this.model = model;
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
