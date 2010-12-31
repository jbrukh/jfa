package org.brukhman.jfa;

import junit.framework.TestCase;

public class NFAutomatonTest extends TestCase {
	
	private final NFAutomaton machine = new NFAutomaton();

	public void setUp() {
		NFAState state0 = machine.addState();
		NFAState state1 = machine.addState();
		NFAState state2 = machine.addState();
		NFAState state3 = machine.addState();
		
		machine.makeFinal(state2);
		machine.makeFinal(state3);
		machine.makeInitial(state0);
		
		machine.addTransition(state0,'a',state1);
		machine.addTransition(state1, 'b',state2);
		machine.addTransition(state2, 'e', state3);
		machine.addTransition(state3, 'e', state3);
	}
	
	public void testCompute() {
		assertFalse( machine.compute("") );
		assertTrue( machine.compute("ab") );
		assertTrue( machine.compute("abe") );
		assertFalse( machine.compute("jake") );
		
		String input = "abe";
		for ( int i = 0; i < 10; i++ ) {
			input = input+"e";
			assertTrue(machine.compute(input));
		}
	}
}
