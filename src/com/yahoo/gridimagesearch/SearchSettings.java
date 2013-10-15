package com.yahoo.gridimagesearch;
import java.io.Serializable;


public class SearchSettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5290397829781929495L;
	private String imageSize;
	private String colorFilter;
	private String imageType;
	private String siteFilter;
	
	public SearchSettings() {
		imageSize = null;
		colorFilter = null;
		imageType = null;
		siteFilter = null;
	}
	
	public String getImageSize() {
		return imageSize;
	}
	
	public String getColorFilter() {
		return colorFilter;
	}
	
	public String getImageType() {
		return imageType;
	}
	
	public String getSiteFilter() {
		return siteFilter;
	}
	
	public void setImageSize(String val) {
		imageSize = val;
	}
	
	public void setColorFilter(String val) {
		colorFilter = val;
	}
	
	public void setImageType(String val) {
		imageType = val;
	}
	
	public void setSiteFilter(String val) {
		siteFilter = val;
	}
}
