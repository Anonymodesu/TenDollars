package Fundamentals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class Person {
	public enum Gender {
		Male, Female
	}
	
	private static int numPeople = 0;
	
	private String name;
	private int age;
	private Gender gender;
	private Person father;
	private Person mother;
	private List<Person> children;
	
	public Person(String name) {
		this(name, Gender.Male);
	}
	
	public Person(String name, Gender gender) {
		this(name, gender, 1);
	}
	
	public Person(String name, Gender gender, int age) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		father = null;
		mother = null;
		children = new ArrayList<Person>();
		numPeople++;
	}
	
	public static int getNumPeople() {
		return numPeople;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return String.format("%s is %d years old", name, age);
	}
	
	// Exceptions
	public void setParentOf(Person child) {		
		if(this.gender == Gender.Male) {
			if(child.father != null) {
				throw new IllegalArgumentException(String.format("%s already has a father", child.name));
			} else {
				child.father = this;
			}
			
			
		} else if(this.gender == Gender.Female) {
			if(child.mother != null) {
				throw new IllegalArgumentException(String.format("%s already has a mother", child.name));
			} else {
				child.mother = this;
			}
		}
		
		children.add(child);
	}
	
	public List<Person> getSiblings() {
		Set<Person> siblings = new HashSet<Person>();
		
		if(father != null) {
			for(Person sibling : father.children) {
				siblings.add(sibling);
			}
		}
		
		if(mother != null) {
			for(Person sibling : mother.children) {
				siblings.add(sibling);
			}
		}
		
		siblings.remove(this);
		
		return new ArrayList<Person>(siblings);
	}
	
	// Recursion
	public List<Person> getAncestors() {
		Set<Person> ancestors = new HashSet<Person>();
		
		if(mother != null) {
			ancestors.add(mother);
			ancestors.addAll(mother.getAncestors());
		}
		if(father != null) {
			ancestors.add(father);
			ancestors.addAll(father.getAncestors());
		}
		
		return new ArrayList<Person>(ancestors);
	}
	
	public List<Person> getDescendants() {
		Set<Person> descendants = new HashSet<Person>();
		for(Person child: children) {
			descendants.add(child);
			descendants.addAll(child.getDescendants());
		}
		return new ArrayList<Person>(descendants);
	}
	
	public List<Person> getFamilyTree() {
		Set<Person> familyTree = new HashSet<Person>();
		getFamilyTree(familyTree);
		familyTree.remove(null);
		return new ArrayList<Person>(familyTree);
	}
	
	// Method overloading
	private void getFamilyTree(Set<Person> existingTree) {
		
		// Lambda function
		Consumer<Person> addToTree = person -> {
			if(person != null && !existingTree.contains(person)) {
				existingTree.add(person);
				person.getFamilyTree(existingTree);
			}
		};
		
		addToTree.accept(father);
		addToTree.accept(mother);
		children.forEach(addToTree);
	}
}
