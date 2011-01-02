package org.brukhman.jfa.automaton;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * An automaton that can be manually constructed by adding states and transitions.
 * 
 * @author jbrukh
 *
 * @param <SymbolType>
 */
public interface ConstructibleAutomaton extends Automaton {
	
	/**
	 * Add a state to the automaton.
	 * 
	 * @param state
	 */
	public abstract Automaton addState( State state );
	
	/**
	 * Remove a state from the machine, and any transitions
	 * that have this state as an endpoint.
	 * 
	 * @param state
	 */
	public abstract void removeState( State state );
	
	/**
	 * Returns the set of states.  This set should be immutable.
	 * 
	 * @return
	 */
	public abstract ImmutableSet<State> getStates();
	
	/**
	 * Return the symbols (alphabet) that this automaton operates on.
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
		
	/**
	 * Return an {@link ImmutableAutomaton} that computes the language
	 * of this automaton.
	 * 
	 * @return
	 */
	public abstract ImmutableAutomaton finish();
	
}
