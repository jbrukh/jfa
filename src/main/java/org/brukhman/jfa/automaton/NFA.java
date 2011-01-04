package org.brukhman.jfa.automaton;

import static com.google.common.base.Preconditions.*;

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
		checkNotNull(states, "Provide states.");
		for ( State state : states ) {
			addState(state);
		}
	}
	
	@Override
	public boolean compute(String input) {
		
	}

}
