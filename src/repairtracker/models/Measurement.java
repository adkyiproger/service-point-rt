/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ehospital.model;
import ehospital.code.EHospital;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author pigeon
 */
public class Measurement {
    private Connection DB=DBDoor.getConn();;
    private int DEP_ID=EHospital.DEP_ID;
    private int DOC_ID=-1;
    private int P_ID=-1;
    private int ID=-1;
    private Date DATE;
    private PreparedStatement preparedStatement=null;
    private static java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ehospital/code/Bundle");
    
    // SQLs
    
    
    public Measurement(int pid, int dep, int doc, Date date){
        System.out.println("Measurement will be created");
        this.DEP_ID=dep;
        this.DOC_ID=doc;
        this.DATE=date;
        this.P_ID=pid;
        insertRecord();
    }
    public Measurement(Integer id){
        System.out.println("Measurement id is: "+id);
        this.ID=id;
        loadDB();
    }
    private void insertRecord() {
        try {
            ResultSet resultSet = DBDoor.executeSelect("select max(measurement_id)+1 as measurement_id from measurements");
            resultSet.next();
            ID = resultSet.getInt("measurement_id");
            if (ID>0) {
                preparedStatement = DB
                        .prepareStatement("insert into measurements values (?,?,?,?,?,0);");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setInt(2, this.DEP_ID);
                preparedStatement.setInt(3, this.P_ID);
                preparedStatement.setInt(4, this.DOC_ID);
                preparedStatement.setDate(5,new java.sql.Date(this.DATE.getTime()));
                preparedStatement.execute();
            }
            System.out.println("ID resolved:" + this.ID);
        } catch (Exception ex) {
            System.err.println("Patient resolveID(): " + ex.toString());
            ex.printStackTrace();
        }
    }
    public int getDocId(){
        return DOC_ID;
    }
    
    public Date getDate(){
        return DATE;
    }
    
    public int getPatientId(){
        return P_ID;
    }
    public void setDate(Date date){
        System.out.println("Date: "+date);
        
    }
    public void setPId(int pid){
        this.P_ID=pid;
    }
    public int id(){
        return this.ID;
    }
    public int getInt(String type) {
        int value=1;
        try {
        ResultSet resultSet = DBDoor.executeSelect(
                "select intval from measurevalues where measurement_id="+ID+" "
                        + "and type='"+type+"';");
        resultSet.next();
                    System.out.println("Measurement ID: "+ID+" type: "+type);
                    value=resultSet.getInt("intval");
        } catch (SQLException ex) {
            System.err.println("public Measurement getInt(): "+ex.toString());
         //   ex.printStackTrace();
        }
        
        return value;
    }
    public double getDouble(String type) {
        double value=1;
        try {
               ResultSet resultSet = DBDoor.getStatement().executeQuery(
                "select doubleval from measurevalues where measurement_id="+ID+" "
                        + "and type='"+type+"';");
                resultSet.next();
                value=resultSet.getDouble("doubleval");
                    System.out.println("public Measurement getDouble():"+resultSet.getDouble("doubleval")+"Value: "+value+" type: "+type);
                    
        } catch (SQLException ex) {
            System.err.println("public Measurement getDouble(): "+ex.toString());
          //  ex.printStackTrace();
}
        return value;
    }
    private void loadDB() {
        try {
            ResultSet resultSet = DBDoor.executeSelect(
                    "select * from measurements where measurement_id=" + ID + " "
                    + "and department_id='" + DEP_ID + "';");
            resultSet.next();
            System.out.println("public Measurement loadDB():" + resultSet.getDouble("doctor_id"));
            DOC_ID = resultSet.getInt("doctor_id");
            P_ID=resultSet.getInt("patient_id");
            DATE=resultSet.getDate("date");
        } catch (SQLException ex) {
            System.err.println("public Measurement loadDB(): " + ex.toString());
          //  ex.printStackTrace();
        }
    }
    public String getString(String type) {
       String value="1";
       try {
       ResultSet resultSet = DBDoor.getStatement().executeQuery(
                "select stringval from measurevalues where measurement_id="+ID+" "
                        + "and type='"+type+"';");
        resultSet.next();
                    //System.out.println(resultSet.getShort("patient_id")+" "+resultSet.getString("mname")+" "+resultSet.getString("fname"));
                    value=resultSet.getString("stringval");
        } catch (SQLException ex) {
            System.err.println("public 1Measurement getString(String type): "+ex.getSQLState());
        }
        return value;
    }
    public void setValue(String type, String value) {
       int id=-1;
       try {
       ResultSet resultSet = DBDoor.getStatement().executeQuery(
                "select max(measureval_id)+1 as measureval_id from measurevalues;");
        resultSet.next();
        id=resultSet.getInt("measureval_id");
        if (id>0) {
                preparedStatement = DB
                        .prepareStatement("insert into measurevalues (measureval_id, measurement_id, stringval, type) values (?,?,?,?);");
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, this.ID);
                preparedStatement.setString(3, value );
                preparedStatement.setString(4, type );
                preparedStatement.execute();
            }
            System.out.println("ID resolved:" + this.ID);
        } catch (SQLException ex) {
            System.err.println("public Measurements setString(): "+ex.toString());
            ex.printStackTrace();
        }
       // return value;
    } 
        public void setValue(String type, int value) {
       int id=-1;
       try {
       ResultSet resultSet = DBDoor.getStatement().executeQuery(
                "select max(measureval_id)+1 as measureval_id from measurevalues;");
        resultSet.next();
        id=resultSet.getInt("measureval_id");
        if (id>0) {
                preparedStatement = DB
                        .prepareStatement("insert into measurevalues (measureval_id, measurement_id, intval, type) values (?,?,?,?);");
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, this.ID);
                preparedStatement.setInt(3, value );
                preparedStatement.setString(4, type );
                preparedStatement.execute();
            }
            System.out.println("ID resolved:" + this.ID);
        } catch (SQLException ex) {
            System.err.println("public Measurements  setValue(): "+ex.toString());
            ex.printStackTrace();
        }
        //return value;
    } 
        public void setValue(String type, Double value) {
       int id=-1;
       try {
       ResultSet resultSet = DBDoor.getStatement().executeQuery(
                "select max(measureval_id)+1 as measureval_id from measurevalues;");
        resultSet.next();
        id=resultSet.getInt("measureval_id");
        if (id>0) {
                preparedStatement = DB
                        .prepareStatement("insert into measurevalues (measureval_id, measurement_id, doubleval, type) values (?,?,?,?);");
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, this.ID);
                preparedStatement.setDouble(3, value );
                preparedStatement.setString(4, type );
                preparedStatement.execute();
            }
            System.out.println("ID resolved:" + this.ID);
        } catch (SQLException ex) {
            System.err.println("public Measurements  setDouble(): "+ex.toString());
            ex.printStackTrace();
        }
        //return value;
    } 
  public static String getBeanNameByID(int meas_id){
      try {
        ResultSet resultSet = DBDoor.getStatement().executeQuery("select CLASS from CMDREG C join measurements M on M.department_id=C.DEP_ID where measurement_id="+meas_id+";");
        resultSet.next();
        System.out.println(resultSet.getString("CLASS"));
        return resultSet.getString("CLASS");
        } catch (SQLException ex) {
            
            System.err.println("public Measurement static String getBeanNameByID(int meas_id)"+ex.toString());
            return "NA";
        }
      
  
  }
    public static String getBeanNameByDep(int dep_id) {
        try {
            ResultSet resultSet = DBDoor.getStatement().executeQuery("select CLASS from CMDREG C where C.DEP_ID=" + dep_id + ";");
            resultSet.next();
            System.out.println(resultSet.getString("CLASS"));
            return resultSet.getString("CLASS");
        } catch (SQLException ex) {
            System.err.println("public Measurement static String getBeanNameByID(int meas_id)" + ex.toString());
            return "NA";
        }

    }
    public static DefaultTableModel listMeasurements(int pid){
        // pid : patient id
        String LIST_SQL="select measurement_id, date from measurements  where patient_id="+pid+" and patient_id>0 and department_id="+EHospital.DEP_ID;

        System.out.println("DefaultTableModel listMeasurements(int "+pid+")");
        
        Object[][] rowDATA={};
        String[] colNames={ "ID", bundle.getString("PatientGUI.MEASUREMENTS.columnModel.title1") };
        DefaultTableModel measurements=new DefaultTableModel(rowDATA, colNames);
   //l     measurements.set
        try {
        ResultSet resultSet = DBDoor.getStatement().executeQuery(LIST_SQL);
        
       
            while (resultSet.next()) {
                    System.out.println(resultSet.getString("measurement_id")+" "+resultSet.getString("date"));
                    measurements.addRow(new Object[]{resultSet.getInt("measurement_id"),
                        resultSet.getString("date")});
                }
        } catch (SQLException ex) {
            
            System.err.println("public PatientsList listPatients(): "+ex.toString());
            ex.printStackTrace();
        }
        
       return measurements;
    }

}
