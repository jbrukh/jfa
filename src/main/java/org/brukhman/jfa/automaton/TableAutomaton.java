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
	protected final HashBasedTransitionTable transitions;
	
	protected Set<State>			states;
	protected Set<Character>		symbols;
	
	/**
	 * Create a new instance.
	 * 
	 */
	public TableAutomaton() {
		transitions = new HashBasedTransitionTable();
		states 		= Sets.newHashSet();
		symbols 	= Sets.newHashSet();
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
		transitions.makeInitial(state);	
	}

	@Override
	public final State getInitial() {
		return transitions.getInitial();
	}

	@Override
	public void makeFinal(State state) {
		transitions.makeFinal(state);
	}
	
	@Override
	public void clearFinal(State state) {
		transitions.clearFinal(state);
	}

	@Override
	public ImmutableSet<State> getFinal() {
		return transitions.getFinal();
	}

	@Override
	public ImmutableSet<Character> getSymbols() {
		return ImmutableSet.copyOf( symbols );
	}
	
	@Override
	public void addTransition( State from, Character symbol, State to ) {
		Preconditions.checkNotNull(from);
		Preconditions.checkNotNull(to);
		Preconditions.checkNotNull(symbol);
		Preconditions.checkState( states.contains(from) && states.contains(to),
				"Either from or to state is not in the machine.");		
		transitions.addTransition(from, symbol, to);
	}

	@Override
	public ImmutableAutomaton finish() {
		return new ImmutableAutomaton(this);
	}
		
}
