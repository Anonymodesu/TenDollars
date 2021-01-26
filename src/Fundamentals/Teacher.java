package Fundamentals;

import java.util.List;

interface Teacher {
	void teach(Subject subject);
	void stopTeaching(Subject subject);
	List<Subject> getTaughtSubjects();
	void setBias(Bias bias);
	int grade(Subject subject, Student student, int grade);
}
