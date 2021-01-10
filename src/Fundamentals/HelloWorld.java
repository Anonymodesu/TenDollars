package Fundamentals;

import java.util.Scanner;

//simple example of the visitor design pattern
public class HelloWorld {
	
	// the MachineLearningAlgorithm provides a method to use a particular loss function
	private static interface MachineLearningAlgorithm {
		void accept(LossFunction a);
	}
	
	private static class CNN implements MachineLearningAlgorithm {
		
		@Override
		public void accept(LossFunction x) {
			System.out.println(x.visit(this));
		}
		
	}
	
	private static class LinearRegression implements MachineLearningAlgorithm {
		
		@Override
		public void accept(LossFunction x) {
			System.out.println(x.visit(this));
		}
		
	}
	
	// we can add any number of visit() function declarations in this interface
	// i.e. we can define any number of loss functions without modifying any classes implementing MachineLearningAlgorithm classes 
	private static interface LossFunction {
		String visit(CNN j);
		String visit(LinearRegression j);
	}
		
		
	private static class CrossEntropy implements LossFunction {

		@Override
		public String visit(CNN j) {
			return "CNN with CrossEntropy loss";
		}
			
		@Override
		public String visit(LinearRegression d) {
			return "Linear Regression with CrossEntropy loss";
		}
			
	}
		
	private static class MeanAbsoluteError implements LossFunction {

		@Override
		public String visit(CNN j) {
			return "CNN with Mean Absolute Error";
		}
			
		@Override
		public String visit(LinearRegression d) {
			return "Linear Regression with Mean Absolute Error";
		}
			
	}
	
	public static void main(String[] args) {
		
		MachineLearningAlgorithm cnn = new CNN();
		MachineLearningAlgorithm dudu = new LinearRegression();
		LossFunction freya = new CrossEntropy();
		LossFunction meimei = new MeanAbsoluteError();
		
		cnn.accept(freya);
		cnn.accept(meimei);
		dudu.accept(freya);
		dudu.accept(meimei);
		
		Scanner in = new Scanner(System.in);
		in.useDelimiter("");
		while(in.hasNext()) {
			char ch = in.next().charAt(0);
			boolean classifier = Character.isDigit(ch);
			System.out.println(ch + "<->" + (int)ch + "<->" + classifier);
		}
		in.close();
	}

}
