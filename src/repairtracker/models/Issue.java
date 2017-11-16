/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repairtracker.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pigeon
 */
public class Issue {
    private static java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("repairtracker/views/Bundle"); // NOI18N
    private static Logger LOGGER=LogManager.getLogger(Issue.class.getName()); 
    private Connection DB = DBDoor.getConn();
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    private Integer ID = -1;
    // Issue ID
    private Integer CLIENT_ID=-1;
    private Integer TYPE=0;
    // Issue type ID
    //  - Regular repair
    //  - Consulting
    //  - Warranty repair
    
    private Integer DEVTYPE=0;
    // Device type ID
    //  - Phone
    //  - Tablet
    //  - Laptop
    //  - PC
    
    private Integer WARRANTY_ID=0;
    
    private String DEVICE_NAME="";
    private String DEVICE_NUMBER = "";
    private String SHORTDESCRIPTION = "";
    private String COMMENTS = "";
    private Date START_DATE=new Date();
    private Date END_DATE=new Date();
    private Double TOTAL_COST=0.0;
    private Double PREPAY=0.0;
    private int STATUS=0;
    
            
    

    public Issue() {
        
        this(-1);
        
    }

    public Issue(Integer id) {
        LOGGER.info("Issue::Issue(" + id+")");
        if (id == -1) {
        Calendar c = Calendar.getInstance(); 
        c.setTime(END_DATE); 
        c.add(Calendar.DATE, 10);
        END_DATE = c.getTime();
        }
        this.ID = id;
        loadDB();
    }
    private void loadDB() {
        if (ID >= 0) {
            try {
                resultSet = DBDoor.executeSelect("select * from issues where issue_id=" + this.ID);
                resultSet.next();
                
                CLIENT_ID=resultSet.getInt("client_id");
                TYPE=resultSet.getInt("issuetype_id");
                DEVTYPE=resultSet.getInt("devicetype_id");
                WARRANTY_ID=resultSet.getInt("warranty_id");
                DEVICE_NAME = resultSet.getString("devicename");
                DEVICE_NUMBER = resultSet.getString("devicenumber");
                SHORTDESCRIPTION = resultSet.getString("shortdescription");
                COMMENTS = resultSet.getString("comments");
                START_DATE = resultSet.getDate("startdate");
                END_DATE = resultSet.getDate("enddate");
                STATUS = resultSet.getInt("status");
                TOTAL_COST = resultSet.getDouble("totalcost");
                PREPAY = resultSet.getDouble("prepay");
                LOGGER.info("Loaded information from database: "
                        + resultSet.toString());
            } catch (SQLException ex) {
                LOGGER.error("Issue::loadDB(): " + ex.getMessage());
            }
        }
}
    private void resolveID() {
        try {
            resultSet = DBDoor.executeSelect("select max(issue_id)+1 as issue_id from issues");
            resultSet.next();
            ID = resultSet.getInt("issue_id");
        } catch (SQLException ex) {
            LOGGER.error("Issue::resolveID(): " + ex.toString());
        }
    }

    private void saveDB(String mode) {
/*
        issue_id bigint NOT NULL,
        issuetype_id bigint NOT NULL,
        client_id bigint NOT NULL,
        devicetype_id bigint NOT NULL,
        warranty_id bigint NOT NULL,
        startdate date not null,
        enddate date not null,
        devicename varchar(128) DEFAULT NULL, 
        devicenumber varchar(128) DEFAULT NULL, 
        shortdescription varchar(256) DEFAULT NULL,  
        comments varchar(256) NOT NULL,
        totalcost double not null default 0,
        prepay double not null default 0,
        
        */
        try {
            if (mode.equals("I")) {
                preparedStatement = DB
                        .prepareStatement("insert into issues values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setInt(2, this.TYPE);
                preparedStatement.setInt(3, this.CLIENT_ID);
                preparedStatement.setInt(4, this.DEVTYPE);
                preparedStatement.setInt(5, this.WARRANTY_ID);
                preparedStatement.setDate(6,new java.sql.Date(this.START_DATE.getTime()));
                preparedStatement.setDate(7,new java.sql.Date(this.END_DATE.getTime()));
                preparedStatement.setInt(8, STATUS);
                preparedStatement.setString(9, DEVICE_NAME);
                preparedStatement.setString(10, DEVICE_NUMBER);
                preparedStatement.setString(11, SHORTDESCRIPTION);
                preparedStatement.setString(12, COMMENTS);
                preparedStatement.setDouble(13, TOTAL_COST);
                preparedStatement.setDouble(14, PREPAY);
            }
            if (mode.equals("U")) {

                preparedStatement = DB
                        .prepareStatement("update issues set "
                                + "issuetype_id=?, "
                                + "client_id=?, "
                                + "devicetype_id=?, "
                                + "warranty_id=?, "
                                + "startdate=?, "
                                + "enddate=?, "
                                + "status=?, "
                                + "devicename=?, "
                                + "devicenumber=?, "
                                + "shortdescription=?, "
                                + "comments=?, "
                                + "totalcost=?, "
                                + "prepay=?"
                                + "where issue_id=?");

                preparedStatement.setInt(1, this.TYPE);
                preparedStatement.setInt(2, this.CLIENT_ID);
                preparedStatement.setInt(3, this.DEVTYPE);
                preparedStatement.setInt(4, this.WARRANTY_ID);
                preparedStatement.setDate(5,new java.sql.Date(this.START_DATE.getTime()));
                preparedStatement.setDate(6,new java.sql.Date(this.END_DATE.getTime()));
                preparedStatement.setInt(7, STATUS);
                preparedStatement.setString(8, DEVICE_NAME);
                preparedStatement.setString(9, DEVICE_NUMBER);
                preparedStatement.setString(10, SHORTDESCRIPTION);
                preparedStatement.setString(11, COMMENTS);
                preparedStatement.setDouble(12, TOTAL_COST);
                preparedStatement.setDouble(13, PREPAY);

            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Issue::saveDB(): " + ex.toString());
        }

    }
    public int id() {
        return ID;
    }
    
    public int clientId() {
        return CLIENT_ID;
    }
    
    public int issueTypeId(){
        return TYPE;
    }
    
    public int deviceTypeId(){
        return DEVTYPE;
    }
    
    public int warrantyTypeId(){
        return WARRANTY_ID;
    }
    public Date startDate(){
        return START_DATE;
    }
    
    public Date endDate(){
        return END_DATE;
    }
    
    public String deviceName(){
        return DEVICE_NAME;
    }
    
    public String deviceNumber(){
        return DEVICE_NUMBER;
    }
    
    public String comments(){
        return COMMENTS;
    }
    
    public String description(){
        return SHORTDESCRIPTION;
    }
    
    public Double totalCost(){
        return TOTAL_COST;
    }
    
    public Double prepayCost(){
        return PREPAY;
    }
    public int status(){
        return STATUS;
    }
    
    // Set methods
    public void setClientId(int id) {
        CLIENT_ID=id;
    }
    
    public void setIssueTypeId(int id){
        TYPE=id;
    }
    
    public void setDeviceTypeId(int id){
        DEVTYPE=id;
    }
    
    public void setWarrantyTypeId(int id){
        WARRANTY_ID=id;
    }
    public void setStartDate(Date d){
        START_DATE=d;
    }
    
    public void setEndDate(Date d){
        END_DATE=d;
    }
    
    public void setDeviceName(String s){
        DEVICE_NAME=s;
    }
    
    public void setDeviceNumber(String s){
        DEVICE_NUMBER=s;
    }
    
    public void setComments(String s){
        COMMENTS=s;
    }
    
    public void setDescription(String s){
        SHORTDESCRIPTION=s;
    }
    
    public void setTotalCost(Double c){
        TOTAL_COST=c;
    }
    
    public void setPrepayCost(Double c){
        PREPAY=c;
    }
    public void setStatus(int s){
        STATUS=s;
    }
    
    public void save() {
        if (this.ID > 0) {
            this.saveDB("U");
        } else {
            this.resolveID();
            this.saveDB("I");
        }
    }
    @Override
    public String toString() {
        return new Client(this.CLIENT_ID).toString()+" : "+this.DEVICE_NAME+" ("+this.DEVICE_NUMBER+")";
    }
}
