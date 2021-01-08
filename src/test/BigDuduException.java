package test;

public class BigDuduException extends Exception {
	public BigDuduException(String personName) {
		super(String.format("%s's dudu is too big to eat more", personName));
	}
}
