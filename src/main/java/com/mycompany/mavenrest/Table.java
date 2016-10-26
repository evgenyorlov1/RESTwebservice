package com.mycompany.mavenrest;



import java.io.Serializable;
import java.util.ArrayList;


public class Table implements Serializable {
    
    public final String name;    
    public final ArrayList<String[]> keyType; 
    public ArrayList<Record> records = new ArrayList<Record>();   
    
    
    public Table(String name, ArrayList<String[]> keyType) {                
        this.name = name;
        this.keyType = keyType;
    }        
    
    public void insert(ArrayList<String[]> keyValue) {
        Record record = new Record(keyValue);
        records.add(record);
    }
        
}
