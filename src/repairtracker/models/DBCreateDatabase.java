/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repairtracker.models;

import java.sql.*;
/**
 *
 * @author user
 */

    
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DBCreateDatabase {

    private Connection conn = null;
    private Statement sttm = null;
    
    private 

    public Connection DBCreateDatabase(String query) {
    try {
        //Obtenemos el Driver de Derby
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        conn = DriverManager.getConnection("jdbc:derby:nombrebasededatos.db;create=true");
        if (conn != null) {
            //JOptionPane.showMessageDialog(null, "Base de Datos Lista");
            try {
                PreparedStatement pstm = conn.prepareStatement(query);
                pstm.execute();
                pstm.close();
                //JOptionPane.showMessageDialog(null, "Base de Datos Creada Correctamente");
                System.out.println("SENTENCIA SQL EFECTUADA CORRECTAMENTE");
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null, "NO SE PUDO EFECTUAR LA SENTENCIA SQL", "Error", JOptionPane.ERROR_MESSAGE);
                //JOptionPane.showMessageDialog(null, "NO SE PUDO EFECTUAR LA SENTENCIA SQL");
            }
        }

    } catch (SQLException e) {
        System.out.println(e.getMessage());
        JOptionPane.showMessageDialog(null, "NO SE PUDO EFECTUAR LA SENTENCIA SQL", "Error", JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(null, "TRONO LA APLICACION EN EJECUTAR LAS SENTENCIAS SQL parte 2");
    } catch (ClassNotFoundException e) {
        System.out.println(e.getMessage());
        JOptionPane.showMessageDialog(null, "NO SE PUDO EFECTUAR LA SENTENCIA SQL", "Error", JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(null, "TRONO LA APLICACION EN EJECUTAR LAS SENTENCIAS SQL parte 3");
    }
    return conn;
}
}