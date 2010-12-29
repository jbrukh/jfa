package org.brukhman.jfa;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The base for constructing NFAs.
 *     
 * @author jbrukh
 *
 */
abstract class NFAutomaton implements Automaton<NFAState> {

	// FIELDS //
	
	private NFAState 						initialState;
	private Set<NFAState> 					finalStates;
	private StateMap<NFAState,Character> 	stateMap;
	
	private int								count;
	private NFAState						currentState;
	
	/**
	 * Create a new instance.
	 */
	public NFAutomaton() {
		this.finalStates 	= new HashSet<NFAState>();
		this.stateMap    	= new StateMap<NFAState, Character>();		
		this.count			= 0;
	}
	
	/**
	 * Add a state.
	 * 
	 * @param state
	 */
	public final void addState( NFAState state ) {
		this.stateMap.add(state);
	}

	/**
	 * Add a state.
	 * 
	 * @return the {@link NFAState} that was added
	 */
	public final NFAState addState() {
		NFAState state = new NFAState(count++);
		addState(state);
		return state;
	}
	
	/**
	 * Remove a state from the machine.
	 * 
	 * @param state
	 */
	public final void removeState( NFAState state ) {
		this.stateMap.remove(state);
		
		if ( state.isInitial() ) {
			state.setInitial(false);
			clearInitialState();
		}
	}
	
	/**
	 * Make a state an initial state.  The state must be in the machine.  If an initial state is
	 * already set, it is cleared.
	 * 
	 * @param state
	 */
	public final void makeInitial( NFAState state ) {
		if ( !stateMap.getStates().contains(state) ) {
			throw new IllegalArgumentException("The state must be in the machine.");
		}
		clearInitialState();
		state.setInitial(true);
		this.initialState = state;
	}
	
	/**
	 * Make a state a final state.  The state must be in the machine.  This method has no effect
	 * if the state is already final.
	 * 
	 * @param state
	 */
	public final void makeFinal( NFAState state ) {
		if ( !this.stateMap.getStates().contains(state) ) {
			throw new IllegalArgumentException("The state must be in the machine.");
		}
		state.setFinal(true);
		this.finalStates.add(state);
	}
	
	/**
	 * Clear the initial state.
	 * 
	 */
	public final void clearInitialState() {
		if ( this.initialState != null ) {
			this.initialState.setInitial(false);
			this.initialState = null;
		}
	}
	
	/**
	 * Revert a final state to a regular state.
	 * 
	 * @param state
	 */
	public final void clearFinalState( NFAState state ) {
		if ( !this.stateMap.getStates().contains(state) || ! finalStates.contains(state)) {
			throw new IllegalArgumentException("The state must be a final state in the machine.");
		}
		this.finalStates.remove(state);
		state.setFinal(false);
	}
	
	@Override
	public boolean compute(String input) {
		if ( input == null ) {
			throw new IllegalArgumentException("Input must not be null.");
		}
		
		// empty string case
		if ( input == "" ) {
			// TODO
		}
		
		char[] inputArray = input.toCharArray();
		
	}

	@Override
	public Set<NFAState> getFinalStates() {
		return Collections.unmodifiableSet(this.finalStates);
	}

	@Override
	public NFAState getInitialState() {
		return this.initialState;
	}

}
