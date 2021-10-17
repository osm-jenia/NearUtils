package ru.ojaqua.NearUtils.Common;

public class NearUtilsError extends RuntimeException {

	private static final long serialVersionUID = -5991061252440201934L;

	private String addInfoForTrace = "";

	public NearUtilsError(String message) {
		super(message);
	}

	public NearUtilsError(Throwable cause) {
		super(cause);
	}

	public NearUtilsError(String message, Throwable cause) {
		super(message, cause);
	}

	public NearUtilsError(String message, String addInfoForTrace) {
		super(message);
		this.addInfoForTrace = addInfoForTrace;
	}

	public NearUtilsError(Throwable cause, String addInfoForTrace) {
		super(cause);
		this.addInfoForTrace = addInfoForTrace;
	}

	public NearUtilsError(String message, Throwable cause, String addInfoForTrace) {
		super(message, cause);
		this.addInfoForTrace = addInfoForTrace;
	}

	public String getAddInfoForTrace() {
		return addInfoForTrace;
	}

}
