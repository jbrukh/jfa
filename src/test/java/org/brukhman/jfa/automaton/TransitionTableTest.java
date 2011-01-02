package org.brukhman.jfa.automaton;

import com.google.common.collect.Sets;

import junit.framework.TestCase;

/**
 * Test the transition table.
 * 
 * @author jbrukh
 *
 */
public class TransitionTableTest extends TestCase {
	
	private final TransitionTable<State,Symbol<Character>> table =
											TransitionTable.create();
	
	private final State a = new State(1);
	private final State b = new State(2);
	private final State c = new State(3);
	private final State d = new State(4);
	
	private final Symbol<Character> A = Symbol.of('a');
	private final Symbol<Character> B = Symbol.of('b');
	

	public void setUp() {
	}

	public void testAddTransitions() {
		table.addTransition(a, A, b);
		table.addTransition(a, A, c);
		table.addTransition(a, A, d);
		table.addTransition(a, A, a);
		
		assertEquals( table.transition(a,A), Sets.newHashSet(a,b,c,d) );
		
	}
	
}
