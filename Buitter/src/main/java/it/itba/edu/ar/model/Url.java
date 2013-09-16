package it.itba.edu.ar.model;

public class Url {

	private int urlid;
	private String url;
	private String buiturl;
	private int buitid;
	
	public Url(int urlid, String url, String buiturl, int buitid){
		if(urlid < 1 || url == null || url.length() > 140 || buiturl == null || 
				buiturl.length() > 140 || buitid < 1)
			throw new IllegalArgumentException();
		
		this.urlid = urlid;
		this.url = url;
		this.buiturl = buiturl;
		this.buitid = buitid;
	}
	
	public Url(String url, String buiturl, int buitid){
		if(url == null || url.length() > 140 || buiturl == null || 
				buiturl.length() > 140 || buitid < 1)
			throw new IllegalArgumentException();
		
		this.url = url;
		this.buiturl = buiturl;
		this.buitid = buitid;
	}
	
	
	public int getUrlid() {
		return urlid;
	}
	public void setUrlid(int urlid) {
		if(urlid < 1 )
			throw new IllegalArgumentException();
		
		this.urlid = urlid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		if(url == null || url.length() > 140 )
			throw new IllegalArgumentException();
		
		this.url = url;
	}
	public String getBuiturl() {
		return buiturl;
	}
	public void setBuiturl(String buiturl) {
		if( buiturl == null || buiturl.length() > 140)
			throw new IllegalArgumentException();
		this.buiturl = buiturl;
	}
	public int getBuitid() {
		return buitid;
	}
	public void setBuitid(int buitid) {
		if(buitid < 1)
			throw new IllegalArgumentException();
		this.buitid = buitid;
	}
	
	
	
}