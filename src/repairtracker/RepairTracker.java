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
<<<<<<< HEAD
    public static String JDBCTYPE = PROPERTIES.getProperty("JDBCTYPE", "derby");
    // Derby configuration
    public static String DERBY_DBNAME = PROPERTIES.getProperty("DERBY_DBNAME", RTProperties.APP_HOME+FS+"db/rt");
    // MySQL configuration
    public static String DBHOST = PROPERTIES.getProperty("DBHOST", "127.0.0.1");
    public static String DBNAME = PROPERTIES.getProperty("DBNAME", "rt");
    public static String DBPASS = PROPERTIES.getProperty("DBPASS", "grandy");
    public static String DBUSER = PROPERTIES.getProperty("DBUSER", "doctor");
=======
    public static String DBHOST = PROPERTIES.getProperty("DBHOST", "192.168.217.130");
    public static String DBNAME = PROPERTIES.getProperty("DBNAME", "ehospital");
     public static String DERBY_DBNAME = PROPERTIES.getProperty("DERBY_DBNAME", PROPERTIES.getProperty("APP_HOME")+"hospital");
     public static String DBPASS = PROPERTIES.getProperty("DBPASS", "grandy");
     public static String DBUSER = PROPERTIES.getProperty("DBUSER", "doctor");
     public static String JDBCTYPE = PROPERTIES.getProperty("JDBCTYPE", "derby");
>>>>>>> 633419db9a10230c8d4e73d436fcee207ef0aacf
    
    // Setup initial look and feel
    public static String THEME = PROPERTIES.getProperty("THEME", "Metal");
    public static String THEME_CLASS = PROPERTIES.getProperty("THEME_CLASS", "javax.swing.plaf.metal.MetalLookAndFeel");
    
    
    public static Logger LOGGER=LogManager.getLogger();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.print("Repair Tracker started!");
        String lc=LOCALE.substring(0,2), lm=LOCALE.substring(3,5);
                
                Locale.setDefault(new Locale(lc,lm));
                System.out.println(System.getProperty("user.dir"));
                if (LOG_FILE=="NA")
                    
                    LOG_FILE=RTProperties.APP_HOME+FS+"logs"+FS+RTProperties.APP_NAME+".log";
                System.setProperty("logFilename", LOG_FILE);
                //System.out.println("App dir: "+System.);
                org.apache.logging.log4j.core.LoggerContext ctx =
    (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
ctx.reconfigure();
         try {
             
             //new McWinLookAndFeel();
             UIManager.setLookAndFeel(THEME_CLASS);
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
        e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
       // handle exception
        e.printStackTrace();
    }
    catch (InstantiationException e) {
       // handle exception
        e.printStackTrace();
    }
    catch (IllegalAccessException e) {
       // handle exception
        e.printStackTrace();
    }
                 DBDoor.setupMyConnection();
               //   LogonForm LF = new LogonForm();
                 //   LogManager.getLogger(EHospital.class.getName()).info("Application initialized");
                 RepairTrackerGUI rt=new RepairTrackerGUI();
                 rt.setVisible(true);
    
    }
    
}
