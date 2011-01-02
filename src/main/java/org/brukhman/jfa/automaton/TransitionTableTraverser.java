package org.brukhman.jfa.automaton;

import static org.brukhman.jfa.automaton.Symbols.EPSILON;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;

public class TransitionTableTraverser<StateType extends GenericState<?>, SymbolType extends Symbol<?>>  {
	
	private final TransitionTable<?,?> table;
	
	/**
	 * Create a new instance.
	 * 
	 * @param <StateType>
	 * @param <SymbolType>
	 * @param table
	 */
	private TransitionTableTraverser( TransitionTable<StateType,SymbolType> table ) {
		Preconditions.checkNotNull(table);
		this.table = table;
	}
	
	public final static <StateType extends GenericState<?>, SymbolType extends Symbol<?>>
	TransitionTableTraverser<StateType, SymbolType> create( TransitionTable<StateType,SymbolType> table ) {
		return new TransitionTableTraverser<StateType, SymbolType>(table);
	}

	/**
	 * DFS for epsilon-reachable states and place them in the provided result set.
	 * 
	 * @param fromState the starting state
	 * @param reachable	the result set
	 */
	private final void epsilonTransition( StateType fromState, Set<StateType> reachable ) {
		// get epsilon transitions
		Collection<StateType> toStates = table.transition(fromState, Symbol.EPSILON);
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
}
