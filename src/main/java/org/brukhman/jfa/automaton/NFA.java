package org.brukhman.jfa.automaton;

import static com.google.common.base.Preconditions.*;

import java.util.Set;

import com.google.common.collect.Iterables;

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
public final class NFA extends NondeterministicTable implements ConstructibleAutomaton {

	/**
	 * Create a new instance.
	 * 
	 * @param states
	 */
	public NFA( State... states ) {
		addStates(states);
	}

	@Override
	public final boolean compute( String input ) {
		checkNotNull(input, "Provide some input.");
		validate();

		char[] inputArray = input.toCharArray();
		NondeterministicTraverser traverser = traverser();
		
		Set<State> currentStates = traverser.epsilonClosureInitial();

		for ( char inputChar : inputArray ) {
			currentStates = traverser.transition(currentStates, inputChar);
		}
		return Iterables.any( currentStates, State.isFinalPredicate );
	}


	/**
	 * Make sure this machine is valid.
	 */
	private final void validate() {
		checkState( getInitial() != null, "There is no initial state in the machine.");
		checkState( !getFinal().isEmpty(), "No final states -- this machine accepts no language.");
		checkState(
				states.containsAll(table.rowKeySet()),
				"Looks like you forgot to add some transitions."
		);
	}

	public final void merge( NFA other ) {
		this.table.putAll(other.table);
		other.clearInitial();//whaaa
		
	}

}
