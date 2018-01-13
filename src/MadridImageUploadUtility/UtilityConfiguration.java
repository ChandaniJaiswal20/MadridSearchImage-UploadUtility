package MadridImageUploadUtility;


import java.awt.geom.IllegalPathStateException;
import java.io.File;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;






public class UtilityConfiguration {

	ConfigParam	config	=	null;
	public String iiopPort;
	public String iiopHost;
	public String databaseHost;
	public String databasePort;
	public String databaseTool;
	public String databaseName;
	public String userName;
	public String password;
	public String madridXMLFolder;
	public String madridImageFolder;
	public String fileType;

	public static Logger logger	= Logger.getLogger(UtilityConfiguration.class);

	public UtilityConfiguration loadConfiguration() {
		logger.debug("loading configuration.....");
			config	= new ConfigParam("MadridImageUploadConfiguration.properties");
		//	patentAnnuityUtilityFolder	=	config.getProperty("patentAnnuityUtilityFolder");
			iiopPort	= config.getProperty("iiopPort");
			iiopHost	= config.getProperty("iiopHost");
			databaseHost	= config.getProperty("databaseHost");
			databasePort	= config.getProperty("databasePort");
			databaseTool	= config.getProperty("databaseTool");
			databaseName	=	config.getProperty("databaseName");
			userName		=	config.getProperty("userName");
			password		=	config.getProperty("password");
			madridXMLFolder	=	config.getProperty("madridXMLFolder");
			madridImageFolder		=	config.getProperty("madridImageFolder");
			fileType		=	config.getProperty("fileType");
			
			logger.debug("IIOP PORT:::"+iiopPort);
			logger.debug("iiopHost:::"+iiopHost);
			logger.debug("databaseHost:::"+databaseHost);
			logger.debug("databasePort:::"+databasePort);
			logger.debug("databaseTool:::"+databaseTool);
			logger.debug("databaseName:::"+databaseName);
			logger.debug("userName:::"+userName);
			logger.debug("madridXMLFolder:::"+madridXMLFolder);
			logger.debug("madridImageFolder:::"+madridImageFolder);
			logger.debug("fileType:::"+fileType);

			
			return this;
				
	}
	
	
	public void checkValuesOfConfigFile(){
		

		if(iiopPort==null||iiopPort.isEmpty()){
			
			logger.error("Please specify value of  iiopPort in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  iiopPort in MadridImageUploadConfiguration.properties");
		}
		
		if(iiopHost==null||iiopHost.isEmpty()){
			
			logger.error("Please specify value of  iiopHost in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  iiopHost in MadridImageUploadConfiguration.properties");
		}
		
		if(databaseHost==null||databaseHost.isEmpty()){
			
			
			logger.error("Please specify value of  databaseHost in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  databaseHost in MadridImageUploadConfiguration.properties");
		}
		
		if(databasePort==null||databasePort.isEmpty()){
			
			
			logger.error("Please specify value of  databasePort in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  databasePort in MadridImageUploadConfiguration.properties");
		}
		
		if(databaseTool==null||databaseTool.isEmpty()){
			
			logger.error("Please specify value of  databaseTool in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  databaseTool in MadridImageUploadConfiguration.properties");
			
		}
		else if(!(databaseTool.equalsIgnoreCase("sql")||databaseTool.equalsIgnoreCase("oracle"))){
			
			logger.error("Please specify correct database tool in MadridImageUploadConfiguration.properties ie. for oracle database::<oracle> and for sql database::<sql>");
			throw new MissingConfigurationException("Please specify correct database tool in MadridImageUploadConfiguration.properties ie. for oracle database::<oracle> and for sql database::<sql>");
		}
		
		if(databaseName==null||databaseName.isEmpty()){
			
			
			logger.error("Please specify value of  databaseName in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  databaseName in MadridImageUploadConfiguration.properties");
		}
		
		if(userName==null||userName.isEmpty()){
			
			logger.error("Please specify value of  userName in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  userName in MadridImageUploadConfiguration.properties");
		}
		
		if(password==null||password.isEmpty()){
			
			
			logger.error("Please specify value of  password in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  password in MadridImageUploadConfiguration.properties");
		}
		
		if(madridXMLFolder==null||madridXMLFolder.isEmpty()){
			
			
			logger.error("Please specify value of  madridXMLFolder in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  madridXMLFolder in MadridImageUploadConfiguration.properties");
		}
		
		else{
			File file	=	new File(madridXMLFolder);
			
			
			if(!file.isDirectory()){
							
				
				logger.error("Value of  madridXMLFolder is not directory in MadridImageUploadConfiguration.properties");
				throw new IllegalPathStateException("Value of  madridXMLFolder is not directory in MadridImageUploadConfiguration.properties");
				
			}
			
		}

	if(madridImageFolder==null||madridImageFolder.isEmpty()){
			
			
			logger.error("Please specify value of  madridImageFolder in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  madridImageFolder in MadridImageUploadConfiguration.properties");
		}
		
		else{
			File file	=	new File(madridImageFolder);
			
			
			if(!file.isDirectory()){
							
				
				logger.error("Value of  madridImageFolder is not directory in MadridImageUploadConfiguration.properties");
				throw new IllegalPathStateException("Value of  madridImageFolder is not directory in MadridImageUploadConfiguration.properties");
				
			}
			
		}
		
		if(fileType==null||fileType.isEmpty()){
			
			
			logger.error("Please specify value of  fileType in MadridImageUploadConfiguration.properties");
			throw new MissingConfigurationException("Please specify value of  fileType in MadridImageUploadConfiguration.properties");
		}

		
	}
	
	
	
	public void checkDateFormat(){
		
		
		
	}
	
	public static void main(String[] args) {
		
		UtilityConfiguration uc = new UtilityConfiguration();
	
			
	//String		log4jConfigFile	=	System.getProperty("user.dir")+File.separator+"log4j.properties";
		
		String		log4jConfigFile	=	"D://PatentAnnuityUtility//log4j.properties";
			
			PropertyConfigurator.configure(log4jConfigFile);
			logger.info("log4jConfigFile:::"+log4jConfigFile);	
	
		uc.loadConfiguration();
		uc.checkValuesOfConfigFile();
		
		
		//ths 
	}
	
}
