/*
 * To change this license header, choose License Headers in Project RTProperties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repairtracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author pigeon
 */
public class RTProperties {
    private static Logger LOGGER=LogManager.getLogger(RTProperties.class.getName()); 
    public static String FS = File.separator;
    static String HOME = System.getProperty("user.home");
    static String TEMP_DIR = System.getProperty("java.io.tmpdir");
    public static String APP_NAME = "RepatirTracker";
    public static String APP_VERSION = "0.1";
    public static String APP_HOME = HOME + FS + APP_NAME;
    static String PROPS_FILE = APP_HOME + FS + "config.properties";
    static String PROPS_XML = APP_HOME + FS + "config.xml";

    public static boolean SaveProperties(java.util.Properties props) {
        OutputStream output = null;
        OutputStream output_xml = null;

        try {

            output = new FileOutputStream(PROPS_FILE);
            output_xml = new FileOutputStream(PROPS_XML);
            props.store(output, "properties for " + APP_NAME);
            props.storeToXML(output_xml, "properties for " + APP_NAME);
            
            LOGGER.info("Properties file have been saved: "+PROPS_FILE);
            
            return true;
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    ex.printStackTrace();
                }
            }
            return false;
        }
    }

    public static java.util.Properties ReadProperties() {
        java.util.Properties prop = new java.util.Properties();

        InputStream input = null;

        try {
            File infile = new File(PROPS_FILE);
            // File inxml=new File(PROPS_XML);
            File appdir = new File(APP_HOME);
            if (!appdir.exists()) {
                appdir.mkdir();
            }
            if (!infile.exists()) {
                infile.createNewFile();
            }
            input = new FileInputStream(PROPS_FILE);
            // load a properties file
            prop.load(input);
            input.close();
            LOGGER.info("Properties read from file: "+PROPS_FILE);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    ex.printStackTrace();
                }
            }
        }
        return prop;
    }
}
