package org.brukhman.jfa.automaton;

import static org.brukhman.jfa.automaton.Symbols.EPSILON;

import java.util.Collection;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;

/**
 * Traverse transition tables.
 * 
 * @author jbrukh
 *
 */
public final class NondeterministicTraverser extends TableTraverser<NondeterministicTable>  {
	
	// FIELDS //
		
	/**
	 * Create a new instance.
	 * 
	 * @param <StateType>
	 * @param <SymbolType>
	 * @param table
	 */
	private NondeterministicTraverser( NondeterministicTable table ) {
		super(table);
	}
	
	/**
	 * Create a new instance.
	 * 
	 * @param table
	 * @return
	 */
	public final static NondeterministicTraverser create( NondeterministicTable table ) {
		return new NondeterministicTraverser(table);
	}
	
	/**
	 * DFS for epsilon-reachable states and place them in the provided result set.
	 * 
	 * @param from the starting state
	 * @param reachable	the result set
	 */
	private final void epsilonClosure( State from, Set<State> reachable ) {
		// get epsilon transitions
		Collection<State> toStates = table.transition(from, EPSILON);
		for ( State state : toStates ) {
			reachable.add(state);
			epsilonClosure(state,reachable);
		}
	}
	
	/**
	 * Transitions the given set by non-epsilon symbols.
	 * 
	 * @param from
	 * @param symbol
	 * @return
	 */
	private final Set<State> concreteClosure( Set<State> from, Character symbol ) {
		Preconditions.checkNotNull( from );
		Preconditions.checkNotNull( symbol );	
		Preconditions.checkArgument( !symbol.equals(EPSILON), "Must provide a non-epsilon symbol.");
		
		Set<State> to = Sets.newHashSet();
		for ( State state : from ) {
			to.addAll(table.transition(state,symbol));
		}
		return to;
	}
	
	/**
	 * Returns the set of states reachable by epsilon transitions from a given state.
	 * 
	 * @param from
	 * @return
	 */
	public final ImmutableSet<State> epsilonClosure( State from ) {
		Preconditions.checkNotNull(from);
		Set<State> to = Sets.newHashSet();
		// add the starting state
		to.add(from);
		// dfs for epsilon transitions
		epsilonClosure(from,to);
		return ImmutableSet.copyOf( to );
	}

	/**
	 * Transition one set of states given a transition symbol.  This is useful in NFAs
	 * for getting closures of a state or set of states.
	 * 
	 * @param from
	 * @param symbol
	 * @return
	 */
	public final ImmutableSet<State> transition( Set<State> from, Character symbol ) {
		Preconditions.checkNotNull(from);
		Preconditions.checkNotNull(symbol);
		
		Set<State> to = Sets.newHashSet();
		for ( State state : from ) {
			to.addAll( transition(state,symbol) );
		}
		
		return ImmutableSet.copyOf( to );
	}
	
	/**
	 * Returns the map image of the given input, e.g. the set of states to
	 * which the machine transitions.
	 * 
	 * @param from
	 * @param symbol
	 * @return
	 */
	public final ImmutableSet<State> transition( State from, Character symbol ) {
		Preconditions.checkNotNull(from);
		Preconditions.checkNotNull(symbol);

		ImmutableSet<State> epsilonStates = epsilonClosure(from);
		
		// epsilon transition?
		if ( symbol.equals(EPSILON) ) {
			return epsilonStates;
		}	
		Set<State> toStates = Sets.newHashSet();
		
		// add all the concrete transitions
		toStates.addAll(
				concreteClosure(epsilonStates, symbol)
		);
	
		return ImmutableSet.copyOf( toStates );
	}

	/**
	 * Gives the epsilon closure of the initial state.
	 * 
	 * @return
	 */
	public ImmutableSet<State> epsilonClosureInitial() {
		State initialState = table.getInitial();
		Preconditions.checkState( initialState != null, "No initial state in the machine.");
		return epsilonClosure(initialState);
	}

}
