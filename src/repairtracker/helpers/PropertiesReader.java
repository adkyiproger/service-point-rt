/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repairtracker.helpers;

import repairtracker.RepairTracker;
import repairtracker.RTProperties;
import static repairtracker.RTProperties.APP_HOME;
import static repairtracker.helpers.PropertiesReader.LOGGER;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pigeon
 */
public class PropertiesReader {
    public static Logger LOGGER=LogManager.getLogger(PropertiesReader.class.getName());
    
    public static String[] getFileAsStringList(String file){
        List<String> list=getFileAsList(file);
        
        String lst[] =(String[])list.toArray();
     
    
    return lst;
    }
    
    public static DefaultListModel<String> getListModel(String file){
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String l : getFileAsList(file))        model.addElement(l);
        return model;
    }
    
    public static DefaultComboBoxModel<String> getComboboxModel(String file){
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String l : getFileAsList(file)) model.addElement(l);
        return model;
    }
    
    public static String getFileAsString(String file) {
        StringBuilder sb = new StringBuilder();
        String str="";
        List<String> list=getFileAsList(file);
        
         for (String l : list) sb.append(l+"\n");
        str=sb.toString();
        return str;
    }
    
    public static List<String> getFileAsList(String file) {
        List<String> lst = new ArrayList<>();

        String locale_file = file.replace(".txt", "_" + Locale.getDefault() + ".txt");

        try {
            InputStream ins = null;
            File infile = new File(RTProperties.APP_HOME + "/"+locale_file);

            if (infile.exists()) {
                LOGGER.info("Reading "+locale_file+" from application directory");
                ins = new FileInputStream(infile);
//                file = locale_file;
                LOGGER.info("File "+locale_file+" found");
            } else {
                
                try {
                    
                ins = RTProperties.class.getClassLoader().getResourceAsStream(locale_file);
                if (ins==null) {
                    LOGGER.info("Locale specific file "+locale_file+" not found using generic file. Trying to load "+file);
                    ins = RTProperties.class.getClassLoader().getResourceAsStream(file);
                }
                LOGGER.info("File "+locale_file+" found in embeded sources");
                } catch (Exception e) {
                    LOGGER.error("Unable to read resource "+e.toString());
                    
//                    file=locale_file;
                }
            }
  //          LOGGER.info("Reading file: " + file);
            InputStreamReader ir = new InputStreamReader(ins,"utf8");
            BufferedReader br = new BufferedReader(ir);

            String line = null;

            while ((line = br.readLine()) != null) {
                lst.add(line);
            }
            
            ins.close();
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        

        return lst;
    }
    
    public static void savePropertiesFile(String filename, String str) {
            SaveFile t=new SaveFile(filename,str);
            t.run();
    }
}

class SaveFile implements Runnable {
   private Thread t;
   private String fileName;
   private String outStr;
   private Logger LOGGER=LogManager.getLogger(PropertiesReader.class.getName());
   
   SaveFile(String filename, String str){
       fileName = filename;
       outStr=str;
       LOGGER.info("Creating " +  fileName );
   }
   
   @Override
   public void run() {
       
        String appDir="";

        fileName=RTProperties.APP_HOME + "/"+ fileName.replace(".txt", "_" + Locale.getDefault() + ".txt");
 
        LOGGER.info("Init file object:"+fileName);
        File infile=new File(fileName);
        
            if(!infile.exists()) {
            try {
                LOGGER.info("Trying to create new file");
                infile.getParentFile().mkdirs();
                
            } catch (Exception ex) {
                LOGGER.error("Something critical happened: \n"+ex);
            }
            }

            LOGGER.info("Saving file:"+fileName);
                
        try (PrintStream out = new PrintStream(new FileOutputStream(fileName),true,"utf8")) {
            
                out.print(outStr);
                out.close();
            LOGGER.info("Properties file: "+fileName+" saved");
        } catch (Exception e) {
            LOGGER.error("Properties file: "+fileName+" not saved:");
            LOGGER.error(e.toString());
        }
        
   }
   
   public void start ()
   {
      if (t == null)
      {
         t = new Thread (this, fileName);
         t.start ();
      }
   }

}
