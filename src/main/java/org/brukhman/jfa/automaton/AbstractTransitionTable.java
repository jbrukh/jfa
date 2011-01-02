package org.brukhman.jfa.automaton;

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Provides base functionality for adding initial and final states.
 * 
 * @author jbrukh
 *
 */
public abstract class AbstractTransitionTable implements TransitionTable {

	// FIELDS //
	
	protected State			initialState;
	protected Set<State>	finalStates;

	/**
	 * Create a new instance.
	 */
	public AbstractTransitionTable() {
		finalStates = Sets.newHashSet();	
	}

	@Override
	public void makeInitial(State state) {
		Preconditions.checkNotNull(state, "Provide a state.");
		initialState = state;
		state.setInitial(true);
	}

	@Override
	public final State getInitial() {
		return initialState;
	}

	@Override
	public void makeFinal(State state) {
		Preconditions.checkNotNull(state, "Provide a state.");
		finalStates.add(state);
		state.setFinal(true);
	}
	
	@Override
	public void clearFinal(State state) {
		Preconditions.checkNotNull(state, "Provide a state.");
		finalStates.remove( state );
		state.setFinal(false);
	}

	@Override
	public ImmutableSet<State> getFinal() {
		return ImmutableSet.copyOf( finalStates );
	}
}
