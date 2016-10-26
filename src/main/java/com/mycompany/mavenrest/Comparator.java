package com.mycompany.mavenrest;

public class Comparator {
    private String key;
    private int order;
    
    public Comparator(String key, int order) {
        this.key = key;        
        this.order = order;                
    }
    
    public boolean compare(String arg1, String arg2) {        
        return (this.order == 1)? 
                ascending_order(arg1,arg2):descending_order(arg1,arg2);
    }
    
    private boolean ascending_order(String arg1, String arg2) {        
        switch(key) {
            case "integer":                
                if(Integer.parseInt(arg1) > Integer.parseInt(arg2)) {
                    return true;
                } else {return false;}                
            case "real":
                if(Float.parseFloat(arg1) > Float.parseFloat(arg2)) {
                    return true;
                } else {return false;}                
            case "longint":
                if(Long.parseLong(arg1) > Long.parseLong(arg2)) {
                    return true;
                } else {return false;}
            case "char":
                if(arg1.charAt(0) > arg2.charAt(0)) {
                    return true;
                } else {return false;}
        }                
        return false;
    }
    
    private boolean descending_order(String arg1, String arg2) {
        switch(key) {
            case "integer":                
                if(Integer.parseInt(arg1) < Integer.parseInt(arg2)) {
                    return true;
                } else {return false;}  
            case "real":
                if(Float.parseFloat(arg1) < Float.parseFloat(arg2)) {
                    return true;
                } else {return false;}                
            case "longint":
                if(Long.parseLong(arg1) < Long.parseLong(arg2)) {
                    return true;
                } else {return false;}
            case "char":
                if(arg1.charAt(0) > arg2.charAt(0)) {
                    return true;
                } else {return false;}                    
        }
        return false;
    }
}
