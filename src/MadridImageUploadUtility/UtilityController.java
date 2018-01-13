package MadridImageUploadUtility;

import java.util.ArrayList;

/**
 * This class is used to instantiate utility that  upload the logo of Trademark
 * which was deleted after processing of the User Document pertaining to BIRTH and PROLONG 
 * 
 * The utility provides an interim solution to the following two issues encountered in the Madrid module
 * which is integrated with IPAS prior to version 3.2.0.
 * 
 * Issue1: Logos of trademarks received through Madrid module are deleted after the user document
 * affecting that particular file is processed through Madrid module.
 * Issue 2: The Madrid Module does not read the image type or color information
 * in the Madrid XML Files. Instead it looks for the available images for the given INTREGN in the
 * following folders: 'gs', 'color' and 'bw' in that order.
 * 
 * 
 *   @author Chandani Jaiswal

 * 
 **/

import java.util.HashMap;

public class UtilityController {
	
	
	
	public static void main(String[] args) {
		
				
				
				UtilityManager	um =	UtilityManager.getInstance();
				
				um.processNotificationFiles();
				um.stopUtility();
	
			}
		
	}
	


