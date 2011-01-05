package org.brukhman.jfa.automaton;

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.collect.Table.Cell;


public final class Operations {

	/**
	 * Creates a new NFA which accepts either of the languages
	 * accepted by the two input NFAs.
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public final static NFA or( NFA one, NFA two ){
		Set<State> oneStates = one.getStates();
		Set<State> twoStates = two.getStates();
		
		Preconditions.checkState( 
				Sets.intersection(oneStates, twoStates).isEmpty(),
				"The two NFA's share states!"
				);
		
		Set<State> allStates = Sets.union(oneStates, twoStates);
		NFA or = new NFA(allStates);
			
		or.getTable().putAll(one.getTable());
		or.getTable().putAll(two.getTable());

		or.finalStates.addAll(one.finalStates);
		or.finalStates.addAll(two.finalStates);
		
		State start = State.next();
		or.addStates(start);
		or.addTransition(start, Symbols.EPSILON, one.getInitial() );
		or.addTransition(start, Symbols.EPSILON, two.getInitial() );
		or.makeInitial(start);
		
		return or;
	}
	
	/**
	 * Create an NFA that accepts the language concatenations of the
	 * the two input NFAs.
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public final static NFA concat( NFA one, NFA two ) {
		Set<State> oneStates = one.getStates();
		Set<State> twoStates = two.getStates();
		
		Preconditions.checkState( 
				Sets.intersection(oneStates, twoStates).isEmpty(),
				"The two NFA's share states!"
				);
		
		Set<State> allStates = Sets.union(oneStates, twoStates);
		NFA newNFA = new NFA(allStates);
			
		newNFA.getTable().putAll(one.getTable());
		newNFA.getTable().putAll(two.getTable());
		newNFA.makeInitial( one.initialState );
		newNFA.finalStates.addAll(two.getFinal());
		
		for ( State finalState : one.getFinal() ) {
			newNFA.addTransition(finalState, Symbols.EPSILON, two.initialState);
		}
		
		return newNFA;
	}
	
	/**
	 * Given an NFA, returns the Kleene closure of that NFA.
	 * 
	 * @param nfa
	 * @return
	 */
	public final static NFA kleeneStar( NFA nfa ) {
		Preconditions.checkNotNull(nfa);
		NFA newNFA = new NFA(nfa.getStates());
		newNFA.getTable().putAll(nfa.getTable());
		newNFA.finalStates.addAll(nfa.finalStates);
		newNFA.finalStates.add(nfa.initialState);  // accept empty string
		newNFA.makeInitial(nfa.initialState);
		
		for ( State finalState : nfa.finalStates ) {
			newNFA.addTransition(finalState, Symbols.EPSILON, nfa.initialState);
		}
		
		return newNFA;
	}
}
