package org.brukhman.jfa.automaton;

import java.util.Set;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

/**
 * 
 * @author jbrukh
 *
 * @param <State>
 * @param <Character>
 */
public abstract class TableAutomaton implements ConstructibleAutomaton {

	// FIELDS //
	protected final TransitionTable transitions;
	
	protected Set<State>			states;
	protected Set<Character>		symbols;
	
	protected State					initialState;
	protected Set<State>			finalStates;

	/**
	 * Create a new instance.
	 * 
	 */
	public TableAutomaton() {
		transitions = new TransitionTable();
		states 		= Sets.newHashSet();
		symbols 	= Sets.newHashSet();
		finalStates = Sets.newHashSet();
	}

	/**
	 * Return this transition table.
	 * 
	 * @return
	 */
	final TransitionTable getTable() {
		return transitions;
	}
	
	@Override
	public Automaton addState(State state) {
		Preconditions.checkNotNull(state, "Provide a state.");
		states.add(state);
		return this;
	}

	@Override
	public void removeState(State state) {
		// not clear whether this functionality is necessary
		throw new UnsupportedOperationException();
	}

	@Override
	public ImmutableSet<State> getStates() {
		return ImmutableSet.copyOf( states );
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
		Preconditions.checkState( transitions.containsState(state), 
				"State must exist in the machine." );
		finalStates.remove( state );
		state.setFinal(false);
	}

	@Override
	public ImmutableSet<State> getFinal() {
		return ImmutableSet.copyOf( finalStates );
	}

	@Override
	public ImmutableSet<Character> getSymbols() {
		return ImmutableSet.copyOf( symbols );
	}
	
	@Override
	public void addTransition( State from, Character symbol, State to ) {
		transitions.addTransition(from, symbol, to);
	}

	@Override
	public ImmutableAutomaton finish() {
		return new ImmutableAutomaton(this);
	}
	
}
