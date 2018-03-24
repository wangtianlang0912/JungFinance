package com.aspsine.irecyclerview.banner;

import java.io.Serializable;


public class BannerBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public BannerBean() {
		
	}

	String title;
	String url;
	int id;
	String imgUrl;
	String summary;
	Object obj;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
//	public BannerBean(Object object) {
//		super();
//		title = Value.stringValueForKey(object, "title", null);
//		url = Value.stringValueForKey(object, "url", null);
//		imgUrl = Value.stringValueForKey(object, "image", null);
//	}
	
	
}
