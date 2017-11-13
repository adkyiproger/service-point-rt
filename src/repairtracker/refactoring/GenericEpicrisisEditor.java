/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genericdep.forms;

import ultrasound.forms.*;
import dialogs.Editor;
import dialogs.PrinterBean;
import ehospital.code.EHospital;
import ehospital.code.EHospitalProperties;
import ehospital.model.Department;
import ehospital.model.Doctor;
import ehospital.model.Epicrisis;
import ehospital.model.Patient;
import helpers.JListHelper;
import helpers.PropertiesReader;
import helpers.TabManager;
import helpers.TextPropsDecorator;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author pigeon
 */
public class GenericEpicrisisEditor extends UltraSoundFormsAbstract {

// Generic properties
    private static java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ultrasound/forms/Bundle"); // NOI18N
    private static Logger LOGGER=LogManager.getLogger(GenericEpicrisisEditor.class.getName());

    private final Patient PATIENT;
    private Epicrisis EPICRISIS;    
// Doctor ID
    int DOC_ID=EHospital.DOC_ID;
    private final Map<Integer,String> DOCTOR_LIST=Doctor.listDoctors();

// Init resources for investigation
    // Prefix used to store values in files
    private String PREFIX="GENERIC";
    private List<JList> INV_LISTS = new ArrayList();
    private List<DefaultListModel<String>> LIST_=new ArrayList<DefaultListModel<String>>();
    private List<DefaultListModel<String>> LIST_S=new ArrayList<DefaultListModel<String>>();
    private String INVEST_STR="", REC_STR="", SUMM_STR="";
    
    
    // Delimiters
    String DEL_ROW=".\n", DEL_WORD=" ";
    boolean SAVED=false;
    /**
     * Creates new form UltraSoundThyroid
     * @param pb
     */
    public GenericEpicrisisEditor(Patient pb) {
        LOGGER.info(java.util.ResourceBundle.getBundle("ultrasound/forms/Bundle").getString("INITIALIZED"));

        PATIENT=pb;
        EPICRISIS=new Epicrisis(PATIENT);
        initComponents();
        if (!EHospital.PROPERTIES.getProperty("EPICRISIS_NAME","NA").equalsIgnoreCase("NA")) {
            FORM_NAME.setText(EHospital.PROPERTIES.getProperty("EPICRISIS_NAME"));
            FORM_ICON.setText(new Department(EHospital.DEP_ID).depName());
        }
        TextPropsDecorator.decorate(L_INVEST1);
        TextPropsDecorator.decorate(L_RECOM);
        TextPropsDecorator.decorate(L_SUMMARY);
        TextPropsDecorator.decorate(TEXT_EDITOR);
        LOGGER.info("Loading data into forms");
        loadFormData();
        
        
        L_PATIENT.setText(PATIENT.toString());
        Iterator it;
        it = DOCTOR_LIST.entrySet().iterator();
        
        QUICKPRINT.setSelected(Boolean.valueOf(EHospital.PROPERTIES.getProperty("QUICKPRINT", "false")));
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            DOCTORS.addItem(pairs.getValue());
            
            if (Integer.parseInt(pairs.getKey().toString())==DOC_ID) DOCTORS.setSelectedIndex(DOCTORS.getItemCount()-1);
        }
        
        insertText(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())+":\n");
    }
    private void loadFormData(){
        
        EDIT_INV.setVisible(EHospital.EDIT_ENABLE);
        EDIT_INVLIST.setVisible(EHospital.EDIT_ENABLE);
        EDIT_REC.setVisible(EHospital.EDIT_ENABLE);
        EDIT_SUM.setVisible(EHospital.EDIT_ENABLE);
        // Load data from bundles
        INV_LIST.setModel(PropertiesReader.getComboboxModel("generic/bundles/INV_LIST_"+PREFIX+".txt"));
        LOGGER.info(java.util.ResourceBundle.getBundle("ultrasound/forms/Bundle").getString("NUMBER OF ITEMS ON INV_LIST IS: ")+INV_LIST.getItemCount());

        for (int ii=0; ii<INV_LIST.getItemCount(); ii++) {
            LIST_.add(PropertiesReader.getListModel("generic/bundles/"+PREFIX+ii+".txt"));
            LIST_S.add(PropertiesReader.getListModel("generic/bundles/SUMMARY_"+PREFIX+ii+".txt"));
        }
        if (LIST_.size()>0) L_INVEST1.setModel(LIST_.get(0));
        if (LIST_S.size()>0) L_SUMMARY.setModel(LIST_S.get(0));

        LOGGER.info("Investigations loaded successfully");
        
        //L_SUMMARY.setModel(PropertiesReader.getListModel("ultrasound/bundles/SUMMARY_"+PREFIX+".txt"));
        //LOGGER.info("Summary loaded successfully");
//        DefaultListModel<String> lm=PropertiesReader.getListModel("ultrasound/bundles/REC_.txt");
//        if (lm.getSize()==0) lm=PropertiesReader.getListModel("ultrasound/bundles/REC_"+PREFIX+".txt");
        L_RECOM.setModel(PropertiesReader.getListModel("generic/bundles/REC_.txt"));
        LOGGER.info("Recommendations loaded successfully");
    }
    
    @Override
    public String toString(){
        return FORM_ICON.getText()+": "+PATIENT.toString();
    }
    @Override
    public void print(Graphics g){
        save();
        TabManager.insertTab(new PrinterBean( EPICRISIS));
    }
    public void getEditor(){
        int ind=INV_LIST.getSelectedIndex();
        new Editor(jTabbedPane1.getTitleAt(0)+": "+INV_LIST.getSelectedItem().toString(),"generic/bundles/"+PREFIX+ind+".txt");
    }
    
    public String epicrisisName(){
        return FORM_NAME.getText();
    }
    
    public Icon getIcon() {
        return FORM_ICON.getIcon();
    }

    @Override
    public void close(){
        if (SAVED==false) {
            int ans=JOptionPane.showConfirmDialog(this, L_SAVE_YES_NO_TEXT.getText(),
                    L_SAVE_YES_NO_TITLE.getText(),
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    L_SAVE_YES_NO_TITLE.getIcon());
            if (ans==0) save();
            if (ans!=2) TabManager.removeTab(this);
        } else TabManager.removeTab(this);
    }
    
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        P_SIZES2 = new javax.swing.JPanel();
        P_SIZES_X = new javax.swing.JFormattedTextField();
        P_SIZES_Y = new javax.swing.JFormattedTextField();
        P_SIZES2_L2 = new javax.swing.JLabel();
        P_SIZES2_L1 = new javax.swing.JLabel();
        P_SIZES2_L3 = new javax.swing.JLabel();
        L_SAVE_YES_NO_TEXT = new javax.swing.JLabel();
        L_SAVE_YES_NO_TITLE = new javax.swing.JLabel();
        P_SIZES1 = new javax.swing.JPanel();
        P_SIZES_X1 = new javax.swing.JFormattedTextField();
        P_SIZES1_L1 = new javax.swing.JLabel();
        P_SIZES1_L2 = new javax.swing.JLabel();
        FORM_ICON = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        L_INVEST1 = new javax.swing.JList();
        EDIT_INV = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        L_SUMMARY = new javax.swing.JList();
        EDIT_SUM = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        L_RECOM = new javax.swing.JList();
        EDIT_REC = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TEXT_EDITOR = new javax.swing.JTextArea();
        B_SAVECLOSE = new javax.swing.JButton();
        B_CANCEL = new javax.swing.JButton();
        B_SAVE = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        DOCTORS = new javax.swing.JComboBox();
        B_PRINT = new javax.swing.JButton();
        L_PATIENT = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        INV_LIST = new javax.swing.JComboBox();
        EDIT_INVLIST = new javax.swing.JButton();
        QUICKPRINT = new javax.swing.JCheckBox();
        FORM_NAME = new javax.swing.JTextField();

        P_SIZES_X.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#.#"))));

        P_SIZES_Y.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#.#"))));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ultrasound/forms/Bundle"); // NOI18N
        P_SIZES_Y.setToolTipText(bundle.getString("GenericEpicrisisEditor.P_SIZES_Y.toolTipText")); // NOI18N

        P_SIZES2_L2.setText(bundle.getString("GenericEpicrisisEditor.P_SIZES2_L2.text")); // NOI18N

        P_SIZES2_L1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Add/Add_48x48.png"))); // NOI18N
        P_SIZES2_L1.setText(bundle.getString("GenericEpicrisisEditor.P_SIZES2_L1.text")); // NOI18N

        P_SIZES2_L3.setText(bundle.getString("GenericEpicrisisEditor.P_SIZES2_L3.text")); // NOI18N

        javax.swing.GroupLayout P_SIZES2Layout = new javax.swing.GroupLayout(P_SIZES2);
        P_SIZES2.setLayout(P_SIZES2Layout);
        P_SIZES2Layout.setHorizontalGroup(
            P_SIZES2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P_SIZES2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(P_SIZES2_L1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(P_SIZES_X, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(P_SIZES2_L2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(P_SIZES_Y, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(P_SIZES2_L3)
                .addContainerGap())
        );
        P_SIZES2Layout.setVerticalGroup(
            P_SIZES2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P_SIZES2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(P_SIZES2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(P_SIZES_X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P_SIZES_Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P_SIZES2_L2)
                    .addComponent(P_SIZES2_L1)
                    .addComponent(P_SIZES2_L3)))
        );

        L_SAVE_YES_NO_TEXT.setText(bundle.getString("GenericEpicrisisEditor.L_SAVE_YES_NO_TEXT.text")); // NOI18N

        L_SAVE_YES_NO_TITLE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Help/Help_48x48.png"))); // NOI18N
        L_SAVE_YES_NO_TITLE.setText(bundle.getString("GenericEpicrisisEditor.L_SAVE_YES_NO_TITLE.text")); // NOI18N

        P_SIZES_X1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#.#"))));

        P_SIZES1_L1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Add/Add_48x48.png"))); // NOI18N
        P_SIZES1_L1.setText(bundle.getString("GenericEpicrisisEditor.P_SIZES1_L1.text")); // NOI18N

        P_SIZES1_L2.setText(bundle.getString("GenericEpicrisisEditor.P_SIZES1_L2.text")); // NOI18N

        javax.swing.GroupLayout P_SIZES1Layout = new javax.swing.GroupLayout(P_SIZES1);
        P_SIZES1.setLayout(P_SIZES1Layout);
        P_SIZES1Layout.setHorizontalGroup(
            P_SIZES1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, P_SIZES1Layout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addComponent(P_SIZES1_L1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(P_SIZES_X1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(P_SIZES1_L2)
                .addContainerGap())
        );
        P_SIZES1Layout.setVerticalGroup(
            P_SIZES1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P_SIZES1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(P_SIZES1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(P_SIZES_X1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P_SIZES1_L1)
                    .addComponent(P_SIZES1_L2)))
        );

        FORM_ICON.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FORM_ICON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/24/clinical-lab-software-for-medical-laboratory.png"))); // NOI18N
        FORM_ICON.setText(bundle.getString("GenericEpicrisisEditor.FORM_ICON.text")); // NOI18N

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        L_INVEST1.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 14)); // NOI18N
        L_INVEST1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                L_INVEST1MouseClicked(evt);
            }
        });
        L_INVEST1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                L_INVEST1KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(L_INVEST1);

        EDIT_INV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Edit/Edit_16x16.png"))); // NOI18N
        EDIT_INV.setText(bundle.getString("GenericEpicrisisEditor.EDIT_INV.text")); // NOI18N
        EDIT_INV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EDIT_INVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 393, Short.MAX_VALUE)
                        .addComponent(EDIT_INV)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EDIT_INV)
                .addGap(6, 6, 6))
        );

        jTabbedPane1.addTab(bundle.getString("GenericEpicrisisEditor.jPanel1.TabConstraints.tabTitle"), new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/24/microscope.png")), jPanel1); // NOI18N

        L_SUMMARY.setDragEnabled(true);
        L_SUMMARY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                L_SUMMARYMouseClicked(evt);
            }
        });
        L_SUMMARY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                L_SUMMARYKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(L_SUMMARY);

        EDIT_SUM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Edit/Edit_16x16.png"))); // NOI18N
        EDIT_SUM.setText(bundle.getString("GenericEpicrisisEditor.EDIT_SUM.text")); // NOI18N
        EDIT_SUM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EDIT_SUMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(EDIT_SUM)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EDIT_SUM)
                .addGap(6, 6, 6))
        );

        jTabbedPane1.addTab(bundle.getString("GenericEpicrisisEditor.jPanel2.TabConstraints.tabTitle"), new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/24/plaster.png")), jPanel2); // NOI18N

        L_RECOM.setDragEnabled(true);
        L_RECOM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                L_RECOMMouseClicked(evt);
            }
        });
        L_RECOM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                L_RECOMKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(L_RECOM);

        EDIT_REC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Edit/Edit_16x16.png"))); // NOI18N
        EDIT_REC.setText(bundle.getString("GenericEpicrisisEditor.EDIT_REC.text")); // NOI18N
        EDIT_REC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EDIT_RECActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(EDIT_REC)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EDIT_REC)
                .addGap(6, 6, 6))
        );

        jTabbedPane1.addTab(bundle.getString("GenericEpicrisisEditor.jPanel3.TabConstraints.tabTitle"), new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/24/first_aid_kit.png")), jPanel3); // NOI18N

        TEXT_EDITOR.setColumns(20);
        TEXT_EDITOR.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        TEXT_EDITOR.setLineWrap(true);
        TEXT_EDITOR.setRows(5);
        TEXT_EDITOR.setWrapStyleWord(true);
        jScrollPane1.setViewportView(TEXT_EDITOR);

        B_SAVECLOSE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Stop/Stop_16x16.png"))); // NOI18N
        B_SAVECLOSE.setText(bundle.getString("GenericEpicrisisEditor.B_SAVECLOSE.text")); // NOI18N
        B_SAVECLOSE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_SAVECLOSEActionPerformed(evt);
            }
        });

        B_CANCEL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Cancel/Cancel_16x16.png"))); // NOI18N
        B_CANCEL.setText(bundle.getString("GenericEpicrisisEditor.B_CANCEL.text")); // NOI18N
        B_CANCEL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_CANCELActionPerformed(evt);
            }
        });

        B_SAVE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Save/Save_16x16.png"))); // NOI18N
        B_SAVE.setText(bundle.getString("GenericEpicrisisEditor.B_SAVE.text")); // NOI18N
        B_SAVE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_SAVEActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/24/doctor.png"))); // NOI18N
        jLabel2.setText(bundle.getString("GenericEpicrisisEditor.jLabel2.text")); // NOI18N

        DOCTORS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DOCTORSActionPerformed(evt);
            }
        });

        B_PRINT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Print/Print_24x24.png"))); // NOI18N
        B_PRINT.setText(bundle.getString("GenericEpicrisisEditor.B_PRINT.text")); // NOI18N
        B_PRINT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_PRINTActionPerformed(evt);
            }
        });

        L_PATIENT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        L_PATIENT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/24/patient.png"))); // NOI18N
        L_PATIENT.setText(bundle.getString("GenericEpicrisisEditor.L_PATIENT.text")); // NOI18N

        jLabel1.setText(bundle.getString("GenericEpicrisisEditor.jLabel1.text")); // NOI18N

        INV_LIST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                INV_LISTActionPerformed(evt);
            }
        });

        EDIT_INVLIST.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ehospital/images/must_have_icon_set/Edit/Edit_16x16.png"))); // NOI18N
        EDIT_INVLIST.setText(bundle.getString("GenericEpicrisisEditor.EDIT_INVLIST.text")); // NOI18N
        EDIT_INVLIST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EDIT_INVLISTActionPerformed(evt);
            }
        });

        QUICKPRINT.setText(bundle.getString("GenericEpicrisisEditor.QUICKPRINT.text")); // NOI18N

        FORM_NAME.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FORM_NAME.setText(bundle.getString("GenericEpicrisisEditor.FORM_NAME.text")); // NOI18N
        FORM_NAME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FORM_NAMEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(INV_LIST, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(FORM_ICON)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(FORM_NAME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(7, 7, 7)
                                .addComponent(EDIT_INVLIST))
                            .addComponent(jTabbedPane1))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DOCTORS, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(L_PATIENT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(QUICKPRINT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(B_PRINT, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(B_CANCEL, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(B_SAVE, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(B_SAVECLOSE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {B_CANCEL, B_PRINT, B_SAVE, B_SAVECLOSE});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FORM_ICON)
                            .addComponent(FORM_NAME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(INV_LIST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EDIT_INVLIST)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(L_PATIENT)
                            .addComponent(B_PRINT)
                            .addComponent(QUICKPRINT))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(DOCTORS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(B_CANCEL)
                    .addComponent(B_SAVE)
                    .addComponent(B_SAVECLOSE))
                .addGap(9, 9, 9))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void B_SAVECLOSEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_SAVECLOSEActionPerformed
        // TODO add your handling code here:
        save();
        B_CANCELActionPerformed(evt);
    }//GEN-LAST:event_B_SAVECLOSEActionPerformed

    private void B_CANCELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_CANCELActionPerformed
        // TODO add your handling code here:
        if (SAVED==false) {
            int ans=JOptionPane.showConfirmDialog(this, L_SAVE_YES_NO_TEXT.getText(),
                    L_SAVE_YES_NO_TITLE.getText(),
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    L_SAVE_YES_NO_TITLE.getIcon());
            if (ans==0) save();
            if (ans!=2) TabManager.removeTab(this);
        } else TabManager.removeTab(this);
        
        
    }//GEN-LAST:event_B_CANCELActionPerformed

    private void B_SAVEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_SAVEActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_B_SAVEActionPerformed

    private void L_RECOMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_L_RECOMKeyPressed
        if (evt.getKeyCode() == 10) {
            insertText(L_RECOM.getSelectedValue().toString());
            JListHelper.scrollSelectedToTop(L_RECOM);
        }
    }//GEN-LAST:event_L_RECOMKeyPressed

    private void L_INVEST1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_L_INVEST1KeyPressed
        if (evt.getKeyCode() == 10) {
           insertText(L_INVEST1.getSelectedValue().toString());
           JListHelper.scrollSelectedToTop(L_INVEST1);
        }
    }//GEN-LAST:event_L_INVEST1KeyPressed

    private void L_INVEST1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_INVEST1MouseClicked
        if (evt.getClickCount() == 2) {
            insertText(L_INVEST1.getSelectedValue().toString());
            JListHelper.scrollSelectedToTop(L_INVEST1);
        }
    }//GEN-LAST:event_L_INVEST1MouseClicked

    private void L_RECOMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_RECOMMouseClicked
        if (evt.getClickCount() == 2) {
            insertText(L_RECOM.getSelectedValue().toString());
            JListHelper.scrollSelectedToTop(L_RECOM);
        }
    }//GEN-LAST:event_L_RECOMMouseClicked

    private void DOCTORSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DOCTORSActionPerformed
        // TODO add your handling code here:
        Iterator it = DOCTOR_LIST.entrySet().iterator();
        int DOC_id=0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            //System.out.println(pairs.getKey() + " = " + pairs.getValue());
            if (pairs.getValue().toString().matches(DOCTORS.getSelectedItem().toString())) {
                //System.out.println("Doctors name for current selection: " + DOCTORS.getSelectedItem().toString() + " id: " + pairs.getKey());
                DOC_id=Integer.parseInt(pairs.getKey().toString());
            }
        }
        EPICRISIS.setDoctorId(DOC_id);
    }//GEN-LAST:event_DOCTORSActionPerformed

    private void L_SUMMARYKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_L_SUMMARYKeyPressed

         if (evt.getKeyCode() == 10) {
            insertText(L_SUMMARY.getSelectedValue().toString());
            JListHelper.scrollSelectedToTop(L_SUMMARY);
        }
    }//GEN-LAST:event_L_SUMMARYKeyPressed

    private void L_SUMMARYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_L_SUMMARYMouseClicked
        if (evt.getClickCount() == 2) {
            insertText(L_SUMMARY.getSelectedValue().toString());
            JListHelper.scrollSelectedToTop(L_SUMMARY);
        }
    }//GEN-LAST:event_L_SUMMARYMouseClicked

    private void B_PRINTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_PRINTActionPerformed
        // TODO add your handling code here:
        save();
        
                PrinterBean pb=new PrinterBean( EPICRISIS);
        if (QUICKPRINT.isSelected()) pb.printAll();
        else TabManager.insertTab(pb);
        EHospital.PROPERTIES.setProperty("QUICKPRINT", String.valueOf(QUICKPRINT.isSelected()));
        EHospitalProperties.SaveProperties(EHospital.PROPERTIES);
    }//GEN-LAST:event_B_PRINTActionPerformed

    private void INV_LISTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_INV_LISTActionPerformed
        int index=INV_LIST.getSelectedIndex();
        if (index<LIST_.size()) {
            L_INVEST1.setModel(LIST_.get(index));
            //L_SUMMARY.setModel(LIST_S.get(index));
        }
        insertText(DEL_ROW+INV_LIST.getSelectedItem().toString()+DEL_ROW);
    }//GEN-LAST:event_INV_LISTActionPerformed

    private void EDIT_INVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EDIT_INVActionPerformed
        // TODO add your handling code here:
        getEditor();
    }//GEN-LAST:event_EDIT_INVActionPerformed

    private void EDIT_SUMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EDIT_SUMActionPerformed
        int ind=INV_LIST.getSelectedIndex();
//        new Editor(INV_LIST.getSelectedItem().toString(),"ultrasound/bundles/"+PREFIX+ind+".txt");
        new Editor(jTabbedPane1.getTitleAt(1)+": "+INV_LIST.getSelectedItem().toString(),"generic/bundles/SUMMARY_"+PREFIX+ind+".txt");
    }//GEN-LAST:event_EDIT_SUMActionPerformed

    private void EDIT_RECActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EDIT_RECActionPerformed
        new Editor(jTabbedPane1.getTitleAt(2),"generic/bundles/REC_.txt");
    }//GEN-LAST:event_EDIT_RECActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        if (jTabbedPane1.getSelectedIndex()>0)
            insertText("\n"+jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())+":\n");
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void EDIT_INVLISTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EDIT_INVLISTActionPerformed
        new Editor(jTabbedPane1.getTitleAt(0)+": ","generic/bundles/INV_LIST_"+PREFIX+".txt");
        
    }//GEN-LAST:event_EDIT_INVLISTActionPerformed

    private void FORM_NAMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FORM_NAMEActionPerformed
        EHospital.PROPERTIES.setProperty("EPICRISIS_NAME", FORM_NAME.getText());
        EHospitalProperties.SaveProperties(EHospital.PROPERTIES);
    }//GEN-LAST:event_FORM_NAMEActionPerformed

    private void save(){
        EPICRISIS.setEpiName(FORM_NAME.getText()+" "+INV_LIST.getSelectedItem().toString());
        EPICRISIS.setEpiDescription(TEXT_EDITOR.getText());
        EPICRISIS.save();
        SAVED=true;
        
    }
    private String filterText(String str) {
        str=str.replace("■", "").replace("└", "").
                replace("├", "").
                replace("[", "").
                replace("]", "").
                replace("─", "").replace("  ", " ").replace("│", "");
        return str;
    }
     private void insertText(String str) {

        if (str.matches("(.?|.+)\\[\\](.?|.+)\\[\\](.?|.+)")) {
            String arr[] = str.replace("]", " ").split("\\[");
            P_SIZES2_L1.setText(filterText(arr[0]));
            P_SIZES2_L2.setText(arr[1]);
            P_SIZES2_L3.setText(arr[2]);
            JOptionPane.showMessageDialog(this, P_SIZES2);
            str = arr[0] + P_SIZES_X.getText() + arr[1] + P_SIZES_Y.getText() + arr[2];
        }

        if (str.matches("(.?|.+)\\[\\](.?|.+)")) {
            String arr[] = str.replace("]", " ").split("\\[");
            P_SIZES1_L1.setText(filterText(arr[0]));
            P_SIZES1_L2.setText(arr[1]);
            JOptionPane.showMessageDialog(this, P_SIZES1);
            str = arr[0] + P_SIZES_X1.getText() + arr[1];
        }
        TEXT_EDITOR.insert(filterText(str) + DEL_WORD, TEXT_EDITOR.getCaretPosition());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_CANCEL;
    private javax.swing.JButton B_PRINT;
    private javax.swing.JButton B_SAVE;
    private javax.swing.JButton B_SAVECLOSE;
    private javax.swing.JComboBox DOCTORS;
    private javax.swing.JButton EDIT_INV;
    private javax.swing.JButton EDIT_INVLIST;
    private javax.swing.JButton EDIT_REC;
    private javax.swing.JButton EDIT_SUM;
    private javax.swing.JLabel FORM_ICON;
    private javax.swing.JTextField FORM_NAME;
    private javax.swing.JComboBox INV_LIST;
    private javax.swing.JList L_INVEST1;
    private javax.swing.JLabel L_PATIENT;
    private javax.swing.JList L_RECOM;
    private javax.swing.JLabel L_SAVE_YES_NO_TEXT;
    private javax.swing.JLabel L_SAVE_YES_NO_TITLE;
    private javax.swing.JList L_SUMMARY;
    private javax.swing.JPanel P_SIZES1;
    private javax.swing.JLabel P_SIZES1_L1;
    private javax.swing.JLabel P_SIZES1_L2;
    private javax.swing.JPanel P_SIZES2;
    private javax.swing.JLabel P_SIZES2_L1;
    private javax.swing.JLabel P_SIZES2_L2;
    private javax.swing.JLabel P_SIZES2_L3;
    private javax.swing.JFormattedTextField P_SIZES_X;
    private javax.swing.JFormattedTextField P_SIZES_X1;
    private javax.swing.JFormattedTextField P_SIZES_Y;
    private javax.swing.JCheckBox QUICKPRINT;
    private javax.swing.JTextArea TEXT_EDITOR;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
