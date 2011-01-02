package org.brukhman.jfa.automaton;

import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

public final class TransitionTable<StateType extends GenericState<?>, SymbolType extends Symbol<?>> {

	// FIELDS //
	
	private final Table<StateType, SymbolType, Set<StateType>> transitions;
	
	/**
	 * Create a new instance, privately.
	 */
	private TransitionTable() {
		transitions = HashBasedTable.create();
	}

	/**
	 * Create a new instance.
	 * 
	 * @param <StateType>
	 * @param <SymbolType>
	 * @return
	 */
	public final static <StateType extends GenericState<?>, SymbolType extends Symbol<?>> 
	TransitionTable<StateType,SymbolType> create() {
		return new TransitionTable<StateType, SymbolType>();
	}
	
	/**
	 * Returns {@code true} if and only if the table contains the specified state.
	 * 
	 * @param state
	 * @return
	 */
	public final boolean containsState( StateType state ) {
		return transitions.rowKeySet().contains(state);
	}
	
	/**
	 * Returns {@code true} if and only if the table contains the specified state.
	 * 
	 * @param symbol
	 * @return
	 */
	public final boolean containsSymbol( SymbolType symbol ) {
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
	public final void addTransition( StateType fromState, SymbolType symbol, StateType toState ) {
		Set<StateType> toStates = transitions.get(fromState, symbol);
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
	public final ImmutableSet<StateType> transition( StateType fromState, SymbolType symbol ) {
		Set<StateType> toStates = transitions.get(fromState, symbol);
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
	public final ImmutableSet<StateType> getStates() {
		return ImmutableSet.copyOf( transitions.rowKeySet() );
	}
	
	/**
	 * Get the symbols of the table.
	 * 
	 * @return
	 */
	public final ImmutableSet<SymbolType> getSymbols() {
		return ImmutableSet.copyOf( transitions.columnKeySet() );
	}

}
 