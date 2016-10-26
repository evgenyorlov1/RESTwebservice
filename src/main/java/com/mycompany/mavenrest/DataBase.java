package com.mycompany.mavenrest;



import java.io.Serializable;
import java.util.ArrayList;


public class DataBase implements Serializable {
    
    public String name;
    ArrayList<Table> tables = new ArrayList<Table>();
    
    
    public DataBase(String name) {
        this.name = name;
    }            
}
