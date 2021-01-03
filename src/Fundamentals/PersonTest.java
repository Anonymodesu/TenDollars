	package Fundamentals;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class PersonTest {
	
	private <T> void assertSetEquals(Collection<T> expected, Collection<T> actual) {
		assertEquals(new HashSet<T>(expected), new HashSet<T>(actual));
	}

	@Test
	void testBasePerson() {
		Person jackdon = new Person("Jackdon", Person.Gender.Male);
		Person freya = new Person("Freya", Person.Gender.Female);
		Person ten = new Person("TenDollars", Person.Gender.Male);
		Person dudu = new Person("Dudu", Person.Gender.Male);
		Person jack = new Person("Jack", Person.Gender.Male);
		Person aurora = new Person("Aurora", Person.Gender.Female);
		Person aiai = new Person("Aiai", Person.Gender.Female);
		Person qinqin = new Person("Qinqin", Person.Gender.Female);
		
		jackdon.setParentOf(ten);
		freya.setParentOf(ten);
		jackdon.setParentOf(dudu);
		freya.setParentOf(dudu);
		
		ten.setParentOf(jack);
		jack.setParentOf(aurora);
		aiai.setParentOf(aurora);
		
		dudu.setParentOf(qinqin);
		
		assertThrows(IllegalArgumentException.class, () -> {
			dudu.setParentOf(qinqin);
		});
		
		assertSetEquals(Arrays.asList(jack, aiai, ten, jackdon, freya), aurora.getAncestors());
		assertSetEquals(Arrays.asList(ten, jackdon, freya), jack.getAncestors());
		assertSetEquals(Arrays.asList(jackdon, freya), dudu.getAncestors());
		assertSetEquals(Arrays.asList(), freya.getAncestors());
		
		assertSetEquals(Arrays.asList(), jackdon.getSiblings());
		assertSetEquals(Arrays.asList(ten), dudu.getSiblings());
		assertSetEquals(Arrays.asList(), aurora.getSiblings());
		
		assertSetEquals(Arrays.asList(ten, dudu, jack, aurora, qinqin), jackdon.getDescendants());
		assertSetEquals(Arrays.asList(aurora), aiai.getDescendants());
		assertSetEquals(Arrays.asList(), qinqin.getDescendants());

		assertSetEquals(Arrays.asList(jackdon, freya, ten, dudu, jack, aurora, qinqin, aiai), jackdon.getFamilyTree());
		assertSetEquals(Arrays.asList(jackdon, freya, ten, dudu, jack, aurora, qinqin, aiai), dudu.getFamilyTree());

	}
	
	@Test
	void testStudent() {
		Subject singing = new Subject("Singing", "SING1011", 1);
		Subject dancing = new Subject("Dancing", "DANC1011", 2);
		Subject playing = new Subject("Playing Video Games", "PLAY1011", 3);
		Subject watching = new Subject("Watching Stupid Videos", "WACH1011", 4);
		Subject cooking = new Subject("Cooking", "COOK1011", 6);
		
		Student dudu = new Student("Dudu", Person.Gender.Male, 3);
		dudu.enrol(singing);
		
		assertThrows(IllegalStateException.class, () -> {
			dudu.enrol(singing); // dudu is already enrolled in singing
		});
		
		assertThrows(IllegalStateException.class, () -> {
			dudu.complete(dancing, 100); // dudu isnt enrolled in dancing
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			dudu.complete(singing, 101); // grade must be between 0 and 100
		});
		
		assertThrows(IllegalStateException.class, () -> {
			dudu.getWAM(); // dudu hasn't completed any subjects
		});
		
		dudu.enrol(dancing);
		dudu.enrol(playing);
		dudu.enrol(watching);
		dudu.enrol(cooking);
		
		dudu.complete(singing, 100);
		dudu.complete(dancing, 90);
		dudu.complete(playing, 80);
		dudu.complete(watching, 70);
		dudu.complete(cooking, 60);
		
		assertEquals(dudu.getWAM(), 72.5);
	}
	
	@Test
	void testSubject() {
		Subject momo = new Subject("Momo stuff", "MOMO9999", 50);
		Subject chacha = new Subject("Chacha stuff", "CHCH9999", 50);
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Subject("FOobar", "rjeit3495", 0); //credit points must be at least 1
		});
		
		Student jackdon = new Student("Jackdon", Person.Gender.Male, 24);
		Student freya = new Student("Freya", Person.Gender.Female, 23);
		Student aiai = new Student("Jackdon", Person.Gender.Female, 25);
		
		jackdon.enrol(momo);
		freya.enrol(momo);
		freya.enrol(chacha);
		aiai.enrol(chacha);
		
		assertThrows(IllegalStateException.class, () -> {
			momo.getAverageGrade(); // momo has no students that have completed the subject
		});
		
		assertSetEquals(Arrays.asList(jackdon), freya.getClassmates(momo));
		assertSetEquals(Arrays.asList(aiai), freya.getClassmates(chacha));
		assertSetEquals(Arrays.asList(aiai), freya.getClassmates(chacha));
		assertSetEquals(Arrays.asList(aiai, jackdon), freya.getClassmates());
		
		jackdon.complete(momo, 80);
		freya.complete(momo, 90);
		freya.complete(chacha, 90);
		aiai.complete(chacha, 100);
		
		assertEquals(85, momo.getAverageGrade());
		assertEquals(95, chacha.getAverageGrade());
	}
	
	@Test
	void testUniversity() {
		University usyd = new University("University of Sydney");
		Subject momo = new Subject("Momo stuff", "MOMO9999", 50);
		Subject chacha = new Subject("Chacha stuff", "CHCH9999", 50);
		Student jackdon = new Student("Jackdon", Person.Gender.Male, 24);
		Student freya = new Student("Freya", Person.Gender.Female, 23);
		
		usyd.addSubject(momo);
		usyd.addSubject(chacha);
		
		usyd.enrol(jackdon);
		usyd.enrol(freya);
		usyd.enrol(freya); //nothing happens
		
		usyd.enrol(jackdon, chacha);
		usyd.enrol(freya, momo);
		usyd.enrol(freya, chacha);
		usyd.enrol(freya, chacha); //nothing happens
		
		Subject peel = new Subject("Peel peel", "PEEL0101", 6);
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
			usyd.complete(freya, momo, 60); // freya is no longer studying momo
		});
	}

}
