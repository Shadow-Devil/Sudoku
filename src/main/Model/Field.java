package main.Model;

public class Field {
	protected int inhalt;
	protected boolean selected;
	
	Field() {
		inhalt = 0;
		selected = false;
	}
	
	Field(int zahl) {
		inhalt = zahl;
		selected = false;
	}
	
	public int getInhalt() {
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
	
	
	public boolean getSelected() {
		return selected;
	}
	
	public void setSelected(boolean a) {
		selected = a;
	}
}
