package org.brukhman.jfa;

import junit.framework.TestCase;

public class StateTest extends TestCase {
	
	public void testEqualsHashcode() {
		State<Integer> state1 = new State<Integer>(0);
		State<Integer> state2 = new State<Integer>(0);
		State<Integer> state3 = new State<Integer>(100);
		
		assertTrue(state1.getName() == 0 );
		assertTrue( state1.equals(state2)  );
		assertTrue( state2.equals(state1)  );
		assertTrue( state1.hashCode() == state2.hashCode() );
		assertFalse( state3.equals(state1));
		assertFalse( state3.equals(state2));
		assertTrue( state3.equals(state3));
		assertFalse( state3.hashCode() == state1.hashCode() );
		assertFalse( state3.hashCode() == state2.hashCode() );
	}
	
	public void testInitialFinal() {
		State<Integer> state1 = new State<Integer>(0);
		state1.setFinal(true);
		assertTrue( state1.isFinal() );
		state1.setFinal(false);
		assertFalse( state1.isFinal() );
		state1.setInitial(true);
		assertTrue(state1.isInitial());
		state1.setInitial(false);
		assertFalse(state1.isInitial());
		state1.setFinal(true);
		state1.setInitial(true);
		assertTrue( state1.isFinal() && state1.isInitial() );
	}

}
