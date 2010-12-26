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
abstract class AbstractAutomaton<T extends State<?>> implements Automaton {

	private T 		initialState;
	private Set<T> 	finalStates;
	private StateMap<T,Character> transitions;
	

	/**
	 * Create a new instance.
	 */
	public AbstractAutomaton() {
		this.finalStates 	= new HashSet<T>();
		this.transitions    = new StateMap<T, Character>();
	}
	
	/**
	 * Add a state.
	 * 
	 * @param state
	 */
	public final boolean addState( T state ) {
		return this.states.add(state);
	}
	
	/**
	 * Add a transition.
	 * 
	 * @param trans
	 * @return
	 */
	public final boolean addTransition( Transition<T> trans ) {
		
	}
}
