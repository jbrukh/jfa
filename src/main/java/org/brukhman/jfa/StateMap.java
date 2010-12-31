package org.brukhman.jfa;

import static org.brukhman.jfa.Symbols.*;

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

/**
 * A map that holds FA transitions.
 * 
 * @author jbrukh
 *
 * @param <StateType>	the type of state that is available
 * @param <Character>	the transition symbol
 */
final class StateMap<StateType extends State<?>> {

	// FIELDS //
	
	private final Map<StateType,Multimap<Character,StateType>> 	transitions;
	private final Set<StateType>		  						states;
		
	/**
	 * Create a new instance.
	 */
	public StateMap() {
		this.transitions = new HashMap<StateType,Multimap<Character,StateType>>();
		this.states = Collections.unmodifiableSet(this.transitions.keySet());
	}
	
	/**
	 * Add a transition.
	 * 
	 * @param fromState
	 * @param symbol
	 * @param toState
	 */
	public final void add( StateType fromState, Character symbol, StateType toState ) {
		Preconditions.checkArgument( fromState!=null && symbol!=null && toState!=null );
		add(fromState);
		transitions.get(fromState).put(symbol, toState);		
	}
	
	/**
	 * Add a state.
	 * 
	 * @param state
	 */
	public final void add( StateType state ) {
		Preconditions.checkArgument( state!=null );
		
		if ( !this.states.contains(state) ) {
			Multimap<Character,StateType> map = HashMultimap.create();
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
	public final void remove( StateType state ) {
		Preconditions.checkArgument( state!=null );
		
		if ( this.states.contains(state) ) {
			// remove the state
			this.transitions.remove(state);
			
			// remove all references to the state
			for ( StateType st : this.states ) {
				Multimap<Character,StateType> map = this.transitions.get(st);
				Iterator<Map.Entry<Character, StateType>> iter = map.entries().iterator();
				while ( iter.hasNext() ) {
					Map.Entry<Character,StateType> entry = iter.next();
					if ( entry.getValue().equals(state)) {
						iter.remove();
					}
				}
			}
		}
		else throw new IllegalArgumentException("State must be in the map.");
	}
	

	
	/**
	 * DFS for epsilon-reachable states and place them in the provided result set.
	 * 
	 * @param fromState the starting state
	 * @param reachable	the result set
	 */
	private final void epsilonTransition( StateType fromState, Set<StateType> reachable ) {
		// get epsilon transitions
		Collection<StateType> toStates = startingAt(fromState).get(EPSILON);
		if ( toStates.isEmpty() ) { 
			return;
		}
		
		for ( StateType state : toStates ) {
			reachable.add(state);
			epsilonTransition(state,reachable);
		}
	}
	
	/**
	 * Returns the set of states reachable by epsilon transitions from a given state.
	 * 
	 * @param fromState
	 * @return
	 */
	public final Set<StateType> epsilonTransition( StateType fromState ) {
		Preconditions.checkArgument( fromState!=null );
		
		Set<StateType> toStates = new HashSet<StateType>();
		
		// add the starting state
		toStates.add(fromState);
		
		// dfs for epsilon transitions
		epsilonTransition(fromState,toStates);
		return Collections.unmodifiableSet(toStates);
	}

	
	
	private final Multimap<Character, StateType> startingAt( StateType state ) {
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
	public final Set<StateType> transition( Set<StateType> fromStates, Character symbol ) {
		Preconditions.checkArgument( fromStates != null && symbol != null );

		Set<StateType> toStates = new HashSet<StateType>();
		for ( StateType state : fromStates ) {
			toStates.addAll( transition(state,symbol) );
		}
		return Collections.unmodifiableSet(toStates);
	}
	
	/**
	 * Returns the map image of the given input, e.g. the set of states to
	 * which the machine transitions.
	 * 
	 * @param fromState
	 * @param symbol
	 * @return
	 */
	public final Set<StateType> transition( StateType fromState, Character symbol ) {
		Preconditions.checkArgument( fromState != null && symbol != null );

		Set<StateType> epsilonStates = epsilonTransition(fromState);
		
		// epsilon transition?
		if ( Objects.equal(symbol,EPSILON) ) {
			return epsilonStates;
		}
		
		Set<StateType> toStates = new HashSet<StateType>();
		
		// add all the concrete transitions
		toStates.addAll(
				concereteTransition(epsilonStates, symbol)
		);
	
		return Collections.unmodifiableSet(toStates);
	}
	
	/**
	 * Transitions the given set by non-epsilon symbols.
	 * 
	 * @param fromStates
	 * @param symbol
	 * @return
	 */
	private final Set<StateType> concereteTransition( Set<StateType> fromStates, Character symbol ) {
		Preconditions.checkArgument( fromStates != null && symbol != null );
		Preconditions.checkArgument( !symbol.equals(EPSILON), "Must provide a non-epsilon symbol.");
		
		Set<StateType> toStates = new HashSet<StateType>();
		for ( StateType fromState : fromStates ) {
			toStates.addAll(startingAt(fromState).get(symbol));
		}
		return Collections.unmodifiableSet(toStates);
	}
	
	/**
	 * Returns a view on the set containing all of the states.
	 * 
	 * @return
	 */
	public final Set<StateType> getStates() {
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
