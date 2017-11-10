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
    private Integer TYPE=1;
    // Issue type ID
    //  - Regular repair
    //  - Consulting
    //  - Warranty repair
    
    private Integer DEVTYPE=1;
    // Device type ID
    //  - Phone
    //  - Tablet
    //  - Laptop
    //  - PC
    
    private Integer WARRANTY_ID=1;
    
    private String DEVICE_NAME="";
    private String DEVICE_NUMBER = "";
    private String SHORTDESCRIPTION = "";
    private String COMMENTS = "";
    private Date START_DATE;
    private Date END_DATE;
    private Double TOTAL_COST=0.0;
    private Double PREPAY=0.0;
    
            
    

    public Issue() {
        this(-1);
    }

    public Issue(Integer id) {
        LOGGER.info("Issue::Issue(" + id+")");
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
                TOTAL_COST = resultSet.getDouble("totalcost");
                PREPAY = resultSet.getDouble("prepay");
                
            } catch (SQLException ex) {
                LOGGER.error("Issue::loadDB(): " + ex.toString());
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

        try {
            if (mode.equals("I")) {
                preparedStatement = DB
                        .prepareStatement("insert into clients values (?,?,?,?)");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setString(2, toUpper(this.FNAME));
                preparedStatement.setString(3, toUpper(this.LNAME));
                preparedStatement.setString(4, toUpper(this.MNAME));
            }
            if (mode.equals("U")) {

                preparedStatement = DB
                        .prepareStatement("update clients set "
                                + "fname=?, "
                                + "lname=?,"
                                + "mname=?"
                                + "where client_id=?");
                preparedStatement.setString(1, toUpper(this.FNAME));
                preparedStatement.setString(2, toUpper(this.LNAME));
                preparedStatement.setString(3, toUpper(this.MNAME));
                preparedStatement.setInt(4, this.ID);

            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Client::saveDB(): " + ex.toString());
        }

    }
private String toUpper(String str){
    if (str.length()>2)
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    else if (str.length()>0)
        return str.substring(0, 1).toUpperCase();
    else return "";
}
    public String lastName() {
        return LNAME;
    }

    public String firstName() {
        return FNAME;
    }

    public String middleName() {
        return MNAME;
    }
    public int id() {
        return ID;
    }
     
    public void setLName(String name) {
        this.LNAME = name;
    }

    public void setFName(String name) {
        this.FNAME = name;
    }

    public void setMName(String name) {
        this.MNAME = name;
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
        return this.lastName() + " " + this.firstName().substring(0, 1) + ". " + this.middleName().substring(0, 1) + ".";
    }
}
