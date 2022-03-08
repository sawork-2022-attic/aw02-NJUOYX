package com.example.poshell.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Purchase {
    private Integer id;
    private Integer uid;
    private Integer pid;
    private Integer amount;
    private Timestamp time;

    public Purchase(){
        id = uid = pid = amount = 0;
        time = null;
    }

    @Override
    public String toString(){
        return "\t" + getId() + "\t" + getUid() + "\t" + getPid() + "\t" + getAmount() + "\t" + getTime();
    }
}
