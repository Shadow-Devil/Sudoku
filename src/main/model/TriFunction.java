package main.model;

public interface TriFunction <T,R,U,V> {
	V apply(T t, R r,U u);
}
