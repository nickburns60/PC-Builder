package org.PCBuilder;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Purchasable> partsSelected = new ArrayList<>();

    public void add(Purchasable itemToAdd) {
        partsSelected.add(itemToAdd);
    }
    public double getTotalPrice() {
        double total = 0.0;
        for (Purchasable item : partsSelected) {
            total += item.getPrice();
        }
        return total;
    }

    public String receipt() {
        String receipt = "\nReceipt\n";
        for (Purchasable item : partsSelected) {
            receipt += item;
            receipt += "\n";
        }

        receipt += "\nTotal: $" + getTotalPrice();

        return receipt;
    }
}