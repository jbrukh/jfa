package org.brukhman.jfa.automaton;

import com.google.common.collect.ImmutableSet;

/**
 * A transition table.
 * <p>
 * A transition table contains a set of states and a set of symbols, and maps
 * a state and a symbol into either a state or a set of states, depending on whether
 * it describes a deterministic or non-deterministic automaton.
 * 
 * @author jbrukh
 *
 */
public interface Table {

	/**
	 * Adds a state to the table.
	 * 
	 * @param state
	 * @return
	 */
	public abstract void addStates(State... state);
	
	/**
	 * Get the set of states of the table.
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
	 * Add a transition to the table.
	 * 
	 * @param from
	 * @param symbol
	 * @param to
	 */
	public void addTransition( State from, Character symbol, State to );
	
	/**
	 * Return a traverser for this table.
	 * 
	 * @return
	 */
	public abstract TableTraverser traverser();
	
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