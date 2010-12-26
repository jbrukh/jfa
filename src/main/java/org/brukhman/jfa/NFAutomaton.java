package org.brukhman.jfa;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The base for constructing NFAs.
 *     
 * @author jbrukh
 *
 */
abstract class NFAutomaton implements Automaton {

	// FIELDS //
	
	private NFAState 						initialState;
	private Set<NFAState> 					finalStates;
	private StateMap<NFAState,Character> 	stateMap;
	
	private int								count;
	
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
	}

}
