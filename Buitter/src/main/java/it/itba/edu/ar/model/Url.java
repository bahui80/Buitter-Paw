package it.itba.edu.ar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Url {
	@Id @GeneratedValue(strategy=javax.persistence.GenerationType.AUTO)private int urlid;
	@Column(length=140)	private String url;
	@Column(length=140)	private String buiturl;
	
	public Url(){
	}
	
	public Url(int urlid, String url, String buiturl, int buitid){
		if(urlid < 1 || url == null || url.length() > 140 || buiturl == null || 
				buiturl.length() > 140)
			throw new IllegalArgumentException();
		
		this.urlid = urlid;
		this.url = url;
		this.buiturl = buiturl;
	}
	
	public Url(String url, String buiturl, int buitid){
		if(url == null || url.length() > 140 || buiturl == null || 
				buiturl.length() > 140)
			throw new IllegalArgumentException();
		
		this.url = url;
		this.buiturl = buiturl;
	}
	
	public Url(String url){
		if(url == null || url.length() > 140 )
			throw new IllegalArgumentException();
		
		this.url = url;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Url other = (Url) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	
	
}
