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
public class Client {

    private Integer ID = -1;
    private static java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("repairtracker/views/Bundle"); // NOI18N
    private static Logger LOGGER=LogManager.getLogger(Client.class.getName()); 
    private String FNAME = "";
    private String LNAME = "";
    private String MNAME = "";
    private Connection DB = DBDoor.getConn();
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    

    public Client() {
        this(-1);
    }

    public Client(Integer id) {
        DB = DBDoor.getConn();
        LOGGER.info("Client::Client(" + id+")");
        this.ID = id;
        loadDB();
    }
    private void loadDB() {
        if (ID >= 0) {
            try {
                resultSet = DBDoor.executeSelect("select * from clients where client_id=" + this.ID);
                resultSet.next();
                FNAME = resultSet.getString("fname");
                LNAME = resultSet.getString("lname");
                MNAME = resultSet.getString("mname");
            } catch (SQLException ex) {
                LOGGER.error("Client::loadDB(): " + ex.toString());
            }
        }
}
    private void resolveID() {
        try {
            resultSet = DBDoor.getStatement()
                    .executeQuery("select max(client_id)+1 as client_id from clients");
            resultSet.next();
            ID = resultSet.getInt("client_id");
        } catch (SQLException ex) {
            LOGGER.error("Client::resolveID(): " + ex.toString());
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
        this.LNAME = name.toLowerCase();
    }

    public void setFName(String name) {
        this.FNAME = name.toLowerCase();
    }

    public void setMName(String name) {
        this.MNAME = name.toLowerCase();
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
        String s="";
        if ((LNAME.length()>2) && FNAME.length()>2 && MNAME.length()>2)
            s=LNAME + " " + FNAME.substring(0, 1) + ". " + MNAME.substring(0, 1) + ".";
        else s=FNAME;
        return s;
    }
}
