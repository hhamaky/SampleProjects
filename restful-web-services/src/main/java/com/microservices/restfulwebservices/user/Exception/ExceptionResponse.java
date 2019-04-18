package com.microservices.restfulwebservices.user.Exception;

import java.util.Date;


public class ExceptionResponse {
	
	
	private Date timeStamp;
	
	private String message;
	
	private String Detail;

	public ExceptionResponse(Date timeStamp, String message, String detail) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		Detail = detail;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetail() {
		return Detail;
	}
	
	

}
