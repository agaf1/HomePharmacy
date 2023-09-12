package pl.aga.service.domain;

import lombok.*;

@Value
public class Contents {

    private final Type type;
    private final Quantity quantity;

    public Contents(Type type,Quantity quantity){
        this.type = type;
        if(type.equals(Type.PILLS)){
            this.quantity = new Quantity(quantity.quantityOfItems);
        }
        else{
            this.quantity = new Quantity(quantity.quantityOfMilliliter);
        }

    }

    enum Type { PILLS, SYRUP, DROPS}
    @Getter
    @Setter
    private class Quantity {
        private int quantityOfItems;
        private double quantityOfMilliliter;

        public Quantity(int quantityOfItems, double quantityOfMilliliter){
            this.quantityOfItems = quantityOfItems;
            this.quantityOfMilliliter = quantityOfMilliliter;
        }

        Quantity(int quantityOfItems) {
            this.quantityOfItems = quantityOfItems;
        }

        Quantity(double quantityOfMilliliter) {
            this.quantityOfMilliliter = quantityOfMilliliter;
        }
    }
}
