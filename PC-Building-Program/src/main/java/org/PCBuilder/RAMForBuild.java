package org.PCBuilder;

public class RAMForBuild implements Purchasable{
    private String model;
    private String type;
    private double price;

    public RAMForBuild(String model, String type, double price) {
        this.model = model;
        this.type = type;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
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
