package org.brukhman.jfa.automaton;

import static com.google.common.base.Preconditions.*;

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

/**
 * A non-deterministic transition table.
 * <p>
 * This means that 
 * 
 * @author jbrukh
 *
 */
public class NondeterministicTable extends AbstractTable<Set<State>> {
	
	/**
	 * Use a factory method.
	 */
	NondeterministicTable() {
	}
	
	/**
	 * Create a new instance.
	 * 
	 * @return
	 */
	public final static NondeterministicTable create() {
		return new NondeterministicTable();
	}

	@Override
	public void addTransition(State from, Character symbol, State to) {
		checkNotNull(from);
		checkNotNull(symbol);
		checkNotNull(to);
		checkState( states.contains(from) && states.contains(to), "States must be in the machine.");
		Set<State> toStates = table.get(from,symbol);
		if ( toStates == null ) {
			toStates = Sets.newHashSet();
			table.put(from, symbol, toStates);
		}
		toStates.add(to);
	}

	@Override
	public NondeterministicTraverser traverser() {
		return NondeterministicTraverser.create(this);
	}

	@Override
	public ImmutableSet<State> transition(State from, Character symbol) {
		checkNotNull(from);
		checkNotNull(symbol);
		Set<State> result = table.get(from, symbol);
		if ( result == null ) {
			return ImmutableSet.of();
		}
		return ImmutableSet.copyOf(result);
	}
}