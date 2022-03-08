package com.example.poshell.db;

import com.example.poshell.model.Cart;
import com.example.poshell.model.Product;
import com.example.poshell.model.Purchase;

import java.util.List;

public interface PosDB {

    List<Product> getProducts();
    Cart getCart(int uid);
    Product getProduct(int productId);
    boolean login(int uid, String password);
    void delItem(int uid, int pid);
    void addItem(int uid, int pid, int amount);
    void buy(int uid, int pid, int amount);
    void declineAccount(int uid, double amount);
    Double getAccount(int uid);
    void pay(int uid, double amount);
    List<Purchase>getPurchase();
}
