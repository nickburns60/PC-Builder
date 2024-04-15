package org.PCBuilder;

public class FansForBuild implements Purchasable{
    private String model;
    private int size;
    private int numOfFans;
    private double price;

    @Override
    public double getPrice(){
        return price;
    }


    public FansForBuild(String model, int size, int numOfFans, double price) {
        this.model = model;
        this.size = size;
        this.numOfFans = numOfFans;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString(){
        return model + "    $" + price;
    }
}
