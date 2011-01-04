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
public final class DeterministicTraverser extends TableTraverser<DeterministicTable>  {
	
	// FIELDS //
		
	/**
	 * Create a new instance.
	 * 
	 * @param <StateType>
	 * @param <SymbolType>
	 * @param table
	 */
	private DeterministicTraverser( DeterministicTable table ) {
		super(table);
	}
	
	/**
	 * Create a new instance.
	 * 
	 * @param table
	 * @return
	 */
	public final static DeterministicTraverser create( DeterministicTable table ) {
		return new DeterministicTraverser(table);
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
			to.add( table.transition(state,symbol) );
		}
		
		return ImmutableSet.copyOf( to );
	}


}
