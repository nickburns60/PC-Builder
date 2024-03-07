package org.PCBuilder;

public class CaseForBuild implements Purchasable{
    private String model;
    private String enclosureType;
    private int length;
    private int width;
    private double price;


    public CaseForBuild(String model, String enclosureType, int length, int width, double price) {
        this.model = model;
        this.enclosureType = enclosureType;
        this.length = length;
        this.width = width;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public String getEnclosureType() {
        return enclosureType;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
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
