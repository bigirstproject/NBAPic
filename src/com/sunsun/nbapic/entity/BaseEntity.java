package com.sunsun.nbapic.entity;

import java.io.Serializable;

public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4189095082110380977L;

	private int id;

	private String url;

	private String imageUrl;
	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
