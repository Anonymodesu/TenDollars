package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Freya {
	private static final String NAME = "Freya";

	private final Set<String> knownFoods;
	private final int maxDudu;
	private List<String> preparedFood;
	private int currentDudu;
	
	public Freya(int maxDudu, Set<String> knownFoods) {
		if(maxDudu < 1) {
			throw new IllegalArgumentException("Freya's dudu must be larger than 0");
		}
		this.knownFoods = knownFoods;
		this.maxDudu = maxDudu;
		this.preparedFood = new ArrayList<String>();
		this.currentDudu = 0;
	}
	
	public void eat(String food) throws MissingFoodException, BigDuduException {
		if(currentDudu >= maxDudu) {
			throw new BigDuduException(NAME);
			
		} else if(preparedFood.contains(food)) {
			preparedFood.remove(food);
			currentDudu++;
			
		} else {
			throw new MissingFoodException(food, NAME);
		}
	}
	
	public void prepare(String food) throws UnknownFoodException {
		if(knownFoods.contains(food)) {
			preparedFood.add(food);
			
		} else {
			throw new UnknownFoodException(food, NAME);
		}
	}
	
	public double duduCapacity() {
		return (double) currentDudu / maxDudu;
	}
	
	@Override
	public String toString() {
		double duduPercentage = duduCapacity() * 100;
		return String.format("%s's dudu is at capacity %.2f%%.\nShe knows how to cook %s.\n", NAME, duduPercentage, knownFoods.toString());
	}
}
