package com.mycompany.mavenrest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import static java.lang.System.out;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 *
 * @author pc
 */
@Path("dbms")
public class DBMSresource {
    
    DBMS dbms = new DBMS();
    Gson gson = new Gson();
    
    
    @GET
    @Path("/show_dbs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show_dbs() {
        
        ArrayList<String> dbs = dbms.show_dbs();                 
        String json = gson.toJson(dbs);
        ArrayList<String[]> keyType = new ArrayList();                
        String test = gson.toJson(keyType, new TypeToken<ArrayList<String[]>>() {}.getType());
        out.println(test);
        
        return Response.ok().entity(json).build();
    } 
        
    @GET
    @Path("/show_tbs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show_tables(
            @MatrixParam("db") String DBname) {
        
        ArrayList<String> tbs = dbms.show_tables(DBname);
        String json = gson.toJson(tbs);
        return Response.ok().entity(json).build();       
    } 

    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(
            @MatrixParam("db") String DBname, 
            @MatrixParam("tb") String Tname) {
        
        ArrayList<ArrayList<String[]>> find = dbms.find(DBname, Tname);        
        String json = gson.toJson(find);
        return Response.ok().entity(json).build();
    }
   
    @GET
    @Path("/sort")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sort(
            @MatrixParam("db") String DBname, 
            @MatrixParam("tb") String Tname, 
            @MatrixParam("key") String key, 
            @MatrixParam("order") int order) {
        
        ArrayList<ArrayList<String[]>> sort = dbms.sort(DBname, Tname, key, order);        
        String json = gson.toJson(sort);
        return Response.ok().entity(json).build();
    }
    
    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response count(
            @MatrixParam("db") String DBname, 
            @MatrixParam("tb") String Tname) {
        
        int count = dbms.count(DBname, Tname);
        String json = gson.toJson(count);
        return Response.ok().entity(json).build();
    }
      
    @PUT
    @Path("/create_database")
    @Produces(MediaType.APPLICATION_JSON)    
    public Response create_database(
            @MatrixParam("db") String DBname) {        
        try {           
            dbms.create_database(DBname);            
            return Response.ok().build();
        } catch(Exception e) {return Response.ok("wrong").build();}
                
    }
            
    @PUT
    @Path("/create_table")
    @Produces(MediaType.APPLICATION_JSON)  
    public Response create_table(
            @MatrixParam("db") String DBname, 
            @MatrixParam("tb") String Tname, 
            @MatrixParam("kv") String kv) {
        
        ArrayList<String[]> keyType = gson.fromJson(kv, new TypeToken<ArrayList<String[]>>() {}.getType());
        dbms.create_table(DBname, Tname, keyType);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/drop_database")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response drop_database(
            @MatrixParam("db") String DBname) {
        
        dbms.drop_database(DBname);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/drop_table")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response drop_table(
            @MatrixParam("db") String DBname, 
            @MatrixParam("tb") String Tname) {
        
        dbms.drop_table(DBname, Tname);
        return Response.ok().build();
    }

    @DELETE
    @Path("/remove")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response remove_id(
            @MatrixParam("db") String DBname, 
            @MatrixParam("tb") String Tname, 
            @MatrixParam("id") String id) {
        
        dbms.remove_id(DBname, Tname, id);
        return Response.ok().build();
    }
    
    @PUT
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)  
    public Response insert(
            @MatrixParam("db") String DBname, 
            @MatrixParam("tb") String Tname, 
            @MatrixParam("kv") String kv) {
        
        ArrayList<String[]> keyValue = gson.fromJson(kv, new TypeToken<ArrayList<String[]>>() {}.getType());
        dbms.insert(DBname, Tname, keyValue);
        return Response.ok().build();
    }  
    
    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)  
    public Response update(
            @MatrixParam("db") String DBname, 
            @MatrixParam("tb") String Tname, 
            @MatrixParam("id") String id,
            @MatrixParam("kv") String kv) {
     
        ArrayList<String[]> keyValue = gson.fromJson(kv, new TypeToken<ArrayList<String[]>>() {}.getType());
        dbms.update(id, keyValue, DBname, Tname);
        return Response.ok().build();
    }
    
    @GET
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)  
    public Response save(
            @MatrixParam("db") String DBname) {
     
        String json = dbms.save(DBname);
        return Response.ok().build();
    }
    
    @GET
    @Path("/save_table")
    @Produces(MediaType.APPLICATION_JSON)  
    public Response save_table(
            @MatrixParam("db") String DBname,
            @MatrixParam("tb") String Tname) {
     
        String json = dbms.save_table(DBname, Tname);
        //dbms.update(id, keyValue, DBname, Tname);
        return Response.ok().build();
    }
    
    @PUT
    @Path("/load")
    @Produces(MediaType.APPLICATION_JSON)  
    public Response load(
            @MatrixParam("db") String DBname) {
            
        String json = dbms.save(DBname);
        out.println(json);
        return Response.ok().build();
    }
        
    @PUT
    @Path("/load_table")
    @Produces(MediaType.APPLICATION_JSON)  
    public Response load_table(
            @MatrixParam("db") String DBname,
            @MatrixParam("tb") String Tname) {
             
        String json = dbms.save_table(DBname, Tname);
        out.println(json);
        return Response.ok().build();
    }
}
