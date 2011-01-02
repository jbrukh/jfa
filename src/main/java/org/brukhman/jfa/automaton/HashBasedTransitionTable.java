package org.brukhman.jfa.automaton;

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

/**
 * A transition table for an NFA.  This means each
 * @author jbrukh
 *
 */
public final class HashBasedTransitionTable extends AbstractTransitionTable {

	// FIELDS //
	
	private final Table<State, Character, Set<State>> 	transitions;
	
	/**
	 * Create a new instance, privately.
	 */
	public HashBasedTransitionTable() {
		transitions = HashBasedTable.create();
	}

	/* (non-Javadoc)
	 * @see org.brukhman.jfa.automaton.TransitionTableInterface#containsState(org.brukhman.jfa.automaton.State)
	 */
	@Override
	public final boolean containsState( State state ) {
		return transitions.rowKeySet().contains(state);
	}
	
	/* (non-Javadoc)
	 * @see org.brukhman.jfa.automaton.TransitionTableInterface#containsSymbol(java.lang.Character)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see org.brukhman.jfa.automaton.TransitionTableInterface#transition(org.brukhman.jfa.automaton.State, java.lang.Character)
	 */
	@Override
	public final ImmutableSet<State> transition( State fromState, Character symbol ) {
		Set<State> toStates = transitions.get(fromState, symbol);
		if ( toStates == null ) {
			return ImmutableSet.of();
		}
		return ImmutableSet.copyOf(toStates);
	}

	/* (non-Javadoc)
	 * @see org.brukhman.jfa.automaton.TransitionTableInterface#getStates()
	 */
	@Override
	public final ImmutableSet<State> getStates() {
		return ImmutableSet.copyOf( transitions.rowKeySet() );
	}
	
	/* (non-Javadoc)
	 * @see org.brukhman.jfa.automaton.TransitionTableInterface#getSymbols()
	 */
	@Override
	public final ImmutableSet<Character> getSymbols() {
		return ImmutableSet.copyOf( transitions.columnKeySet() );
	}

	@Override
	public NondeterministicTransitionTableTraverser traverser() {
		return new NondeterministicTransitionTableTraverser(this);
	}
}
 