/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repairtracker.helpers;

import repairtracker.RepairTracker;
import java.awt.print.Paper;

/**
 *
 * @author pigeon
 */
public class PageDecorator {
    
    private static int A4[]={595,842};
    private static int A5[]={420,595};
    private static int PRINTER_BORDER=Integer.parseInt(RepairTracker.PROPERTIES.getProperty("PRINTER_BORDER","20"));
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
        
    
    
    System.out.println("PRINTER_BORDER: "+PRINTER_BORDER);    
    paper.setSize(width, height);
    paper.setImageableArea(PRINTER_BORDER, PRINTER_BORDER, width-2*PRINTER_BORDER, height-2*PRINTER_BORDER);
    
    return paper;
    }
}
