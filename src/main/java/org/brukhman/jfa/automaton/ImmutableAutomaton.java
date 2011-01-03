package org.brukhman.jfa.automaton;

import com.google.common.base.Preconditions;

/**
 * An automaton that the only thing that you can do is compute with.
 * 
 * @author jbrukh
 *
 */
public final class ImmutableAutomaton implements Automaton {
	
	// FIELDS //
	
	private final Automaton automaton;
	
	/**
	 * Create a new instance.
	 * 
	 * @param automaton
	 */
	public ImmutableAutomaton( Automaton automaton ) {
		Preconditions.checkNotNull(automaton, "Provide an automaton.");
		this.automaton = automaton;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public boolean compute(String input) {
		return automaton.compute(input);
	}
}
