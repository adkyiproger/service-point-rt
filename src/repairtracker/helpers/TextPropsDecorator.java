/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repairtracker.helpers;

import repairtracker.RepairTracker;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 *
 * @author pigeon
 */
public class TextPropsDecorator {
    
    private static String EDITOR_FONT_FAMILY=RepairTracker.PROPERTIES.getProperty("EDITOR_FONT_FAMILY","DejaVu Sans Mono");
    private static int EDITOR_FONT_SIZE=Integer.parseInt(RepairTracker.PROPERTIES.getProperty("EDITOR_FONT_SIZE","12"));
    private static String LIST_FONT_FAMILY=RepairTracker.PROPERTIES.getProperty("LIST_FONT_FAMILY");
    private static int LIST_FONT_SIZE=Integer.parseInt(RepairTracker.PROPERTIES.getProperty("LIST_FONT_SIZE","12"));
    private static String PRINT_FONT_FAMILY=RepairTracker.PROPERTIES.getProperty("PRINT_FONT_FAMILY");
    private static int PRINT_FONT_SIZE=Integer.parseInt(RepairTracker.PROPERTIES.getProperty("PRINT_FONT_SIZE","12"));
            
    
    public static void decorate(JTextArea ta) {
        JTextArea EDITOR=ta;
        EDITOR.setFont(new java.awt.Font(EDITOR_FONT_FAMILY, 0, EDITOR_FONT_SIZE)); // NOI18N
        EDITOR.setLineWrap(true);

        EDITOR.setWrapStyleWord(true);
    }
    
    public static void printDecorate(JTextArea ta) {
        JTextArea EDITOR=ta;
        EDITOR.setFont(new java.awt.Font(PRINT_FONT_FAMILY, 0, PRINT_FONT_SIZE)); // NOI18N
        EDITOR.setLineWrap(true);

        EDITOR.setWrapStyleWord(true);
    }
    public static void decorate(JList jl) {
        jl.setFont(new java.awt.Font(LIST_FONT_FAMILY, 0, LIST_FONT_SIZE));
    }
}
