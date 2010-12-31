package org.brukhman.jfa;

import java.util.Set;

import org.junit.Test;
import org.junit.experimental.theories.Theory;

import junit.framework.TestCase;

public class NFAutomatonTest extends TestCase {
	
	private final NFAutomaton machine = new NFAutomaton();
	private NFAState state0, state1, state2, state3;

	/**
	 * Create an automaton that accepts the language described by
	 * <code>(ab)?e*</code>.
	 */
	public void setUp() {
		state0 = machine.addState();
		state1 = machine.addState();
		state2 = machine.addState();
		state3 = machine.addState();
		
		machine.makeFinal(state2);
		machine.makeFinal(state3);
		machine.makeInitial(state0);
		
		machine.addTransition(state0,'a',state1);
		machine.addTransition(state1, 'b',state2);
		machine.addTransition(state2, 'e', state3);
		machine.addTransition(state3, 'e', state3);
		machine.addTransition(state0, Symbols.EPSILON, state3);
	}
	
	/**
	 * Test that this NFAutomaton accepts and rejects appropriate strings.
	 */
	public void testCompute() {
		assertTrue( machine.compute("") );
		assertTrue( machine.compute("ab") );
		assertTrue( machine.compute("abe") );
		assertFalse( machine.compute("jake") );
		assertTrue( machine.compute("eeee") );
		String input = "abe";
		for ( int i = 0; i < 10; i++ ) {
			input = input+"e";
			assertTrue(machine.compute(input));
		}
	}
	
	/**
	 * Test the the method {@link NFAutomaton#getFinalStates()}.
	 */
	public void testFinalStates() {
		Set<NFAState> states = machine.getFinalStates();
		assertTrue(states!=null && states.size()==2);
		assertTrue(states.contains(state2) && states.contains(state3) );
	}
	
	/**
	 * Test the method {@link NFAutomaton#getInitialState()}.
	 */
	public void testInitialState() {
		NFAState initialState = machine.getInitialState();
		assertNotNull(initialState);
		assertTrue(initialState.equals(state0));
	}
	
	/**
	 * Test the {@link NFAutomaton#makeInitial(NFAState)} method.
	 */
	public void testMakeInitial() {
		machine.clearInitialState();
		assertNull(machine.getInitialState());
		machine.makeInitial(state0);
	}
	
	/**
	 * Test what happens when you attempt to clear a non-final state.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testClearFinalNotFinal() {
		machine.clearFinalState(state0);
	}
	
	/**
	 * Test the clearing of a final state, and the 
	 * {@link NFAutomaton#makeFinal(NFAState)} method.
	 */
	public void testClearFinal() {
		machine.clearFinalState(state2);
		Set<NFAState> states = machine.getFinalStates();
		assertTrue(states!=null && states.size()==1);
		assertTrue(states.contains(state3));
		assertFalse(state2.isFinal());
		machine.makeFinal(state2);
		states = machine.getFinalStates();
		assertTrue(states!=null && states.size()==2);
		assertTrue(states.contains(state3) && states.contains(state2));
		assertTrue(state2.isFinal());
	}
}
