package Data_structure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class Test_StuGrade {

	@Test
	void testAdd() {
		Map<String, String> expect = new HashMap<>(); 
		Map_StuGrade cs = new Map_StuGrade(new HashMap<>());
		
		cs.addStudent("Carl");
		cs.addStudent("Joe");
		cs.addStudent("Jackdon");
		cs.addStudent("Sarah");
		
		expect.put("Carl", null);
		expect.put("Joe", null);
		expect.put("Jackdon", null);
		expect.put("Sarah", null);
		
		assertEquals(expect, cs.getStuGrade());	
	}
	
	@Test
	void testRemove() {
		Map<String, String> expect = new HashMap<>(); 
		Map_StuGrade cs = new Map_StuGrade(new HashMap<>());
		
		cs.addStudent("Carl");
		cs.addStudent("Joe");
		cs.addStudent("Jackdon");
		cs.addStudent("Sarah");
		
		expect.put("Carl", null);
		expect.put("Joe", null);
		expect.put("Sarah", null);
		
		assertThrows(IllegalArgumentException.class, () -> {
			cs.removeStudent("Freya");
		});
		
		cs.removeStudent("Jackdon");
		assertEquals(expect, cs.getStuGrade());
	}
	
	@Test 
	void modifyGrade(){
		Map<String, String> expect = new HashMap<>(); 
		Map_StuGrade cs = new Map_StuGrade(new HashMap<>());
		
		cs.addStudent("Carl");
		cs.addStudent("Joe");
		cs.addStudent("Sarah");
		
		cs.modifyGrade("Carl", "B+");
		cs.modifyGrade("Joe", "C");
		cs.modifyGrade("Sarah", "A");
		
		expect.put("Carl", "B+");
		expect.put("Joe", "C");
		expect.put("Sarah", "A");
		
		assertEquals(expect, cs.getStuGrade());
		cs.printAllGrades();
	}

}
