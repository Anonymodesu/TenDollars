package Fundamentals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Student extends Person {
	private Set<Subject> currentlyStudying;
	private Map<Subject, Integer> finishedStudying;
	
	public Student(String name, Person.Gender gender, int age) {
		super(name, gender, age);
		currentlyStudying = new LinkedHashSet<>();
		finishedStudying = new LinkedHashMap<>();
	}
	
	public List<Subject> getSubjects() {
		ArrayList<Subject> subjects = new ArrayList<>(currentlyStudying);
		subjects.addAll(finishedStudying.keySet());
		return subjects;
	}
	
	void enrol(Subject subject) {
		if(currentlyStudying.contains(subject)) {
			throw new IllegalStateException(String.format("%s is already studying %s", getName(), subject));
		} else if(finishedStudying.keySet().contains(subject)) {
			throw new IllegalStateException(String.format("%s has already completed %s", getName(), subject));
		}
		
		currentlyStudying.add(subject);
		subject.enrolledStudents.add(this);
	}
	
	void complete(Subject subject, int grade) {
		if(!currentlyStudying.contains(subject)) {
			throw new IllegalStateException(String.format("%s isn't studying %s", getName(), subject));
		} else if(grade < 0 || grade > 100) {
			throw new IllegalArgumentException("Grade must be between 0 and 100");
		}
		
		currentlyStudying.remove(subject);
		finishedStudying.put(subject, grade);
		subject.enrolledStudents.remove(this);
		subject.finishedStudents.put(this, grade);
	}
	
	public int getGrade(Subject subject) {
		return finishedStudying.get(subject);
	}
	
	public double getWAM() {
		if(finishedStudying.isEmpty()) {
			throw new IllegalStateException(String.format("%s has not finished any subjects", getName()));
		}
		
		double totalCP = finishedStudying.keySet()
				.stream()
				.mapToDouble(a -> a.creditPoints)
				.sum();
		
		int sum = 0;
		for(Entry<Subject, Integer> entry: finishedStudying.entrySet()) {
			sum += entry.getKey().creditPoints * entry.getValue();
		}
		
		return sum / totalCP;
	}
	
	public List<Student> getClassmates(Subject subject) {
		if(currentlyStudying.contains(subject)) {
			Set<Student> students = subject.enrolledStudents;
			students.remove(this);
			return new ArrayList<>(students);
			
		} else {
			return new ArrayList<>();
		}
	}
	
	public List<Student> getClassmates() {
		Set<Student> students = new LinkedHashSet<>();
		for(Subject subject: currentlyStudying) {
			students.addAll(subject.enrolledStudents);
		}
		students.remove(this);
		return new ArrayList<Student>(students);
	}
	
	public boolean currentlyStudies(Subject subject) {
		return currentlyStudying.contains(subject);
	}
	
	@Override
	public String toString() {
		return super.toString() + " and studies " + currentlyStudying.toString();
	}
	
}
