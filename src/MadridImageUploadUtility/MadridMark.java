package MadridImageUploadUtility;



import org.apache.log4j.Logger;
import org.ipas.commons.CActionTypeId;
import org.ipas.commons.CCountViennaClassList;
import org.ipas.commons.CCriteriaFileId;
import org.ipas.commons.CCriteriaMark;
import org.ipas.commons.CCriteriaProcessByAction;
import org.ipas.commons.CCriteriaStatus;
import org.ipas.commons.CFileId;
import org.ipas.commons.CFileSummary;
import org.ipas.commons.CFileSummaryList;
import org.ipas.commons.CLogo;
import org.ipas.commons.CMark;
import org.ipas.commons.CMarkSimilarityList;
import org.ipas.commons.CProcessId;
import org.ipas.commons.CSound;
import org.ipas.commons.CommonsProxyFactory;
import org.ipas.commons.IMark;
import org.ipas.proxy.IpasException;
import org.ipas.proxy.IpasInteger;

/**
 * This class is having code for uploading Madrid image
 * @author Chandani Jaiswal
 *
 */
public class MadridMark {
	
	

	
	static	IMark iMark	=	null;
	private String file_seq	;
	private String file_typ	;
	private String file_ser	;
	private String file_nbr	;
	private String sign_wcode	;
	private CMark mark	;
	private	String intregn;
	private	String imagePath;
	public static Logger logger	= Logger.getLogger(MadridMark.class);
	
	public String getFile_seq() {
		return file_seq;
	}


	public void setFile_seq(String file_seq) {
		this.file_seq = file_seq;
	}


	public String getFile_typ() {
		return file_typ;
	}


	public void setFile_typ(String file_typ) {
		this.file_typ = file_typ;
	}


	public String getFile_ser() {
		return file_ser;
	}


	public void setFile_ser(String file_ser) {
		this.file_ser = file_ser;
	}


	public String getFile_nbr() {
		return file_nbr;
	}


	public void setFile_nbr(String file_nbr) {
		this.file_nbr = file_nbr;
	}


	public String getSign_wcode() {
		return sign_wcode;
	}


	public void setSign_wcode(String sign_wcode) {
		this.sign_wcode = sign_wcode;
	}


	public CMark getMark() {
		return mark;
	}


	public void setMark(CMark mark) {
		this.mark = mark;
	}


	
	public String getIntregn() {
		return intregn;
	}


	public void setIntregn(String intregn) {
		this.intregn = intregn;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	
	
	
	public boolean isCurrentIntregnEligibleForLogoProcessing() {
		
		boolean	process=	false;
		
		if((sign_wcode==null ||sign_wcode.isEmpty())   ){
			
		//	logger.debug("This mark sign type does not contain logo hence processing next ");
			process	=	true;
			
		}
		
		if((!sign_wcode.equals("F")) && (!sign_wcode.equals("M"))   ){
			
	//		logger.debug("This mark sign type does not contain logo hence processing next file");
			process	=	true;
			
		}
		
	//	CMark  mark	=	getCurrentMark(markDetailsMap);
		if(mark.getMarkContainsLogo()){
		//	logger.debug("This mark logo data is not missing hence processing next file");
			
		
			process	=	true;
			
		}
		
		
				return process;
					
				
		
	}


	
		// get CMark instance on the basis of criteria set
	
	public  CMark getCurrentMark(CommonsProxyFactory commonsProxyFactory){
			
		 CCriteriaMark cCriteriaMark =	null;
		 CCriteriaFileId fileId =	null;
		 CCriteriaStatus cCriteriaStatus	=	null;
		 CCriteriaProcessByAction criteriaProcessByAction = null;
		 CMark mark = null; 
		 iMark = commonsProxyFactory.getIMark();
		 cCriteriaMark = new CCriteriaMark();
			fileId = new CCriteriaFileId();
			fileId.setFileSeq(getFile_seq());
			fileId.setFileType(getFile_typ());
			fileId.setFileSeries(new IpasInteger(getFile_ser()));
			fileId.setFileNbrFrom(new IpasInteger(getFile_nbr()));
			fileId.setFileNbrTo(new IpasInteger(getFile_nbr()));
		
			cCriteriaMark.setCriteriaFileId(fileId);
		
			// Setting status criteria
					 cCriteriaStatus = new CCriteriaStatus();
			//	cCriteriaStatus.setStatusCode("ackn");
					 cCriteriaMark.setCriteriaStatus(cCriteriaStatus);
			//Setting action criteria
					 criteriaProcessByAction = new CCriteriaProcessByAction();
					 criteriaProcessByAction.setActionTypeId(new CActionTypeId("ack_app"));
					
					try {
						if(iMark==null){
							
							logger.debug("Please correct the GLASSFISH_HOME path in the run.bat file of the utility");
							logger.debug("Program exit!!!");
							System.exit(0);
						}
						
						CFileSummaryList fileSummaryList = iMark.mGetList(cCriteriaMark);

						//logger.debug("Total mark Count:"+fileSummaryList.size());
						if(fileSummaryList != null && !fileSummaryList.isEmpty()){
							for (CFileSummary fileSummary : fileSummaryList) {
								
								mark	=	iMark.mRead(fileSummary.getFileId(), true, false);
								
							}
						}
												
					} catch (IpasException e) {
						e.printStackTrace();
						logger.error("iMark.mRead() error:" + e.getMessage()+"\r\n");
					}
					
					
		return mark;
			
	}
	
	//update mark logo 
	public  void updateMarkLogo(String logoType,byte[] logoData){
		
					mark.getSignData().setLogo(new CLogo());
					mark.getSignData().getLogo().setLogoType(logoType);
					mark.getSignData().getLogo().setLogoData(logoData);
					try {
							iMark.mUpdate(mark, null);
					 } catch (IpasException e) {
						// TODO Auto-generated catch block
						
						logger.error("Exception occured while updating mark logo::"+mark.getMarkFormatted()+ e.getMessage()+"\r\n");
					//	e.printStackTrace();
					}
			//		logger.debug("Mark logo updated Sucessfully.....");
		
	}
				
				




	

	

	



	
}
