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
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pigeon
 */
public class IssueType {
    private static Logger LOGGER=LogManager.getLogger(IssueType.class.getName()); 
    
    private Integer ID = 1;
    private String NAME = "";
    private Connection DB = DBDoor.getConn();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public IssueType() {
        this(1);
    }

    public IssueType(Integer id) {
        this.ID = id;
        loadDB();

    }
   public static DefaultTableModel getTypesAsTable(){
        String SQL = "select issuetype_id, name from issuetypes";
        return getListFromDB(SQL);
    }
    
   private static DefaultTableModel getListFromDB(String sql) {
        Object[][] rowDATA = {};
        String[] colNames = {"#ID", "IssueType"};
        DefaultTableModel _model = new DefaultTableModel(rowDATA, colNames);
        try {
        ResultSet resultSet = DBDoor.getStatement().executeQuery(sql);
            while (resultSet.next()) {
                    _model.addRow(new Object[]{resultSet.getInt("issuetype_id"),resultSet.getString("name")});
                }
        } catch (SQLException ex) {
            LOGGER.error("IssueType::getListFromDB(): "+ex.getMessage());
        }
       
       return _model;
    }
   private void loadDB() {
        if (ID >= 0) {
            try {
                resultSet = DBDoor.executeSelect("select * from issuetypes where issuetype_id=" + this.ID);
                resultSet.next();
                ID = resultSet.getInt("issuetype_id");
                NAME = resultSet.getString("name");
            } catch (SQLException ex) {
                LOGGER.error("IssueType::loadDB(): " + ex.getMessage());
            }
        }
    }
    
    private void saveDB(String mode) {
        LOGGER.info("IssueType::saveDB(): "+mode);
        try {
            if (mode.equals("I")) {
                preparedStatement = DB
                        .prepareStatement("insert into issuetypes values (?,?)");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setString(2, this.NAME);
            }
            if (mode.equals("U")) {
                preparedStatement = DB
                        .prepareStatement("update issuetypes set "
                                + "name=? "
                                + "where issuetype_id=?");
                preparedStatement.setString(1, this.NAME);
                preparedStatement.setInt(2,this.ID);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("IssueType::saveDB(): " + ex.getMessage());
        }
   }
   private void resolveID() {
        try {
            resultSet = DBDoor.getStatement()
                    .executeQuery("select max(issuetype_id)+1 as issuetype_id from issuetypes");
            resultSet.next();
            ID = resultSet.getInt("issuetype_id");
        } catch (SQLException ex) {
            LOGGER.error("IssueType::resolveID(): " + ex.toString());
        }
    }
    // Return methods
    public String getName() {
        return NAME;
    }

    public int id() {
        return ID;
    }
    
    // Set methods
    public void setName(String name) {
        this.NAME = name;
    }

    public void save() {
        if (this.ID >= 0) {
            this.saveDB("U");
            
        } else {
            this.resolveID();
            this.saveDB("I");
        }
    }
}
