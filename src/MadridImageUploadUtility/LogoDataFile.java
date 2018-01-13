package MadridImageUploadUtility;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * This class is having code for uploading Madrid image
 * @author Chandani Jaiswal
 *
 */

public class LogoDataFile {
	
	
	 String logoType	=	null;
	 byte[] bytearrayImageData =	null;
	 String imagePath	= "";
	 public static Logger logger	= Logger.getLogger(LogoDataFile.class);

	
	
	public String getImagePath() {
		return imagePath;
	}




	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}




	public  byte[] getLogoData()  {
		
		File file = new File(imagePath);
		InputStream is;
		try {
			is = new FileInputStream(file);
	
		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		 bytearrayImageData = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytearrayImageData.length
				&& (numRead = is.read(bytearrayImageData, offset, bytearrayImageData.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytearrayImageData.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Close the input stream and return bytes

		return bytearrayImageData;
	}
	
	
	
	public String searchImageFille( Entry<String, String> entry, File file) {
		String filename="";
		String path="";
		File[] list = file.listFiles();
		
		String name	=	entry.getKey();
		
		if(name.startsWith("0")){
			
			name=name.substring(1);
			
		}
		for( int i=0;i<list.length;i++){
			
			File fil	=	list[i];
			 filename	=	fil.getName().toUpperCase();
			if(fil.isDirectory() && (filename.startsWith("N") || filename.startsWith("X")) && filename.length()>=7){
				
				
				File[] filesColorList = fil.listFiles();
				
				for( int j=0;j<filesColorList.length;  j++){
					
					File filesCOLOR	=	filesColorList[j];
					String		filesCOLORName	=	filesCOLOR.getName();
					
					
					if(entry.getValue().equalsIgnoreCase("Y") && filesCOLOR.isDirectory()  ){
						
						
						if( ("color").equalsIgnoreCase(filesCOLORName) && filesCOLOR.isDirectory()){
						
						File[] listOfIntregnImage	=		filesCOLOR.listFiles();
							for( int  k=0; k<listOfIntregnImage.length;  k++){
								File imageFile=listOfIntregnImage[k];
								String imageName	=	imageFile.getName().toUpperCase();
								if(imageName.contains(name)){
									
									path	=	imageFile.getAbsolutePath();
									
								}
							}
					}
				}	
					
					else	if( filesCOLOR.isDirectory() &&	(("bw").equalsIgnoreCase(filesCOLORName) || ("color").equalsIgnoreCase(filesCOLORName) || ("gs").equalsIgnoreCase(filesCOLORName) )){
					
						File[] listOfIntregnImage	=		filesCOLOR.listFiles();
						
						
						
						for( int  k=0; k<listOfIntregnImage.length;  k++){
							
							
							
							File imageFile=listOfIntregnImage[k];
							String imageName	=	imageFile.getName().toUpperCase();
							if(j==0 && imageName.endsWith("TIF") ){
							if(imageName.contains(name)){
								
								path	=	imageFile.getAbsolutePath();
							
							}
							}
							else if(((j==1||j==2)) && imageName.endsWith("JPG"))
							{
								if(imageName.contains(name)){
									
									path	=	imageFile.getAbsolutePath();
								
								}
								
								
							}
						
						}
							
						}
							
			
				
				
			
				
							
						}
				
			}
			
			
		}
		
		
		return path;
		
	}
	
	


	
	public void processImagePath(Entry<String, String> entry,String madridImageFolder){
		
		String imageType = "";
		String [] imageTypearray = null;
		
		if(!madridImageFolder.endsWith("images")){
			
			madridImageFolder	=	madridImageFolder+"\\images";
			
		}
		
			
			
	
		imagePath  = searchImageFille(entry, new File(madridImageFolder));
		
	
		
		if(!("").equals(imagePath)&&imagePath!=null){
		//	logger.debug("Location Of Image of Intregn ::"+entry.getKey() + "is::"+imagePath);
		 imageTypearray	=	imagePath.split("\\.");
		 	if(imageTypearray.length>=1){
			imageType		=	imageTypearray[1];
		//	logger.debug("ImageType is :: "+imageType);
			setLogoType(imageType.trim().toUpperCase());
		 	}
				}
	
	}
	



	private void setLogoType(String logoType){
		
		this.logoType	=	logoType;
		
		
	}
	
	
	public String getLogoType(){
		
		return logoType;
		
		
	}
	

	
	

	
	





}