package Fundamentals.Subjects;

import Fundamentals.Subject;
import Fundamentals.Students.BachelorsStudent;
import Fundamentals.Students.HonoursStudent;
import Fundamentals.Students.MastersStudent;
import Fundamentals.Students.PhdStudent;

public class PostgradSubject extends Subject {

	private static final double HONS_REQUIRED_WAM = 75;
	private static final int HONS_REQUIRED_CP = 144;
	
	public PostgradSubject(String name, String code, int creditPoints) {
		super(name, code, creditPoints);
	}

	@Override
	public boolean canEnrol(BachelorsStudent student) {
		return false;
	}

	@Override
	public boolean canEnrol(HonoursStudent student) {
		boolean ret;
		try {
			ret = student.getWAM() >= HONS_REQUIRED_WAM && 
					student.getCompletedCP() >= HONS_REQUIRED_CP;
		} catch(IllegalStateException e) {
			ret = false;
		}
		return ret;
	}

	@Override
	public boolean canEnrol(MastersStudent student) {
		return true;
	}

	@Override
	public boolean canEnrol(PhdStudent student) {
		return false;
	}

}
