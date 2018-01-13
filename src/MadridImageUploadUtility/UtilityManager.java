package MadridImageUploadUtility;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.ipas.commons.CommonsProxyFactory;
import org.xml.sax.SAXException;

public class UtilityManager {

	 static{
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
	        System.setProperty("current.date.time", dateFormat.format(new Date()));
	    }
	
	 //only one instance of UtilityManager
	 private static UtilityManager uniqueInstance =	null;
	 
	
	  private UtilityConfiguration configuration 	=	null;
	  private 	final	DBdataFetcher	dataFetcher	; // composition (part of ) should be initialise in constructor
	
	
	  private	CommonsProxyFactory commonsProxyFactory;
  	  private 	static final	String  LOG4JCONFIGFILE	= "log4j.properties";
	  private	static Connection conn	=null;
	  private 	static Logger logger	=	Logger.getLogger(UtilityManager.class);
	  private 	 List<String> imageNotPresentList		=	new ArrayList<String>();
	  private 	 List<String> xmlParsedList		=	new ArrayList<String>();
	  private 	 List<String> xmlNotParsedList		=	new ArrayList<String>();
	  private 	 List<String> intregnNotPresentinDBList		=	new ArrayList<String>();
	  private	XMLParser xmlParser	=	new XMLParser();
	  private List<String> markLogoUpdatedList		=	new ArrayList<String>();
	  private List<String> logoNotEligibleForProcessingList	=	new ArrayList<String>();
	  
	private UtilityManager()
	{

		PropertyConfigurator.configure(LOG4JCONFIGFILE);
		logger.info("Initializing Utility...........................................!!!!!");
		logger.debug("################################################");
		logger.debug("=================================================");
		
		logger.debug("LOG4JCONFIGFILE:::"+LOG4JCONFIGFILE);
		configuration 	=	new UtilityConfiguration();	//aggregation (has a)
		configuration.loadConfiguration();
		configuration.checkValuesOfConfigFile();
		commonsProxyFactory = new CommonsProxyFactory(configuration.iiopHost +":"+configuration.iiopPort);
		logger.debug("Obtaining Database Connection....");
		dataFetcher	= new DBdataFetcher(configuration.databaseHost,configuration.databasePort,
					  configuration.databaseTool,configuration.databaseName,configuration.userName,configuration.password);
		
		logger.info("Initializing Completed...............................................!!!!!!");		
	
		logger.debug("################################################");
	}
	
//	 private void init() {
//		
//		
//	}
	
	
	
		 //returning only unique instance
	
		public static  UtilityManager getInstance(){
			
					if(uniqueInstance==null)
					{	
						uniqueInstance	=	 new UtilityManager();
					}
					
					
			return uniqueInstance;
					
		}
	
		public UtilityConfiguration getConfiguration(){
			return configuration;
						
		}
	


	
	public void configureLogger(){
	
		
		PropertyConfigurator.configure(LOG4JCONFIGFILE);
			
	}
		


	
		public void processNotificationFiles() {
			
			
			
			
			File files[]	=	getListOfNotificationFilesToBeParsed();
		
		
			parseIntregenfromNotificationFiles(files);
			
	    		
			processIntregn(xmlParser.getIntregnMap());
		
	    	
	    
			
		}
	
	
	private void parseIntregenfromNotificationFiles(File[] files) {
    	
	
    	for(int i=0; i<files.length; i++){
    		
    		
    		File individualFile	=	new File(configuration.madridXMLFolder+"\\"+files[i].getName());
    		
        	try {
        	//	logger.debug("===========================================");
        	//	logger.debug("Going to parse xml::"+ files[i].getName() );
        		xmlParser.parseIntregnFromCurrentXML(individualFile);
        		 xmlParsedList.add(files[i].getName());
        	//	logger.debug("===========================================");
        	} catch (SAXException e1) {
    			  xmlNotParsedList.add(files[i].getName());
    			logger.debug("Exception occured while parsing xml file::"+files[i].getName(),e1);
    			
    		} catch (IOException e1) {
    			xmlNotParsedList.add(files[i].getName());
    			logger.debug("Exception occured loacting  xml file on path::"+files[i].getName(),e1);
    		
    		} catch (ParserConfigurationException e1) {
    			xmlNotParsedList.add(files[i].getName());
    			logger.debug("Exception occured while builing document by DocumentBuilderFactory::"+files[i].getName(),e1);
    		
    		}
        	
    		
    		
    
    	}
    	
    	logger.debug("###############################");
		logger.debug("Final Intregen Map to be processed::"+ xmlParser.getIntregnMap() + "; length::"+xmlParser.getIntregnMap().size());
		logger.debug("###############################");
			
		}

	private File [] getListOfNotificationFilesToBeParsed() {
		
		
		File fileDirectory	=	new File(configuration.madridXMLFolder);
    	
    	
    	
    	File files[] = fileDirectory.listFiles(new FilenameFilter() {
    		
    		@Override
    		public boolean accept(File dir,String name){
    			
    			String lowercase	=	name.toLowerCase();
    			
    			if(lowercase.endsWith(".xml")){
    				
    				return true;
    				
    				
    			}
    			
    			else{
    				
    				return false;
    				
    			}
    			
    			
    		}
    		
    		
    	}
    		);
    	
    	return files;
    	
	}

	public  void processIntregn(HashMap<String,String> intregnMap)  {
		
		
	
		
	//	Iterator<String> intregnIterator	=	intregnSet.iterator();
		
//		Iterator<Map.Entry<String, String>> intregnIterator	=	intregnMap.entrySet().iterator();
		
		String intregn	= null;
		
		
	Iterator<Map.Entry<String, String>> intregnIterator = intregnMap.entrySet().iterator();
		while (intregnIterator.hasNext()) {
		  Map.Entry<String, String> entry = intregnIterator.next();
		  
	
			
			MadridMark madridMark	=	new MadridMark();
			LogoDataFile	logoObj=	new LogoDataFile();
			
						
			intregn	=entry.getKey() ;
			
			
		//	logger.debug("=================================");
		//	logger.debug("Going to process Intregn::"+intregn);
			
			madridMark.setIntregn(intregn);
			
			
			if(!processCurrentIntregnInfoFromIP_MARK(madridMark)){
				intregnNotPresentinDBList.add(madridMark.getIntregn());
			//	logger.debug("This intregn::"+madridMark.getIntregn()+"::is not present in database hence processing next intregn");
				continue;
			}
			
			
			
			
			processCMarkDataOfCurrentIntregn(madridMark);
			
	
			
			 if(madridMark.isCurrentIntregnEligibleForLogoProcessing()){
				 
				 
				 logoNotEligibleForProcessingList.add(madridMark.getIntregn());
				 
				 continue;
			 }
			

	
			
			 processLogoPathOfCurrentIntregn(entry,logoObj);
			 
			
			
		//	logger.debug("-----------------------------------------" );
			
	
	
			processMarkLogoOfCurrentIntregn(madridMark,logoObj);
		
			
	
			
			
		
			
	}
		logger.debug("##########################" );
		//logoNotEligibleForProcessingList
		
		
		if(markLogoUpdatedList.size()>0){
			
			logger.info("Following marks logo are updated successfully successfully::");
			logger.info(markLogoUpdatedList);
			
		}
		
		if(logoNotEligibleForProcessingList.size()>0){
			
			logger.info("Following marks are not eligible for parsing as either their logo is not missing or  sign_code is not of logo type::");
			logger.info(logoNotEligibleForProcessingList);
			
		}
		
		if(xmlParsedList.size()>0){
			logger.debug("##########################" );
			logger.info("Following notification files are  parsed successfully::");
			logger.info(xmlParsedList);
			
			}
		
		if(xmlNotParsedList.size()>0){
			logger.debug("##########################" );
			logger.info("Following notification files are not parsed due to exception while parsing::");
			logger.info(xmlNotParsedList);
			
			}
		
		if(intregnNotPresentinDBList.size()>0){
			logger.debug("##########################" );
			logger.debug("Intregn  are not present in database for following FILE::");
			logger.debug(intregnNotPresentinDBList);
			}
	
		if(imageNotPresentList.size()>0){
			logger.debug("##########################" );
			logger.debug("Images are not present for following FILE::");
			logger.debug(imageNotFoundList());
			
			}
		
	
		logger.debug("##########################" );
		logger.debug("Processing Completed!!!");
		logger.debug("Please check processed details in log file generated in MadridImageUploadUtility folder");
		//out.close();
		
	}
	
	




	private void processMarkLogoOfCurrentIntregn(MadridMark madridMark,
			LogoDataFile logoObj) {


		if(logoObj.getImagePath()!=null	&&	!("").equals(logoObj.getImagePath())){
			
			 madridMark.updateMarkLogo(logoObj.getLogoType(),logoObj.getLogoData());
			markLogoUpdatedList.add(madridMark.getIntregn());
		}
		
		else
		{		
				imageNotPresentList.add(madridMark.getIntregn());
			//	logger.debug("image with file::"+madridMark.getIntregn()+" not found in image directory!!!");
			
		}
		
		
	}

	private void processLogoPathOfCurrentIntregn(Entry<String, String>  entry, LogoDataFile logoObj) {
		
		
	
		
		logoObj.processImagePath(entry,configuration.madridImageFolder); 
		
	//	logoObj.getLogoData(madridMark2.getIntregn()),logoObj.getLogoType()
	}

	private void processCMarkDataOfCurrentIntregn(
			MadridMark madridMark2) {
		
		
		madridMark2.setMark(madridMark2.getCurrentMark(commonsProxyFactory));
		
	}

	private boolean processCurrentIntregnInfoFromIP_MARK(MadridMark madridMark2) {
		
		return	dataFetcher.getMarkDetails(madridMark2);
		
	
		
	}

	
	//image not found
	
	public  List<String> imageNotFoundList(){
		
		return	imageNotPresentList;
		
	}
	
	
	
	public void stopUtility(){
		
		if(dataFetcher.getConnection()!=null){
			logger.debug("Closing database connection..........!!");
			dataFetcher.closeConnection();
			logger.debug("Database connection closed..........!!");
			
		}
	
		logger.info("Processing Completed.......................!!!");
		logger.info("=================================================");
		logger.info("Please check processed details in log file generated in log folder");
		logger.info("********************************************************");
	}
	
}
