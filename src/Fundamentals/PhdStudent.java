package Fundamentals;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PhdStudent extends Student implements Supervisable, Teacher {

	private Professor supervisor;
	private Set<Subject> taughtSubjects;
	
	public PhdStudent(String name, Gender gender, int age) {
		super(name, gender, age);
		supervisor = null;
		taughtSubjects = new LinkedHashSet<>();
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

	@Override
	public void teach(Subject subject) {
		if(supervisor.getTaughtSubjects().contains(subject)) {
			taughtSubjects.add(subject);
			
			//the previous teacher stops teaching this subject
			if(subject.teacher != null) {
				subject.teacher.stopTeaching(subject);
			}
			
			taughtSubjects.add(subject);
			subject.teacher = this;
			
		} else {
			String msg = String.format("Supervisor %s must teach %s for %s to teach %s", supervisor.getName(), subject, this.getName(), subject);
			throw new IllegalStateException(msg);
		}
	}
	
	@Override
	public void stopTeaching(Subject subject) {
		if(taughtSubjects.remove(subject)) {
			subject.teacher = null;
		}
	}
	
	@Override
	public List<Subject> getTaughtSubjects() {
		return new ArrayList<>(taughtSubjects);
	}

}
