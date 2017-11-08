/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guitypes;

import javax.swing.Icon;

/**
 *
 * @author pigeon
 */
public abstract class TabAbstractPanel extends javax.swing.JPanel {

    /**
     *
     * @return
     */
    @Override
    public abstract String toString();
    public abstract Icon getIcon();
    public abstract void close();
    
}
