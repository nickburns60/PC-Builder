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


    public void compatible(ProcessorForBuild processor) throws PartCompatibilityException{
        if (!socket.equals(processor.getSocket())){
            throw new PartCompatibilityException("Selected part " + "'" + model + "' does not have a 'socket type' " +
                    "compatible with the processor you selected. \n Please make sure 'Socket' on your " +
                    "selected processor matches the motherboard you choose." +
                    "This is the socket from your processor: " + processor.getSocket());
        }
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
