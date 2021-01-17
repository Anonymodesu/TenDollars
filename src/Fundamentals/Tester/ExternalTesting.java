package Fundamentals.Tester;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.Queue;

import org.junit.jupiter.api.Test;

import Fundamentals.BachelorsStudent;
import Fundamentals.Bias;
import Fundamentals.MastersStudent;
import Fundamentals.Person;
import Fundamentals.Person.Gender;
import Fundamentals.PhdStudent;
import Fundamentals.PostgradSubject;
import Fundamentals.Professor;
import Fundamentals.ResearchSubject;
import Fundamentals.Student;
import Fundamentals.Subject;
import Fundamentals.UndergradSubject;
import Fundamentals.University;

// this class represents an external class using the University class and does not have access to package private methods
class ExternalTesting {
	
	@Test
	void testUniversity() {
		University usyd = new University("University of Sydney");
		Subject momo = new UndergradSubject("Momo stuff", "MOMO9999", 50);
		Subject chacha = new UndergradSubject("Chacha stuff", "CHCH9999", 50);
		Student jackdon = new BachelorsStudent("Jackdon", Person.Gender.Male, 24);
		Student freya = new BachelorsStudent("Freya", Person.Gender.Female, 23);
		
		usyd.addSubject(momo);
		usyd.addSubject(chacha);
		
		usyd.enrol(jackdon);
		usyd.enrol(freya);
		usyd.enrol(freya); //nothing happens
		
		usyd.enrol(jackdon, chacha);
		usyd.enrol(freya, momo);
		usyd.enrol(freya, chacha);
		usyd.enrol(freya, chacha); //nothing happens
		
		Subject peel = new UndergradSubject("Peel peel", "PEEL0101", 6);
		assertThrows(IllegalStateException.class, () -> {
			usyd.enrol(jackdon, peel); //peel isnt taught by the uni
		});
		assertThrows(IllegalStateException.class, () -> {
			usyd.complete(jackdon, momo, 60); //jackdon isnt currently enrolled in momo
		});
		assertThrows(IllegalArgumentException.class, () -> {
			usyd.complete(jackdon, chacha, -1); //grade must be between 0 and 100
		});
		
		usyd.complete(jackdon, chacha, 1);
		usyd.complete(freya, momo, 1);
		
		assertThrows(IllegalStateException.class, () -> {
			usyd.enrol(freya, momo); // freya has already completed momo
		});
		
	}
	
	@Test
	void testTeacher() {
		University usyd = new University("University of Sydney");
		Professor ladao = new Professor("La Dao", Person.Gender.Male, 5);
		Professor maomao = new Professor("Mao Mao", Person.Gender.Female, 24);
		Subject aiai = new PostgradSubject("Aiai", "AIAI0101", 6);
		Subject qinqin = new ResearchSubject("QinQin", "QIQI0101", 6);
		MastersStudent freya = new MastersStudent("Freya", Person.Gender.Female, 23);
		PhdStudent geiQianRen = new PhdStudent("Sean", Person.Gender.Male, 25);
		
		assertThrows(IllegalStateException.class, () -> {
			usyd.teach(ladao, aiai); //ladao isnt employed by the university and aiai isnt taught by the university
		});
		
		usyd.addSubject(aiai);
		assertThrows(IllegalStateException.class, () -> {
			usyd.teach(ladao, aiai); //ladao isnt employed by the university
		});
		
		usyd.employ(ladao);
		usyd.teach(ladao, aiai);
		assertEquals(ladao, aiai.getTeacher());
		
		//change aiai's teacher from ladao to maomao
		usyd.employ(maomao);
		usyd.teach(maomao, aiai);
		assertEquals(maomao, aiai.getTeacher());
		assertTrue(ladao.getTaughtSubjects().isEmpty());
		
		assertThrows(IllegalStateException.class, () -> {
			usyd.supervise(ladao, freya); //freya isnt enrolled in the university
		});
		
		usyd.enrol(freya, aiai);
		usyd.complete(freya, aiai, 75);
		usyd.addSubject(qinqin);
		assertThrows(IllegalArgumentException.class, () -> {
			usyd.enrol(freya, qinqin); //freya cant enrol in a research subject without a supervisor
		});
		
		usyd.supervise(ladao, freya);
		assertEquals(ladao, freya.getSupervisor());
		usyd.enrol(freya, qinqin);
		
		//change freya's supervisor from ladao to maomao
		usyd.supervise(maomao, freya);
		assertEquals(maomao, freya.getSupervisor());
		assertTrue(ladao.getSupervisedStudents().isEmpty());
		
		usyd.enrol(geiQianRen);
		assertThrows(IllegalArgumentException.class, () -> {
			usyd.enrol(geiQianRen, qinqin); //geiQianRen cant enrol in a research subject without a supervisor
		});
		usyd.supervise(ladao, geiQianRen);
		usyd.enrol(geiQianRen, qinqin);
		
		assertThrows(IllegalStateException.class, () -> {
			usyd.teach(geiQianRen, qinqin); //geiQianRen can teach a research subject only if his supervisor teaches it
		});

		//switch geiQianRen's supervisor to maomao and teach all of maomao's subjects
		usyd.teach(maomao, qinqin);
		usyd.supervise(maomao, geiQianRen);
		usyd.teach(geiQianRen, qinqin);
		usyd.teach(geiQianRen, aiai);
		assertEquals(geiQianRen, qinqin.getTeacher());
		assertEquals(geiQianRen, aiai.getTeacher());
		assertTrue(maomao.getTaughtSubjects().isEmpty());
		assertTrue(ladao.getSupervisedStudents().isEmpty());
		
		// professors cant supervise more than 4 people
		MastersStudent a = new MastersStudent("a", Person.Gender.Female, 23);
		MastersStudent b = new MastersStudent("b", Person.Gender.Female, 23);
		MastersStudent c = new MastersStudent("c", Person.Gender.Female, 23);
		usyd.enrol(a);
		usyd.enrol(b);
		usyd.enrol(c);
		usyd.supervise(maomao, a);
		usyd.supervise(maomao, b);
		assertThrows(IllegalStateException.class, () -> {
			usyd.supervise(maomao, c);
		});
		
		// professors cant teach more than 3 subjects
		Subject cooking = new UndergradSubject("Cooking", "COOK0101", 6);
		Subject dancing = new PostgradSubject("Dancing", "DANC0101", 6);
		usyd.addSubject(cooking);
		usyd.addSubject(dancing);
		usyd.teach(maomao, cooking);
		usyd.teach(maomao, dancing);
		usyd.teach(maomao, qinqin);
		assertThrows(IllegalStateException.class, () -> {
			usyd.teach(maomao, aiai);
		});
		
	}
	
	@Test
	void testBias() {
		University usyd = new University("University of Sydney");
		Professor prof = new Professor("Mr Guy", Person.Gender.Male, 45);
		Subject stuff = new PostgradSubject("Stuff", "STUF1101", 6);
		usyd.addSubject(stuff);
		usyd.employ(prof);
		usyd.teach(prof, stuff);

		//example a
		prof.setBias((subject, student, grade) -> {
			if(student.getGender() == Gender.Female) {
				grade = grade + 1;
			}
			return Bias.restrictGrade(grade);
		});
		Student freya = new MastersStudent("Freya", Gender.Female, 23);
		usyd.enrol(freya, stuff);
		usyd.complete(freya, stuff, 80);
		assertEquals(81, freya.getGrade(stuff));
		
		//example b
		Subject mobile = new PostgradSubject("Mobile Computing", "INFO6000", 6);
		usyd.addSubject(mobile);
		prof.setBias((subject, student, grade) -> {
			if(student.currentlyStudies(mobile) && student.getGender() == Gender.Male) {
				grade += 1;
			}
			return Bias.restrictGrade(grade);
		});
		Student jackdon = new MastersStudent("Jackdon", Gender.Male, 24);
		usyd.enrol(jackdon, mobile);
		usyd.enrol(jackdon, stuff);
		usyd.complete(jackdon, stuff, 80);
		assertEquals(81, jackdon.getGrade(stuff));
		
		//example c
		prof.setBias((subject, student, grade) -> {
			if(subject.getStudents().size() < 10) {
				grade++;
			};
			return Bias.restrictGrade(grade);
		});
		Student dudu = new MastersStudent("Dudu", Gender.Male, 5);
		usyd.enrol(dudu, stuff);
		usyd.complete(dudu, stuff, 80);
		assertEquals(81, dudu.getGrade(stuff));
		
		//example d
		prof.setBias((subject, student, grade) -> {
			if(student instanceof MastersStudent || student instanceof PhdStudent) {
				++grade;
			};
			return Bias.restrictGrade(grade);
		});
		Student kuku = new MastersStudent("KuKu", Gender.Male, 5);
		usyd.enrol(kuku, stuff);
		usyd.complete(kuku, stuff, 80);
		assertEquals(81, kuku.getGrade(stuff));
		
		//example e
		prof.setBias((subject, student, grade) -> {
			try {
				if(student.getWAM() > 70) {
					grade = (int) (grade * 1.1);
				}
			} catch(IllegalStateException e) {}
			
			return Bias.restrictGrade(grade);
		});
		Student poopoo = new MastersStudent("Poo Poo", Gender.Male, 11115);
		usyd.enrol(poopoo, stuff);
		usyd.complete(poopoo, stuff, 80);
		assertEquals(80, poopoo.getGrade(stuff));
		prof.teach(mobile);
		usyd.enrol(poopoo, mobile);
		usyd.complete(poopoo, mobile, 80);
		assertEquals(88, poopoo.getGrade(mobile));
		
		//example f
		prof.setBias((subject, student, grade) -> {
			if(prof.getSupervisedStudents().contains(student)) {
				++grade;
			};
			return Bias.restrictGrade(grade);
		});
		MastersStudent baobao = new MastersStudent("Baobaooooo", Gender.Female, 52);
		usyd.enrol(baobao, stuff);
		usyd.supervise(prof, baobao);
		usyd.complete(baobao, stuff, 80);
		assertEquals(81, baobao.getGrade(stuff));
		
		//example g
		prof.setBias((subject, student, grade) -> {
			if(student.getAncestors().contains(prof)) {
				grade++;
			};
			return Bias.restrictGrade(grade);
		});
		Student jiaojiao = new MastersStudent("Jiaojiao", Gender.Female, 23);
		prof.setParentOf(jiaojiao);
		usyd.enrol(jiaojiao, stuff);
		usyd.complete(jiaojiao, stuff, 80);
		assertEquals(81, jiaojiao.getGrade(stuff));
		
		
		//example h
		prof.setBias(new Bias() {
			private int previousGrade = 0;
			
			@Override
			public int influenceGrade(Subject subject, Student student, int grade) {
				if(previousGrade > 70) {
					grade++;
				}
				previousGrade = grade;
				return Bias.restrictGrade(grade);
			}
		});
		Student shuishui = new MastersStudent("Shuishui", Gender.Female, 23);
		usyd.enrol(shuishui, stuff);
		usyd.complete(shuishui, stuff, 80);
		assertEquals(80, shuishui.getGrade(stuff));
		usyd.enrol(shuishui, mobile);
		usyd.complete(shuishui, mobile, 80);
		assertEquals(81, shuishui.getGrade(mobile));
		
		//example i
		prof.setBias(new Bias() {
			private ArrayDeque<Integer> previousGrades = new ArrayDeque<>(3);
			
			@Override
			public int influenceGrade(Subject subject, Student student, int grade) {
				
				if(!previousGrades.isEmpty() && 
					previousGrades.stream().mapToDouble(e -> e).average().getAsDouble() > 70) {
					grade++;
				}
				
				if(previousGrades.size() == 3) {
					previousGrades.removeLast();
				}
				previousGrades.addFirst(grade);
				
				return Bias.restrictGrade(grade);
			}
		});
		// grade 5 students; 5th student will get the bonus because the sequence of grades is 0, 80, 80, 80, 80
		Student tengteng1 = new MastersStudent("Tengteng 1", Gender.Female, 23);
		usyd.enrol(tengteng1, stuff);
		usyd.complete(tengteng1, stuff, 0);
		assertEquals(0, tengteng1.getGrade(stuff));
		Student tengteng2 = new MastersStudent("Tengteng 2", Gender.Female, 23);
		usyd.enrol(tengteng2, stuff);
		usyd.complete(tengteng2, stuff, 80);
		assertEquals(80, tengteng2.getGrade(stuff));
		Student tengteng3 = new MastersStudent("Tengteng 3", Gender.Female, 23);
		usyd.enrol(tengteng3, stuff);
		usyd.complete(tengteng3, stuff, 80);
		assertEquals(80, tengteng3.getGrade(stuff));
		Student tengteng4 = new MastersStudent("Tengteng 4", Gender.Female, 23);
		usyd.enrol(tengteng4, stuff);
		usyd.complete(tengteng4, stuff, 80);
		assertEquals(80, tengteng4.getGrade(stuff));
		Student tengteng5 = new MastersStudent("Tengteng 5", Gender.Female, 23);
		usyd.enrol(tengteng5, stuff);
		usyd.complete(tengteng5, stuff, 80);
		assertEquals(81, tengteng5.getGrade(stuff));
	}
	
	void testCallbacks() {
		University usyd = new University("University of Sydney");
		Student studentA = new BachelorsStudent("A", Person.Gender.Male, 1);
		Student studentB = new BachelorsStudent("B", Person.Gender.Male, 1);
		Professor profA = new Professor("A", Person.Gender.Male, 1);
		Professor profB = new Professor("B", Person.Gender.Male, 1);
		Subject subjA = new UndergradSubject("A", "A", 6);
		Subject subjB = new UndergradSubject("B", "B", 6);
		Subject subjC = new UndergradSubject("C", "C", 6);
		
		usyd.employ(profA);
		usyd.employ(profB);
		
		usyd.addSubject(subjA);
		usyd.addSubject(subjB);
		usyd.addSubject(subjC);
		
		usyd.teach(profA, subjA);
		usyd.teach(profA, subjB);
		usyd.teach(profB, subjC);
		
		usyd.enrol(studentA, subjA);
		usyd.enrol(studentA, subjC);
		usyd.enrol(studentA, subjB);
		usyd.enrol(studentB, subjA);
		usyd.enrol(studentB, subjB);
		usyd.enrol(studentB, subjC);
		
		usyd.complete(studentA, subjA, 100);
		usyd.complete(studentA, subjB, 0);
		usyd.complete(studentA, subjC, 30);
		usyd.complete(studentB, subjA, 90);
		usyd.complete(studentB, subjB, 50);
		usyd.complete(studentB, subjC, 30);
		
		assertEquals(studentB, usyd.getBestStudent());
		assertEquals(studentA, usyd.getWorstStudent());
		assertEquals(subjA, usyd.getEasiestSubject());
		assertEquals(subjB, usyd.getHardestSubject());
		assertEquals(profA, usyd.getMostLenientTeacher());
		assertEquals(profB, usyd.getStrictestTeacher());
	}
}
