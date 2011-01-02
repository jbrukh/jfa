package org.brukhman.jfa.automaton;

import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

/**
 * A transition table for an NFA.  This means each
 * @author jbrukh
 *
 */
public final class TransitionTable {

	// FIELDS //
	
	private final Table<State, Character, Set<State>> transitions;
	
	/**
	 * Create a new instance, privately.
	 */
	public TransitionTable() {
		transitions = HashBasedTable.create();
	}

	/**
	 * Returns {@code true} if and only if the table contains the specified state.
	 * 
	 * @param state
	 * @return
	 */
	public final boolean containsState( State state ) {
		return transitions.rowKeySet().contains(state);
	}
	
	/**
	 * Returns {@code true} if and only if the table contains the specified state.
	 * 
	 * @param symbol
	 * @return
	 */
	public final boolean containsSymbol( Character symbol ) {
		return transitions.columnKeySet().contains(symbol);
	}
	
	/**
	 * Add a transition.
	 * 
	 * @param fromState
	 * @param symbol
	 * @param toState
	 * @return
	 */
	public final void addTransition( State fromState, Character symbol, State toState ) {
		Set<State> toStates = transitions.get(fromState, symbol);
		if ( toStates == null ) {
			toStates = Sets.newHashSet();
			transitions.put(fromState,symbol,toStates);
		}
		toStates.add(toState);
	}
	
	/**
	 * Get a transition.
	 * 
	 * @param fromState
	 * @param symbol
	 * @return
	 */
	public final ImmutableSet<State> transition( State fromState, Character symbol ) {
		Set<State> toStates = transitions.get(fromState, symbol);
		if ( toStates == null ) {
			return ImmutableSet.of();
		}
		return ImmutableSet.copyOf(toStates);
	}

	/**
	 * Get the states of the table.
	 * 
	 * @return
	 */
	public final ImmutableSet<State> getStates() {
		return ImmutableSet.copyOf( transitions.rowKeySet() );
	}
	
	/**
	 * Get the symbols of the table.
	 * 
	 * @return
	 */
	public final ImmutableSet<Character> getSymbols() {
		return ImmutableSet.copyOf( transitions.columnKeySet() );
	}

}
 