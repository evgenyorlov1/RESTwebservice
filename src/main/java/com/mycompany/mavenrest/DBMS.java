package com.mycompany.mavenrest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author pc
 */
public class DBMS {
        
    public static ArrayList<DataBase> databases = new ArrayList<>();    
    private static boolean isAdmin = true;
        
    
    public DBMS() {
        super();
    }
    
        
    public ArrayList<String> show_dbs() {
        System.out.println("SingletonDBMS.show_dbs");
        ArrayList<String> databaseNames = new ArrayList<String>();                
        for(int i=0; i<databases.size(); i++) {
            databaseNames.add(databases.get(i).name);
        }                                
        return databaseNames;
    }
    
    public ArrayList<String> show_tables(String DBname) {
        System.out.println("SingletonDBMS.show_tables");        
        ArrayList<String> tables = new ArrayList<String>();                
        for(int i=0; i<databases.size(); i++) {            
            if(databases.get(i).name.equals(DBname)) {               
                for(int j=0; j<databases.get(i).tables.size(); j++) {                    
                    tables.add(databases.get(i).tables.get(j).name);
                }
            }
        }                
        return tables;
    }
    
    public void drop_database(String DBname) {
        System.out.println("SingletonDBMS.drop_database");
        if(isAdmin) {
            for(int i=0; i<databases.size(); i++) {
                if(databases.get(i).name.equals(DBname)) {
                    databases.remove(i);
                }
            }
        }
    }
    
    public void drop_table(String DBname, String Tname) {
        System.out.println("SingletonDBMS.drop_table");
        if(isAdmin) {
            for(int i=0; i<databases.size(); i++) {
                if(databases.get(i).name.equals(DBname)) {
                    for(int j=0; j<databases.get(i).tables.size(); j++) {
                       if(databases.get(i).tables.get(j).name.equals(Tname)) {
                           databases.get(i).tables.remove(j);
                       }
                    }
                }
            }
        }
    }
    
    public void create_database(String DBname) {
        System.out.println("SingletonDBMS.create_database");
        if(isAdmin) {            
            DataBase db = new DataBase(DBname);            
            databases.add(db);            
        }        
    }
    
    public void create_table(String DBname, String Tname, ArrayList<String[]> keyType) {                
        System.out.println("SingletonDBMS.create_table");        
        final String[] id = new String[] {"_id", "integer"};        
        keyType.add(id);        
        if(isAdmin)
            for(int i=0; i<databases.size(); i++)
                if(databases.get(i).name.equals(DBname)) 
                    databases.get(i).tables.add(new Table(Tname, keyType));                                          
    }
    
    public String save(String DBname) {
        System.out.println("SingletonDBMS.save_serialization");
        
        String json = "";
        try {                
            Gson gson = new Gson();
            json = gson.toJson(databases, new TypeToken<ArrayList<ArrayList<DataBase>>>() {}.getType());
        } catch(Exception e) {System.out.println("DBMS.save: " + e);}
        return json;
    }
    
    public String save_table(String DBname, String Tname) {
        System.out.println("SingletonDBMS.save_serialization");
        Table table = null;
        String json = "";
        try {
            for(DataBase db : databases) {
                if(db.name.equals(DBname)) {
                    for(Table tb : db.tables) {
                        if(tb.name.equals(Tname)) {
                            table = tb;
                        }
                    }
                }
            }
            Gson gson = new Gson();
            json = gson.toJson(table, new TypeToken<Table>() {}.getType());
        } catch(Exception e) {System.out.println("DBMS.save_table: " + e);}
        return json;
    }
   
    public void load(String file) {
        System.out.println("SingletonDBMS.load_serialization");
        try {
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(in);
            DataBase db = (DataBase) (ois.readObject());
            this.databases.add(db);        
        } catch(Exception e) {}
    }
    
    public void load_table(String DBname, String file) {
        System.out.println("SingletonDBMS.load_serialization");
        try {
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(in);
            Table tb = (Table) (ois.readObject());        

            for(DataBase db : databases) {
                if(db.name.equals(DBname)) {
                    db.tables.add(tb);
                }
            }  
        } catch(Exception e) {}
    }
        
    public ArrayList<ArrayList<String[]>> find(String DBname, String Tname) {
        System.out.println("SingletonDBMS.find");
        ArrayList<Record> recs = get_records(DBname, Tname);  
        ArrayList<ArrayList<String[]>> records = new ArrayList<>();        
        for(int i=0; i<recs.size(); i++) 
            records.add(recs.get(i).keyValue);                 
        return records;
    }
    
    public ArrayList<String[]> get_metadata(String DBname, String Tname) {
        System.out.println("SingletonDBMS.get_metadata");        
        ArrayList<String[]> metadata = new ArrayList<>();                
        for(DataBase db : databases) {
            if(db.name.equals(DBname)) {
                for(Table tb : db.tables) {
                    if(tb.name.equals(Tname))
                        metadata = tb.keyType;                    
                }
            }
        }                                                             
        return metadata;
    }   
    
    public ArrayList<ArrayList<String[]>> sort(String DBname, String Tname, String key, int order) {
        System.out.println("SingletonDBMS.sort");
        ArrayList<Record> recs = get_records(DBname, Tname);  
        ArrayList<ArrayList<String[]>> records = new ArrayList<>();        
        for(int i=0; i<recs.size(); i++) 
            records.add(recs.get(i).keyValue); 
        
        String type = "";                                
        ArrayList<String[]> KeyType = get_metadata(DBname, Tname);; 
        
        for(String[] keyType : KeyType)
            if(keyType[0].equals(key))
                type = keyType[1];     
        
        Comparator comparator = new Comparator(type, order);        
        ArrayList<ArrayList<String[]>> sorted = bubble_sort(recs, comparator, key);                
        return sorted;
    }

    public int count(String DBname, String Tname) {
        System.out.println("SingletonDBMS.count");
        int count = 0;
        for(DataBase db : databases) {
            if(db.name.equals(DBname)) {
                for(Table tb : db.tables) {
                    if(tb.name.equals(Tname)) {
                        count = tb.records.size();
                    }
                }
            }
        }
        
        return count;
    }
        
    public void insert(String DBname, String Tname, ArrayList<String[]> keyValue) {
        System.out.println("SingletonDBMS.insert");        
        String[] id = new String[2];
        id[0] = "_id";
                
        for(String[] row : keyValue) 
            id[1] += row[1];        
                
        id[1] = String.valueOf(id[1].hashCode());        
        keyValue.add(id);        
        
        for(DataBase db : databases) {
            if(db.name.equals(DBname)) {
                for(Table tb : db.tables) {
                    tb.insert(keyValue);
                }
            }
        }       
    }
    
    public void update(String _id, ArrayList<String[]> keyVal, String DBname, String Tname) {
        System.out.println("SingletonDBMS.update");        
        ArrayList<Record> records = new ArrayList<Record>();
        System.out.println(keyVal.size());
        for(DataBase db : databases) {
            if(db.name.equals(DBname))
                for(Table tb : db.tables) {
                    if(tb.name.equals(Tname))
                        records = tb.records;
                }
        }
        
        for(Record record : records) {
            if(record.get_by_key("_id").equals(_id)) {                
                for(String[] kv : keyVal) {
                    System.out.println(kv[0] + ", " + kv[1]);
                    record.set_by_key(kv[0], kv[1]);
                }
            }            
        }
    }
    
    public boolean is_unique_name(String useState) {
        for(int i=0; i<databases.size(); i++) {
            if(databases.get(i).name.equals(useState))
                return false;
        }
        return true;
    }
    
    public void remove_id(String DBname, String Tname, String id) {
        System.out.println("SingletonDBMS.remove id");
        for(DataBase db : databases) 
            if(db.name.equals(DBname)) 
                for(Table tb : db.tables) 
                    if(tb.name.equals(Tname)) 
                        for(int i=0; i<tb.records.size(); i++) 
                            if(tb.records.get(i).get_by_key("_id").equals(id))
                                tb.records.remove(i);    
    }   
    
    public ArrayList<Record> get_records(String DBname, String Tname) {
        System.out.println("SingletonDBMS.get_records");
        
        ArrayList<Record> rec = new ArrayList<>();
        
        for(DataBase db : databases) {
            if(db.name.equals(DBname)) {
                for(Table tb : db.tables) {
                    if(tb.name.equals(Tname)) 
                        rec = tb.records;                    
                }
            }
        }
                                                       
        return rec;
    }
    
    public ArrayList<ArrayList<String[]>> bubble_sort(ArrayList<Record> records, Comparator camparator, String key) {
        
        for(int i=0; i<records.size(); i++) {                        
            for(int j=0; j<records.size(); j++) {                
                if(camparator.compare(records.get(i).get_by_key(key),records.get(j).get_by_key(key))) {                
                    Collections.swap(records, j, i);
                }
            }
        }
        
        ArrayList<ArrayList<String[]>> result = new ArrayList<>();        
        for(int i=0; i<records.size(); i++) 
            result.add(records.get(i).keyValue); 
        
        return result;
    }   
    
}
