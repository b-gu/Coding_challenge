package com.mkyong.csv;

//imports for parsing csv file
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//imports for uploading to sqlite database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//imports for creating sqlite database
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

//imports for writing to log file
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;

public class passCSV{
	
	public static void createNewDatabase(String fileName) {
 
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public static void createNewTable(String fileName) {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/" + fileName;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS clients (A CHAR(50), B CHAR(50), C CHAR(50),
		D CHAR(50), E CHAR(50), F CHAR(50), G CHAR(50), H CHAR(50), I CHAR(50), J CHAR(50))";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	private Connection connect(String fileName) {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/" + fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public static void main(String[] args){
		
		createNewDatabase("Jr. Coding Challenge Page 2.csv");
		createNewTable("Jr. Coding Challenge Page 2.csv");
		
		//record counts
		int recordsReceived = 0;
		int successfulRecords = 0;
		int failedRecords = 0;
		
		//set up csv file for parsing
		String csvData = "Jr. Coding Challenge Page 2.csv";
		BufferedReader BR = null;
		String line = "";
		String splitBy = ",";
		
		//create database here
		createNewDatabase("Jr. Coding Challenge Page 2.csv");
		
		//file for bad entries
		FileWriter csvWriter = new FileWriter("Jr. Coding Challenge Page 2-bad.csv");
		
		try{
		BR = new BufferedReader(new FileReader(csvData));
		while((line = br.readLine()) != null){
			String[] client = line.split(splitBy);
			recordsReceived++;
			//dealing with a bad entry
			if (client.length != 10){
				failedRecords++;
				for(int i=0; i<client.length; i++){
					csvWriter.append(String.join(",", client[i]));
				}
				csvWriter.append("\n");
				continue;
			}
			String sqlInsert = "INSERT INTO clients(A,B,C,D,E,F,G,H,I,J) VALUES(?,?,?,?,?,?,?,?,?,?)";
			try (Connection con = this.connect();
			PreparedStatement prepstmt = con.prepareStatement(sqlInsert)){
				prepstmt.setString(1, client[0]);
				prepstmt.setString(2, client[1]);
				prepstmt.setString(3, client[2]);
				prepstmt.setString(4, client[3]);
				prepstmt.setString(5, client[4]);
				prepstmt.setString(6, client[5]);
				prepstmt.setString(7, client[6]);
				prepstmt.setString(8, client[7]);
				prepstmt.setString(9, client[8]);
				prepstmt.setString(10, client[9]);
			}
			successfulRecords++;
		}
	
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		Logger logger = Logger.getLogger(WriteLogToFile.class.getName());
		FileHandler fileHandler = new FileHandler("Jr. Coding Challenge Page 2.log", true);        
        logger.addHandler(fileHandler);
		 if (logger.isLoggable(Level.INFO)) {
            logger.info("Records received: " + recordsReceived);
			logger.info("Records successful: " + successfulRecords);
			logger.info("Records faile: " + failedRecords);
        }
	}

}