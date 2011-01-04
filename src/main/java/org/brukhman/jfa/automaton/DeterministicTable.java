package org.brukhman.jfa.automaton;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * A deterministic transition table.
 * 
 * @author jbrukh
 *
 */
public class DeterministicTable extends AbstractTable<State> {

	@Override
	public void addTransition(State from, Character symbol, State to) {
		checkNotNull(from);
		checkNotNull(symbol);
		checkNotNull(to);
		checkState( states.contains(from) && states.contains(to), "States must be in the machine.");
		table.put(from, symbol, to);
	}

	@Override
	public DeterministicTraverser traverser() {
		return DeterministicTraverser.create(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return null if there is no such transition
	 */
	@Override
	public State transition(State from, Character symbol) {
		checkNotNull(from);
		checkNotNull(symbol);
		return table.get(from, symbol);
	}

}
