package Fundamentals.Subjects;

import Fundamentals.Subject;
import Fundamentals.Students.BachelorsStudent;
import Fundamentals.Students.HonoursStudent;
import Fundamentals.Students.MastersStudent;
import Fundamentals.Students.PhdStudent;

public class ResearchSubject extends Subject {

	private static final int MASTERS_REQUIRED_WAM = 65;
	
	public ResearchSubject(String name, String code, int creditPoints) {
		super(name, code, creditPoints);
	}

	@Override
	public boolean canEnrol(BachelorsStudent student) {
		return false;
	}

	@Override
	public boolean canEnrol(HonoursStudent student) {
		return false;
	}

	@Override
	public boolean canEnrol(MastersStudent student) {
		boolean ret;
		try {
			ret = student.getWAM() >= MASTERS_REQUIRED_WAM;
		} catch(IllegalStateException e) {
			ret = false;
		}
		return ret;
	}

	@Override
	public boolean canEnrol(PhdStudent student) {
		return true;
	}

}
