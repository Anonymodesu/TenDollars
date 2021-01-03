package Fundamentals;

import java.util.LinkedHashSet;
import java.util.Set;

public class University {
	private String name;
	private Set<Student> students;
	private Set<Subject> subjects;
	
	public University(String name) {
		this.name = name;
		students = new LinkedHashSet<>();
		subjects = new LinkedHashSet<>();
	}
	
	public void addSubject(Subject subject) {
		subjects.add(subject);
	}
	
	public void enrol(Student student) {
		students.add(student);
	}
	
	public void enrol(Student student, Subject subject) {
		if(!subjects.contains(subject)) {
			throw new IllegalStateException("%s is not taught by %as");
			
		} else if(!student.currentlyStudies(subject)) {
			student.enrol(subject);
			enrol(student);
		}
	}
	
	public void complete(Student student, Subject subject, int grade) {
		student.complete(subject, grade);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
