package Fundamentals;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Professor extends Person implements Teacher {
	private static final int MAX_SUBJECTS = 3;
	private static final int MAX_SUPERVISEES = 4;
	
	private Set<Subject> subjects;
	private Set<Supervisable> supervisees;
	
	public Professor(String name, Gender gender, int age) {
		super(name, gender, age);
		subjects = new LinkedHashSet<>(MAX_SUBJECTS);
		supervisees = new LinkedHashSet<>(MAX_SUPERVISEES);
	}

	@Override
	public void teach(Subject subject) {
		if(subjects.size() < MAX_SUBJECTS) {
			
			//the previous teacher stops teaching this subject
			if(subject.teacher != null) {
				subject.teacher.stopTeaching(subject);
			}
			
			subjects.add(subject);
			subject.teacher = this;
		} else {
			throw new IllegalStateException(String.format("%s can't teach more than %d subjects", getName(), MAX_SUBJECTS));
		}
	}
	
	@Override
	public void stopTeaching(Subject subject) {
		if(subjects.remove(subject)) {
			subject.teacher = null;
		}
	}
	
	@Override
	public List<Subject> getTaughtSubjects() {
		return new ArrayList<>(subjects);
	}
	
	public List<Supervisable> getSupervisedStudents() {
		return new ArrayList<>(supervisees);
	}
	
	public List<Student> getTaughtStudents() {
		Set<Student> students = new LinkedHashSet<>();
		subjects.forEach(subject -> students.addAll(subject.enrolledStudents));
		return new ArrayList<>(students);
	}
	
	void finishSupervising(Supervisable supervisee) {
		supervisees.remove(supervisee);
		supervisee.acceptSupervisor(null);
	}
	
	void supervise(Supervisable supervisee) {
		if(supervisees.size() < MAX_SUPERVISEES) {
			
			//the previous supervisor stops supervising this supervisee
			if(supervisee.getSupervisor() != null) {
				supervisee.getSupervisor().finishSupervising(supervisee);
			}
			
			supervisees.add(supervisee);
			supervisee.acceptSupervisor(this);
		} else {
			throw new IllegalStateException(String.format("%s can't supervise more than %d students", getName(), MAX_SUPERVISEES));
		}
	}

}
