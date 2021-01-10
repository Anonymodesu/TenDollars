package Fundamentals;

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
		boolean eligible;
		try {
			eligible = student.getWAM() >= MASTERS_REQUIRED_WAM;
		} catch(IllegalStateException e) {
			eligible = false;
		}
		return eligible && student.getSupervisor() != null;
	}

	@Override
	public boolean canEnrol(PhdStudent student) {
		return student.getSupervisor() != null;
	}
	

}
