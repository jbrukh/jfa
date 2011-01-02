package org.brukhman.jfa.automaton;

import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

/**
 * A Nondeterministic Finite Automaton.
 * <p>
 * Since a DFA is a more general case of an NFA, this class can represent DFAs as well, however,
 * not necessarily in the most computationally efficient way.
 * <p>
 * An NFA consists of a set of states, one of which is an initial state and some or none of which
 * are final states.  Additionally, states may be transitioned given an input symbol.  
 * 
 * @author jbrukh
 *
 */
public final class NFA extends TableAutomaton {

	// FIELDS //
	
	/** Predicate for finding final states. */
	private final static Predicate<GenericState<?>> isFinalPredicate = new Predicate<GenericState<?>>() {
		@Override
		public boolean apply(GenericState<?> state) {
			return state.isFinal();
		}
	};
	

	/**
	 * Create a new instance.
	 * 
	 * @param states
	 */
	private NFA( Iterator<State> states ) {
		if ( states != null ) {
			Iterators.addAll(this.states, states);
		}
	}
	
	/**
	 * Create a new instance.
	 * 
	 * @param states
	 */
	private NFA( State... states ) {
		this( Iterators.forArray(states) );
	}
	

	@Override
	public boolean compute(String input) {
		Preconditions.checkNotNull(input);
		
		char[] inputArray = input.toCharArray();
		TransitionTableTraverser traverser = new TransitionTableTraverser(transitions);
		
		Set<State> currentStates = traverser.epsilonClosure(initialState);

		for ( char inputChar : inputArray ) {
			currentStates = traverser.transition(currentStates, inputChar);
		}
		return Iterables.any(currentStates, isFinalPredicate);
	}

}
