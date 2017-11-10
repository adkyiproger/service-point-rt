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
public class DeviceType {
    private static Logger LOGGER=LogManager.getLogger(DeviceType.class.getName()); 
    
    private Integer ID = 1;
    private String NAME = "";
    private Connection DB = DBDoor.getConn();
    //private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public DeviceType() {
        this(1);
    }

    public DeviceType(Integer id) {
        this.ID = id;
        loadDB();

    }
   public static DefaultTableModel getTypesAsTable(){
        String SQL = "select devicetype_id, name from devicetypes";
        return getListFromDB(SQL);
    }
    
   private static DefaultTableModel getListFromDB(String sql) {
        Object[][] rowDATA = {};
        String[] colNames = {"#ID", "DeviceType"};
        DefaultTableModel _model = new DefaultTableModel(rowDATA, colNames);
        try {
        ResultSet resultSet = DBDoor.getStatement().executeQuery(sql);
            while (resultSet.next()) {
                    _model.addRow(new Object[]{resultSet.getInt("devicetype_id"),resultSet.getString("name")});
                }
        } catch (SQLException ex) {
            LOGGER.error("DeviceType::getListFromDB(): "+ex.getMessage());
        }
       
       return _model;
    }
   private void loadDB() {
        if (ID >= 0) {
            try {
                resultSet = DBDoor.executeSelect("select * from devicetypes where devicetype_id=" + this.ID);
                resultSet.next();
                ID = resultSet.getInt("devicetype_id");
                NAME = resultSet.getString("name");
            } catch (SQLException ex) {
                LOGGER.error("DeviceType::loadDB(): " + ex.getMessage());
            }
        }
    }
    
    private void saveDB(String mode) {
        LOGGER.info("DeviceType::saveDB(): "+mode);
        try {
            if (mode.equals("I")) {
                preparedStatement = DB
                        .prepareStatement("insert into devicetypes values (?,?)");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setString(2, this.NAME);
            }
            if (mode.equals("U")) {
                preparedStatement = DB
                        .prepareStatement("update devicetypes set "
                                + "name=? "
                                + "where devicetype_id=?");
                preparedStatement.setString(1, this.NAME);
                preparedStatement.setInt(2,this.ID);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("DeviceType::saveDB(): " + ex.getMessage());
        }
   }
   private void resolveID() {
        try {
            resultSet = DBDoor.getStatement()
                    .executeQuery("select max(devicetype_id)+1 as devicetype_id from devicetypes");
            resultSet.next();
            ID = resultSet.getInt("devicetype_id");
        } catch (SQLException ex) {
            LOGGER.error("DeviceType::resolveID(): " + ex.toString());
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
