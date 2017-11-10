/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repairtracker.models;

import java.util.Iterator;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pigeon
 */
public class Template {
    
    private Integer ID = -1, DEP_ID=1, AUTHOR_ID=1;
    private String NAME = "";
    private String CONTENT="";
    private Connection DB = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public Template() {
        this(-1);
    }

    public Template(Integer id) {
        DB = DBDoor.getConn();
        //System.out.println("Template id is: " + id);
        this.ID = id;
        loadDB();

    }
   public static DefaultTableModel listTTemplates(){
        String SQL = "select template_id, name from templates";
        return getTemplateListFromDB(SQL);
    }
    
   private static DefaultTableModel getTemplateListFromDB(String sql) {
        Object[][] rowDATA = {};
        String[] colNames = {"#CARD", "Template"};
        DefaultTableModel _model = new DefaultTableModel(rowDATA, colNames);
        try {
        ResultSet resultSet = DBDoor.getStatement().executeQuery(sql);
            while (resultSet.next()) {
//                    System.out.println("Template id: "+resultSet.getInt("template_id")+" name: "+resultSet.getString("name"));
                    _model.addRow(new Object[]{resultSet.getInt("template_id"),resultSet.getString("name")});
                }
        } catch (SQLException ex) {
            System.err.println("Template::getTemplateListFromDB(): "+ex.toString());
            ex.printStackTrace();
        }
       
       return _model;
    }
   private void loadDB() {
        if (ID >= 0) {
            try {
                resultSet = DBDoor.getStatement()
                        .executeQuery("select * from templates where template_id=" + this.ID);
                resultSet.next();
                //System.out.println(resultSet.getShort("template_id") + resultSet.getString("name"));
                ID = resultSet.getInt("template_id");
                DEP_ID=resultSet.getInt("department_id");
                NAME = resultSet.getString("name");
                AUTHOR_ID=resultSet.getInt("author_id");
                CONTENT = resultSet.getString("content");
            } catch (SQLException ex) {
                System.err.println("Template::loadDB(): " + ex.toString());
                ex.printStackTrace();
            }
        }
    }
    
    private void saveDB(String mode) {
        System.out.println("Template::saveDB(): "+mode);
        try {
            if (mode.equals("I")) {
                preparedStatement = DB
                        .prepareStatement("insert into templates values (?,?,?,?,?)");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setString(2, this.NAME);
                preparedStatement.setInt(3, this.DEP_ID);
                preparedStatement.setString(4, this.CONTENT);
                preparedStatement.setInt(5, this.AUTHOR_ID);
            }
            if (mode.equals("U")) {
                preparedStatement = DB
                        .prepareStatement("update templates set "
                                + "name=?, "
                                + "content=?,"
                                + "department_id=?,"
                                + "author_id=?"
                                + "where template_id=?");
                preparedStatement.setString(1, this.NAME);
                preparedStatement.setString(2, this.CONTENT);
                preparedStatement.setInt(3,this.DEP_ID);
                preparedStatement.setInt(4,this.AUTHOR_ID);
                preparedStatement.setInt(5,this.ID);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Template::saveDB(): " + ex.toString());
            ex.printStackTrace();
        }
   }
   private void resolveID() {
        try {
            resultSet = DBDoor.getStatement()
                    .executeQuery("select max(template_id)+1 as template_id from templates");
            resultSet.next();
            ID = resultSet.getInt("template_id");
            //System.out.println("ID resolved:" + this.ID);
        } catch (Exception ex) {
            System.err.println("Template::resolveID(): " + ex.toString());
            ex.printStackTrace();
        }
    }
    // Return methods
    public String getName() {
        return NAME;
    }

    public String getContent() {
        return CONTENT;
    }
     public int id() {
        return ID;
    }
    
     public int getDepartmentId(){
         return this.DEP_ID;     
     }
     public int getAuthorId(){
         return this.AUTHOR_ID;     
     }
    // Set methods
    public void setName(String name) {
        this.NAME = name;
    }

    public void setContent(String name) {
        this.CONTENT = name;
    }
    public void setDepID(int id) {
        this.DEP_ID = id;
    }
    public void setAuthorId(int id) {
        this.AUTHOR_ID = id;
    }
    
     public void save() {
        if (this.ID >= 0) {
            this.saveDB("U");
            
        } else {
            this.resolveID();
            this.saveDB("I");
        }
    }
    
    public static Map<String,String> listTemplates(){
 
        Map<String,String> doctors = new HashMap<>();
        
        try {
        ResultSet resultSet = DBDoor.getStatement().executeQuery("select name, content from templates where department_id="+EHospital.DEP_ID);
            while (resultSet.next()) {
                    doctors.put(resultSet.getString("name"),resultSet.getString("content"));
                }
        } catch (SQLException ex) {
            System.err.println("Template::listTemplates(): "+ex.toString());
            ex.printStackTrace();
        }
       return doctors;
    }
}
