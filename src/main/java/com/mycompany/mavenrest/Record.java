package com.mycompany.mavenrest;



import java.io.Serializable;
import java.util.ArrayList;


public class Record implements Serializable {        
        
    public ArrayList<String[]> keyValue;        
    
    
    public Record(ArrayList<String[]> keyValue) {
        try {            
            this.keyValue = keyValue;
        } catch(Exception e) {System.out.println("Record.Record: " + e);}
    }
    
    public String get_by_key(String key) {
        String value = "";
        for(String[] keyval :  keyValue) {
            if(keyval[0].equals(key))
                value = keyval[1];
        }
        return value;
    }
    
    public void set_by_key(String key, String value) {
        
        for(int i=0; i<keyValue.size(); i++) {
            if(keyValue.get(i)[0].equals(key))
                keyValue.get(i)[1] = value;
        }                
    }
}