package test;

public class MissingFoodException extends Exception {
	
	public MissingFoodException(String food, String personName) {
		super(String.format("%s has not been prepared by %s yet", food, personName));
	}

}
