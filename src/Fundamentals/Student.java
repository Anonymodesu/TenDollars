package Fundamentals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Student extends Person {
	private Set<String> currentlyStudying;
	private Map<String, Integer> finishedStudying;
	
	public Student(String name, Person.Gender gender, int age) {
		super(name, gender, age);
		currentlyStudying = new LinkedHashSet<>();
		finishedStudying = new LinkedHashMap<>();
	}
	
	public List<String> getSubjects() {
		ArrayList<String> subjects = new ArrayList<>(currentlyStudying);
		subjects.addAll(finishedStudying.keySet());
		return subjects;
	}
	
	public void enrol(String subject) {
		if(currentlyStudying.contains(subject) || finishedStudying.keySet().contains(subject)) {
			throw new IllegalArgumentException(String.format("%s is already studying %s", "blah", subject));
		}
		
		currentlyStudying.add(subject);
	}
	
	public void complete(String subject, int grade) {
		if(!currentlyStudying.contains(subject)) {
			throw new IllegalArgumentException(String.format("%s isn't studying %s", getName(), subject));
		} else if(grade < 0 || grade > 100) {
			throw new IllegalArgumentException("Grade must be between 0 and 100");
		}
		
		currentlyStudying.remove(subject);
		finishedStudying.put(subject, grade);
	}
	
	public int getGrade(String subject) {
		return finishedStudying.get(subject);
	}
	
	public double getWAM() {
		if(finishedStudying.isEmpty()) {
			throw new IllegalStateException(String.format("%s has not finished any subjects", getName()));
		}
		
		return finishedStudying.values()
				.stream()
				.mapToDouble(num -> num)
				.average()
				.getAsDouble();
	}
	
	@Override
	public String toString() {
		return super.toString() + " and studies " + currentlyStudying.toString();
	}
	
}
