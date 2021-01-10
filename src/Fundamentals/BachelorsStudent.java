package Fundamentals;

public class BachelorsStudent extends Student {

	public BachelorsStudent(String name, Gender gender, int age) {
		super(name, gender, age);
	}

	@Override
	protected boolean canEnrol(Subject subject) {
		return subject.canEnrol(this);
	}
	

}
