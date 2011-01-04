package org.brukhman.jfa.automaton;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import junit.framework.TestCase;


public class NondeterministicTableTest extends TestCase {
	
	private NondeterministicTable table;
	private State a,b,c,d;
	
	public void setUp() {
		table = NondeterministicTable.create();
		assertNotNull("Could not instantiate the table.", table);
		
		a = State.next();
		b = State.next();
		c = State.next();
		d = State.next();
		
		ImmutableSet<State> expectedStates = ImmutableSet.of(a,b,c,d);
		ImmutableSet<Character> expectedSymbols = ImmutableSet.of('a','b','c','d');
		
		assertNotNull(a);
		assertNotNull(b);
		assertNotNull(c);
		assertNotNull(d);
		
		table.addStates(a,b,c,d);
		assertEquals(table.getStates(), expectedStates );
		
		table.addTransition(a, 'a', b);
		table.addTransition(b, 'b', c);
		table.addTransition(c, 'c', d);
		table.addTransition(d, 'd', a);
		assertEquals( table.getSymbols(), expectedSymbols );
	
		table.makeInitial(a);
		table.makeFinal(d);
	
		assertEquals(a, table.getInitial());
		assertEquals( ImmutableSet.of(d), table.getFinal() );

		ensureStateConsistency();
	}
	
	/**
	 * Ensures that the internal bookkeeping of final and initial states
	 * corresponds to those states' settings.
	 * 
	 */
	private void ensureStateConsistency() {
		Set<State> finalStates = Sets.filter(table.getStates(), State.isFinalPredicate );
		assertEquals( table.getFinal(), finalStates );
		Set<State> initialStates = Sets.filter(table.getStates(), State.isInitialPredicate );
		assertEquals( ImmutableSet.of(table.getInitial()), initialStates );
	}
	
	public void testClearFinal() {
		table.clearFinal(d);
		
		assertEquals( table.getFinal(), ImmutableSet.of() );
		ensureStateConsistency();
		
		table.makeFinal(d);
		assertEquals( table.getFinal(), ImmutableSet.of(d) );
		ensureStateConsistency();
	}
	
	public void testInitial() {
		table.makeInitial(b);
		assertEquals(table.getInitial(), b);
		ensureStateConsistency();
		
		table.makeInitial(a);
		assertEquals(table.getInitial(), a);
		ensureStateConsistency();
	}
	
	public void testTransitions() {
		assertEquals( table.transition(a, 'a'), ImmutableSet.of(b));
		assertEquals( table.transition(b, 'b'), ImmutableSet.of(c));
		assertEquals( table.transition(c, 'c'), ImmutableSet.of(d));
		assertEquals( table.transition(d, 'd'), ImmutableSet.of(a));
		
		assertEquals( table.transition(a, 'c'), ImmutableSet.of());
	}
	

}
