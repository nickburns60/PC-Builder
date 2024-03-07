package org.PCBuilder;

public class MoboForBuild implements Purchasable{
    private String model;
    private String socket;
    private String formFactor;
    private String compatibleRAM;
    private double price;

    public MoboForBuild(String model, String socket, String formFactor, String compatibleRAM, double price) {
        this.model = model;
        this.socket = socket;
        this.formFactor = formFactor;
        this.compatibleRAM = compatibleRAM;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public String getSocket() {
        return socket;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public String getCompatibleRAM() {
        return compatibleRAM;
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
