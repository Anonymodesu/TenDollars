package test;

public class EmptyFoodsFileException extends Exception {
	public EmptyFoodsFileException(String fileName) {
		super(String.format("%s is empty", fileName));
	}
}
