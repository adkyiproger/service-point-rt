/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehospital.model;

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
public class Patient {

    private Integer ID = -1;
    private static java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ehospital/code/Bundle"); // NOI18N
    private static Logger LOGGER=LogManager.getLogger(Patient.class.getName()); 
    private String FNAME = "";
    private String LNAME = "";
    private String MNAME = "";
    private String DESCRIPTION = "";
    private String OCCUPATION="";
    private int GENDER;
    private Date DAYOFBIRTH;
    private Connection DB = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    

    public Patient() {
        this(-1);
    }

    public Patient(Integer id) {
        DB = DBDoor.getConn();
        LOGGER.info("Patient::Patient(" + id+")");
        this.ID = id;
        loadDB();

    }

    public static DefaultTableModel listPatients() {
        String SQL = "select * from patients";
        return getPatientsListFromDB(SQL);
    }

    public static DefaultTableModel listPatients(String lname, String fname, String mname) {
        String SQL = "select * from patients where lower(lname) like '" + lname + "%' and lower(fname) like '" + fname + "%' and lower(mname) like '" + mname + "%'";
        return getPatientsListFromDB(SQL);
    }
    
    public static String[] listOccupationNames(){
        return sqlToList("select distinct OCCUPATION from patients order by 1");
    }
    
    private static String[] sqlToList(String sql){
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

    private static DefaultTableModel getPatientsListFromDB(String sql) {
        Object[][] rowDATA = {};
        String[] colNames = {"#CARD",
            bundle.getString("EHospitalGUI.jLabel5.text"),
            bundle.getString("EHospitalGUI.jLabel6.text"),
            bundle.getString("EHospitalGUI.jLabel7.text")};
        DefaultTableModel patients_model = new DefaultTableModel(rowDATA, colNames);
        try {
            ResultSet resultSet = DBDoor.getStatement().executeQuery(sql);
            while (resultSet.next()) {
                patients_model.addRow(new Object[]{resultSet.getInt("patient_id"), resultSet.getString("lname"),
                    resultSet.getString("fname"), resultSet.getString("mname")});
            }
        } catch (SQLException ex) {
            LOGGER.error("Patient::getPatientsListFromDB(): " + ex.toString());
        }

        return patients_model;
    }

    private void loadDB() {
        if (ID >= 0) {
            try {
                resultSet = DBDoor.getStatement()
                        .executeQuery("select * from patients where patient_id=" + this.ID);
                resultSet.next();
                ID = resultSet.getInt("patient_id");
                FNAME = resultSet.getString("fname");

                LNAME = resultSet.getString("lname");
                MNAME = resultSet.getString("mname");
                DESCRIPTION = resultSet.getString("description");
                GENDER = resultSet.getShort("gender");
                DAYOFBIRTH = resultSet.getDate("dayofbirth");
                
                OCCUPATION=resultSet.getString("occupation");
                
                
            } catch (SQLException ex) {
                LOGGER.error("Patient::loadDB(): " + ex.toString());
            }
        }
    }

    private void resolveID() {
        try {
            resultSet = DBDoor.getStatement()
                    .executeQuery("select max(patient_id)+1 as patient_id from patients");
            resultSet.next();
            ID = resultSet.getInt("patient_id");
        } catch (SQLException ex) {
            LOGGER.error("Patient::resolveID(): " + ex.toString());
        }
    }

    private void saveDB(String mode) {

        try {
            if (mode.equals("I")) {
                preparedStatement = DB
                        .prepareStatement("insert into patients values (?,?,?,?,?,?,?,?)");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setString(2, toUpper(this.FNAME));
                preparedStatement.setString(3, toUpper(this.LNAME));
                preparedStatement.setString(4, toUpper(this.MNAME));
                preparedStatement.setString(5, this.DESCRIPTION);
                preparedStatement.setDate(6, new java.sql.Date(this.DAYOFBIRTH.getTime()));

                preparedStatement.setInt(7, (this.GENDER));
                preparedStatement.setString(8, this.OCCUPATION);
            }
            if (mode.equals("U")) {

                preparedStatement = DB
                        .prepareStatement("update patients set "
                                + "fname=?, "
                                + "lname=?,"
                                + "mname=?,"
                                + "description=?,"
                                + "dayofbirth=?,"
                                + "gender=?,"
                                + "OCCUPATION=?"
                                + "where patient_id=?");
                preparedStatement.setString(1, toUpper(this.FNAME));
                preparedStatement.setString(2, toUpper(this.LNAME));
                preparedStatement.setString(3, toUpper(this.MNAME));
                preparedStatement.setString(4, this.DESCRIPTION);
                preparedStatement.setDate(5, new java.sql.Date(this.DAYOFBIRTH.getTime()));
                preparedStatement.setInt(6, (this.GENDER));
                preparedStatement.setString(7, this.OCCUPATION);
                preparedStatement.setInt(8, this.ID);

            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Patient::saveDB(): " + ex.toString());
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
    public String descrition() {
        return DESCRIPTION;
    }
    public int gender() {
        return GENDER;
    }

    public Date dataOfBirth() {
        return DAYOFBIRTH;
    }

    public int id() {
        return ID;
    }
    
    public String occupation() {
        return OCCUPATION;
    }
    
    public void setOccupation(String name) {
        this.OCCUPATION = name;
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
    public void setDescrition(String name) {
        this.DESCRIPTION = name;
    }
    public void setGender(int g) {
        this.GENDER = g;
    }

    public void setBirthday(Date bd) {
        this.DAYOFBIRTH = bd;
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
