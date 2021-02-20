package main.model;

public class FinalField extends Field {
	
	public FinalField(int zahl) {
		super(zahl);
	}
	
	@Override
	public void setInhalt(int zahl) {
		//cant overwrite;
		System.err.println("Trying to Overwrite Constant Field");
	}
	
	@Override
	public boolean isConstant() {
		return true;
	}
	
	@Override
	public void reset() {
	}
}
