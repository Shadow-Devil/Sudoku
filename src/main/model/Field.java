package main.model;

public class Field {
	protected int inhalt;
	
	Field() {
		inhalt = 0;
	}
	
	Field(int zahl) {
		inhalt = zahl;
	}
	
	public int getContent() {
		return inhalt;
	}
	
	public void setInhalt(int zahl) {
		inhalt = zahl;
	}
	
	public void reset(){
		inhalt = 0;
	}
	
	public boolean isConstant() {
		return false;
	}
}
