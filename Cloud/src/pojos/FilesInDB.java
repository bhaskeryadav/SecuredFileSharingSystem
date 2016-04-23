package pojos;

import java.util.Date;

public class FilesInDB {

	String fileName;
	long size;
	Date uploadedOn;
	
	
	
	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public long getSize() {
		return size;
	}



	public void setSize(long size) {
		this.size = size;
	}



	public Date getUploadedOn() {
		return uploadedOn;
	}



	public void setUploadedOn(Date uploadedOn) {
		this.uploadedOn = uploadedOn;
	}



	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return fileName.hashCode();
	}
	
	
	
}
