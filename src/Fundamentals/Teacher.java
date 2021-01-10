package Fundamentals;

import java.util.List;

interface Teacher {
	void teach(Subject subject);
	void stopTeaching(Subject subject);
	List<Subject> getTaughtSubjects();
}
