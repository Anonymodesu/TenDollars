package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FreyaFeeder {
	public static void main(String[] args) {
		Set<String> knownFoods = getKnownFoods("knownFoods.txt");
		Scanner userInput = new Scanner(System.in);
		int duduSize = getDuduSize(userInput);
		Freya freya = new Freya(duduSize, knownFoods);
		feedFreya(freya, userInput);
		userInput.close();
	}
	
	private static Set<String> getKnownFoods(String fileName) {
		Set<String> knownFoods = new HashSet<>();
		File myObj = new File(fileName);
		Scanner fileReader = null;
		
		try {
			fileReader = new Scanner(myObj);
		    
			try {
				while (fileReader.hasNextLine()) {
			        String food = fileReader.nextLine();
			        knownFoods.add(food);
			    }
				
			    if(knownFoods.isEmpty()) {
			    	throw new EmptyFoodsFileException(fileName);
			    }
			    
			} catch (EmptyFoodsFileException e) {
				System.out.println(fileName + " is empty. Initialising knownFoods with default parameters");
				knownFoods = defaultKnownFoods();
				
			} finally {
				fileReader.close();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println("Initialising knownFoods with default parameters");
			knownFoods = defaultKnownFoods();
		} 

		return knownFoods;
	}
	
	private static Set<String> defaultKnownFoods() {
		Set<String> knownFoods = new HashSet<>();
		knownFoods.add("beef");
		knownFoods.add("fanfan");
		knownFoods.add("jianbing");
		knownFoods.add("quanquan");
		return knownFoods;
	}
	
	private static int getDuduSize(Scanner userInput) {
		System.out.println("Please enter Freya's dudu size");
		Integer duduSize = null;
		
		while(duduSize == null) {
			String input = userInput.nextLine();
			
			try {
				duduSize = Integer.parseInt(input);
			} catch(NumberFormatException e) {
				System.out.println(input + " is not an integer");
			}
		}
		
		return duduSize;
	}
	
	private static void feedFreya(Freya freya, Scanner userInput) {
		System.out.println(freya);
		System.out.println("Should Freya eat, prepare food, or sleep?");
		
		while(userInput.hasNextLine()) {
			String command = userInput.nextLine();
			
			switch(command) {
			case "eat": {
				System.out.println("What should Freya eat?");
				
				String food = userInput.nextLine();
				try {
					freya.eat(food);
					System.out.println("Freya ate some yummy " + food);
				} catch (MissingFoodException e) {
					System.out.println(e.getMessage());
				} catch (BigDuduException e) {
					System.out.println(e.getMessage());
				}
				
				break;
			}
				
			case "prepare": {
				System.out.println("What should Freya prepare?");
				String food = userInput.nextLine();
				
				try {
					freya.prepare(food);
					System.out.println("Freya prepared some " + food);
				} catch (UnknownFoodException e) {
					System.out.println(e.getMessage());
				}
				
				break;
			}
				
			case "sleep":
				System.out.println("Yayyyyy");
				return;
				
			default:
				System.out.println(command + " is not a valid command");
				break;
			}
			
			System.out.print(freya);
			System.out.println("Should Freya eat, prepare food, or sleep?");
		}
	}
}
