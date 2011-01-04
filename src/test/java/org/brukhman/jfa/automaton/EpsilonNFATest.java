package org.brukhman.jfa.automaton;

import org.brukhman.jfa.automaton.atoms.Atoms;

import junit.framework.TestCase;


public class EpsilonNFATest extends TestCase {
	
	/**
	 * Test the epsilonNFA.
	 */
	public void testEpsilonNFA() {
		NFA nfa = Atoms.epsilonNFA;
		assertTrue( nfa.compute("") );
		assertFalse( nfa.compute("a") );
		assertFalse( nfa.compute("aa") );
		assertFalse( nfa.compute("aaa") );
		assertFalse( nfa.compute("aaaa") );
		
	}

}
