package org.brukhman.jfa.automaton;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public interface TransitionTable {

	/**
	 * Returns {@code true} if and only if the table contains the specified state.
	 * 
	 * @param state
	 * @return
	 */
	public abstract boolean containsState(State state);

	/**
	 * Returns {@code true} if and only if the table contains the specified state.
	 * 
	 * @param symbol
	 * @return
	 */
	public abstract boolean containsSymbol(Character symbol);

	/**
	 * Return a traverser for this table.
	 * 
	 * @return
	 */
	public abstract TransitionTableTraverser traverser();

	/**
	 * Transition.
	 *  
	 * @param from
	 * @param symbol
	 * @return
	 */
	public abstract ImmutableSet<State> transition( State from, Character symbol );
	
	/**
	 * Get the states of the table.
	 * 
	 * @return
	 */
	public abstract ImmutableSet<State> getStates();

	/**
	 * Get the symbols of the table.
	 * 
	 * @return
	 */
	public abstract ImmutableSet<Character> getSymbols();
	
	/**
	 * Make a state the initial state.  This will clear any states
	 * that have already been thus designated.
	 * 
	 * @param state
	 */
	public abstract void makeInitial( State state );
	
	/**
	 * Returns the initial state.
	 * 
	 * @return
	 */
	public abstract State getInitial();
	
	/**
	 * Make a state a final state.
	 * 
	 * @param state
	 */
	public abstract void makeFinal( State state );
	
	/**
	 * Clear a state from the final states.
	 * 
	 * @param state
	 */
	public abstract void clearFinal( State state );
	
	/**
	 * Returns the final states.
	 * 
	 * @return
	 */
	public abstract ImmutableSet<State> getFinal();

}