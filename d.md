## 设计目标：
1. 设置用户账户，pos机每次服务一个用户。
2. 用户需要进行登录以进行购买。
3. 用户的购物车会持久保留用户的购买记录。
4. 用户可以选择性的购买购物车内的商品，或者全部购买
5. 用户账户记录用户的网银数目，购买商品需要消费网银
6. pos机提供充值方式
7. pos服务器会记录所有的购买记录


## 数据库设计

### Login：
```shell
uid  INT
password VCHAR(8)
cash INT
```

### Cart
```shell
uid INT
pid INT
amount INT
```

### Product
```shell
pid INT
name VCHAR(30)
price INT
```

### Purchase
```shell
id INT
uid INT
pid INT
amount INT
time TIMESTAMP
```
