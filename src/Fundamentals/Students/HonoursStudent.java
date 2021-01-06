package Fundamentals.Students;

import Fundamentals.Student;
import Fundamentals.Subject;

public class HonoursStudent extends Student {

	public HonoursStudent(String name, Gender gender, int age) {
		super(name, gender, age);
	}
	
	@Override
	protected boolean canEnrol(Subject subject) {
		return subject.canEnrol(this);
	}
}
