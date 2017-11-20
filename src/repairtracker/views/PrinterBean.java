/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repairtracker.views;
/* import ehospital.code.EHospital;
import ehospital.code.EHospitalProperties;
import ehospital.code.TabAbstractPanel;
import ehospital.model.Address;
import ehospital.model.Doctor;
import ehospital.model.Epicrisis;
import ehospital.model.Measurement;
import ehospital.model.Patient;
import ehospital.model.Template;
*/
import repairtracker.helpers.PageDecorator;
import guitypes.TabManager;
import guitypes.TabAbstractPanel;
import repairtracker.helpers.TextPropsDecorator;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Iterator;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repairtracker.RTProperties;
import repairtracker.RepairTracker;
import repairtracker.helpers.PropertiesReader;
import repairtracker.models.Address;
import repairtracker.models.Client;
import repairtracker.models.Issue;
import repairtracker.models.IssueAttribute;
import repairtracker.models.Warranty;

/**
 *
 * @author pigeon
 */
public class PrinterBean extends TabAbstractPanel {
//private JTabbedPane PARENT_TAB=EHospitalGUI.PANEL;
private Issue ISSUE;
private Client CLIENT;
private Address ADDRESS;
private static Logger LOGGER=LogManager.getLogger(PrinterBean.class.getName());

    public PrinterBean(Issue iss) {
    //PARENT_TAB=;
    ISSUE=iss;
    
//        System.out.println("Getting getPatientId");
    CLIENT= new Client(ISSUE.clientId());
    
    LOGGER.info("Initializing printer form");
        ADDRESS=new Address(CLIENT.id());
        initComponents();
        //TEMPLATE_BOX.setModel(PropertiesReader.getFilesAsComboboxModel("templates"+RTProperties.FS));
        
       // TextPropsDecorator.printDecorate(EDITOR);
      FORM_NAME.setText(FORM_NAME.getText()+": "+CLIENT.toString());
      
        if (RepairTracker.PROPERTIES.getProperty("TEMPLATE_INDEX")==null) {
         LOGGER.warn("RepairTracker.PROPERTIES.getProperty(TEMPLATE_INDEX) is empty or null: "+RepairTracker.PROPERTIES.getProperty("TEMPLATE_INDEX"));
         TEMPLATE_BOX.setSelectedIndex(0);
        } else {
            TEMPLATE_BOX.setSelectedItem(RepairTracker.PROPERTIES.getProperty("TEMPLATE_INDEX"));
        }
        SELECT_PRINTER.setSelected(Boolean.valueOf(RepairTracker.PROPERTIES.getProperty("SELECT_PRINTER", "false")));
//        EDITOR.setText(PropertiesReader.getFileAsString("templates"+RTProperties.FS+TEMPLATE_BOX.getSelectedItem().toString()));
        
    
}

  
@Override
    public String toString(){
    
    return FORM_NAME.getText();
    }
@Override
      public Icon getIcon() {
        return FORM_NAME.getIcon();
    }
       @Override
    public void close() {
        TabManager.removeTab(this);
    }
    
@Override
public void save(){
}    

    
    public void print(){
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat format = pj.getPageFormat(null);
        Paper paper = PageDecorator.decorate(format.getPaper());
       // System.out.println("paper parameter: " + format.getPaper().getWidth() + " x " + format.getPaper().getHeight());
        format.setPaper(paper);
        format = pj.validatePage(format);
      //  System.out.println("paper parameter: " + format.getPaper().getWidth() + " x " + format.getPaper().getHeight());
        pj.setPrintable(EDITOR.getPrintable(null, null), format);
        boolean PRINT=true;
        if (SELECT_PRINTER.isSelected() && (!pj.printDialog()))
          PRINT=false;   
        if (PRINT) {
        try {
                pj.print();

            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("PRINTER_NOT_FOUND"), java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("PRINTER_NOT_FOUND_DIALOG"), JOptionPane.ERROR_MESSAGE);
                LOGGER.error(e);
            }
        }
        RepairTracker.PROPERTIES.setProperty("TEMPLATE", TEMPLATE_BOX.getSelectedItem().toString());
        //RTProperties.SaveProperties(RepairTracker.PROPERTIES);
        RepairTracker.PROPERTIES.setProperty("SELECT_PRINTER", String.valueOf(SELECT_PRINTER.isSelected()));
        RTProperties.SaveProperties(RepairTracker.PROPERTIES);
    }
    public void printAll(){
        SELECT_PRINTER.setSelected(false);
        print();
    }
    
    private String tableModelToHTMLTable(DefaultTableModel model){
        String OUT=java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("PRINT_TABLE_HEADER");
        
        int wl=model.getRowCount();
        
        for (int i=0;i<wl;i++) {
            OUT+="<tr><td>"+model.getValueAt(i, 1).toString()+"</td><td>"+model.getValueAt(i, 2).toString()+"</td></tr>";
            
        }
        
        return OUT+="</table>";
    }
    
    private String replaceValues(String input){
        String OUT=input;
//        System.err.println("Hello");
        OUT=OUT.replaceAll("FNAME", CLIENT.firstName());
        OUT=OUT.replaceAll("LNAME", CLIENT.lastName());
        OUT=OUT.replaceAll("MNAME", CLIENT.middleName());
        if (ADDRESS!= null ) {
        OUT=OUT.replaceAll("STREET", ADDRESS.address1());
        OUT=OUT.replaceAll("PHONE", ADDRESS.phone());
        }
        //getName
        if (ISSUE != null) {
            OUT=OUT.replaceAll("DEVICE_NAME", ISSUE.deviceName());
            OUT=OUT.replaceAll("DEVICE_NUMBER", ISSUE.deviceNumber());
            
            
                 
            
            OUT=OUT.replaceAll("DEVICE_TYPE",String.valueOf(
                java.util.ResourceBundle.getBundle("repairtracker/views/Bundle")
                        .getString("IssueEditor.DEVICE_TYPE").split(",")[ISSUE.deviceTypeId()]
            ));
            OUT=OUT.replaceAll("ISSUE_ID",String.valueOf(ISSUE.id()));
            
            OUT=OUT.replaceAll("START_DATE", ISSUE.startDate().toString());
            OUT=OUT.replaceAll("END_DATE", ISSUE.endDate().toString());
            OUT=OUT.replaceAll("COMMENTS", ISSUE.comments());
            OUT=OUT.replaceAll("DESCRIPTION", ISSUE.description());
            OUT=OUT.replaceAll("WARRANTY", new Warranty(ISSUE.warrantyTypeId()).getName());
            OUT=OUT.replaceAll("WARRANTY_TEXT", new Warranty(ISSUE.warrantyTypeId()).getDescription());
            
            
            OUT=OUT.replaceAll("WORKLOG", tableModelToHTMLTable(IssueAttribute.getAttributesAsTable(ISSUE.id(), 0)));
            OUT=OUT.replaceAll("REPLACEMENT", tableModelToHTMLTable(IssueAttribute.getAttributesAsTable(ISSUE.id(), 1)));
            
        }
        
        return OUT;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FORM_NAME = new javax.swing.JLabel();
        TEMPLATE_BOX = new javax.swing.JComboBox();
        FORM_ = new javax.swing.JLabel();
        PRINTER = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        SELECT_PRINTER = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        EDITOR = new javax.swing.JEditorPane();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("repairtracker/views/Bundle"); // NOI18N
        FORM_NAME.setText(bundle.getString("PrinterBean.FORM_NAME.text")); // NOI18N

        TEMPLATE_BOX.setModel(PropertiesReader.getFilesAsComboboxModel("templates"+RTProperties.FS));
        TEMPLATE_BOX.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TEMPLATE_BOXItemStateChanged(evt);
            }
        });
        TEMPLATE_BOX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TEMPLATE_BOXActionPerformed(evt);
            }
        });
        TEMPLATE_BOX.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TEMPLATE_BOXPropertyChange(evt);
            }
        });

        FORM_.setText(bundle.getString("PrinterBean.FORM_.text")); // NOI18N

        PRINTER.setText(bundle.getString("PrinterBean.PRINTER.text")); // NOI18N
        PRINTER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PRINTERActionPerformed(evt);
            }
        });

        jButton3.setText(bundle.getString("PrinterBean.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        SELECT_PRINTER.setText(bundle.getString("PrinterBean.SELECT_PRINTER.text")); // NOI18N

        EDITOR.setContentType("text/html"); // NOI18N
        EDITOR.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(EDITOR);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(FORM_)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TEMPLATE_BOX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(93, 93, 93))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 483, Short.MAX_VALUE)
                        .addComponent(SELECT_PRINTER)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PRINTER)))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TEMPLATE_BOX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FORM_))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PRINTER)
                    .addComponent(jButton3)
                    .addComponent(SELECT_PRINTER)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TEMPLATE_BOXPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TEMPLATE_BOXPropertyChange
    }//GEN-LAST:event_TEMPLATE_BOXPropertyChange

    private void TEMPLATE_BOXItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TEMPLATE_BOXItemStateChanged
        // TODO add your handling code here:
        //loadTemplate();
        //System.out.println("Changed");
    }//GEN-LAST:event_TEMPLATE_BOXItemStateChanged

    private void PRINTERActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PRINTERActionPerformed
        print();
    
    }//GEN-LAST:event_PRINTERActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        TabManager.removeTab(this);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void TEMPLATE_BOXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TEMPLATE_BOXActionPerformed
              
        if (TEMPLATE_BOX.getItemCount()>0) { 
            //TEMPLATE_BOX.setSelectedIndex(0);
            
            LOGGER.info("TEMPLATE_BOX.getItemCount(): "+TEMPLATE_BOX.getItemCount());
        EDITOR.setText(replaceValues(PropertiesReader.getFileAsString("templates"+RTProperties.FS+TEMPLATE_BOX.getSelectedItem().toString()))); }
    }//GEN-LAST:event_TEMPLATE_BOXActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane EDITOR;
    private javax.swing.JLabel FORM_;
    private javax.swing.JLabel FORM_NAME;
    private javax.swing.JButton PRINTER;
    private javax.swing.JCheckBox SELECT_PRINTER;
    private javax.swing.JComboBox TEMPLATE_BOX;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables


}
