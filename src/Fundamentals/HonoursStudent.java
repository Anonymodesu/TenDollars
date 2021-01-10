package Fundamentals;

public class HonoursStudent extends Student {

	public HonoursStudent(String name, Gender gender, int age) {
		super(name, gender, age);
	}
	
	@Override
	protected boolean canEnrol(Subject subject) {
		return subject.canEnrol(this);
	}
}
