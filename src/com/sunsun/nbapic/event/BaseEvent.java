package com.sunsun.nbapic.event;

import java.io.Serializable;

public abstract class BaseEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2918236742131883947L;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
