/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guitypes;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author pigeon
 */
public class IconFactory {
    public static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = IconFactory.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
public static Icon scaleImageIcon(Icon icon, int size) {    
    try {
            return new ImageIcon(((ImageIcon)icon).getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
        } catch (Exception ex) {
            System.out.println("IconFactory.scaleImageIcon: Could not read resource: " + ex.toString());
            return null;
        }
    
}

    public static Image createImage(String path) {
        
        try {
            return ImageIO.read(IconFactory.class.getResource(path));
        } catch (IOException ex) {
            System.out.println("Could not read resource" + ex.toString());
            return null;
        }
    }
}
