package Data_structure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Map_StuGrade {
	private Map<String, String> stuGrade;

	public Map_StuGrade() {
		stuGrade = new HashMap<>();
	}
	
	public Map_StuGrade(Map<String, String> stuGrade) {
		this.stuGrade = stuGrade;
	}

	public Map<String, String> getStuGrade() {
		return stuGrade;
	}
	
	public void addStudent(String name) {
		stuGrade.put(name, null);
	}
	
	public void removeStudent(String name) {
		Set<String> nameSet = stuGrade.keySet();
		if (!nameSet.contains(name)) {
			throw new IllegalArgumentException("There is no the student.");
		} else {
			nameSet.remove(name);
		}
	}
	
	public void modifyGrade(String name, String grade) {
		stuGrade.put(name, grade);
	}
	
	public void printAllGrades() {
		Map<String, String> treeMap = new TreeMap<>();
		treeMap.putAll(stuGrade);
		
		for (String student : treeMap.keySet()) {
			String grade = treeMap.get(student);
			System.out.format("%s: %s", student, grade);
		}
	}

}
