package org.brukhman.jfa.automaton;

import static com.google.common.base.Preconditions.*;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;

/**
 * Provides base functionality for adding initial and final states,
 * and keeping a list of states for the table.
 * <p>
 * 
 * @author jbrukh
 */
abstract class AbstractTable<T> implements Table {

	// FIELDS //
	
	protected State				initialState;
	protected Set<State>		finalStates;
	protected Set<State>		states;
	protected com.google.common.collect.Table<State,Character,T>
								table;

	/**
	 * Create a new instance.
	 */
	public AbstractTable() {
		finalStates = Sets.newHashSet();
		states 		= Sets.newHashSet();
		table		= HashBasedTable.create();
	}
	
	@Override
	public void makeInitial(State state) {
		checkNotNull(state, "Provide a state.");
		if ( initialState!=null ) {
			initialState.setInitial(false);
		}
		initialState = state;
		state.setInitial(true);
	}
	
	@Override
	public void clearInitial() {
		if ( initialState != null ) {
			initialState.setInitial(false);
			initialState = null;
		}
	}

	@Override
	public final State getInitial() {
		return initialState;
	}

	@Override
	public void makeFinal(State state) {
		checkNotNull(state, "Provide a state.");
		finalStates.add(state);
		state.setFinal(true);
	}
	
	@Override
	public void clearFinal(State state) {
		checkNotNull(state, "Provide a state.");
		finalStates.remove( state );
		state.setFinal(false);
	}

	@Override
	public ImmutableSet<State> getFinal() {
		return ImmutableSet.copyOf( finalStates );
	}

	@Override
	public void addStates(State... states) {
		// TODO: FIX THIS
		checkNotNull(states, "Provide states.");
		for ( State state : states ) {
			if ( state != null ) { this.states.add(state); }
		}
	}
	
	@Override
	public void addStates( Iterable<State> states ) {
		checkNotNull(states, "Provide states.");
		for ( State state : states ) {
			if ( state != null ) { this.states.add(state); }
		}
	}

	@Override
	public ImmutableSet<State> getStates() {
		return ImmutableSet.copyOf(states);
	}

	@Override
	public ImmutableSet<Character> getSymbols() {
		return ImmutableSet.copyOf( table.columnKeySet() );
	}
	
	/**
	 * Transition this table.
	 * 
	 * @param from
	 * @param symbol
	 * @return
	 */
	public abstract T transition( State from, Character symbol );
	
}
