/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repairtracker.views;

import guitypes.TabAbstractPanel;
import guitypes.TabManager;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repairtracker.RTProperties;
import repairtracker.dialogs.Editor;
import repairtracker.helpers.PropertiesReader;
import repairtracker.models.Address;
import repairtracker.models.Client;
import repairtracker.models.Issue;
import repairtracker.models.IssueAttribute;
import repairtracker.models.IssueAttributeType;
import repairtracker.models.Warranty;

/**
 *
 * @author user
 */
public class IssueEditor extends TabAbstractPanel {
    
    public static Logger LOGGER=LogManager.getLogger(IssueEditor.class.getName());
    Issue ISSUE;
    Client CLIENT;
    Address CLIENT_ADDRESS;
    
    ArrayList<IssueAttribute> ISSUE_ATTRIBETES;

    /**
     * Creates new form IssueEditor
     */
    public IssueEditor() {
      this(-1);
    }
    
    public IssueEditor(int id) {
        initComponents();
        
        
        LOGGER.info("Opening issue: "+id);
        ISSUE=new Issue(id);
        if (ISSUE.id()>-1) THIS_COMPONENT.setText(ISSUE.toString());
        else THIS_COMPONENT.setText(java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("UNNAMED"));
        CLIENT=new Client(ISSUE.clientId());
        CLIENT_ADDRESS=new Address(CLIENT.id());
        ISSUE_TYPE.setSelectedIndex(ISSUE.issueTypeId());
        START_DATE.setDate(ISSUE.startDate());
        END_DATE.setDate(ISSUE.endDate());
        PREPAID.setValue(ISSUE.prepayCost());
        ISSUE_STATUS.setSelectedIndex(ISSUE.status());
        WARRANTY_TYPE.setSelectedIndex(ISSUE.warrantyTypeId());
        CLIENT_NAME.setText(CLIENT.firstName());
        CLIENT_PHONE.setText(CLIENT_ADDRESS.phone());
        CLIENT_EMAIL.setText(CLIENT_ADDRESS.email());
        CLIENT_STREET.setText(CLIENT_ADDRESS.address1());
        COMMENTS.setText(ISSUE.comments());
        DESCRIPTION.setText(ISSUE.description());
        DEVICE_MODEL.setText(ISSUE.deviceName());
        DEVICE_NUMBER.setText(ISSUE.deviceNumber());
        
        
        // load predifined model
        DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>();
        for (String l : java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("IssueEditor.DEVICE_TYPE").split(","))
            model1.addElement(l);
        DEVICE_TYPE.setModel(model1);
        DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>();
        for (String l : java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("ISSUE_STATUS").split(","))
            model2.addElement(l);
        ISSUE_STATUS.setModel(model2);
        
        
        if (PropertiesReader.getFilesAsComboboxModel("warranties").getSize()>0) WARRANTY_TYPE.setModel(PropertiesReader.getFilesAsComboboxModel("warranties"));
        
        ISSUE_ATTRIBUTES.setModel(PropertiesReader.getTableModel(getIssueAttributesFileName()));
        //ISSUE_ATTRIBUTES.getColumnModel().getColumn(1).setWidth(50);
        
        Object[][] rowDATA = {};
        String[] colNames = {"",java.util.ResourceBundle.getBundle("repairtracker/helpers/Bundle").getString("ITEMNAME"),java.util.ResourceBundle.getBundle("repairtracker/helpers/Bundle").getString("PRICE")};
        REPLACEMENT_PARTS.setModel(new DefaultTableModel(rowDATA, colNames));
        WORKLOG.setModel(new DefaultTableModel(rowDATA, colNames));
        
        if (ISSUE.id()>-1) {
            WORKLOG.setModel(IssueAttribute.getAttributesAsTable(ISSUE.id(), 0));
            REPLACEMENT_PARTS.setModel(IssueAttribute.getAttributesAsTable(ISSUE.id(), 1));
            WARRANTY_TYPE.setSelectedItem(new Warranty(ISSUE.warrantyTypeId()).getName());
        }
        
        
        ISSUE_ATTRIBUTES.getColumnModel().getColumn(1).setMaxWidth(120);
        /*REPLACEMENT_PARTS.getColumnModel().getColumn(0).setMaxWidth(0);
        REPLACEMENT_PARTS.getColumnModel().getColumn(0).setPreferredWidth(0);
        REPLACEMENT_PARTS.getColumnModel().getColumn(0).setWidth(0);
        REPLACEMENT_PARTS.getColumnModel().getColumn(0).setMinWidth(0);*/
        REPLACEMENT_PARTS.getColumnModel().removeColumn(REPLACEMENT_PARTS.getColumnModel().getColumn(0));
        WORKLOG.getColumnModel().removeColumn(WORKLOG.getColumnModel().getColumn(0));
        REPLACEMENT_PARTS.getColumnModel().getColumn(1).setMaxWidth(120);
        WORKLOG.getColumnModel().getColumn(1).setMaxWidth(120);
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        THIS_COMPONENT = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        ISSUE_TYPE = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        END_DATE = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        START_DATE = new com.toedter.calendar.JDateChooser();
        ISSUE_STATUS = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        WARRANTY_TYPE = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        PREPAID = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        DEVICE_MODEL = new javax.swing.JTextField();
        DEVICE_TYPE = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        DEVICE_NUMBER = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        RB_REPLACEMENT_PARTS = new javax.swing.JRadioButton();
        RB_WORK = new javax.swing.JRadioButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        ISSUE_ATTRIBUTES = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        CLIENT_STREET = new javax.swing.JTextField();
        CLIENT_PHONE = new javax.swing.JTextField();
        CLIENT_NAME = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        CLIENT_EMAIL = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        REPLACEMENT_PARTS = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        COMMENTS = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        DESCRIPTION = new javax.swing.JTextPane();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        WORKLOG = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        DELETE_REPLACEMENT = new javax.swing.JButton();
        NEW_REPLACEMENT = new javax.swing.JButton();
        DELETE_WORK = new javax.swing.JButton();
        NEW_WORK = new javax.swing.JButton();

        THIS_COMPONENT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/icons8-scorecard-16.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("repairtracker/views/Bundle"); // NOI18N
        THIS_COMPONENT.setText(bundle.getString("IssueEditor.THIS_COMPONENT.text")); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("IssueEditor.jPanel3.border.title"))); // NOI18N

        ISSUE_TYPE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular", "Warranty", "Consulting" }));

        jLabel7.setText(bundle.getString("IssueEditor.jLabel7.text")); // NOI18N

        jLabel6.setText(bundle.getString("IssueEditor.jLabel6.text")); // NOI18N

        ISSUE_STATUS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "New", "In progress", "Completed" }));

        jLabel11.setText(bundle.getString("IssueEditor.jLabel11.text")); // NOI18N

        jLabel4.setText(bundle.getString("IssueEditor.jLabel4.text")); // NOI18N

        jLabel9.setText(bundle.getString("IssueEditor.jLabel9.text")); // NOI18N

        WARRANTY_TYPE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular new", "Used parts", "Regular repair" }));

        jLabel16.setText(bundle.getString("IssueEditor.jLabel16.text")); // NOI18N

        PREPAID.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0.##"))));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(23, 23, 23))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(21, 21, 21)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ISSUE_STATUS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ISSUE_TYPE, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(WARRANTY_TYPE, 0, 158, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel16)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(END_DATE, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                        .addComponent(PREPAID))
                    .addComponent(START_DATE, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(START_DATE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ISSUE_TYPE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(ISSUE_STATUS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(END_DATE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(WARRANTY_TYPE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(PREPAID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("IssueEditor.jPanel4.border.title"))); // NOI18N

        jLabel8.setText(bundle.getString("IssueEditor.jLabel8.text")); // NOI18N

        DEVICE_MODEL.setText(bundle.getString("IssueEditor.DEVICE_MODEL.text")); // NOI18N
        DEVICE_MODEL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEVICE_MODELActionPerformed(evt);
            }
        });

        DEVICE_TYPE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phone", "Tablet", "Laptop", "PC" }));
        DEVICE_TYPE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEVICE_TYPEActionPerformed(evt);
            }
        });

        jLabel12.setText(bundle.getString("IssueEditor.jLabel12.text")); // NOI18N

        DEVICE_NUMBER.setText(bundle.getString("IssueEditor.DEVICE_NUMBER.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DEVICE_TYPE, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DEVICE_MODEL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DEVICE_NUMBER)
                .addGap(206, 206, 206))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DEVICE_TYPE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(DEVICE_MODEL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(DEVICE_NUMBER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("IssueEditor.jPanel7.border.title"))); // NOI18N

        jButton9.setText(bundle.getString("IssueEditor.jButton9.text")); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        buttonGroup1.add(RB_REPLACEMENT_PARTS);
        RB_REPLACEMENT_PARTS.setText(bundle.getString("IssueEditor.RB_REPLACEMENT_PARTS.text")); // NOI18N
        RB_REPLACEMENT_PARTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_REPLACEMENT_PARTSActionPerformed(evt);
            }
        });

        buttonGroup1.add(RB_WORK);
        RB_WORK.setSelected(true);
        RB_WORK.setText(bundle.getString("IssueEditor.RB_WORK.text")); // NOI18N
        RB_WORK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_WORKActionPerformed(evt);
            }
        });

        ISSUE_ATTRIBUTES.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ISSUE_ATTRIBUTES.setRowSelectionAllowed(false);
        ISSUE_ATTRIBUTES.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ISSUE_ATTRIBUTES.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ISSUE_ATTRIBUTESMousePressed(evt);
            }
        });
        jScrollPane7.setViewportView(ISSUE_ATTRIBUTES);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RB_WORK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RB_REPLACEMENT_PARTS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addContainerGap())
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(RB_REPLACEMENT_PARTS)
                    .addComponent(RB_WORK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("IssueEditor.jPanel8.border.title"))); // NOI18N

        jLabel1.setText(bundle.getString("IssueEditor.jLabel1.text")); // NOI18N

        jLabel2.setText(bundle.getString("IssueEditor.jLabel2.text")); // NOI18N

        jLabel3.setText(bundle.getString("IssueEditor.jLabel3.text")); // NOI18N

        CLIENT_STREET.setText(bundle.getString("IssueEditor.CLIENT_STREET.text")); // NOI18N

        CLIENT_PHONE.setText(bundle.getString("IssueEditor.CLIENT_PHONE.text")); // NOI18N

        CLIENT_NAME.setText(bundle.getString("IssueEditor.CLIENT_NAME.text")); // NOI18N

        jLabel15.setText(bundle.getString("IssueEditor.jLabel15.text")); // NOI18N

        CLIENT_EMAIL.setText(bundle.getString("IssueEditor.CLIENT_EMAIL.text")); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CLIENT_NAME, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CLIENT_EMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CLIENT_STREET)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(CLIENT_PHONE, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CLIENT_NAME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel15)
                    .addComponent(CLIENT_EMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CLIENT_PHONE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CLIENT_STREET, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel3, jPanel4, jPanel7});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanel5.setPreferredSize(new java.awt.Dimension(607, 700));

        REPLACEMENT_PARTS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(REPLACEMENT_PARTS);

        jScrollPane6.setViewportView(COMMENTS);

        jScrollPane5.setViewportView(DESCRIPTION);

        jLabel5.setText(bundle.getString("IssueEditor.jLabel5.text")); // NOI18N

        WORKLOG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(WORKLOG);

        jLabel10.setText(bundle.getString("IssueEditor.jLabel10.text")); // NOI18N

        jLabel13.setText(bundle.getString("IssueEditor.jLabel13.text")); // NOI18N

        jLabel14.setText(bundle.getString("IssueEditor.jLabel14.text")); // NOI18N

        DELETE_REPLACEMENT.setText(bundle.getString("IssueEditor.DELETE_REPLACEMENT.text")); // NOI18N
        DELETE_REPLACEMENT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DELETE_REPLACEMENTActionPerformed(evt);
            }
        });

        NEW_REPLACEMENT.setText(bundle.getString("IssueEditor.NEW_REPLACEMENT.text")); // NOI18N
        NEW_REPLACEMENT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NEW_REPLACEMENTActionPerformed(evt);
            }
        });

        DELETE_WORK.setText(bundle.getString("IssueEditor.DELETE_WORK.text")); // NOI18N
        DELETE_WORK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DELETE_WORKActionPerformed(evt);
            }
        });

        NEW_WORK.setText(bundle.getString("IssueEditor.NEW_WORK.text")); // NOI18N
        NEW_WORK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NEW_WORKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NEW_WORK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DELETE_WORK))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NEW_REPLACEMENT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DELETE_REPLACEMENT))
                    .addComponent(jScrollPane6)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(5, 5, 5))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {DELETE_WORK, NEW_WORK});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {DELETE_REPLACEMENT, NEW_REPLACEMENT});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(DELETE_REPLACEMENT)
                    .addComponent(NEW_REPLACEMENT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(DELETE_WORK)
                    .addComponent(NEW_WORK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void DEVICE_MODELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEVICE_MODELActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DEVICE_MODELActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        new Editor("EDIT: "+getIssueAttributesFileName(),getIssueAttributesFileName());        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed
        
    private void DEVICE_TYPEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEVICE_TYPEActionPerformed
        updateIssueAttributesModel();       
        
    }//GEN-LAST:event_DEVICE_TYPEActionPerformed

    private void RB_REPLACEMENT_PARTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_REPLACEMENT_PARTSActionPerformed
        updateIssueAttributesModel();
    }//GEN-LAST:event_RB_REPLACEMENT_PARTSActionPerformed

    private void RB_WORKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_WORKActionPerformed
        updateIssueAttributesModel();
    }//GEN-LAST:event_RB_WORKActionPerformed

    private void ISSUE_ATTRIBUTESMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ISSUE_ATTRIBUTESMousePressed
        
        if (evt.getClickCount()==2) {
            String item_name=String.valueOf(ISSUE_ATTRIBUTES.getModel().getValueAt(ISSUE_ATTRIBUTES.getSelectedRow(), 0));
            String price=String.valueOf(ISSUE_ATTRIBUTES.getModel().getValueAt(ISSUE_ATTRIBUTES.getSelectedRow(), 1));
            LOGGER.info("Got element: "+item_name+" -> "+price);
            if (RB_REPLACEMENT_PARTS.isSelected()) ((DefaultTableModel)REPLACEMENT_PARTS.getModel()).addRow(new Object[]{"-1",item_name,price});
            if (RB_WORK.isSelected()) ((DefaultTableModel)WORKLOG.getModel()).addRow(new Object[]{"-1",item_name,price});
        }
    }//GEN-LAST:event_ISSUE_ATTRIBUTESMousePressed

    private void NEW_REPLACEMENTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NEW_REPLACEMENTActionPerformed
        ((DefaultTableModel)REPLACEMENT_PARTS.getModel()).addRow(new Object[]{"-1","",""});
    }//GEN-LAST:event_NEW_REPLACEMENTActionPerformed

    private void DELETE_REPLACEMENTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DELETE_REPLACEMENTActionPerformed
        int row=REPLACEMENT_PARTS.getSelectedRow();
        if ((row>-1)&& (row<REPLACEMENT_PARTS.getRowCount())) ((DefaultTableModel)REPLACEMENT_PARTS.getModel()).removeRow(row);
    }//GEN-LAST:event_DELETE_REPLACEMENTActionPerformed

    private void NEW_WORKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NEW_WORKActionPerformed
        ((DefaultTableModel)WORKLOG.getModel()).addRow(new Object[]{"-1","",""});
    }//GEN-LAST:event_NEW_WORKActionPerformed

    private void DELETE_WORKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DELETE_WORKActionPerformed
        int row=WORKLOG.getSelectedRow();
        if ((row>-1)&& (row<WORKLOG.getRowCount())) ((DefaultTableModel)WORKLOG.getModel()).removeRow(row);
    }//GEN-LAST:event_DELETE_WORKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CLIENT_EMAIL;
    private javax.swing.JTextField CLIENT_NAME;
    private javax.swing.JTextField CLIENT_PHONE;
    private javax.swing.JTextField CLIENT_STREET;
    private javax.swing.JTextPane COMMENTS;
    private javax.swing.JButton DELETE_REPLACEMENT;
    private javax.swing.JButton DELETE_WORK;
    private javax.swing.JTextPane DESCRIPTION;
    private javax.swing.JTextField DEVICE_MODEL;
    private javax.swing.JTextField DEVICE_NUMBER;
    private javax.swing.JComboBox<String> DEVICE_TYPE;
    private com.toedter.calendar.JDateChooser END_DATE;
    private javax.swing.JTable ISSUE_ATTRIBUTES;
    private javax.swing.JComboBox<String> ISSUE_STATUS;
    private javax.swing.JComboBox<String> ISSUE_TYPE;
    private javax.swing.JButton NEW_REPLACEMENT;
    private javax.swing.JButton NEW_WORK;
    private javax.swing.JFormattedTextField PREPAID;
    private javax.swing.JRadioButton RB_REPLACEMENT_PARTS;
    private javax.swing.JRadioButton RB_WORK;
    private javax.swing.JTable REPLACEMENT_PARTS;
    private com.toedter.calendar.JDateChooser START_DATE;
    private javax.swing.JLabel THIS_COMPONENT;
    private javax.swing.JComboBox<String> WARRANTY_TYPE;
    private javax.swing.JTable WORKLOG;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    // End of variables declaration//GEN-END:variables

    private String getIssueAttributesFileName(){
        String type="";
        if (RB_REPLACEMENT_PARTS.isSelected()) type="replacement";
        if (RB_WORK.isSelected()) type="work";
        return "issueitems"+RTProperties.FS+"issue_"+String.valueOf(DEVICE_TYPE.getSelectedIndex())+"_"+type+".txt";
    }
    
    private void updateIssueAttributesModel(){
        ISSUE_ATTRIBUTES.setModel(PropertiesReader.getTableModel(getIssueAttributesFileName()));
        ISSUE_ATTRIBUTES.getColumnModel().getColumn(1).setMaxWidth(120);
    }
    
    
    
    public void save(){
        
        // Save Client
        CLIENT.setFName(CLIENT_NAME.getText());
        CLIENT.save();
        // Save Address
        CLIENT_ADDRESS.setPatientId(CLIENT.id());
        CLIENT_ADDRESS.setPhone(CLIENT_PHONE.getText());
        CLIENT_ADDRESS.setEmail(CLIENT_EMAIL.getText());
        CLIENT_ADDRESS.setAddress(CLIENT_STREET.getText());
        CLIENT_ADDRESS.save();
        
        // Save Issue
        ISSUE.setClientId(CLIENT.id());
        ISSUE.setIssueTypeId(ISSUE_TYPE.getSelectedIndex());
        ISSUE.setDeviceTypeId(DEVICE_TYPE.getSelectedIndex());
        ISSUE.setDeviceName(DEVICE_MODEL.getText());
        ISSUE.setDeviceNumber(DEVICE_NUMBER.getText());
        ISSUE.setComments(COMMENTS.getText());
        ISSUE.setDescription(DESCRIPTION.getText());
        ISSUE.setStartDate(START_DATE.getDate());
        ISSUE.setEndDate(END_DATE.getDate());
        ISSUE.setEndDate(END_DATE.getDate());
        ISSUE.setPrepayCost(Double.parseDouble(PREPAID.getText()));
        ISSUE.setStatus(ISSUE_STATUS.getSelectedIndex());
        
        // Set Warranty
        Warranty w=new Warranty(WARRANTY_TYPE.getSelectedItem().toString());
        if (w.id()==-1) { 
            w.setContent(PropertiesReader.getFileAsString("warranties"+RTProperties.FS+WARRANTY_TYPE.getSelectedItem().toString()));
            w.save();
        } 
        ISSUE.setWarrantyTypeId(w.id());
        
        ISSUE.save();
        
       
        int rc=REPLACEMENT_PARTS.getModel().getRowCount();
        int wl=WORKLOG.getModel().getRowCount();
        
        for (int i=0;i<wl;i++) {
            int id=Integer.parseInt(String.valueOf(WORKLOG.getModel().getValueAt(i, 0)));
            String item_name=String.valueOf(WORKLOG.getModel().getValueAt(i, 1));
            Double price=Double.parseDouble(String.valueOf(WORKLOG.getModel().getValueAt(i, 2)));
            IssueAttribute ia=new IssueAttribute(id);
            ia.setName(item_name);
            ia.setIssueId(ISSUE.id());
            ia.setTypeId(0);
            ia.setPrice(price);
            LOGGER.info("Save element: "+id+" : "+item_name+" -> "+price);
            ia.save();
        }
        
        for (int i=0;i<rc;i++) {
            int id=Integer.parseInt(String.valueOf(REPLACEMENT_PARTS.getModel().getValueAt(i, 0)));
            String item_name=String.valueOf(REPLACEMENT_PARTS.getModel().getValueAt(i, 1));
            Double price=Double.parseDouble(String.valueOf(REPLACEMENT_PARTS.getModel().getValueAt(i, 2)));
            IssueAttribute ia=new IssueAttribute(id);
            ia.setName(item_name);
            ia.setIssueId(ISSUE.id());
            ia.setPrice(price);
            ia.setTypeId(1);
            LOGGER.info("Save element: "+id+" : "+item_name+" -> "+price);
            ia.save();
        }
        
        THIS_COMPONENT.setText(ISSUE.toString());
        TabManager.updateTitle(this);
        
    }
    
    @Override
    public void close() {
        int dialogResult=JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("DO YOU WANT TO SAVE CHANGES?"),java.util.ResourceBundle.getBundle("repairtracker/views/Bundle").getString("SAVE CHANGES: ")+THIS_COMPONENT.getText(),JOptionPane.WARNING_MESSAGE);
                        
                        if(dialogResult == JOptionPane.YES_OPTION){
                            save();
                           
                        } 
        
        TabManager.removeTab(this);
    }
     @Override
    public Icon getIcon() {
        return THIS_COMPONENT.getIcon();
    }
    public static JMenu getMenu(){
           return null; 
    }
    
    @Override
    public String toString() {
        return THIS_COMPONENT.getText();
    }
    
    
    @Override
    public void print(){
        TabManager.insertTab(new PrinterBean(ISSUE));
    }

    @Override
    public Boolean isSaved() {
        return true;
    }
    
    @Override
    public void newItem() {
        TabManager.insertTab(new IssueEditor());
    }
}