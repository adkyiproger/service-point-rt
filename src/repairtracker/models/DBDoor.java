/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repairtracker.models;
import repairtracker.RepairTracker;
import static repairtracker.RepairTracker.DBHOST;
import static repairtracker.RepairTracker.DBNAME;
import static repairtracker.RepairTracker.DBPASS;
import static repairtracker.RepairTracker.DBUSER;
import static repairtracker.RepairTracker.JDBCTYPE;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pigeon
 */
public class DBDoor {
    
    private String user=null;
    private String pass=null;
    private String db=null;
    private String jdbctype=null;
    private Connection connect = null;
    private static Connection CONN = null;
    private static Statement STATEMENT=null;
    private static boolean CONN_STATE=true;
    private static final Logger LOGGER=LogManager.getLogger(DBDoor.class.getName());
    
    //private static String[][] RT_TABLES = {"clients", "address", "issues", "issueattributes"};
    private static Map<String, String> RT_TABLES = new HashMap<String, String>();
    
    
    
    
    public DBDoor() {
        
    }
    public void setDBUser(String USER){
        this.user=USER;
    };
    public void setDBName(String DBNAME){
        this.db=DBNAME;
    };
    public void setDBPassword(String DBPASS){
        this.pass=DBPASS;
    };
    public Connection getConnection(){
        return connect;
    }
    public static boolean isConnected(){
        if (!CONN_STATE) {//LOGGER.error("DB connection failed" ); 
        return false;
        }
        return CONN_STATE;
    }
    public static void setupMyConnection() {
        LOGGER.info("Setting DB connection");
        boolean res=false;
        try {
            res=InetAddress.getByName(DBHOST).isReachable(5000);
            
        } catch (UnknownHostException e) {
            LOGGER.error("UnknownHost connection error:"+e.toString());
        
        } catch (IOException e) {
            LOGGER.error(" Generic connection error:"+e.toString());
        }
        
        
        try {
            if (JDBCTYPE.equalsIgnoreCase("mysql")) {
                if (res) {
              CONN = DriverManager.getConnection("jdbc:mysql://"+DBHOST+"/"+DBNAME+"?"
              + "user="+DBUSER+"&password="+DBPASS+"&characterEncoding=UTF-8");
              LOGGER.info("Link:"+"jdbc:mysql://"+DBHOST+"/"+DBNAME+"?"
              + "user="+DBUSER+"&password="+DBPASS+"&characterEncoding=UTF-8");
              STATEMENT=CONN.createStatement();
            STATEMENT.setQueryTimeout(15);
            CONN_STATE=true;
                } else CONN_STATE=false;
            }
           
            if (JDBCTYPE.equalsIgnoreCase("derby")) {
                LOGGER.info("Link:"+"jdbc:derby:"+RepairTracker.DERBY_DBNAME+";create=true");
                CONN = DriverManager.getConnection("jdbc:derby:"+RepairTracker.DERBY_DBNAME+";create=true");
              STATEMENT=CONN.createStatement();
            STATEMENT.setQueryTimeout(15);
            CONN_STATE=true;
            
            }  else CONN_STATE=false;
           
            
              
        } catch ( SQLException ex) {
            LOGGER.error(ex.toString());
            CONN_STATE=false;
        }
        if (CONN_STATE==true) {
            LOGGER.info("DB connection successfully created");
            LOGGER.info("Checking tables");
            createTables();
        }
    }
    public static int checkDBAccess(){
        return 0;
    }
    
    public static Connection getConn(){
        return CONN;
    
    }
    public static Statement getStatement(){
        return STATEMENT;
    }
    public static ResultSet executeSelect(String sql){
        ResultSet rs=null;
        try {
               rs = STATEMENT.executeQuery(sql);
               
        } catch (SQLException ex) {
            LOGGER.error(ex.toString());
        }
        return rs;
    }
    
    public static void listTables(){
        try {

        ResultSet rs = CONN.getMetaData().getTables(null, "APP", "%", null);
        LOGGER.info("Tables list for default DB schema (APP):");
        while (rs.next()) {
            LOGGER.info(rs.getString(3));
        }
        } catch (SQLException ex) {
            LOGGER.error(ex.toString());
        }
    }
    public static boolean checkTable(String tbl_name) {
        boolean flag=false;
        try {
        ResultSet rs = CONN.getMetaData().getTables(null, "APP", "%", null);
        
        while (rs.next()) {
            if ( rs.getString(3).equalsIgnoreCase(tbl_name) ) {
            LOGGER.info("Table: "+tbl_name+" exists");
            flag=true;
            }
        }
        } catch (SQLException ex) {
            LOGGER.error(ex.toString());
        }
        return flag;
    }
    private static void defTables(){
        RT_TABLES.put("clients",
            "CREATE TABLE clients ( client_id bigint NOT NULL, fname varchar(128) NOT NULL, "
            + "lname varchar(128) NOT NULL, mname varchar(128) DEFAULT NULL, PRIMARY KEY (client_id))");
    }
    private static boolean createTables(){
        defTables();
        boolean flag=false;
        for (String s: RT_TABLES.keySet()) {
            if (checkTable(s)==false) {
                LOGGER.error("Table: "+s+" does not exists");
                try {
                getStatement().execute(RT_TABLES.get(s));
                LOGGER.info("Table "+s+" created");
                } catch (SQLException ex) {
                 LOGGER.error(ex.toString());
                }
            }
        }
        return flag;
    }
    
}
