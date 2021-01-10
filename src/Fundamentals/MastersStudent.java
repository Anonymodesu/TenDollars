package Fundamentals;

public class MastersStudent extends Student implements Supervisable {

	private Professor supervisor;
	
	public MastersStudent(String name, Gender gender, int age) {
		super(name, gender, age);
		supervisor = null;
	}
	
	@Override
	protected boolean canEnrol(Subject subject) {
		return subject.canEnrol(this);
	}

	@Override
	public void acceptSupervisor(Professor professor) {
		this.supervisor = professor;
	}

	@Override
	public Professor getSupervisor() {
		return supervisor;
	}

}
