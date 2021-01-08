package test;

public class UnknownFoodException extends Exception {
	public UnknownFoodException(String food, String personName) {
		super(String.format("%s doesn't know how to prepare %s", personName, food));
	}
}
