			++ READ ME ++
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Functionality of the utility:


It provides an interim solution to 2 issues currently in the IPAS MADRID Module.

Issue 1: IPAS has an issue where the Madrid image logos get deleted when any approval action is executed on the Madrid transaction.
The issue of missing Madrid logos raised through development JIRA IPAS-992 has been fixed and will be available with the future release of IPAS3.2.0.

Issue 2: IPAS Madrid Module does not read the image type or color information in the Madrid xml file. It looks for the available images for the intregn in the all folders 'gs', 'color' and 'bw'. It gives first priority to images in 'gs' folder, then in 'color' folder and last in 'bw' folder.
Development JIRA IPAS-1349 has already been raised and as per the current status, the fixed version should probably be available in the IPAS release 3.4.0 
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Regarding Resolution :
Resolution for the 2 issues as provided by the utility is summarized below.

For the 1st Issue: it reloads the IPAS database with the deleted logos that are made available to this utility using configuration settings.
For the 2nd Issue: it reads the Madrid xml files to retrieve the color information in it and uploads the deleted logos accordingly.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Scope of the utility:

The utility is generic for any office and helpful for those offices who are working on the environment prior to IPAS 3.2.0 who would want to upload the logo of trademark which got missing after processing User Document on Birth and Prolong transaction.
The current utility is packaged with IPAS 2.7.0c version of IpasApi.jar file.
The IpasApi.jar (placed in the lib folder of the utility) can be replaced with the IpasApi.jar file of different versions of IPAS and can be used with those as well.
The utility has been tested to run with both Oracle and MSSQL databases and the same can be configured using the MadridImageUploadConfiguration.properties file in this utility.
This ReadMe.txt file is provided with the utility and explains the steps required to configure and run the utility.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Utility Processing steps:

This utility reads the Madrid xml files and retrieves all the INTREGN numbers. 
It then looks for the images in the configured image folder and uploads them to the IPAS database in case they are missing.
It also checks the IMAGE tag in the Madrid xml file and does the upload accordingly depending on the color specified.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Prerequisites:

Jdk(Java version 1.6.*) should be installed in the running system.
Set JAVA_HOME in environment variables to point to the jdk directory.



++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
MadridXMLImageUploadUtility_Final folder should contains following folders/files:

1)MadridImageUploadConfiguration.properties		(file)
2)lib      			(folder)
3)MadridImageUploaderUtility.jar	(file)
4)ReadMe.txt			(file)
5)run.bat			(file)
6)log				(folder: will be generated and contain logs for the utility run)
7)log4j.properties 	(file)
8)Utility program to resolve issues related to Madrid Transactions.docx  (file describing the utility)




'MadridImageUploadConfiguration.properties' files contains the details of database, Madrid xml & images folder location of the IPAS environment. The property values need to be modified accordingly before running this program.

'lib' folder contains all jar files that is required for this program.

'MadridImageUploaderUtility.jar' is main executable jar to upload missing logos.

'ReadMe.txt' file contains the instructions to run this program.

'run.bat' file contains script to execute this batch utility program.

'log' folder will get generated on running the utility and will contain the details of the run.

'log4j.properties' contains configuration details about the logging (Log generation) of the utility program

'Utility program to resolve issues related to Madrid Transactions.docx'  is a document file that describes the purpose of the utility.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
HOW TO RUN PROGRAM?
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

1) Open 'MadridImageUploadConfiguration.properties' file and change properties of all the property attributes and save it.
2) Ensure that the GLASSFISH HOME & JAVA HOME is set correctly in run.bat file.
3) Double click on 'run.bat' to start the utility program.
4) After successful executing of this script, check the log file generated for the run. It will contain the details of processing for each of the XML files.












