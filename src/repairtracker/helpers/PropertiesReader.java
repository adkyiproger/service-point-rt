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
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static repairtracker.helpers.PropertiesReader.FS;

/**
 *
 * @author pigeon
 */
public class PropertiesReader {
    public static Logger LOGGER=LogManager.getLogger(PropertiesReader.class.getName());
    public static String FS=System.getProperty("file.separator","/"); //NOI18N
    
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
    
    public static DefaultListModel<String> getFilesAsListModel(String path){
        DefaultListModel<String> model = new DefaultListModel<>();
        listFiles(path).forEach((l) -> {
            model.addElement(l);
        });
        return model;
    }
    
        public static DefaultComboBoxModel<String> getFilesAsComboboxModel(String path){
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        listFiles(path).forEach((l) -> {
            model.addElement(l);
        });
        return model;
    }
    
    public static DefaultComboBoxModel<String> getComboboxModel(String file){
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String l : getFileAsList(file)) model.addElement(l);
        return model;
    }
    
    public static String getFileAsString(String file) {
        StringBuilder sb = new StringBuilder();
        List<String> list=getFileAsList(file);
        
        list.forEach((l) -> {
            sb.append(l).append("\n"); //NOI18N
        });
        String str=sb.toString();
        return str;
    }
    
    public static Boolean renameFile(String src_path, String dest_path){
        File infile = new File(RTProperties.APP_HOME+FS+src_path);
        File dest_file=new File(RTProperties.APP_HOME+FS+dest_path);

            if (infile.exists()) {
                LOGGER.info("Rename file "+src_path+" => "+dest_path); //NOI18N
                infile.renameTo(dest_file);
              //  infile.delete();
              
            }
            
        return true;    
    }
    
    public static List<String> getPropertiesFiles(){
        File folder = new File(RTProperties.APP_HOME+FS);
        File[] listOfFiles = folder.listFiles();
        List<String> f= new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
        if (listOfFiles[i].isFile()) {
        System.out.println("File " + listOfFiles[i].getName()); //NOI18N
        f.add(listOfFiles[i].getName());
       } else if (listOfFiles[i].isDirectory()) {
        System.out.println("Directory " + listOfFiles[i].getName()); //NOI18N
       }
      }
        return f;
    }
    
    public static DefaultTableModel getTableModel(String file) {
        Object[][] rowDATA = {};
        String[] colNames = {java.util.ResourceBundle.getBundle("repairtracker/helpers/Bundle").getString("ITEMNAME"),java.util.ResourceBundle.getBundle("repairtracker/helpers/Bundle").getString("PRICE")};
        
        DefaultTableModel _model = new DefaultTableModel(rowDATA, colNames) {
            @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
        };

        getFileAsList(file).forEach((l) -> {
            String item_name, price;
            item_name=l.split("\\|")[0]; //NOI18N
            if (l.split("\\|").length<2) { price="0"; //NOI18N
            } else {
                price=l.split("\\|")[1]; //NOI18N
            }
            _model.addRow(new Object[]{item_name,price});
            LOGGER.info("Property: " +l); //NOI18N
        });
        
        return _model;
    }
    
    public static List<String> listFiles(String path){
        List<String> lst = new ArrayList<>();
        File folder = new File(RTProperties.APP_HOME+FS+path);
        LOGGER.info("Init file object:"+path); //NOI18N
            if(!folder.exists()) {
            try {
                LOGGER.info("Trying to create new file"); //NOI18N
                folder.mkdirs();
                
            } catch (Exception ex) {
                LOGGER.error("Something critical happened: \n"+ex.getMessage(),ex); //NOI18N
            }
            }
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles.length>0) {
        for (File file : listOfFiles) {
        if (file.isFile()) {
            lst.add(file.getName());
        } }
}
    
    return lst;
        
    }
    
    
    public static List<String> getFileAsList(String file) {
        List<String> lst = new ArrayList<>();

        //String locale_file = file.replace(".txt", "_" + Locale.getDefault() + ".txt"); //NOI18N //NOI18N
        String locale_file=file;
        try {
            InputStream ins = null;
            File infile = new File(RTProperties.APP_HOME+FS+locale_file);

            if (infile.exists()) {
                LOGGER.info("Reading "+locale_file+" from application directory"); //NOI18N
                ins = new FileInputStream(infile);
//                file = locale_file;
                LOGGER.info("File "+locale_file+" found"); //NOI18N
            } else {
                
                try {
                    
                ins = RTProperties.class.getClassLoader().getResourceAsStream(locale_file);
                if (ins==null) {
                    LOGGER.info("Locale specific file "+locale_file+" not found using generic file. Trying to load "+file); //NOI18N
                    ins = RTProperties.class.getClassLoader().getResourceAsStream(file);
                }
                LOGGER.info("File "+locale_file+" found in embeded sources"); //NOI18N
                } catch (Exception e) {
                    LOGGER.error("Unable to read resource "+e.toString()); //NOI18N
                    
//                    file=locale_file;
                }
            }
  //          LOGGER.info("Reading file: " + file);
            InputStreamReader ir = new InputStreamReader(ins,"utf8"); //NOI18N
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
       LOGGER.info("Creating " +  fileName ); //NOI18N
   }
   
   @Override
   public void run() {
       
        String appDir=""; //NOI18N

        //fileName=RTProperties.APP_HOME +FS+ fileName.replace(".txt", "_" + Locale.getDefault() + ".txt"); //NOI18N //NOI18N
        fileName=RTProperties.APP_HOME +FS+ fileName; //NOI18N //NOI18N
 
        LOGGER.info("Init file object:"+fileName); //NOI18N
        File infile=new File(fileName);
        
            if(!infile.exists()) {
            try {
                LOGGER.info("Trying to create new file"); //NOI18N
                infile.getParentFile().mkdirs();
                
            } catch (Exception ex) {
                LOGGER.error("Something critical happened: \n"+ex.getMessage(),ex); //NOI18N
            }
            }

            LOGGER.info("Saving file:"+fileName); //NOI18N
                
        try (PrintStream out = new PrintStream(new FileOutputStream(fileName),true,"utf8")) { //NOI18N
            
                out.print(outStr);
                out.close();
            LOGGER.info("Properties file: "+fileName+" saved"); //NOI18N
        } catch (Exception e) {
            LOGGER.error("Properties file: "+fileName+" not saved:"); //NOI18N
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
