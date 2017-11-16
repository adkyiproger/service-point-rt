/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repairtracker.models;

import repairtracker.models.DBDoor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author pigeon
 */
public class Address {
    private static Logger LOGGER=LogManager.getLogger(Address.class.getName()); 
    private Integer ID=-1;
    private String REGION;
    private String DISTRICT;
    private String ZIP;
    private String CITY;
    private String ADDRESS1;
    private String PHONE;
    private String EMAIL;
    private Integer P_ID=-1;
    private Connection DB=DBDoor.getConn();
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
   public Address(){
        this(-1);
    }
   public Address(Integer id){
        LOGGER.info("Address:: new Address("+id+")");
        this.P_ID=id;
        loadDB();
        
    }
   public static String[] listDistrictNames(){
        return sqlToList("select district from address where length(district)>1 group by district");
    }
   public static String[] listRegionNames(){
        return sqlToList("select region from address where length(region)>1 group by region");
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
            LOGGER.error("private static String[] sqlToList(String sql): "+Arrays.toString(ex.getStackTrace()));
            
        }
       }
        String[] stockArr = new String[list.size()];
        stockArr = list.toArray(stockArr);
        
        return stockArr;
    
   }
    private void loadDB() {
        if (P_ID>0) {
        try {
            resultSet = DBDoor.getStatement()
                    .executeQuery("select * from address where client_id=" + this.P_ID);
            if (resultSet.next()) {
           // System.out.println(resultSet.getShort("patient_id") + resultSet.getString("mname") + resultSet.getString("fname"));
            ID=resultSet.getInt("address_id");
            REGION=resultSet.getString("region");
            DISTRICT=resultSet.getString("district");
            ZIP=resultSet.getString("zip");
            ADDRESS1=resultSet.getString("address1");
            CITY=resultSet.getString("city");
            PHONE=resultSet.getString("phone");
            EMAIL=resultSet.getString("email");
            }
        } catch (Exception ex) {
            LOGGER.error("Patient resolveID(): " + ex.toString());
            //ex.printStackTrace();
        }
        }
    }

    private void resolveID() {
        try {
            resultSet = DBDoor.getStatement()
                    .executeQuery("select max(address_id)+1 as address_id from address");
            resultSet.next();
            ID = resultSet.getInt("address_id");
            LOGGER.info("address_id resolved:" + this.ID);
        } catch (Exception ex) {
            LOGGER.error("address_id resolveID(): " + ex.toString());
            //ex.printStackTrace();
        }
    }
    private void saveDB(String mode) {
      LOGGER.info("Address DUMP:"
                        +"REGION="+this.REGION
                        +" DISTRICT="+this.DISTRICT
                +" ZIP="+this.ZIP
                +" CITY="+this.CITY
                +" ADDRESS1="+this.ADDRESS1
                        +" PHONE="+this.PHONE
                        +" EMAIL="+this.EMAIL
                +" ID="+this.ID);
        try {
            if (mode.equals("I")) {
                
                LOGGER.info("Insert Mode");
                preparedStatement = DB
                        .prepareStatement("insert into address values (?,?,?,?,?,?,?,?,?)");
                preparedStatement.setInt(1, this.ID);
                preparedStatement.setString(2, this.REGION);
                preparedStatement.setString(3, this.DISTRICT);
                preparedStatement.setString(4, this.ZIP);
                preparedStatement.setString(5, this.CITY);
                preparedStatement.setString(6, this.ADDRESS1);
                preparedStatement.setString(7, this.PHONE);
                preparedStatement.setString(8, this.EMAIL);
                preparedStatement.setInt(9, (this.P_ID));
            }
            if (mode.equals("U")) {
                
                LOGGER.info("Update Mode");
                preparedStatement = DB
                        .prepareStatement("UPDATE address SET region=?,district=?,zip=?,city=?,address1=?,phone=?,email=? WHERE address_id=? ");
               
                preparedStatement.setString(1, this.REGION);
                preparedStatement.setString(2, this.DISTRICT);
                preparedStatement.setString(3, this.ZIP);
                preparedStatement.setString(4, this.CITY);
                preparedStatement.setString(5, this.ADDRESS1);
                preparedStatement.setString(6, this.PHONE);
                preparedStatement.setString(7, this.EMAIL);
                preparedStatement.setInt(8, this.ID);
                   
            }

            preparedStatement.executeUpdate();
            
        } catch (Exception ex) {
            LOGGER.error("Address saveDB(): " + ex.toString());
            //ex.printStackTrace();
        }

    }
    public String region(){
        return REGION;
    }
    public String district(){
        return DISTRICT;
    }
    public String zip(){
        return ZIP;
    }
    public String city(){
        return CITY;
    }
    public String address1(){
        return ADDRESS1;
    }
    public String email(){
        return EMAIL;
    }
    public String phone(){
        return PHONE;
    }
    public int patientId(){
        return P_ID;
    }
    public void setRegion(String name){
        this.REGION=name;
    }
    public void setDistrict(String name){
        this.DISTRICT=name;
    }
    public void setZIP(String name){
        this.ZIP=name;
    }
    public void setCity(String name){
        this.CITY=name;
    }
    public void setEmail(String name){
        this.EMAIL=name;
    }
    public void setPhone(String name){
        this.PHONE=name;
    }
    public void setAddress(String name){
        this.ADDRESS1=name;
    }
    public void setPatientId(int name){
        this.P_ID=name;
    }

    public void save() {
       //System.out.print(this.DAYOFBIRTH.toString());
        if (this.ID>0) {
            this.saveDB("U");
        } else {
            this.resolveID();
            this.saveDB("I");
        }
    LOGGER.info(ID+"   "+EMAIL+":"+CITY);
    }
}
