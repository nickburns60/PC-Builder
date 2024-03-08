package org.PCBuilder;

public class CaseForBuild implements Purchasable{
    private String model;
    private String formFactorCompatible;
    private int length;
    private int width;
    private double price;


    public CaseForBuild(String model, String enclosureType, int length, int width, double price) {
        this.model = model;
        this.formFactorCompatible = enclosureType;
        this.length = length;
        this.width = width;
        this.price = price;
    }
    public void compatible(MoboForBuild mobo) throws PartCompatibilityException{
        if (mobo.getFormFactor().equals("ATX") && formFactorCompatible.equals("microATX")){
            throw new PartCompatibilityException("Selected part " + "'" + model + "' does not support" +
                    "the form factor of the motherboard you've selected. \nPlease make sure 'Form Factor' on your " +
                    "selected motherboard matches 'Form Factor' on the Case you choose." +
                    "\nThis is the 'Form Factor' compatible with your motherboard: " + mobo.getFormFactor());
        }
    }

    public String getModel() {
        return model;
    }

    public String getEnclosureType() {
        return formFactorCompatible;
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
