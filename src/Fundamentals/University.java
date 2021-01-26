package Fundamentals;

import java.util.LinkedHashSet;
import java.util.Set;

public class University {
	private String name;
	private Set<Student> students;
	private Set<Subject> subjects;
	private Set<Professor> professors;
	
	public University(String name) {
		this.name = name;
		students = new LinkedHashSet<>();
		subjects = new LinkedHashSet<>();
		professors = new LinkedHashSet<>();
	}
	
	public void addSubject(Subject subject) {
		subjects.add(subject);
	}
	
	public void enrol(Student student) {
		students.add(student);
	}
	
	public void enrol(Student student, Subject subject) {
		if(!subjects.contains(subject)) {
			throw new IllegalStateException(String.format("%s is not taught by %s", subject.name, this.name));
			
		} else if(!student.currentlyStudies(subject)) {
			student.enrol(subject);
			enrol(student);
		}
	}
	
	public void complete(Student student, Subject subject, int grade) {
		if(subject.teacher != null) {
			grade = subject.teacher.grade(subject, student, grade);
		}
		student.complete(subject, grade);
	}
	
	public void employ(Professor professor) {
		professors.add(professor);
	}
	
	public void teach(Teacher teacher, Subject subject) {
		if(subjects.contains(subject)) {
			
			if(professors.contains(teacher) || students.contains(teacher)) {
				teacher.teach(subject);
			} else {
				throw new IllegalStateException(String.format("%s is not employed in %s", teacher, this.name));
			}
			
		} else {
			throw new IllegalStateException(String.format("%s is not taught by %s", subject.name, this.name));
		}
	}
	
	public void supervise(Professor professor, Supervisable student) {
		if(professors.contains(professor)) {
			
			if(students.contains(student)) {
				professor.supervise(student);
			} else {
				throw new IllegalStateException(String.format("%s is not enrolled in %s", student.toString(), this.name));
			}
			
		} else {
			throw new IllegalStateException(String.format("%s is not employed in %s", professor.getName(), this.name));
		}
	}
		
	@Override
	public String toString() {
		return name;
	}
}
