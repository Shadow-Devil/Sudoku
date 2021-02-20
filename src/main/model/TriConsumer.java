package main.model;

public interface TriConsumer <T,R,H> {
	void consume(T t, R r, H h);
}
