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
import java.util.ArrayList;
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
    private static Map<String, String> RT_DATA = new HashMap<String, String>();
    
    
    
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
    
    public static String[] sqlToList(String sql){
       ArrayList<String> list=new ArrayList<>();
       if (DBDoor.isConnected()) {
       try {
        ResultSet resultSet = DBDoor.getStatement().executeQuery(sql);
            while (resultSet.next()) {
                    
                    list.add(resultSet.getString(1));
                }
        } catch (SQLException ex) {
            LOGGER.error("private static String[] sqlToList(String sql): "+ex.toString());
            list.add("NA");
        }
       }
        String[] stockArr = new String[list.size()];
        stockArr = list.toArray(stockArr);
        
        return stockArr;
    
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
        
        // -- CLIENTS
        RT_TABLES.put("clients",
            "CREATE TABLE clients ( client_id bigint NOT NULL, fname varchar(128) NOT NULL, "
            + "lname varchar(128) NOT NULL, mname varchar(128) DEFAULT NULL, PRIMARY KEY (client_id))");
        // -- ADDRESS
        RT_TABLES.put("address", "CREATE TABLE address (address_id bigint NOT NULL,"
                + " region varchar(256) NOT NULL,  district varchar(256) NOT NULL,  zip varchar(9) NOT NULL,  city varchar(256) NOT NULL,"
                + "  address1 varchar(1024) NOT NULL,  phone varchar(32) NOT NULL,  email varchar(256) NOT NULL,  client_id bigint NOT NULL,"
                + "  PRIMARY KEY (address_id),  CONSTRAINT FK_ADPTS  FOREIGN KEY (client_id) REFERENCES clients (client_id))");
        
        // -- DEVICETYPES
        RT_TABLES.put("devicetypes", "CREATE TABLE devicetypes (devicetype_id bigint NOT NULL, name varchar(128) NOT NULL, PRIMARY KEY(devicetype_id) ) ");
        // -- WARRANTIES
        RT_TABLES.put("WARRANTIES", "CREATE TABLE WARRANTIES (warranty_id bigint NOT NULL, name varchar(128) NOT NULL, description varchar(4096) NOT NULL, PRIMARY KEY(warranty_id) ) ");
        
        // -- ISSUETYPES
        // http://sc24.su/pages/
        RT_TABLES.put("issuetypes", "CREATE TABLE issuetypes (issuetype_id bigint NOT NULL, name varchar(128) NOT NULL, PRIMARY KEY(issuetype_id) ) ");
        
        // -- ISSUES
        RT_TABLES.put("issues", "CREATE TABLE issues (issue_id bigint NOT NULL, issuetype_id bigint NOT NULL, client_id bigint NOT NULL,"
                + " devicetype_id bigint NOT NULL, warranty_id bigint NOT NULL,"
                + "startdate date not null, enddate date not null,"
                + "status int default 0,"
                + "devicename varchar(128) DEFAULT NULL, devicenumber varchar(128) DEFAULT NULL, shortdescription varchar(256) DEFAULT NULL,  comments varchar(256) NOT NULL,"
                + "totalcost double not null default 0, prepay double not null default 0,"
                + "  PRIMARY KEY (issue_id), "
                + "CONSTRAINT FK_ISSCL  FOREIGN KEY (client_id)  REFERENCES clients (client_id),"
                + "CONSTRAINT FK_ISTYP  FOREIGN KEY (issuetype_id)  REFERENCES issuetypes (issuetype_id),"
                + "CONSTRAINT FK_ISDEV  FOREIGN KEY (devicetype_id)  REFERENCES devicetypes (devicetype_id),"
                + "CONSTRAINT FK_ISWAR  FOREIGN KEY (warranty_id)  REFERENCES WARRANTIES (warranty_id)"
                + ")");
        // -- issueattributes types
        RT_TABLES.put("issueattrtypes", "CREATE TABLE issueattrtypes (issueattrtype_id bigint NOT NULL, name varchar(128) NOT NULL, PRIMARY KEY(issueattrtype_id) ) ");
        
        RT_TABLES.put("issueattributes", "CREATE TABLE issueattributes (issueattribute_id bigint NOT NULL, issue_id bigint NOT NULL,"
                + " description varchar(128) NOT NULL, price double default null, issueattrtype_id bigint not null, PRIMARY KEY(issueattribute_id),"
                + "CONSTRAINT FK_ISAIS  FOREIGN KEY (issue_id)  REFERENCES issues (issue_id),"
                + "CONSTRAINT FK_ISATY  FOREIGN KEY (issueattrtype_id)  REFERENCES issueattrtypes (issueattrtype_id)"
                + " ) ");
        RT_DATA.put("issueattrtypes","insert into issueattrtypes values (0,'Work'), (1,'Parts')");
        RT_DATA.put("issuetypes", "insert into issuetypes values (0,'Regular'), (1,'Warranty'), (2,'Consulting')");
        RT_DATA.put("devicetypes","insert into devicetypes values (0,'Phone'), (1,'Tablet'), (2,'Laptop'),(3,'PC'), (4,'Other')");
        RT_DATA.put("WARRANTIES","insert into WARRANTIES values (0,'Regular new','Regular new'), (1,'Used parts','Used parts'), (2,'Regular repair','Regular repair')");

    }
    private static boolean createTables(){
        defTables();
        boolean flag=true;
        for (String s: RT_TABLES.keySet()) {
            if (checkTable(s)==false) {
                LOGGER.error("Table: "+s+" does not exists");
                try {
                    LOGGER.info("SQL: "+RT_TABLES.get(s));
                getStatement().execute(RT_TABLES.get(s));
                LOGGER.info("SQL: "+RT_DATA.get(s));
                if (RT_DATA.containsKey(s)) getStatement().execute(RT_DATA.get(s));
                else LOGGER.info("SQL: Skip for table "+s);
                LOGGER.info("Table "+s+" created");
                } catch (SQLException ex) {
                 LOGGER.error("Table "+s+" was not created");   
                 LOGGER.error(ex.toString());
                 flag=false;
                }
                
            }
        }
        if (flag==true) {
            LOGGER.info("createTables(): Completed successfully. All required tables are present in database");
        } else {
            LOGGER.error("createTables(): Failed. Application will not function properly");
        }
        return flag;
    }
    
}
