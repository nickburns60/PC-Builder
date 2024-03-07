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

    public void compatible(MoboForBuild mobo) throws PartCompatibilityException{
        if(!type.equals(mobo.getCompatibleRAM())){
            throw new PartCompatibilityException("Selected part " + "'" + model + "' does not have a 'RAM type' " +
                    "compatible with the motherboard you selected. \n Please make sure 'RAM Type' on your " +
                    "selected motherboard matches the memory you choose." +
                    "This is the RAM type for your motherboard: " + mobo.getCompatibleRAM());
        }
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
