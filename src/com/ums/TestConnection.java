package com.ums;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
public class TestConnection {
public static void main(String[] args) {
try (Connection connection = DBManager.getConnection()) {
JOptionPane.showMessageDialog (null, "Connexion à la base OK." );
} catch (Exception e) {
JOptionPane.showMessageDialog (null, e.getMessage(), "Error", 
JOptionPane.ERROR_MESSAGE );
}
}
}