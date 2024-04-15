package org.PCBuilder;

public class ProcessorForBuild implements Purchasable{
    private String model;
    private String socket;
    private String compatibleRAM;
    private double price;


    public ProcessorForBuild(String model, String socket, String compatibleRAM, double price) {
        this.model = model;
        this.socket = socket;
        this.compatibleRAM = compatibleRAM;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public String getSocket() {
        return socket;
    }

    @Override
    public double getPrice() {
        return price;
    }
    @Override
    public String toString(){
        return model + "    $" + price;
    }
}
