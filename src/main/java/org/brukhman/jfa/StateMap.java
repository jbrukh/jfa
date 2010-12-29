package org.brukhman.jfa;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.SetMultimap;

/**
 * A map that holds FA transitions.
 * 
 * @author jbrukh
 *
 * @param <T>	the type of state that is available
 * @param <Character>	the transition symbol
 */
final class StateMap<T extends State<?>> {

	// FIELDS //
	
	private final Map<T,Multimap<Character,T>> 	transitions;
	private final Set<T>		  				states;
	
	/** The empty symbol. */
	private final static Character				EPSILON 		= new Character('\0');
	
	/**
	 * Create a new instance.
	 */
	public StateMap() {
		this.transitions = new HashMap<T,Multimap<Character,T>>();
		this.states = Collections.unmodifiableSet(this.transitions.keySet());
		//this.alphabet = new HashSet<S>();
	}
	
	/**
	 * Add a transition.
	 * 
	 * @param fromState
	 * @param symbol
	 * @param toState
	 */
	public final void add( T fromState, Character symbol, T toState ) {
		Preconditions.checkArgument( fromState!=null && symbol!=null && toState!=null );
		add(fromState);
		transitions.get(fromState).put(symbol, toState);		
	}
	
	/**
	 * Add a state.
	 * 
	 * @param state
	 */
	public final void add( T state ) {
		Preconditions.checkArgument( state!=null );
		
		if ( !this.states.contains(state) ) {
			Multimap<Character,T> map = HashMultimap.create();
			this.transitions.put(state,map);
		}
	}
	
	/**
	 * Remove a state from the map.
	 * <p>
	 * Note: This call is expensive because it has to remove all references to
	 * the given state by iterating over all transitions.
	 * 
	 * @param state
	 */
	public final void remove( T state ) {
		Preconditions.checkArgument( state!=null );
		
		if ( this.states.contains(state) ) {
			// remove the state
			this.transitions.remove(state);
			
			// remove all references to the state
			for ( T st : this.states ) {
				Multimap<Character,T> map = this.transitions.get(st);
				Iterator<Map.Entry<Character, T>> iter = map.entries().iterator();
				while ( iter.hasNext() ) {
					Map.Entry<Character,T> entry = iter.next();
					if ( entry.getValue().equals(state)) {
						iter.remove();
					}
				}
			}
		}
		else throw new IllegalArgumentException("State must be in the map.");
	}
	
	/**
	 * Returns the map image of the given input, e.g. the state to
	 * which the machine transitions.
	 * 
	 * @param fromState
	 * @param symbol
	 * @return
	 */
	public final Set<T> transition( T fromState, Character symbol ) {
		Preconditions.checkArgument( fromState != null && symbol != null );
		Preconditions.checkState( !Objects.equal(symbol, EPSILON), "Attempting to transition on Epsilon." );

		Set<T> toStates = new HashSet<T>();
		
		// add the usual transitions
		toStates.addAll(
				startingAt(fromState).get(symbol)
		);
		
	
		
		return Collections.unmodifiableSet(toStates);
	}
	
	private final Set<T> epsilonTransition( Set<T> fromState ) {
		Set<T> toStates = new HashSet<T>();
		toStates.addAll(
				startingAt(fromState).get(EPSILON)
		);
		toStates.addAll(
				epsilonTransition(toStates)
		);
		return Collections.unmodifiableSet(toStates);
	}
	
	private final Multimap<Character, T> startingAt( T state ) {
		return this.transitions.get(state);
	}
	
	/**
	 * Transition one set of states given a transition symbol.  This is useful in NFAs
	 * for getting closures of a state or set of states.
	 * 
	 * @param fromStates
	 * @param symbol
	 * @return
	 */
	public final Set<T> transition( Set<T> fromStates, Character symbol ) {
		Preconditions.checkArgument( fromStates != null && symbol != null );

		Set<T> toStates = new HashSet<T>();
		for ( T state : fromStates ) {
			if ( state == null ) continue;
			
			// add all states reachable from fromStates by the symbol
			toStates.addAll( this.transitions.get(state).get(symbol) );
			
			//
		}
		return Collections.unmodifiableSet(toStates);
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
