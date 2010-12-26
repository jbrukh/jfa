package org.brukhman.jfa;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author jbrukh
 *
 */
abstract class AbstractAutomaton<T extends State<?>> implements Automaton {

	private T 		initialState;
	private Set<T> 	finalStates;
	private Set<T>	states;

	/**
	 * Create a new instance.
	 */
	public AbstractAutomaton() {
		this.finalStates = new HashSet<T>();
		this.states 		= new HashSet<T>();
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
