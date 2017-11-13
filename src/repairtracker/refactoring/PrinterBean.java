/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialogs;
import ehospital.code.EHospital;
import ehospital.code.EHospitalProperties;
import ehospital.code.TabAbstractPanel;
import ehospital.model.Address;
import ehospital.model.Doctor;
import ehospital.model.Epicrisis;
import ehospital.model.Measurement;
import ehospital.model.Patient;
import ehospital.model.Template;
import helpers.PageDecorator;
import helpers.TabManager;
import helpers.TextPropsDecorator;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Iterator;
import java.util.Map;
import javax.swing.Icon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pigeon
 */
public class PrinterBean extends TabAbstractPanel {
//private JTabbedPane PARENT_TAB=EHospitalGUI.PANEL;
private Measurement MEASUREMENT;
private Epicrisis EPICRISIS;
private Patient PATIENT;
private Address ADDRESS;
private Map<String,String> TEMPLATES=Template.listTemplates();
private static Logger LOGGER=LogManager.getLogger(PrinterBean.class.getName());


    /**
     * Creates new form PrinterForm
     * @param parent 
     * @param meas 
     */

    /**
     * Creates new form PrinterForm
     * @param parent
     * @param ep
     */

    public PrinterBean(Epicrisis ep) {
    //PARENT_TAB=;
    EPICRISIS=ep;
    
//        System.out.println("Getting getPatientId");
    PATIENT= new Patient(EPICRISIS.getPatientId());
    
        LOGGER.info("Initializing printer form");
    initForm();
    
}


    public PrinterBean(Measurement meas) {
        //PARENT_TAB=parent;
        MEASUREMENT=meas;
        PATIENT= new Patient(MEASUREMENT.getPatientId());
        
        initForm();
        
    }
    
@Override
    public String toString(){
    
    return FORM_NAME.getText();
    }
@Override
      public Icon getIcon() {
        return FORM_.getIcon();
    }
       @Override
    public void close() {
        TabManager.removeTab(this);
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
                LOGGER.info(e);
            }
        }
        EHospital.PROPERTIES.setProperty("TEMPLATE", TEMPLATE_BOX.getSelectedItem().toString());
        EHospitalProperties.SaveProperties(EHospital.PROPERTIES);
        EHospital.PROPERTIES.setProperty("SELECT_PRINTER", String.valueOf(SELECT_PRINTER.isSelected()));
        EHospitalProperties.SaveProperties(EHospital.PROPERTIES);
    }
    public void printAll(){
        SELECT_PRINTER.setSelected(false);
        print();
    }
    
    private void initForm() {
    ADDRESS=new Address(PATIENT.id());
        initComponents();
        TextPropsDecorator.printDecorate(EDITOR);
      FORM_NAME.setText(FORM_NAME.getText()+": "+PATIENT.toString());
        Iterator it;
        it = TEMPLATES.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            //System.out.println(pairs.getKey() + " = " + pairs.getValue());
            TEMPLATE_BOX.addItem(pairs.getKey());
        }
        
        
        TEMPLATE_BOX.setSelectedItem(EHospital.PROPERTIES.getProperty("TEMPLATE"));
        SELECT_PRINTER.setSelected(Boolean.valueOf(EHospital.PROPERTIES.getProperty("SELECT_PRINTER", "false")));
        loadTemplate();
        
        
    }

    private void loadTemplate(){
     
         if (TEMPLATE_BOX.getSelectedIndex()>0) {
             String selected_temp=TEMPLATE_BOX.getSelectedItem().toString();
             selected_temp=selected_temp.replaceAll("[^\\p{Alpha}\\p{Digit}]+","");
             for (Map.Entry pairs : TEMPLATES.entrySet()) {
                 System.out.println(pairs.getKey().toString() + " = " + selected_temp);
                 
                 if (pairs.getKey().toString().replaceAll("[^\\p{Alpha}\\p{Digit}]+","").matches(selected_temp)) {
//                     System.out.println("Template text: " + selected_temp );
                     EDITOR.setText(replaceValues(pairs.getValue().toString()));
  //                   System.out.println(replaceValues(pairs.getValue().toString()));
                 }}
            
         }
    }
         
    
    private String replaceValues(String input){
        String OUT=input;
//        System.err.println("Hello");
        OUT=OUT.replaceAll("FNAME", PATIENT.firstName());
        OUT=OUT.replaceAll("LNAME", PATIENT.lastName());
        OUT=OUT.replaceAll("MNAME", PATIENT.middleName());
        OUT=OUT.replaceAll("BIRTHDATE", PATIENT.dataOfBirth().toString());
        if (ADDRESS!= null ) {
        OUT=OUT.replaceAll("TOWN", ADDRESS.city());
        OUT=OUT.replaceAll("STREET", ADDRESS.address1());
        }
        //getName
        if (EPICRISIS != null) {
            OUT=OUT.replaceAll("EPICRISIS_NAME", EPICRISIS.epiName());
            OUT=OUT.replaceAll("EPICRISIS", EPICRISIS.epiDescription());
            OUT=OUT.replaceAll("DATE", EPICRISIS.getDate().toString());
            OUT=OUT.replaceAll("DOCTOR", Doctor.getShortName(EPICRISIS.getDocId()));
        }
        if (MEASUREMENT != null ) {
            OUT=OUT.replaceAll("MEASUREDATE", MEASUREMENT.getDate().toString());
        OUT=OUT.replaceAll("DOCTOR", Doctor.getShortName(MEASUREMENT.getDocId()));
        OUT=OUT.replaceAll("WEIGHT", String.valueOf(MEASUREMENT.getDouble("WEIGHT")));
        OUT=OUT.replaceAll("HEIGHT", String.valueOf(MEASUREMENT.getDouble("HEIGHT")));
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
        jScrollPane2 = new javax.swing.JScrollPane();
        EDITOR = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        SELECT_PRINTER = new javax.swing.JCheckBox();

        FORM_NAME.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Print/Print_24x24.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("dialogs/Bundle"); // NOI18N
        FORM_NAME.setText(bundle.getString("PrinterBean.FORM_NAME.text")); // NOI18N

        TEMPLATE_BOX.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select..." }));
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

        FORM_.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Print/Print_24x24.png"))); // NOI18N
        FORM_.setText(bundle.getString("PrinterBean.FORM_.text")); // NOI18N

        PRINTER.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Print/Print_32x32.png"))); // NOI18N
        PRINTER.setText(bundle.getString("PrinterBean.PRINTER.text")); // NOI18N
        PRINTER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PRINTERActionPerformed(evt);
            }
        });

        EDITOR.setColumns(20);
        EDITOR.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        EDITOR.setLineWrap(true);
        EDITOR.setRows(5);
        EDITOR.setWrapStyleWord(true);
        jScrollPane2.setViewportView(EDITOR);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/exit.png"))); // NOI18N
        jButton3.setText(bundle.getString("PrinterBean.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        SELECT_PRINTER.setText(bundle.getString("PrinterBean.SELECT_PRINTER.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(FORM_)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TEMPLATE_BOX, 0, 279, Short.MAX_VALUE)
                        .addGap(93, 93, 93))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SELECT_PRINTER)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PRINTER)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TEMPLATE_BOX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FORM_))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                loadTemplate();
    }//GEN-LAST:event_TEMPLATE_BOXActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea EDITOR;
    private javax.swing.JLabel FORM_;
    private javax.swing.JLabel FORM_NAME;
    private javax.swing.JButton PRINTER;
    private javax.swing.JCheckBox SELECT_PRINTER;
    private javax.swing.JComboBox TEMPLATE_BOX;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables


}
