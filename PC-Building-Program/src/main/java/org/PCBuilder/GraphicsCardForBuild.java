package org.PCBuilder;

public class GraphicsCardForBuild implements Purchasable{
    private String model;
    private int length;
    private int width;
    private int recommendedPSU;
    private double price;


    public GraphicsCardForBuild(String model, int length, int width, int recommendedPSU, double price) {
        this.model = model;
        this.length = length;
        this.width = width;
        this.recommendedPSU = recommendedPSU;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getRecommendedPSU() {
        return recommendedPSU;
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
