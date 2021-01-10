package Fundamentals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Subject {
	final String name;
	final String code;
	final int creditPoints;
	final Set<Student> enrolledStudents;
	final Map<Student, Integer> finishedStudents;
	Teacher teacher;
	
	protected Subject(String name, String code, int creditPoints) {
		if(creditPoints < 1) {
			throw new IllegalArgumentException(String.format("%s has fewer than 1 credit point", name));
		}
		
		this.name = name;
		this.code = code;
		this.creditPoints = creditPoints;
		this.enrolledStudents = new LinkedHashSet<>();
		this.finishedStudents = new LinkedHashMap<>();
		teacher = null;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public List<Student> getStudents() {
		return new ArrayList<>(enrolledStudents);
	}
	
	public double getAverageGrade() {
		if(finishedStudents.isEmpty()) {
			throw new IllegalStateException(String.format("No students have finished %s", name));
		}
		
		return finishedStudents.values()
				.stream()
				.mapToDouble(a -> a)
				.average()
				.getAsDouble();
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public abstract boolean canEnrol(BachelorsStudent student);
	public abstract boolean canEnrol(HonoursStudent student);
	public abstract boolean canEnrol(MastersStudent student);
	public abstract boolean canEnrol(PhdStudent student);
	
}
