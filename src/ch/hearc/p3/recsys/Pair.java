package ch.hearc.p3.recsys;

public class Pair<T, U> implements Comparable<Pair<T, U>> {
	private T t;
	private U u;

	public Pair(T t, U u) {
		this.t = t;
		this.u = u;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Pair<T, U> o) {
		return this == null ? (o == null ? 0 : 1) : ((Comparable<T>) t)
				.compareTo(o.t);
	}

	@Override
	public String toString() {
		return t + "\t" + u;
	}

	public T getKey() {
		return this.t;
	}

	public U getValue1() {
		return this.u;
	}

	public void setKey(T t) {
		this.t = t;
	}

	public void setValue1(U u) {
		this.u = u;
	}
}
