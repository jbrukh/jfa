package org.brukhman.jfa.automaton;

import static org.junit.Assert.*;

import org.brukhman.jfa.automaton.atoms.Atoms;
import org.junit.Test;

public class OperationsTest {

	private final static NFA one = Atoms.singleSymbolNFA('a');
	private final static NFA two = Atoms.singleSymbolNFA('b');
	
	
	@Test
	public void testOr() {
		NFA op = Operations.or(one, two);
		assertNotNull(op);
		assertFalse(op.compute(""));
		assertTrue(op.compute("a"));
		assertTrue(op.compute("b"));
		assertFalse(op.compute("ab"));
		assertFalse(op.compute("ba"));
		assertFalse(op.compute("poop"));
		assertFalse(op.compute("hello"));
		assertFalse(op.compute("doggie"));
	}

	@Test
	public void testConcat() {
		NFA op = Operations.concat(one, two);
		assertNotNull(op);
		assertFalse(op.compute(""));
		assertTrue(op.compute("ab"));
		assertFalse(op.compute("b"));
		assertFalse(op.compute("ba"));
		assertFalse(op.compute("poop"));
		assertFalse(op.compute("hello"));
		assertFalse(op.compute("doggie"));
		
	}

	@Test
	public void testKleeneStar() {
		NFA op = Operations.kleeneStar(one);
		assertNotNull(op);
		assertTrue(op.compute(""));
		assertTrue(op.compute("a"));
		assertTrue(op.compute("aa"));
		assertTrue(op.compute("aaa"));
		assertTrue(op.compute("aaaa"));
		assertTrue(op.compute("aaaaa"));
		assertTrue(op.compute("aaaaaa"));
		assertTrue(op.compute("aaaaaaa"));
		assertTrue(op.compute("aaaaaaaa"));
		assertFalse(op.compute("ab"));
		assertFalse(op.compute("b"));
		assertFalse(op.compute("ajke"));
	}

	@Test
	public void testComplicatedExpression() {
		// we will test the expression (dog|book)*
		NFA dogOrBook = Operations.or(
								Atoms.singleWordNFA("book"),
								Atoms.singleWordNFA("dog")
								);
		NFA dogOrBookStar = Operations.kleeneStar(dogOrBook);
		
		assertNotNull(dogOrBookStar);
		assertTrue(dogOrBookStar.compute(""));
		assertTrue(dogOrBookStar.compute("dog"));
		assertTrue(dogOrBookStar.compute("book"));
		assertTrue(dogOrBookStar.compute("dogbook"));
		assertTrue(dogOrBookStar.compute("bookdog"));
		assertTrue(dogOrBookStar.compute("dogdog"));
		assertTrue(dogOrBookStar.compute("dogdogdog"));
		assertTrue(dogOrBookStar.compute("bookbook"));
		assertTrue(dogOrBookStar.compute("bookbookbook"));
		assertTrue(dogOrBookStar.compute("dogbookdogbook"));
		assertTrue(dogOrBookStar.compute("dogdogbookdogdogbook"));
		assertTrue(dogOrBookStar.compute("bookbookdogbookbookbook"));
		assertFalse(dogOrBookStar.compute("haha"));
		assertFalse(dogOrBookStar.compute("d"));
		assertFalse(dogOrBookStar.compute("do"));
		assertFalse(dogOrBookStar.compute("b"));
		assertFalse(dogOrBookStar.compute("bo"));
		assertFalse(dogOrBookStar.compute("boo"));
		assertFalse(dogOrBookStar.compute("books"));
		assertFalse(dogOrBookStar.compute("hello"));
		assertFalse(dogOrBookStar.compute("doggie"));
		assertFalse(dogOrBookStar.compute("bookie"));
		assertFalse(dogOrBookStar.compute("jake"));
		
	}
}
