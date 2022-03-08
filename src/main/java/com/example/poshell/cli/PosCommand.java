package com.example.poshell.cli;

import com.example.poshell.biz.PosService;
import com.example.poshell.model.Cart;
import com.example.poshell.model.Item;
import com.example.poshell.model.Product;
import com.example.poshell.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class PosCommand {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @ShellMethod(value = "Login", key = "l")
    public String login(int uid, String password){
        boolean res = posService.login(uid,password);
        if(res){
            return String.format("Welcome! User [%d]\n", uid);
        }else{
            return "Login Failed!\n";
        }
    }

    @ShellMethod(value = "List Products", key = "pp")
    public String products() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t").append("pid").append("\t").append("name").append("\t").append("price").append("\n");
        for (Product product : posService.products()) {
            stringBuilder.append("\t").append(product).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "Add a Product to Cart", key = "a")
    public String addToCart(int productId, int amount) {
        if (posService.add(productId, amount)) {
            return posService.getCart().toString();
        }
        return "ERROR";
    }

    @ShellMethod(value = "List Products in Cart", key = "pc")
    public String listCart(){
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        Cart cart = posService.getCart();
        if(cart != null) {
            for (Item item : cart.getItems()) {
                stringBuilder.append("\t").append(++i).append("\t").append(item).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "Buy Product", key = "b")
    public void buyFromCart(int... index){
        for(int i : index){
            posService.buy(i);
        }
    }

    @ShellMethod(value = "Buy All Products In Cart", key = "c")
    public void buyAllCart(){
        posService.buyAll();
    }

    @ShellMethod(value = "Show User Account",key = "s")
    public String showAccount(){
        return String.format("User: [%d] -------- Account:[%f]\n", posService.getUid(), posService.getAccount());
    }

    @ShellMethod(value = "Pay For Your Account", key = "pay")
    public String pay(int amount){
        posService.pay(amount);
        return showAccount();
    }

    @ShellMethod(value = "Record In This Pos", key = "r")
    public String record(){
        List<Purchase> rs = posService.getPurchase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t").append("id").append("\t").append("uid").append("\t").append("pid").append("\t").append("amount")
                .append("\t").append("timestamp\n");
        for(Purchase p : rs){
            stringBuilder.append(p).append("\n");
        }
        return stringBuilder.toString();
    }
}
