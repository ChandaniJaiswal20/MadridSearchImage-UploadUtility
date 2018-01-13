package MadridImageUploadUtility;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;




public class XMLParser {
 
	
	public static Logger logger	= Logger.getLogger(XMLParser.class);
	
	private	HashSet<String>  intregnSet = new HashSet<String>();
	
	private 	HashMap<String,String>  intregnMap = new HashMap<String, String>();
	
	
	public void parseIntregnFromCurrentXML(File filePath) throws ParserConfigurationException, SAXException, IOException {
	
	//	HashSet<String> individualFileIntregnSet = new HashSet<String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        	
        Document doc = db.parse(filePath);
        
        
        Element docEle = doc.getDocumentElement();
        NodeList	nodeList	=  docEle.getChildNodes();
            
        NodeList imageNodeList = docEle.getElementsByTagName("IMAGE");

        boolean isIntregnAttributeFound	=	false;
        int 	intregnAttributeNotFoundCounter	=	0;
        
        for(int i=0; i< imageNodeList.getLength();i++){  
        		
        		isIntregnAttributeFound	=	false;
        		Node imageNode = imageNodeList.item(i);
              
              
              if (imageNode.getNodeType() == Node.ELEMENT_NODE) {

                 

                  Element imageNodeElement = (Element) imageNode;
               //   Element currentNodeElement	= imageNodeElement;
                  String name	= null;
                  String colour	= null;
                  
                  
                  Node parentNode	= imageNodeElement.getParentNode();
                  Element parentNodeElement = (Element) parentNode;
                  
                  	if(parentNodeElement.hasAttribute("INTREGN")){
                	  
                	  
                 	   name	= 		parentNodeElement.getAttribute("INTREGN");
                 	   isIntregnAttributeFound	=	true;
                	  
                  }
                  	
          
              
             /*     
                  while(!currentNodeElement.getParentNode().getNodeName().equalsIgnoreCase("ENOTIF")){
                	  
                	  Node parentNode	=	currentNodeElement.getParentNode();
                	  
                	  Element parentNodeElement = (Element) parentNode;
                	  
                	
		                 if(  parentNodeElement.hasAttribute("INTREGN") ){
		                	  		              
		                	   name	= 		parentNodeElement.getAttribute("INTREGN");
		                	   isIntregnAttributeFound	=	true;
		                	   break;
		                  }
		                 

                  
		                 currentNodeElement=parentNodeElement;
                  }
*/
                  
                if( imageNodeElement.hasAttributes()){
                	 
                	if(!isIntregnAttributeFound){
                		name	= 	imageNodeElement.getAttribute("NAME");
                		intregnAttributeNotFoundCounter++;
                	 }
                	 
                		colour	= 	imageNodeElement.getAttribute("COLOUR");
               
                	 
                		intregnMap.put(name, colour);
          
                 }
      
           	 
              }	 
         
        }         
        
      
        logger.debug("intregnAttributeNotFoundCounter:::"+intregnAttributeNotFoundCounter  );
  
      //  System.out.println("in file::"+intregnMap);
      //System.out.println("=======================================");
        //previous code
        
        /**
        
        
	      for(int i=0; i< nodeList.getLength();i++){
	    	  
	    	  
	    	  Node node	=	nodeList.item(i);
	    	  
	    	  if (node.getNodeType() == Node.ELEMENT_NODE) {
	    	  
	    	
	    		    		  
	    		  Element e = (Element) node;
	    		    		  
	    		  
	    
	    		
	    		
	    				
	    		
	    		  		if(e.hasAttribute("INTREGN")){
	    		  			logger.debug("Node::" +node.getNodeName() +"; Intregn::"+e.getAttribute("INTREGN"));
	    		  		logger.debug("Node Name::" +node.getNodeName() +"; Intregn::"+e.getAttribute("INTREGN"));
	    		  			
	    		  		individualFileIntregnSet.add(e.getAttribute("INTREGN"));
	    		  		  intregnSet.add(e.getAttribute("INTREGN"));
	    		  	}
	    		
	    	
	    	  }
	    	  
	      }
      
	      logger.debug("intregn obtained from:::"+filePath+" are :::" + individualFileIntregnSet+" length:: "+ individualFileIntregnSet.size());
	      
	      **/
	//	return intregnSet;


	}
	

  /* 
    public  HashSet<String> getIntregnSet() {
		return intregnSet;
	}
	*/

    public  HashMap<String,String> getIntregnMap() {
		return intregnMap;
	}


    public void  setIntregnMap(HashMap<String,String> intregnMap) {
  		this.intregnMap=intregnMap;
  	}
    
   /* 
	public  void setIntregnSet(HashSet<String> intregnSet) {
		this.intregnSet = intregnSet;
	}
	*/
	
	
	public void parseXMLForImage(){
		
		
		
	}

/**

	public static void main(String[] args) {
    	
    	    	
    	XMLParser parser	=	new XMLParser();
    	
    	    	
    	File fileDirectory	=	new File("D:\\IPASS_BUILD\\madridXml");
    	
    
    	
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
    	
    	
    	
    	
    	for(int i=0; i<files.length; i++){
    		
    		
    		File individualFile	=	new File("D:\\IPASS_BUILD\\madridXml\\"+files[i].getName());

        	try {
        		logger.debug("===========================================");
        		logger.debug("Going to parse xml::"+ files[i].getName() );
    			parser.parseIntregnFromCurrentXML(individualFile);
    			logger.debug("===========================================");
    			//System.out.println("Final Intregen Set::"+ parser.intregnSet + "; length::"+parser.intregnSet.size());
    		//	System.out.println(intregnMap);
    		} catch (ParserConfigurationException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SAXException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
  	
    		
    
    	}
    	
    	
    	System.out.println(intregnMap);
    	
    }

**/
        
}