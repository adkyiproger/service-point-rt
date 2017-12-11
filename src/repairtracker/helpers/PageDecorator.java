/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repairtracker.helpers;

import repairtracker.RepairTracker;
import java.awt.print.Paper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author pigeon
 */
public class PageDecorator {
    private static Logger LOGGER=LogManager.getLogger(PageDecorator.class.getName()); 
    private static int A4[]={595,842};
    private static int A5[]={420,595};
    private static int BORDER_TOP=Integer.parseInt(RepairTracker.PROPERTIES.getProperty("PRINTER_BORDER_TOP","20"));
    private static int BORDER_LEFT=Integer.parseInt(RepairTracker.PROPERTIES.getProperty("PRINTER_BORDER_LEFT","20"));
    private static int BORDER_RIGHT=Integer.parseInt(RepairTracker.PROPERTIES.getProperty("PRINTER_BORDER_RIGHT","20"));
    private static int BORDER_BUTTOM=Integer.parseInt(RepairTracker.PROPERTIES.getProperty("PRINTER_BORDER_BUTTOM","20"));
    private static String PRINTER_ORIENTATION=RepairTracker.PROPERTIES.getProperty("PRINTER_ORIENTATION","Portrait");
    private static int width=A4[0];
    private static int height=A4[1];
    
    public static Paper decorate(Paper paper) {
    
    
    
    if (RepairTracker.PROPERTIES.getProperty("PRINTER_PAGE","A5").equalsIgnoreCase("A5")){
    
        width=A5[0];
        height=A5[1];
    }
    if (RepairTracker.PROPERTIES.getProperty("PRINTER_PAGE","A4").equalsIgnoreCase("A4")){
    
        width=A4[0];
        height=A4[1];
    }
    if (PRINTER_ORIENTATION.equalsIgnoreCase("Landscape"))
    {
        int t=height;
        height=width;
        width=t;
    }
        
    
    
    LOGGER.info("Setting printer borders: Left="+BORDER_LEFT+" Top="+BORDER_TOP+" Right="+BORDER_RIGHT+" Buttom="+BORDER_BUTTOM);    
    paper.setSize(width, height);
    paper.setImageableArea(BORDER_LEFT, BORDER_TOP, width-BORDER_LEFT-BORDER_RIGHT, height-BORDER_TOP-BORDER_BUTTOM);
    
    return paper;
    }
}
