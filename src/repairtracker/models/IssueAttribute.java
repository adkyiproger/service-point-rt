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
public class IssueAttribute {
    private static Logger LOGGER=LogManager.getLogger(IssueAttribute.class.getName()); 
    /*issueattribute_id bigint NOT NULL, issue_id bigint NOT NULL,"
                + " description varchar(128) NOT NULL, price double default null, issueattrtype_id bigint not null,
*/
    private Integer ID = -1;
    private Integer ISSUE_ID = -1;
    private Integer TYPE=-1;
    private String DESCRIPTION = "";
    private Double PRICE=0.0;
    private Connection DB = DBDoor.getConn();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public IssueAttribute() {
        this(-1);
    }

    public IssueAttribute(Integer id) {
        this.ID = id;
        loadDB();

    }
   public static DefaultTableModel getTypesAsTable(){
        String SQL = "select issueattribute_id, description from issueattributes";
        return getListFromDB(SQL);
    }
    
   private static DefaultTableModel getListFromDB(String sql) {
        Object[][] rowDATA = {};
        String[] colNames = {"#ID", "Name"};
        DefaultTableModel _model = new DefaultTableModel(rowDATA, colNames);
        try {
        ResultSet resultSet = DBDoor.getStatement().executeQuery(sql);
            while (resultSet.next()) {
                    _model.addRow(new Object[]{resultSet.getInt("issueattrtype_id"),resultSet.getString("name")});
                }
        } catch (SQLException ex) {
            LOGGER.error("IssueAttributeType::getListFromDB(): "+ex.getMessage());
        }
       
       return _model;
    }
   private void loadDB() {
        if (ID >= 0) {
            try {
                resultSet = DBDoor.executeSelect("select * from issueattrtypes where issueattrtype_id=" + this.ID);
                resultSet.next();
                ID = resultSet.getInt("issueattrtype_id");
                DESCRIPTION = resultSet.getString("name");
            } catch (SQLException ex) {
                LOGGER.error("IssueAttributeType::loadDB(): " + ex.getMessage());
            }
        }
    }
    
    private void saveDB(String mode) {
        LOGGER.info("IssueAttributeType::saveDB(): "+mode);
        try {
            if (mode.equals("I")) {
                preparedStatement = DB
                        .prepareStatement("insert into issueattrtypes values (?,?)");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setString(2, this.DESCRIPTION);
            }
            if (mode.equals("U")) {
                preparedStatement = DB
                        .prepareStatement("update issueattrtypes set "
                                + "name=? "
                                + "where issueattrtype_id=?");
                preparedStatement.setString(1, this.DESCRIPTION);
                preparedStatement.setInt(2,this.ID);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("IssueAttributeType::saveDB(): " + ex.getMessage());
        }
   }
   private void resolveID() {
        try {
            resultSet = DBDoor.getStatement()
                    .executeQuery("select max(issueattrtype_id)+1 as issueattrtype_id from issueattrtypes");
            resultSet.next();
            ID = resultSet.getInt("issueattrtype_id");
        } catch (SQLException ex) {
            LOGGER.error("IssueAttributeType::resolveID(): " + ex.toString());
        }
    }
    // Return methods
    public String getName() {
        return DESCRIPTION;
    }

    public int id() {
        return ID;
    }
    
    // Set methods
    public void setName(String name) {
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
}
