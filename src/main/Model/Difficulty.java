package main.Model;

public enum Difficulty {
	EASY(45, 51),
	MEDIUM(50, 56),
	HARD(55, 57),
	EXTREME (57,68);
	
	private final int min, max;
	
	Difficulty (int min, int max){
		this.min = min;
		this.max = max;
	}
	
	public boolean isInRange(int x){
		return x >= min && x <= max;
	}
	
	
}
