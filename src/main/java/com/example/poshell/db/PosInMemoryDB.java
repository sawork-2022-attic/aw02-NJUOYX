package com.example.poshell.db;

import com.example.poshell.model.Cart;
import com.example.poshell.model.Item;
import com.example.poshell.model.Product;
import com.example.poshell.model.Purchase;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PosInMemoryDB implements PosDB {

    private JdbcTemplate jdbcTemplate;


    public PosInMemoryDB(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/pos",
                "root",
                "123456"
        );
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Product> getProducts() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Cart getCart(int uid) {
        String sql = "SELECT pid FROM cart WHERE uid = ?";
        List<Integer> pid = jdbcTemplate.queryForList(sql, Integer.class, uid);
        String sql1 = "SELECT amount FROM cart WHERE uid = ?";
        List<Integer> amount = jdbcTemplate.queryForList(sql1, Integer.class, uid);
        Cart cart = new Cart();
        for(int i = 0;i<pid.size();++i) {
            cart.addItem(new Item(getProduct(pid.get(i)), amount.get(i)));
        }
        return cart;
    }

    @Override
    public Product getProduct(int productId) {
        String sql = "SELECT * FROM product WHERE pid = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), productId);
    }


    @Override
    public boolean login(int uid, String password) {
        String sql = "SELECT COUNT(*) FROM login WHERE uid = ? AND password = ?";
        Integer res = jdbcTemplate.queryForObject(sql, Integer.class, uid, password);
        if(res == null || res == 0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void delItem(int uid, int pid) {
        String sql = "DELETE FROM cart WHERE uid = ? AND pid = ?";
        jdbcTemplate.update(sql, uid, pid);
    }

    @Override
    public void addItem(int uid, int pid, int amount) {
        String sql = "SELECT COUNT(*) FROM cart WHERE uid = ? AND pid = ?";
        Integer res = jdbcTemplate.queryForObject(sql, Integer.class, uid, pid);
        String sql1;
        if(res == null|| res == 0){
            sql1 = "INSERT INTO cart (uid, pid, amount) VALUE (?, ?, ?)";
            jdbcTemplate.update(sql1, uid, pid, amount);
        }else{
            sql1 = "UPDATE cart SET amount = amount + ? WHERE uid = ? AND pid = ?";
            jdbcTemplate.update(sql1, amount, uid, pid);
        }
    }

    @Override
    public void buy(int uid, int pid, int amount) {
        String sql = "INSERT INTO purchase (uid, pid, amount, time) VALUE (?, ?, ?, sysdate())";
        jdbcTemplate.update(sql, uid, pid, amount);
    }

    @Override
    public void declineAccount(int uid, double amount) {
        String sql = "UPDATE login SET cash = cash - ? WHERE uid = ?";
        jdbcTemplate.update(sql, amount, uid);
    }

    @Override
    public Double getAccount(int uid) {
        String sql = "SELECT cash FROM login WHERE uid = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, uid);
    }

    @Override
    public void pay(int uid, double amount) {
        String sql = "UPDATE login SET cash = cash + ? WHERE uid = ?";
        jdbcTemplate.update(sql, amount, uid);
    }

    @Override
    public List<Purchase> getPurchase() {
        String sql = "SELECT * FROM purchase";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Purchase.class));
    }
}
