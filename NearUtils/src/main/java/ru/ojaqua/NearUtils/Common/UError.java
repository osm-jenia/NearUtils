package ru.ojaqua.NearUtils.Common;

public class UError extends RuntimeException {

	private static final long serialVersionUID = -5991061252440201934L;
	
	private String addInfoForTrace = "";
	

	public UError(String message) {
		super(message);
	}

	public UError(Throwable cause) {
		super(cause);
	}

	public UError(String message, Throwable cause) {
		super(message, cause);
	}

	public UError(String message, String addInfoForTrace) {
		super(message);
		this.addInfoForTrace = addInfoForTrace;
	}

	public UError(Throwable cause, String addInfoForTrace) {
		super(cause);
		this.addInfoForTrace = addInfoForTrace;
	}

	public UError(String message, Throwable cause, String addInfoForTrace) {
		super(message, cause);
		this.addInfoForTrace = addInfoForTrace;
	}
	
	public String getAddInfoForTrace() {
		return addInfoForTrace; 
	}
	

}
