/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ehospital.model;

import ehospital.code.EHospital;
import helpers.ExceptionHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author pigeon
 */
public class Epicrisis {
    private Integer ID = -1;
    private static java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ehospital/code/Bundle"); // NOI18N
    private static Logger LOGGER=LogManager.getLogger(Epicrisis.class.getName());
    private String NAME = "NA";
    private static String[] LIST_COL_NAMES = {"#CARD",bundle.getString("DATE"),bundle.getString("EPICRISIS_NAME"),bundle.getString("PATIENT_NAME"),bundle.getString("DOCTOR")};
    private String DESCRIPTION="";
    private int DEPARTMENT_ID=EHospital.DEP_ID;
    private int DOCTOR_ID=EHospital.DOC_ID;
    private int PATIENT_ID;
    private Date DATE;
    private Patient PATIENT;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    Connection DB= DBDoor.getConn();
    
    public Epicrisis(Integer id) {
        this.ID = id;
        loadDB();
        LOGGER.info("Initialized. Epicrisis id is: " + this.ID);
    }
    public Epicrisis(Patient p) {
        PATIENT=p;
        PATIENT_ID=p.id();
        this.ID = -1;
        LOGGER.info("Initialized. Epicrisis id is: " + this.ID);
    }
    
     public static DefaultTableModel listTEpicrisis(Patient pid) {
        String SQL ="select e.epicrisis_id as EPICRISIS_ID, e.date as date, e.name as name, upper(SUBSTR(p.lname,1,1))||SUBSTR(p.lname,2,length(p.lname)-1)||' '||upper(SUBSTR(p.fname,1,1))||'. '||upper(SUBSTR(p.mname,1,1))||'.' as patient_name, d.lname as doctor_name from epicrisis e " +
" join patients p on p.patient_id=e.patient_id " +
" join doctors d on d.doctor_id=e.doctor_id "
                + " where e.patient_id="+pid.id();
//                +" and e.department_id="+EHospital.DEP_ID;
         System.out.println("SQL: "+ SQL);
        return getEpicrisisListFromDB(SQL);
    }
     public static DefaultTableModel listTEpicrisisForDep() {
        String SQL = "select e.epicrisis_id as EPICRISIS_ID, e.date as date, e.name as name, upper(SUBSTR(p.lname,1,1))||SUBSTR(p.lname,2,length(p.lname)-1)||' '||upper(SUBSTR(p.fname,1,1))||'. '||upper(SUBSTR(p.mname,1,1))||'.' as patient_name, d.lname as doctor_name from epicrisis e " +
" join patients p on p.patient_id=e.patient_id " +
" join doctors d on d.doctor_id=e.doctor_id "+
                " where e.department_id="+EHospital.DEP_ID;
        return getEpicrisisListFromDB(SQL);
    }
    public static DefaultTableModel listTEpicrisisForPatientByDate(Patient pid, Date d_from, Date d_to) {
        String SQL = "select e.epicrisis_id as EPICRISIS_ID, e.date as date, e.name as name, upper(SUBSTR(p.lname,1,1))||SUBSTR(p.lname,2,length(p.lname)-1)||' '||upper(SUBSTR(p.fname,1,1))||'. '||upper(SUBSTR(p.mname,1,1))||'.' as patient_name, d.lname as doctor_name from epicrisis e " +
" join patients p on p.patient_id=e.patient_id " +
" join doctors d on d.doctor_id=e.doctor_id where e.department_id="+EHospital.DEP_ID;
        return getEpicrisisListFromDB(SQL);
    }

    public static DefaultTableModel listTEpicrisisForDepByDate(Date d_from, Date d_to) {
        //Object[][] rowDATA = {};
        //String[] colNames = {"#CARD","Name","Date","Patien","Doctor"};
        DefaultTableModel _model=null;// = new DefaultTableModel(rowDATA, colNames);
        
        String SQL = "select e.epicrisis_id as EPICRISIS_ID, e.date as date, e.name as name, upper(SUBSTR(p.lname,1,1))||SUBSTR(p.lname,2,length(p.lname)-1)||' '||upper(SUBSTR(p.fname,1,1))||'. '||upper(SUBSTR(p.mname,1,1))||'.' as patient_name, d.lname as doctor_name from epicrisis e " +
" join patients p on p.patient_id=e.patient_id " +
" join doctors d on d.doctor_id=e.doctor_id "
                + " where e.department_id=? "+
                "and date<=? and date>=?";
        
        System.out.println("SQL: "+SQL);
        try {
            PreparedStatement pstm=DBDoor.getConn().prepareStatement(SQL);
            pstm.setInt(1, EHospital.DEP_ID);
            pstm.setDate(2, new java.sql.Date(d_to.getTime()) );
            pstm.setDate(3, new java.sql.Date(d_from.getTime()));
            _model=getEpicrisisListFromDB(pstm);
        } catch (Exception e) {
            System.err.println("fuck fuck fuck"+ e.toString());
            
        }
        return _model;
    }
    private static DefaultTableModel getEpicrisisListFromDB(PreparedStatement stm) {
        Object[][] rowDATA = {};

        DefaultTableModel _model = new DefaultTableModel(rowDATA, LIST_COL_NAMES);

        try (ResultSet resultSet = stm.executeQuery()) {
            while (resultSet.next()) {
                _model.addRow(new Object[]{resultSet.getInt("epicrisis_id"), resultSet.getDate("date"), resultSet.getString("name"), resultSet.getString("patient_name"), resultSet.getString("doctor_name")});
            }
        } catch (SQLException ex) {
            LOGGER.error("Epicrisis::getEpicrisisListFromDB(): " + ExceptionHelper.toString(ex));
        }

        return _model;
    }
    private static DefaultTableModel getEpicrisisListFromDB(String sql) {
        //Object[][] rowDATA = {};
        DefaultTableModel _model = new DefaultTableModel(new Object[][]{} , LIST_COL_NAMES);
        LOGGER.info("SQL: " + sql);
            try (ResultSet resultSet = DBDoor.getStatement().executeQuery(sql)) {
                while (resultSet.next()) {
                    _model.addRow(new Object[]{resultSet.getInt("epicrisis_id"),resultSet.getDate("date"), resultSet.getString("name"),resultSet.getString("patient_name"),resultSet.getString("doctor_name")});
                }
        } catch (SQLException ex) {
            LOGGER.error("Epicrisis::getEpicrisisListFromDB(): " + ExceptionHelper.toString(ex));
        }
       return _model;
    }
    
    private void loadDB() {
        if (ID >= 0) {
            try {
                resultSet = DBDoor.getStatement()
                        .executeQuery("select * from epicrisis where epicrisis_id=" + this.ID);
                resultSet.next();
                ID = resultSet.getInt("epicrisis_id");
                PATIENT_ID=resultSet.getInt("patient_id");
                DOCTOR_ID=resultSet.getInt("doctor_id");
                DEPARTMENT_ID=resultSet.getInt("department_id");
                DATE=resultSet.getDate("date");
                NAME = resultSet.getString("name");

                DESCRIPTION = resultSet.getString("description");
            } catch (SQLException ex) {
                LOGGER.error("Epicrisis::loadDB(): " + ExceptionHelper.toString(ex));
            }
        }
    }
    
    private void saveDB(String mode) {
        LOGGER.info("Epicrisis::saveDB(): mode is "+mode);
        try {
            if (mode.equals("I")) {
                preparedStatement = DB
                        .prepareStatement("insert into epicrisis values (?,?,?,?,?,?,?)");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setInt(2, PATIENT_ID);
                preparedStatement.setInt(3, DOCTOR_ID);
                preparedStatement.setInt(4, DEPARTMENT_ID);
                preparedStatement.setDate(5,new java.sql.Date(DATE.getTime()));
                preparedStatement.setString(6, this.NAME.toLowerCase());
                preparedStatement.setString(7, this.DESCRIPTION);
            }
            if (mode.equals("U")) {
                preparedStatement = DB
                        .prepareStatement("update epicrisis set "
                                + "name=?, "
                                + "description=?"
                                + "where epicrisis_id=?");
                preparedStatement.setString(1, this.NAME);
                preparedStatement.setString(2, this.DESCRIPTION);
                preparedStatement.setInt(3,this.ID);
                
                LOGGER.info(preparedStatement.toString());

            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Epicrisis::saveDB(): " + ExceptionHelper.toString(ex));
        }
   }
   
    // Return methods
    public String epiName() {
        return NAME;
    }

    public String epiDescription() {
        return DESCRIPTION;
    }
    
     public int id() {
        return ID;
    }
     public Date getDate() {
        return DATE;
    }
      public int getDocId() {
        return DOCTOR_ID;
    }
     public int getPatientId() {
        return PATIENT_ID;
    }
    // Set methods
    public void setEpiName(String name) {
        this.NAME = name;
    }

    public void setEpiDescription(String name) {
        this.DESCRIPTION = name;
    }
    
    public void setDoctorId(int id){
        this.DOCTOR_ID=id;
    }
    
    
    private void resolveID() {
        try {
            resultSet = DBDoor.getStatement()
                    .executeQuery("select max(epicrisis_id)+1 as epicrisis_id from epicrisis");
            resultSet.next();
            ID = resultSet.getInt("epicrisis_id");
            DATE=new Date();
            //System.out.println("ID resolved:" + this.ID);
        } catch (Exception ex) {
           LOGGER.error("Epicrisis::resolveID(): " + ExceptionHelper.toString(ex));
            //ex.printStackTrace();
        }
    }
     public void save() {
        if (this.ID >= 0) {
            this.saveDB("U");
            
        } else {
            this.resolveID();
            LOGGER.info("Epicrisis saved for patient");
            this.saveDB("I");
        }
    }
}
