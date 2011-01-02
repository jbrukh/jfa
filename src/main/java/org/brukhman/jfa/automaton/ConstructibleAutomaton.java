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
public interface ConstructibleAutomaton
										<StateType extends GenericState<?>, 
										 SymbolType extends Symbol<?>> 
extends Automaton {
	
	/**
	 * Add a state to the automaton.
	 * 
	 * @param state
	 */
	public abstract Automaton addState( StateType state );
	
	/**
	 * Remove a state from the machine, and any transitions
	 * that have this state as an endpoint.
	 * 
	 * @param state
	 */
	public abstract void removeState( StateType state );
	
	/**
	 * Returns the set of states.  This set should be immutable.
	 * 
	 * @return
	 */
	public abstract ImmutableSet<StateType> getStates();
	
	/**
	 * Return the symbols (alphabet) that this automaton operates on.
	 * 
	 * @return
	 */
	public abstract ImmutableSet<SymbolType> getSymbols();

	/**
	 * Make a state the initial state.  This will clear any states
	 * that have already been thus designated.
	 * 
	 * @param state
	 */
	public abstract void makeInitial( StateType state );
	
	/**
	 * Returns the initial state.
	 * 
	 * @return
	 */
	public abstract StateType getInitial();
	
	/**
	 * Make a state a final state.
	 * 
	 * @param state
	 */
	public abstract void makeFinal( StateType state );
	
	/**
	 * Clear a state from the final states.
	 * 
	 * @param state
	 */
	public abstract void clearFinal(StateType state);
	
	/**
	 * Returns the final states.
	 * 
	 * @return
	 */
	public abstract ImmutableSet<StateType> getFinal();
		
	/**
	 * Return an {@link ImmutableAutomaton} that computes the language
	 * of this automaton.
	 * 
	 * @return
	 */
	public abstract ImmutableAutomaton finish();
	
}
