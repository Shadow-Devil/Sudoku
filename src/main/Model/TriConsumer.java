package main.Model;

public interface TriConsumer <T,R,H> {
	void consume(T t, R r, H h);
}
