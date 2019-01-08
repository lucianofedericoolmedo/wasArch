package com.isban.javaapps.reporting.connections;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Ignore;
import org.junit.Test;

public class TestConnection {
    
    @Ignore @Test
    public void test() {
    	    	
    	//DESCOMENTAR DE ACA PARA ABAJO
    	
        System.out.println("-------- Oracle JDBC Connection Testing ------");
        
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;

        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@180.250.40.192:5321:srv_RIO233D_ap", "ODSCORE", "prueba123");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }
		
        
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            Statement stmt;
			try {
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM ODSCORE.HE0_DT_PAIS");
				while (rs.next()) {
		            String pais = rs.getString("DES_PAIS");
		            System.out.println(pais);
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } else {
            System.out.println("Failed to make connection!");
        }
    
        
        assertTrue(true);
    	
    }

}
