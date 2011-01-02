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
 * @param <StateType>
 * @param <SymbolType>
 */
public abstract class TableAutomaton
			 <StateType extends GenericState<?>, 
			  SymbolType extends Symbol<?>> implements ConstructibleAutomaton<StateType, SymbolType>{

	// FIELDS //
	protected final TransitionTable<StateType, SymbolType> 	transitions;
	
	protected Set<StateType>								states;
	protected Set<SymbolType>								symbols;
	
	protected StateType 									initialState;
	protected Set<StateType>								finalStates;

	/**
	 * Create a new instance.
	 * 
	 */
	public TableAutomaton() {
		transitions = TransitionTable.create();
		states 		= Sets.newHashSet();
		symbols 	= Sets.newHashSet();
	}

	@Override
	public Automaton addState(StateType state) {
		Preconditions.checkNotNull(state, "Provide a state.");
		states.add(state);
		return this;
	}

	@Override
	public void removeState(StateType state) {
		// not clear whether this functionality is necessary
		throw new UnsupportedOperationException();
	}

	@Override
	public ImmutableSet<StateType> getStates() {
		return ImmutableSet.copyOf( states );
	}

	@Override
	public void makeInitial(StateType state) {
		Preconditions.checkNotNull(state, "Provide a state.");
		initialState = state;
		state.setFinal(true);
	}

	@Override
	public final StateType getInitial() {
		return initialState;
	}

	@Override
	public void makeFinal(StateType state) {
		Preconditions.checkNotNull(state, "Provide a state.");
		finalStates.add(state);
		state.setFinal(true);
	}
	
	@Override
	public void clearFinal(StateType state) {
		Preconditions.checkNotNull(state, "Provide a state.");
		Preconditions.checkState( transitions.containsState(state), 
				"State must exist in the machine." );
		finalStates.remove( state );
		state.setFinal(false);
	}

	@Override
	public ImmutableSet<StateType> getFinal() {
		return ImmutableSet.copyOf( finalStates );
	}

	@Override
	public ImmutableSet<SymbolType> getSymbols() {
		return ImmutableSet.copyOf( symbols );
	}

	@Override
	public ImmutableAutomaton finish() {
		return new ImmutableAutomaton(this);
	}
	
}
