/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guitypes;

import repairtracker.RepairTracker;
import repairtracker.views.RepairTrackerGUI;
import static guitypes.TabManager.PANEL;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.apache.logging.log4j.LogManager;



public class TabManager {

    
    ResourceBundle LANG=java.util.ResourceBundle.getBundle("guitypes/Bundle"); 
    private static org.apache.logging.log4j.Logger LOGGER=LogManager.getLogger(TabManager.class.getName()); 
    static JMenu MENU;
    static List<JMenuItem> M_ITEMS;
    static JTabbedPane PANEL;
    
public TabManager(JTabbedPane panel) {
        PANEL = panel;
        MENU = new JMenu(LANG.getString("TabManager"));
        MENU.setIcon(IconFactory.createImageIcon("/images/16/window.png"));
        addMenuItem();
    }

public static JMenu getMenu(){
    return MENU;
}

private static void toDo(java.awt.event.ActionEvent evt){
    
    String title=((JMenuItem)evt.getSource()).getText();
    int cnt = PANEL.getTabCount();
    for (int ii = 0; ii < cnt; ii++) {
            if (PANEL.getTitleAt(ii).equalsIgnoreCase(title)) {
                PANEL.setSelectedIndex(ii);
                break;
            }
        }
}
private static void addMenuItem(){
    
    
    if (PANEL.getTabCount()>0) {
    try {
        JMenuItem main_item=new JMenuItem(PANEL.getTitleAt(PANEL.getTabCount()-1));
        main_item.setIcon(IconFactory.scaleImageIcon(PANEL.getIconAt(PANEL.getTabCount()-1),16));
        main_item.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toDo(evt);
            } });
        MENU.add(main_item);
    } catch (Exception e) {
        System.out.println("Could not insert new menu item"+e.toString()); //NOI18N
    }
    }
}

public static void removeTab(Component cmp){
    PANEL.setSelectedComponent(cmp);
    String title=PANEL.getTitleAt(PANEL.getSelectedIndex());
    int cnt=MENU.getItemCount();
    for (int ii = 0; ii < cnt; ii++) {
            if (MENU.getItem(ii).getText().equalsIgnoreCase(title)) {
                MENU.remove(ii);
                break;
            }
        }
    PANEL.remove(cmp);
}

    public static void updateTitle(TabAbstractPanel cmp) {
        LOGGER.info("Updating title: " + cmp.toString() + " : " + PANEL.getSelectedIndex());
        try {
            PANEL.setSelectedComponent(cmp);

            if (cmp.isSaved()) {
                PANEL.setTitleAt(PANEL.getSelectedIndex(), cmp.toString());
                PANEL.setTabComponentAt(PANEL.getSelectedIndex(), new CloseComponent(cmp.toString(), cmp.getIcon()));
            } else {
                PANEL.setTitleAt(PANEL.getSelectedIndex(), cmp.toString() + "*");
                PANEL.setTabComponentAt(PANEL.getSelectedIndex(), new CloseComponent(cmp.toString()+ "*", cmp.getIcon()));
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

public static void insertTab(String classname)  {
     try {
          insertTab((TabAbstractPanel)Class.forName(classname).newInstance());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RepairTrackerGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(RepairTrackerGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RepairTrackerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
}
public static void insertTab(TabAbstractPanel usf)  {
    //System.out.println("This is starta:   "+usf.toString());
    String title = usf.toString();
    int cnt=PANEL.getTabCount();
    int index=-1;
    RepairTrackerGUI.STATUS.setText(java.util.ResourceBundle.getBundle("guitypes/Bundle").getString("INSERTED")+": "+title);
    if (cnt>0) index = PANEL.indexOfTab(title);
    if (index==-1) {
            PANEL.insertTab( title, usf.getIcon(), usf, null, cnt);
            
            PANEL.setSelectedIndex(cnt);
            //PANEL.getTabComponentAt(1).getC
            PANEL.setTabComponentAt(cnt, new CloseComponent(title,usf.getIcon()));
      
            PANEL.setSelectedIndex(cnt);
            addMenuItem();
    
    } else {
        PANEL.setSelectedIndex(index);
    }
}
    


}

class CloseComponent extends JPanel {

    public CloseComponent(String title, Icon icon) {
        
        setLayout(new java.awt.GridBagLayout());
        setOpaque(false);
JLabel lblTitle = new JLabel(title,IconFactory.scaleImageIcon(icon,Integer.parseInt(RepairTracker.PROPERTIES.getProperty("ICON_SIZE_TAB","16"))), 0);
//JLabel lblTitle=new JLabel("aaaa");

JButton btnClose = new JButton(

);
                btnClose.setIcon(IconFactory.scaleImageIcon(IconFactory.createImageIcon("/images/16/close.png"),
                        Integer.parseInt(RepairTracker.PROPERTIES.getProperty("ICON_SIZE_TAB","16"))));
        btnClose.setOpaque(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);

btnClose.addActionListener(new MyCloseActionHandler(title));

GridBagConstraints gbc = new GridBagConstraints();
gbc.gridx = 0;
gbc.gridy = 0;
gbc.weightx = 1;
add(lblTitle, gbc);
gbc.gridx++;
gbc.weightx = 0;
add(btnClose, gbc);

        
        
    }
    
        
}

 class MyCloseActionHandler implements ActionListener {

    private String tabName;

    public MyCloseActionHandler(String tabName) {
        this.tabName = tabName;
    }

    public String getTabName() {
        return tabName;
    }

    public void actionPerformed(ActionEvent evt) {

        int index = PANEL.indexOfTab(getTabName());
        if (index >= 0) {

            ((TabAbstractPanel)PANEL.getComponentAt(index)).close();
        }

    }

}   