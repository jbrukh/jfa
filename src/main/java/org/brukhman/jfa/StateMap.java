package org.brukhman.jfa;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A map that holds FA transitions.
 * 
 * @author jbrukh
 *
 * @param <T>	the type of state that is available
 * @param <S>	the transition symbol
 */
final class StateMap<T extends State<?>,S> {

	// FIELDS //
	
	private final Map<T,Map<S,T>> transitions;
	private final Set<T>		  states;
	private final Set<S>		  alphabet;
	
	/**
	 * Create a new instance.
	 */
	public StateMap() {
		this.transitions = new HashMap<T,Map<S,T>>();
		this.states = Collections.unmodifiableSet(this.transitions.keySet());
		this.alphabet = new HashSet<S>();
	}
	
	/**
	 * Add a transition.
	 * 
	 * @param fromState
	 * @param symbol
	 * @param toState
	 */
	public final void add( T fromState, S symbol, T toState ) {
		if ( fromState == null || toState == null || symbol == null ) {
			throw new IllegalArgumentException();
		}
		
		add(fromState);
		alphabet.add(symbol);
		Map<S,T> map = transitions.get(fromState);
		map.put(symbol, toState);
		
	}
	
	/**
	 * Add a state.
	 * 
	 * @param state
	 */
	public final void add( T state ) {
		if ( state == null ) {
			throw new IllegalArgumentException();
		}
		
		if ( !this.states.contains(state) ) {
			Map<S,T> map = new HashMap<S,T>();
			this.transitions.put(state,map);
		}
	}
	
	/**
	 * Returns the map image of the given input, e.g. the state to
	 * which the machine transitions.
	 * 
	 * @param fromState
	 * @param symbol
	 * @return
	 */
	public final T transition( T fromState, S symbol ) {
		if ( fromState == null || symbol == null ) {
			throw new IllegalArgumentException();
		}
		Map<S,T> map = transitions.get(fromState);
		if ( map == null ) {
			return null;
		}
		
		return map.get(symbol);
	}
	
	/**
	 * Returns a view on the set containing all of the states.
	 * 
	 * @return
	 */
	public final Set<T> getStates() {
		return this.states;
	}
	
	/**
	 * Clear the map.
	 * 
	 */
	public final void clear() {
		this.transitions.clear();
	}
}
