package com.kreative.rainbowstudio.gui.editor;

public class AnimationTooComplexException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public AnimationTooComplexException() {
		super();
	}
	
	public AnimationTooComplexException(String message) {
		super(message);
	}
	
	public AnimationTooComplexException(Throwable cause) {
		super(cause);
	}
	
	public AnimationTooComplexException(String message, Throwable cause) {
		super(message, cause);
	}
}
