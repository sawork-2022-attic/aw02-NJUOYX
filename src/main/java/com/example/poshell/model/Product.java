package com.example.poshell.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private int pid;
    private String name;
    private double price;

    public Product(){
        this.pid = -1;
        name = "";
        price = 0.0;
    }

    @Override
    public String toString() {
        return getPid() + "\t" + getName() + "\t" + getPrice();
    }

}
