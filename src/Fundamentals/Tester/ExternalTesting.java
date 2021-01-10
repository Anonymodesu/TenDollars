package Fundamentals.Tester;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Fundamentals.BachelorsStudent;
import Fundamentals.MastersStudent;
import Fundamentals.Person;
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
	}
}
