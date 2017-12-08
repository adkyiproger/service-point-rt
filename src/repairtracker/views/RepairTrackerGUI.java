/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repairtracker.views;

import guitypes.IconFactory;
import guitypes.TabAbstractPanel;
import guitypes.TabManager;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repairtracker.RTProperties;
import repairtracker.RepairTracker;
import repairtracker.models.DBDoor;

/**
 *
 * @author pigeon
 */
public class RepairTrackerGUI extends javax.swing.JFrame {

    /**
     * Creates new form RepairTrackerGUI
     */
    
    private static Logger LOGGER=LogManager.getLogger(RepairTrackerGUI.class.getName());
    TabManager tabManager;
    Dashboard DASHBOARD;
    public RepairTrackerGUI() {
        setIconImage(IconFactory.createImage("/images/app-logo.png"));
        DASHBOARD=new Dashboard();
        initComponents();
        tabManager=new TabManager(MAIN_PANEL);
        TabManager.insertTab(DASHBOARD);
        setTitle(RTProperties.APP_NAME+" "+RTProperties.APP_VERSION);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        STATUS = new javax.swing.JLabel();
        MAIN_PANEL = new javax.swing.JTabbedPane();
        jToolBar1 = new javax.swing.JToolBar();
        TB_NEW = new javax.swing.JButton();
        TB_SAVE = new javax.swing.JButton();
        TB_SAVEALL = new javax.swing.JButton();
        TB_PRINT = new javax.swing.JButton();
        MAIN_MENU = new javax.swing.JMenuBar();
        MENU_FILE = new javax.swing.JMenu();
        MENU_NEWISSUE = new javax.swing.JMenuItem();
        MENU_SAVE = new javax.swing.JMenuItem();
        MENU_SAVEALL = new javax.swing.JMenuItem();
        MENU_PRINT = new javax.swing.JMenuItem();
        MENU_SETTINGS = new javax.swing.JMenuItem();
        MENU_EXIT = new javax.swing.JMenuItem();
        MENU_ISSUES = new javax.swing.JMenu();
        MENU_TEMPLATES = new javax.swing.JMenuItem();
        MENU_WARRANTIES = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                WindowClosing(evt);
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("repairtracker/views/Bundle"); // NOI18N
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("RepairTrackerGUI.jPanel2.border.title"))); // NOI18N

        STATUS.setText(bundle.getString("RepairTrackerGUI.STATUS.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(STATUS)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(STATUS)
                .addGap(12, 12, 12))
        );

        jToolBar1.setRollover(true);

        TB_NEW.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/30/icons8-add-file-30.png"))); // NOI18N
        TB_NEW.setText(bundle.getString("RepairTrackerGUI.TB_NEW.text")); // NOI18N
        TB_NEW.setToolTipText(bundle.getString("RepairTrackerGUI.TB_NEW.toolTipText")); // NOI18N
        TB_NEW.setFocusable(false);
        TB_NEW.setHideActionText(true);
        TB_NEW.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TB_NEW.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        TB_NEW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TB_NEWActionPerformed(evt);
            }
        });
        jToolBar1.add(TB_NEW);

        TB_SAVE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/30/icons8-save-30.png"))); // NOI18N
        TB_SAVE.setText(bundle.getString("RepairTrackerGUI.TB_SAVE.text")); // NOI18N
        TB_SAVE.setToolTipText(bundle.getString("RepairTrackerGUI.TB_SAVE.toolTipText")); // NOI18N
        TB_SAVE.setFocusable(false);
        TB_SAVE.setHideActionText(true);
        TB_SAVE.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TB_SAVE.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        TB_SAVE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TB_SAVEActionPerformed(evt);
            }
        });
        jToolBar1.add(TB_SAVE);

        TB_SAVEALL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/30/icons8-save-all-30.png"))); // NOI18N
        TB_SAVEALL.setText(bundle.getString("RepairTrackerGUI.TB_SAVEALL.text")); // NOI18N
        TB_SAVEALL.setFocusable(false);
        TB_SAVEALL.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TB_SAVEALL.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        TB_SAVEALL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TB_SAVEALLActionPerformed(evt);
            }
        });
        jToolBar1.add(TB_SAVEALL);

        TB_PRINT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/30/icons8-send-to-printer-30.png"))); // NOI18N
        TB_PRINT.setText(bundle.getString("RepairTrackerGUI.TB_PRINT.text")); // NOI18N
        TB_PRINT.setToolTipText(bundle.getString("RepairTrackerGUI.TB_PRINT.toolTipText")); // NOI18N
        TB_PRINT.setFocusable(false);
        TB_PRINT.setHideActionText(true);
        TB_PRINT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TB_PRINT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        TB_PRINT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TB_PRINTActionPerformed(evt);
            }
        });
        jToolBar1.add(TB_PRINT);

        MENU_FILE.setText(bundle.getString("RepairTrackerGUI.MENU_FILE.text")); // NOI18N

        MENU_NEWISSUE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/icons8-add-file-16.png"))); // NOI18N
        MENU_NEWISSUE.setText(bundle.getString("RepairTrackerGUI.MENU_NEWISSUE.text")); // NOI18N
        MENU_NEWISSUE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MENU_NEWISSUEActionPerformed(evt);
            }
        });
        MENU_FILE.add(MENU_NEWISSUE);

        MENU_SAVE.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        MENU_SAVE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/icons8-save-16.png"))); // NOI18N
        MENU_SAVE.setText(bundle.getString("RepairTrackerGUI.MENU_SAVE.text")); // NOI18N
        MENU_SAVE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MENU_SAVEActionPerformed(evt);
            }
        });
        MENU_FILE.add(MENU_SAVE);

        MENU_SAVEALL.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        MENU_SAVEALL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/icons8-save-all-16.png"))); // NOI18N
        MENU_SAVEALL.setText(bundle.getString("RepairTrackerGUI.MENU_SAVEALL.text")); // NOI18N
        MENU_SAVEALL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MENU_SAVEALLActionPerformed(evt);
            }
        });
        MENU_FILE.add(MENU_SAVEALL);

        MENU_PRINT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/icons8-send-to-printer-16.png"))); // NOI18N
        MENU_PRINT.setText(bundle.getString("RepairTrackerGUI.MENU_PRINT.text")); // NOI18N
        MENU_PRINT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MENU_PRINTActionPerformed(evt);
            }
        });
        MENU_FILE.add(MENU_PRINT);

        MENU_SETTINGS.setText(bundle.getString("RepairTrackerGUI.MENU_SETTINGS.text")); // NOI18N
        MENU_SETTINGS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MENU_SETTINGSActionPerformed(evt);
            }
        });
        MENU_FILE.add(MENU_SETTINGS);

        MENU_EXIT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/icons8-shutdown-16.png"))); // NOI18N
        MENU_EXIT.setText(bundle.getString("RepairTrackerGUI.MENU_EXIT.text")); // NOI18N
        MENU_EXIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MENU_EXITActionPerformed(evt);
            }
        });
        MENU_FILE.add(MENU_EXIT);

        MAIN_MENU.add(MENU_FILE);

        MENU_ISSUES.setText(bundle.getString("RepairTrackerGUI.MENU_ISSUES.text")); // NOI18N

        MENU_TEMPLATES.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/icons8-web-design-16.png"))); // NOI18N
        MENU_TEMPLATES.setText(bundle.getString("RepairTrackerGUI.MENU_TEMPLATES.text")); // NOI18N
        MENU_TEMPLATES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MENU_TEMPLATESActionPerformed(evt);
            }
        });
        MENU_ISSUES.add(MENU_TEMPLATES);

        MENU_WARRANTIES.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/icons8-quality-16.png"))); // NOI18N
        MENU_WARRANTIES.setText(bundle.getString("RepairTrackerGUI.MENU_WARRANTIES.text")); // NOI18N
        MENU_WARRANTIES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MENU_WARRANTIESActionPerformed(evt);
            }
        });
        MENU_ISSUES.add(MENU_WARRANTIES);

        MAIN_MENU.add(MENU_ISSUES);

        setJMenuBar(MAIN_MENU);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MAIN_PANEL)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1306, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MAIN_PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MENU_NEWISSUEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MENU_NEWISSUEActionPerformed
        ((TabAbstractPanel)MAIN_PANEL.getSelectedComponent()).newItem();
    }//GEN-LAST:event_MENU_NEWISSUEActionPerformed

    private void TB_NEWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TB_NEWActionPerformed
        MENU_NEWISSUEActionPerformed(evt);
    }//GEN-LAST:event_TB_NEWActionPerformed

    private void MENU_EXITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MENU_EXITActionPerformed
        WindowClosing(new java.awt.event.WindowEvent(this,0) );
    }//GEN-LAST:event_MENU_EXITActionPerformed

    private void MENU_SAVEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MENU_SAVEActionPerformed
        ((TabAbstractPanel)MAIN_PANEL.getSelectedComponent()).save();
    }//GEN-LAST:event_MENU_SAVEActionPerformed

    private void MENU_TEMPLATESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MENU_TEMPLATESActionPerformed
        TabManager.insertTab(new TemplateEditor());
    }//GEN-LAST:event_MENU_TEMPLATESActionPerformed

    private void MENU_PRINTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MENU_PRINTActionPerformed
        ((TabAbstractPanel)MAIN_PANEL.getSelectedComponent()).print();
    }//GEN-LAST:event_MENU_PRINTActionPerformed

    private void TB_SAVEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TB_SAVEActionPerformed
        MENU_SAVEActionPerformed(evt);
    }//GEN-LAST:event_TB_SAVEActionPerformed

    private void TB_PRINTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TB_PRINTActionPerformed
        MENU_PRINTActionPerformed(evt);
    }//GEN-LAST:event_TB_PRINTActionPerformed

    private void MENU_WARRANTIESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MENU_WARRANTIESActionPerformed
        TabManager.insertTab(new TemplateEditor("Warranties Editor","warranties"));
    }//GEN-LAST:event_MENU_WARRANTIESActionPerformed

    private void WindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_WindowClosing
        int dialogResult=JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("EXIT_MESSAGE"),java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("EXIT_TITLE"),JOptionPane.WARNING_MESSAGE);
                        
                        if(dialogResult == JOptionPane.YES_OPTION){
                            if (DBDoor.shutdown()) {
                                
                                RTProperties.SaveProperties(RepairTracker.PROPERTIES);
                                LOGGER.info("Exit application completely");
                                
                                System.exit(0);
                            }
                            
                        } else {
                            LOGGER.error("Something went wrong with database");
                        }
    }//GEN-LAST:event_WindowClosing

    private void MENU_SAVEALLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MENU_SAVEALLActionPerformed
        for ( Component s : MAIN_PANEL.getComponents())  {
            ((TabAbstractPanel)s).save();
    }
    }//GEN-LAST:event_MENU_SAVEALLActionPerformed

    private void TB_SAVEALLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TB_SAVEALLActionPerformed
        MENU_SAVEALLActionPerformed(evt);
    }//GEN-LAST:event_TB_SAVEALLActionPerformed

    private void MENU_SETTINGSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MENU_SETTINGSActionPerformed
        LOGGER.info("TabManager.insertTab(new Settings());");
        TabManager.insertTab(new Settings());
    }//GEN-LAST:event_MENU_SETTINGSActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RepairTrackerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RepairTrackerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RepairTrackerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RepairTrackerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RepairTrackerGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MAIN_MENU;
    private javax.swing.JTabbedPane MAIN_PANEL;
    private javax.swing.JMenuItem MENU_EXIT;
    private javax.swing.JMenu MENU_FILE;
    private javax.swing.JMenu MENU_ISSUES;
    private javax.swing.JMenuItem MENU_NEWISSUE;
    private javax.swing.JMenuItem MENU_PRINT;
    private javax.swing.JMenuItem MENU_SAVE;
    private javax.swing.JMenuItem MENU_SAVEALL;
    private javax.swing.JMenuItem MENU_SETTINGS;
    private javax.swing.JMenuItem MENU_TEMPLATES;
    private javax.swing.JMenuItem MENU_WARRANTIES;
    public static javax.swing.JLabel STATUS;
    private javax.swing.JButton TB_NEW;
    private javax.swing.JButton TB_PRINT;
    private javax.swing.JButton TB_SAVE;
    private javax.swing.JButton TB_SAVEALL;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
