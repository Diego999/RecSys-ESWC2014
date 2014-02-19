package ch.hearc.p3.recsys.utils;

public class Triple<T, U, V> implements Comparable<Triple<T, U, V>> {
	private T t;
	private U u;
	private V v;

	public Triple(T t, U u, V v) {
		this.t = t;
		this.u = u;
		this.v = v;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Triple<T, U, V> o) {
		return this == null ? (o == null ? 0 : 1) : ((Comparable<T>) t)
				.compareTo(o.t);
	}

	@Override
	public String toString() {
		return t + "\t" + u + "\t" + v;
	}

	public T getKey() {
		return this.t;
	}

	public U getValue1() {
		return this.u;
	}

	public V getValue2() {
		return this.v;
	}

	public void setKey(T t) {
		this.t = t;
	}

	public void setValue1(U u) {
		this.u = u;
	}

	public void setValue2(V v) {
		this.v = v;
	}

}
