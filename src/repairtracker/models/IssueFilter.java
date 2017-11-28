/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repairtracker.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Date;

/**
 *
 * @author user
 */
public class IssueFilter {
    
    
    private static java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("repairtracker/views/Bundle"); // NOI18N
    private static Logger LOGGER=LogManager.getLogger(IssueFilter.class.getName()); 

    // Declaration of Read only table model
    Object[][] rowDATA = {};
    String[] colNames = {"#ID", java.util.ResourceBundle.getBundle("repairtracker/models/Bundle").getString("DEVICE"),java.util.ResourceBundle.getBundle("repairtracker/models/Bundle").getString("CLIENT_NAME"), java.util.ResourceBundle.getBundle("repairtracker/models/Bundle").getString("PHONE"), java.util.ResourceBundle.getBundle("repairtracker/models/Bundle").getString("TOTAL_COST"),java.util.ResourceBundle.getBundle("repairtracker/models/Bundle").getString("TOTAL_WORK"), java.util.ResourceBundle.getBundle("repairtracker/models/Bundle").getString("START_DATE"), java.util.ResourceBundle.getBundle("repairtracker/models/Bundle").getString("END_DATE"), java.util.ResourceBundle.getBundle("repairtracker/models/Bundle").getString("STATUS")};
    
    DefaultTableModel TABLE_MODEL = new DefaultTableModel(rowDATA, colNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    
    // filter criteria
    Boolean F_ISSUE=false;
    Boolean F_STATUS=false;
    Boolean F_CLIENT=false;
    Boolean F_START=false;
    Boolean F_END=false;
    
    // Variables to hold filter values:
    private Integer ISSUE_ID=-1;
    private Date START_DATE;
    private Date END_DATE;
    private Integer STATES=0;
    private String CLIENT="";
    // Statement registry:
    //   0. Generic statement:
    private String SQL = "select iss.issue_id as issue_id, devicename, fname, phone, totalcost,"
            + " (select sum(price) from issueattributes ia where ia.issue_id=iss.issue_id and ia.issueattrtype_id=0) as total_work, "
            + "startdate, enddate, status from issues iss"
                    + " join clients cl on iss.client_id=cl.client_id"
                    + " join address ad on iss.client_id=ad.client_id"
                    + " where 1=1";
    //    1. by ISSUE_ID:
    private static final String BY_ISSUE_ID=" and iss.issue_id=?";
    //    2. by Client name:
    private static final String BY_CLIENT=" and lower(fname) like ?";
    //    3. by STATUS:
    private static final String BY_STATUS=" and status=?";
    //    4. by Start date:
    private static final String BY_START_DATE=" and startdate>=? and startdate<=? ";
    //    5. by End date:
    private static final String BY_END_DATE=" and enddate>=? and enddate<=? ";

    public void setClient(String client){
        if (client.equalsIgnoreCase("")==false) {
            F_CLIENT=true;
            CLIENT="%"+client.toLowerCase()+"%";
        }
    }
    
    public void setIsseID(Integer id){
        if (id>-1) {
            F_ISSUE=true;
            ISSUE_ID=id;
        }
    }
    
    public void setStatus(Integer status){
        if (status>-1) {
            F_STATUS=true;
            STATES=status;
        }
    }
        
    public void setDate(Date s_date1, Date s_date2, int start_or_end){
            if (start_or_end==0) F_START=true;
            if (start_or_end==1) F_END=true;
            START_DATE=s_date1;
            END_DATE=s_date2;
    }
            
   
    public DefaultTableModel getModel(){
        applyFilter();
        return TABLE_MODEL;
    }
    
    private void applyFilter(){
        
        if (F_ISSUE) {
            SQL+=BY_ISSUE_ID;
        }
        else {
            if (F_STATUS) SQL+=BY_STATUS;
            if (F_CLIENT) SQL+=BY_CLIENT;
            if (F_START) SQL+=BY_START_DATE;
            if (F_END) SQL+=BY_END_DATE;
        }
        try {
            PreparedStatement ps=DBDoor.getConn().prepareStatement(SQL);
            if (F_ISSUE) {
                ps.setInt(1, ISSUE_ID);
                getListFromDB(ps);
            } else {
                int ii=1;
                
                if (F_STATUS) { ps.setInt(ii, STATES); 
                ii++;
                }
                if (F_CLIENT) { ps.setString(ii, CLIENT); 
                ii++;
                }
                if (F_START || F_END) { 
                    ps.setDate(ii, new java.sql.Date(START_DATE.getTime()) );
                    ii++;
                    ps.setDate(ii, new java.sql.Date(END_DATE.getTime()) );
                }
                getListFromDB(ps);
                LOGGER.info("SQL: "+SQL);
            }
        } catch (SQLException ex) {
                LOGGER.error("SQL: "+SQL);
                LOGGER.error(ex.getMessage(), ex);
        }
        
    }
    
    
    
    // Get list from DB by prepared statement
    private void getListFromDB(PreparedStatement ps) {
        try {
        ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                    TABLE_MODEL.addRow(new Object[]{resultSet.getInt("issue_id"),
                                resultSet.getString("devicename"),
                                resultSet.getString("fname"),
                                resultSet.getString("phone"),
                                resultSet.getDouble("totalcost"),
                                resultSet.getDouble("total_work"),
                                resultSet.getDate("startdate"),
                                resultSet.getDate("enddate"),
                                java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("ISSUE_STATUS").split(",")[resultSet.getInt("status")]});
                    LOGGER.info("Issue status: "+java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("ISSUE_STATUS").split(",")[resultSet.getInt("status")]);
                }
        } catch (SQLException ex) {
            LOGGER.error("Issue::getListFromDB(): "+ex.getMessage(),ex);
        }
       
    }
}
