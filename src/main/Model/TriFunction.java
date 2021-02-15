package main.Model;

public interface TriFunction <T,R,U,V> {
	V apply(T t, R r,U u);
}
