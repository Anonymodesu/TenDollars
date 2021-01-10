package Fundamentals;

public class UndergradSubject extends Subject {

	public UndergradSubject(String name, String code, int creditPoints) {
		super(name, code, creditPoints);
	}

	@Override
	public boolean canEnrol(BachelorsStudent student) {
		return true;
	}

	@Override
	public boolean canEnrol(HonoursStudent student) {
		return true;
	}

	@Override
	public boolean canEnrol(MastersStudent student) {
		return false;
	}

	@Override
	public boolean canEnrol(PhdStudent student) {
		return false;
	}

}
