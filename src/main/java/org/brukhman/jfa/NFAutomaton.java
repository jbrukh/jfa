package org.brukhman.jfa;

import java.util.Collection;

/**
 * A non-deterministic finite automaton.
 * 
 * @author jbrukh
 *
 */
public class NFAutomaton implements Automaton {

	@Override
	public boolean compute(String input) {
		throw new IllegalAccessError();
	}

	@Override
	public Collection<State<?>> getFinalStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State<?> getInitialState() {
		// TODO Auto-generated method stub
		return null;
	}
}
