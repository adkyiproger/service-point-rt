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

/**
 *
 * @author pigeon
 */
public class RTProperties {
    
    static String SEP = File.separator;
    static String HOME = System.getProperty("user.home");
    static String TEMP_DIR = System.getProperty("java.io.tmpdir");
    public static String APP_NAME = "SPRepatirTracker";
    public static String APP_HOME = HOME + SEP + APP_NAME;
    static String PROPS_FILE = APP_HOME + SEP + "config.properties";
    static String PROPS_XML = APP_HOME + SEP + "config.xml";

    public static boolean SaveProperties(java.util.Properties props) {
        OutputStream output = null;
        OutputStream output_xml = null;
        try {

            output = new FileOutputStream(PROPS_FILE);
            output_xml = new FileOutputStream(PROPS_XML);
            props.store(output, "properties for "+APP_NAME);
            props.storeToXML(output_xml, "properties for "+APP_NAME);
            return true;
        } catch (IOException io) {
            io.printStackTrace();
            return false;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    public static java.util.Properties ReadProperties() {
        java.util.Properties prop = new java.util.Properties();

        InputStream input = null;
        
        

        try {
            File infile=new File(PROPS_FILE);
           // File inxml=new File(PROPS_XML);
            File appdir = new File(APP_HOME);
            if(!appdir.exists()) {
                appdir.mkdir();
            }
            if(!infile.exists()) 
                infile.createNewFile();
            
            
           // if(!inxml.exists())
               // inxml.createNewFile();
            input = new FileInputStream(PROPS_FILE);
            

            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
}
