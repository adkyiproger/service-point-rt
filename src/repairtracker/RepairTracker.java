/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repairtracker;
import java.util.Locale;
import java.util.Properties;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repairtracker.models.DBDoor;
import repairtracker.views.RepairTrackerGUI;



/**
 *
 * @author pigeon
 */
public class RepairTracker {
    //

    // System properties
    public static String FS=System.getProperty("file.separator","/");
    public static Properties PROPERTIES=RTProperties.ReadProperties();
    public static String LOCALE = PROPERTIES.getProperty("LOCALE_CODE", "ru_RU");
    public static String LOG_FILE = PROPERTIES.getProperty("LOG_FILE", "NA");
    
    // Database connection properties
    public static String JDBCTYPE = PROPERTIES.getProperty("JDBCTYPE", "derby");
    // Derby configuration
    public static String DERBY_DBNAME = PROPERTIES.getProperty("DERBY_DBNAME", RTProperties.APP_HOME+FS+"db/rt");
    // MySQL configuration
    public static String DBHOST = PROPERTIES.getProperty("DBHOST", "127.0.0.1");
    public static String DBNAME = PROPERTIES.getProperty("DBNAME", "rt");
    public static String DBPASS = PROPERTIES.getProperty("DBPASS", "grandy");
    public static String DBUSER = PROPERTIES.getProperty("DBUSER", "doctor");
    
    // Setup initial look and feel
    public static String THEME = PROPERTIES.getProperty("THEME", "Metal");
    public static String THEME_CLASS = PROPERTIES.getProperty("THEME_CLASS", "javax.swing.plaf.metal.MetalLookAndFeel");
    
    
    public static Logger LOGGER;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String lc=LOCALE.substring(0,2), lm=LOCALE.substring(3,5);
                
                Locale.setDefault(new Locale(lc,lm));
                
                if (LOG_FILE=="NA")
                    
                    LOG_FILE=RTProperties.APP_HOME+FS+"logs"+FS+RTProperties.APP_NAME+".log";
                System.setProperty("logFilename", LOG_FILE);
                System.setProperty("derby.stream.error.file", RTProperties.APP_HOME+FS+"logs"+FS+"derby.log");
                //System.out.println("logFilename: "+LOG_FILE);
                //org.apache.logging.log4j.core.LoggerContext ctx =
    //(org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
//ctx.reconfigure();
LOGGER=LogManager.getLogger();
LOGGER.info("Repair Tracker started!");
         try {
             
             //new McWinLookAndFeel();
             UIManager.setLookAndFeel(THEME_CLASS);
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
        LOGGER.error(e.getMessage(),e);
        
    }
    catch (ClassNotFoundException e) {
       // handle exception
        LOGGER.error(e.getMessage(),e);
    }
    catch (InstantiationException e) {
       // handle exception
        LOGGER.error(e.getMessage(),e);
    }
    catch (IllegalAccessException e) {
       // handle exception
        LOGGER.error(e.getMessage(),e);
    }
         try {
                DBDoor.setupMyConnection();
         
                //   LogonForm LF = new LogonForm();
                //   LogManager.getLogger(EHospital.class.getName()).info("Application initialized");
                RepairTrackerGUI rt=new RepairTrackerGUI();
                rt.setVisible(true);
            } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
         }
    }
    
}
