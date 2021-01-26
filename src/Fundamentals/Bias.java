package Fundamentals;

public interface Bias {
	int influenceGrade(Subject subject, Student student, int grade);
	
	static int restrictGrade(int grade) {
		if(grade < 0) {
			return 0;
		} if(grade > 100) {
			return 100;
		} else {
			return grade;
		}
	}
}
