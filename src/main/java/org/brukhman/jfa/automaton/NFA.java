package org.brukhman.jfa.automaton;

import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Preconditions;
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
	
	/**
	 * Create a new instance.
	 * 
	 * @param states
	 */
	public NFA( Iterator<State> states ) {
		if ( states != null ) {
			Iterators.addAll(this.states, states);
		}
	}
	
	/**
	 * Create a new instance.
	 * 
	 * @param states
	 */
	public NFA( State... states ) {
		this( Iterators.forArray(states) );
	}
	

	@Override
	public boolean compute(String input) {
		Preconditions.checkNotNull(input);
		validate();
		
		char[] inputArray = input.toCharArray();
		NondeterministicTransitionTableTraverser traverser = new NondeterministicTransitionTableTraverser(transitions);
		
		Set<State> currentStates = traverser.epsilonClosureInitial();

		for ( char inputChar : inputArray ) {
			currentStates = traverser.transition(currentStates, inputChar);
		}
		return Iterables.any( currentStates, State.isFinalPredicate );
	}

	@Override
	public ImmutableAutomaton finish() {
		validate();
		return super.finish();
	}
	
	/**
	 * Make sure this machine is valid.
	 */
	private final void validate() {
		Preconditions.checkState( transitions.getInitial() != null, "There is no initial state in the machine.");
		Preconditions.checkState( !transitions.getFinal().isEmpty(), "No final states -- this machine accepts no language.");
		Preconditions.checkState(
				states.containsAll(transitions.getStates()),
				"Looks like you forgot to add some transitions."
		);
	}

}
