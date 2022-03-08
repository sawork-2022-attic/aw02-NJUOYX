package com.example.poshell.biz;

import com.example.poshell.model.Cart;
import com.example.poshell.model.Product;
import com.example.poshell.model.Purchase;

import java.util.List;

public interface PosService {
    Cart getCart();
    boolean add(int productId, int amount);
    List<Product> products();
    boolean login(int uid, String password);
    void buy(int index);
    void buyAll();
    Integer getUid();
    Double getAccount();
    void pay(int amount);
    List<Purchase>getPurchase();
}
