package Fundamentals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToDoubleBiFunction;

public class University {

	
	private String name;
	private Set<Student> students;
	private Set<Subject> subjects;
	private Set<Professor> professors;
	
	private static final ToDoubleBiFunction<Double, Double> getLarger = (a, b) -> {
		if(a > b) {
			return a;
		} else {
			return b;
		}
	};
	
	private static final ToDoubleBiFunction<Double, Double> getSmaller = (a, b) -> {
		if(a < b) {
			return a;
		} else {
			return b;
		}
	};
	
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
	
	private <T> T findOptimal(Iterable<T> list, Function<T,Double> valueFunc, ToDoubleBiFunction<Double, Double> comparisonFunc) {
		T optimalElement = list.iterator().next();
		
		for(T currentElement: list) {
			double currentValue = valueFunc.apply(currentElement);
			double optimalValue = valueFunc.apply(optimalElement);
			
			double newValue = comparisonFunc.applyAsDouble(optimalValue, currentValue);
			if(newValue == currentValue) {
				optimalElement = currentElement;
			}
		}
		return optimalElement;
	}
	
	public Student getBestStudent() {
		return findOptimal(students, student -> student.getWAM(), getLarger);
	}
	
	public Student getWorstStudent() {
		return findOptimal(students, student -> student.getWAM(), getSmaller);
	}

	public Subject getEasiestSubject() {
		return findOptimal(subjects, subject -> subject.getAverageGrade(), getLarger);
	}
	
	public Subject getHardestSubject() {
		return findOptimal(subjects, subject -> subject.getAverageGrade(), getSmaller);
	}
	
	public Teacher getMostLenientTeacher() {
		return findOptimal(getTeachers(), 
				teacher -> teacher.getTaughtSubjects().stream()
					.mapToDouble(subject -> subject.getAverageGrade())
					.average()
					.orElse(Double.MIN_VALUE), 
					getLarger);
	}
	
	public Teacher getStrictestTeacher() {
		return findOptimal(getTeachers(), 
				teacher -> teacher.getTaughtSubjects().stream()
					.mapToDouble(subject -> subject.getAverageGrade())
					.average()
					.orElse(Double.MAX_VALUE), 
					getSmaller);
	}
	
	// returns Professors and Phd students who teach subjects
	private List<Teacher> getTeachers() {
		List<Teacher> teachers = new ArrayList<>();
		
		for(Subject subject: subjects) {
			if(subject.teacher != null) {
				teachers.add(subject.teacher);
			}
		}
		
		return teachers;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
