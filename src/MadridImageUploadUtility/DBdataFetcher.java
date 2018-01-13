package MadridImageUploadUtility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;


public class DBdataFetcher {
	
	UtilityConfiguration  uc	=	null;
	
	
	private String databaseHost	= null; 
	private String databasePort	=	null;
	private String databaseTool	=	null;
	private String databaseName	=	null;
	private	String userName		=	null;
	private	String password		=	 null;
	private	Connection connection	=	null;
	
	public static Logger logger	=	Logger.getLogger(DBdataFetcher.class);
	
	
	
	
	public DBdataFetcher(String databaseHost, String databasePort,
			String databaseTool, String databaseName,String userName,String password ){
		
		
		 this.databaseHost	= databaseHost; 
		 this.databasePort	=	databasePort;
		 this.databaseTool	=	databaseTool;
		 this.databaseName	=	databaseName;
		 this.userName		=	userName;
		 this.password		=	 password;
		 
			init();
		
	}
	
	public void init(){
		
		if(connection==null)
			configureConnection();
		
	}
	
	public Connection getConnection(){
		
		return connection;
	}
	
	
	public void closeConnection(){
		
		 if(connection!=null){
		 
		 try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
		
	}
	
	
	
	public  void 	configureConnection() {
		// TODO Auto-generated method stub
		//Connection conn = null;
		if( databaseTool.equalsIgnoreCase("oracle") ){
			
			logger.info("Database Type is Oracle....");
			
		//("jdbc:oracle:thin:@" + databaseHost + ":" + databasePort + ":"+databaseName,userName,password  );
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection	=	DriverManager.getConnection("jdbc:oracle:thin:@" + databaseHost + ":" + databasePort + ":"+databaseName,userName,password  );
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			
			logger.error("Error Occured while loading OracleDriver class ", e);
			//e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Error Occured while obtaining connection ", e);
		//	e.printStackTrace();
		}
		}
		else if(databaseTool.equalsIgnoreCase("sql")){
			
			logger.info("Database Type is MSSQL....");
			
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				
				connection	=	DriverManager.getConnection( "jdbc:sqlserver://"+databaseHost+ ":" + databasePort +";" +
			    		  "databaseName="+databaseName+";"+"user="+userName+";"+"password="+password);
			} catch (ClassNotFoundException e) {
				logger.error("Error Occured while loading SQLDriver class ", e);
			//	e.printStackTrace();
			} catch (SQLException e) {
				logger.error("Error Occured While obtaining  Connection: ", e);
			//	e.printStackTrace();
			}
			
			
		}
		
		
	
		
		
	}
	
	
	public boolean  getMarkDetails( MadridMark madridMark2){
		
		
		PreparedStatement preparedStatement = null;
		ResultSet rs	= null;
		boolean	isPresent	=	false;
		
		String query2 = "SELECT * FROM IP_MARK where INTREGN = ?";
		try {
			init();
			preparedStatement	=	connection.prepareStatement(query2);
			
			preparedStatement.setString(1, madridMark2.getIntregn());

			 
		      rs = preparedStatement.executeQuery();
		
		      while (rs.next()) {
		    	  
		    	  madridMark2.setFile_nbr(rs.getString("FILE_NBR"));
		    	  madridMark2.setFile_seq(rs.getString("FILE_SEQ"));
  		    	  madridMark2.setFile_ser( rs.getString("FILE_SER"));
		    	  madridMark2.setFile_typ( rs.getString("FILE_TYP"));
		    	  madridMark2.setSign_wcode( rs.getString("SIGN_WCODE"));
		    
		    	  isPresent=true;
		      }
		
		  
		   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Error Occured while fetching  data related to intregn ::" +madridMark2.getIntregn() +"::from IP_MARK table.... ", e);
		}
	    
		finally{
			 if (rs != null){
				 
				 try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 
			 
			 if(preparedStatement!=null){
			 
				 try {
					 preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }

	}
		return isPresent;
		
	
	}
	

	

	
	
	

		


}
