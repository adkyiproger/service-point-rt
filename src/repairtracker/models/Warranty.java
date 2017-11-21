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
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pigeon
 */
public class Warranty {
    private static Logger LOGGER=LogManager.getLogger(IssueType.class.getName()); 
    private Integer ID = -1;
    private String NAME = "";
    private String DESCRIPTION="";
    private String LOCAL_PATH="";
    private Connection DB = DBDoor.getConn();
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public Warranty() {
        this(1);
    }

    public Warranty(Integer id) {
        this.ID = id;
        loadDB();

    }
    
    public Warranty(String str) {
        this.NAME = str;
        loadDB();

    }
    
   public static DefaultTableModel getWarrantiesAsTable(){
        return getListFromDB("select warranty_id, name from warranties");
    }
    
   private static DefaultTableModel getListFromDB(String sql) {
        Object[][] rowDATA = {};
        String[] colNames = {"#ID", "Name"};
        DefaultTableModel _model = new DefaultTableModel(rowDATA, colNames);
        try {
        ResultSet resultSet = DBDoor.getStatement().executeQuery(sql);
            while (resultSet.next()) {
                    _model.addRow(new Object[]{resultSet.getInt("warranty_id"),resultSet.getString("name")});
                }
        } catch (SQLException ex) {
            LOGGER.error("Warranty::getListFromDB(): "+ex.getMessage(),ex);
        }
       
       return _model;
    }
   private void loadDB() {
        if (ID >= 0) {
            try {
                resultSet = DBDoor.getStatement()
                        .executeQuery("select * from warranties where warranty_id=" + this.ID);
                resultSet.next();
                ID = resultSet.getInt("warranty_id");
                NAME = resultSet.getString("name");
                DESCRIPTION = resultSet.getString("description");
            } catch (SQLException ex) {
                LOGGER.error("Warranty::loadDB(): "+ex.getMessage(),ex);

            }
        }
        else if (NAME.length()>0) {
       try {
                preparedStatement = DB
                        .prepareStatement("select * from warranties where name=?");
                preparedStatement.setString(1, NAME);
                resultSet=preparedStatement.executeQuery();
                resultSet.next();
                ID = resultSet.getInt("warranty_id");
                NAME = resultSet.getString("name");
                DESCRIPTION = resultSet.getString("description");
                LOCAL_PATH = resultSet.getString("local_path");
            } catch (SQLException ex) {
                LOGGER.error("Warranty::loadDB(): "+ex.getMessage(),ex);

            }
        }
    }
    
    private void saveDB(String mode) {
        LOGGER.info("Warranty::saveDB(): "+mode);
        try {
            if (mode.equals("I")) {
                preparedStatement = DB
                        .prepareStatement("insert into warranties values (?,?,?,?)");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setString(2, this.NAME);
                preparedStatement.setString(3, this.DESCRIPTION);
                preparedStatement.setString(4, this.LOCAL_PATH);
            }
            if (mode.equals("U")) {
                preparedStatement = DB
                        .prepareStatement("update warranties set "
                                + "name=?, "
                                + "description=?,"
                                + "local_path=?,"
                                + "where warranty_id=?");
                preparedStatement.setString(1, this.NAME);
                preparedStatement.setString(2, this.DESCRIPTION);
                preparedStatement.setString(3, LOCAL_PATH);
                preparedStatement.setInt(4,this.ID);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
                LOGGER.error("Warranty::saveDB(): "+ex.getMessage(),ex);
        }
   }
   private void resolveID() {
        try {
            resultSet = DBDoor.getStatement()
                    .executeQuery("select max(warranty_id)+1 as warranty_id from warranties");
            resultSet.next();
            ID = resultSet.getInt("warranty_id");
        } catch (SQLException ex) {
            LOGGER.error("Warranty::resolveID(): "+ex.getMessage(),ex);
            
        }
    }
    // Return methods
    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }
     public int id() {
        return ID;
    }
    // Set methods
    public void setName(String name) {
        this.NAME = name;
    }
    
    public void setLocalPath(String path) {
            LOCAL_PATH=path;
    }

    public void setContent(String name) {
        this.DESCRIPTION = name;
    }
     public void save() {
        if (this.ID >= 0) {
            this.saveDB("U");
            
        } else {
            this.resolveID();
            this.saveDB("I");
        }
    }
    
    public static Map<String,String> getWarrantiesAsMap(){
 
        Map<String,String> warr = new HashMap<>();
        
        try {
        ResultSet resultSet = DBDoor.executeSelect("select name, description from warranties");
            while (resultSet.next()) {
                    warr.put(resultSet.getString("name"),resultSet.getString("description"));
                }
        } catch (SQLException ex) {
            LOGGER.error("Warranty::getWarrantiesAsMap(): "+ex.getMessage(),ex);
        }
       return warr;
    }
}
