package org.brukhman.jfa;

import static org.brukhman.jfa.Symbols.*;

import java.util.Set;

import junit.framework.TestCase;

public class StateMapTest extends TestCase {

	private final StateMap<NFAState> map 	= new StateMap<NFAState>();
	private final NFAState state0 			= new NFAState(0);
	private final NFAState state1 			= new NFAState(1);
	private final NFAState state2 			= new NFAState(2);
	private final NFAState state3			= new NFAState(3);
	private final NFAState state4 			= new NFAState(4);
	
	@Override
	public void setUp() {
		map.add(state0);
		map.add(state1);
		map.add(state2);
		map.add(state3);
		map.add(state4);
	
		state0.setInitial(true);
		state1.setFinal(true);
		state3.setFinal(true);
		
		map.add(state0, 'a', state1);
		map.add(state0, 'a', state4);
		map.add(state0, EPSILON, state2);
		map.add(state2, 'b', state1);
		map.add(state2, EPSILON, state3);
	}
	
	public void tearDown() {
		map.remove(state1);
		Set<NFAState> states = map.getStates();
		assertTrue( states != null && states.size() == 4);

		states = map.transition(state0, 'a');
		assertTrue( states != null && states.size() == 1);
		assertTrue( states.contains(state4) );
		
		map.clear();
		states = map.getStates();
		assertTrue(states!= null && states.size()==0);
		
	}
	
	public void testGetStates() {
		Set<NFAState> states = map.getStates();
		assertTrue(states.size()==5);
		assertTrue(states.contains(state0) && states.contains(state1) && states.contains(state2) 
				&& states.contains(state3) );
	}
	
	public void testEpsilonTransition() {
		Set<NFAState> states = map.epsilonTransition(state0);
		assertTrue(states.size()==3);
		assertTrue(states.contains(state0) && states.contains(state2) && states.contains(state3));
	}
	
	public void testTransition() {
		
		Set<NFAState> states = map.transition(state0, EPSILON);
		assertTrue(states.size()==3);
		assertTrue(states.contains(state0) && states.contains(state2) && states.contains(state3));
		
		states = map.transition(state0,'a');
		assertTrue( states.size()==2 );
		assertTrue( states.contains(state1) && states.contains(state4) );
		
	}
	
}
