package org.brukhman.jfa.automaton.atoms;

import static org.junit.Assert.*;

import org.brukhman.jfa.automaton.NFA;
import org.junit.Test;

public class AtomsTest {

	@Test
	public void testSingleSymbolNFA() {
		NFA nfa = Atoms.singleSymbolNFA('a');	
		assertNotNull(nfa);
		assertTrue(nfa.getStates().size()==2);
		assertTrue(nfa.compute("a"));
		assertFalse(nfa.compute(""));
		assertFalse(nfa.compute("b"));
		assertFalse(nfa.compute("aa"));
		assertFalse(nfa.compute("jake"));
		assertFalse(nfa.compute("hello"));
		assertFalse(nfa.compute("doggie"));
	}
	
	@Test
	public void testSingleWordNFA() {
		NFA nfa = Atoms.singleWordNFA("doggie");
		assertNotNull(nfa);
		assertTrue(nfa.compute("doggie"));
		assertFalse(nfa.compute(""));
		assertFalse(nfa.compute("jake"));
		assertFalse(nfa.compute("book"));
	}
	
	
	@Test
	public void testEpsilonNFA() {
		NFA nfa = Atoms.epsilonNFA;
		assertNotNull(nfa);
		assertTrue(nfa.getStates().size()==1);
		assertTrue(nfa.compute(""));
		assertFalse(nfa.compute("a"));
		assertFalse(nfa.compute("b"));
		assertFalse(nfa.compute("aa"));
		assertFalse(nfa.compute("jake"));
		assertFalse(nfa.compute("hello"));
		assertFalse(nfa.compute("doggie"));
	}

}
