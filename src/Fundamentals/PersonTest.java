	package Fundamentals;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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

}
