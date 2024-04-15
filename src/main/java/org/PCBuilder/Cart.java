package org.PCBuilder;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    //Makes a list for parts selected by the user
    private List<Purchasable> partsSelected = new ArrayList<>();

    //fills list with selected parts
    public void add(Purchasable itemToAdd) {
        partsSelected.add(itemToAdd);
    }
    //gets the price of each part and adds it to the total
    public double getTotalPrice() {
        double total = 0.0;
        for (Purchasable item : partsSelected) {
            total += item.getPrice();
        }
        return total;
    }

    //shows user a list of all the items they chose, their respective cost, and the final total
    public String receipt() {
        String receipt = "\nReceipt\n";
        for (Purchasable item : partsSelected) {
            receipt +=  item;
            receipt += "\n";
        }

        receipt += "\nTotal: $" + getTotalPrice();

        return receipt;
    }
}