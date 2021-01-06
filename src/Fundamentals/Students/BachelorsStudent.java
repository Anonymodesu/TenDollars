package Fundamentals.Students;

import Fundamentals.Student;
import Fundamentals.Subject;

public class BachelorsStudent extends Student {

	public BachelorsStudent(String name, Gender gender, int age) {
		super(name, gender, age);
	}

	@Override
	protected boolean canEnrol(Subject subject) {
		return subject.canEnrol(this);
	}
	

}
