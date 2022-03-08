package com.example.poshell.biz;

import com.example.poshell.db.PosDB;
import com.example.poshell.model.Cart;
import com.example.poshell.model.Item;
import com.example.poshell.model.Product;
import com.example.poshell.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PosServiceImp implements PosService {

    private PosDB posDB;
    private Integer uid = null;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public Cart getCart() {
        if(uid != null){
            return getCart(uid);
        }else{
            return null;
        }
    }

    private Cart getCart(int uid){
        return posDB.getCart(uid);
    }

    private boolean add(Product product, int amount) {
        if (product == null) {
            return false;
        }
        posDB.addItem(uid, product.getPid(), amount);
        return true;
    }

    @Override
    public boolean add(int productId, int amount) {
        if(uid !=null) {
            return add(posDB.getProduct(productId), amount);
        }else{
            return false;
        }
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }

    @Override
    public boolean login(int uid, String password) {
        boolean res = posDB.login(uid, password);
        if(res){
            this.uid = uid;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void buy(int index) {
        if(uid != null){
            buy(uid, index);
        }
    }

    private void buy(int uid, int index){
        Item item = getCart(uid).getItems().get(index-1);
        posDB.delItem(uid, item.getProduct().getPid());
        posDB.buy(uid, item.getProduct().getPid(),item.getAmount());
        double total = item.getProduct().getPrice() * item.getAmount();
        posDB.declineAccount(uid, total);
    }

    @Override
    public void buyAll() {
        if(uid != null){
            buyAll(uid);
        }
    }

    private void buyAll(int uid){
        int size = getCart(uid).getItems().size();
        for(int i = 0;i<size;++i){
            buy(uid, 1);
        }
    }

    @Override
    public Integer getUid() {
        return uid;
    }

    @Override
    public Double getAccount() {
        if(uid != null){
            return getAccount(uid);
        }else{
            return null;
        }
    }

    private double getAccount(int uid){
        return posDB.getAccount(uid);
    }

    @Override
    public void pay(int amount) {
        if(uid != null){
            pay(uid, amount);
        }
    }

    @Override
    public List<Purchase> getPurchase() {
        return posDB.getPurchase();
    }

    private void pay(int uid, int amount){
        posDB.pay(uid, amount);
    }
}
