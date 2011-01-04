package org.brukhman.jfa.automaton;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * An automaton that can be manually constructed by adding states and 
 * transitions. An automaton can be described by a transition table, and therefore
 * these two concepts are nearly identical.
 * <p>
 * A deterministic finite automaton (DFA) can be described by a table that
 * maps a state and a symbol to another state in a machine, where no epsilons
 * are allowed as symbols.
 * <p>
 * A nondeterministic finite automaton (NFA) can be similarly described by a table,
 * but one that maps a state and a symbol to a set of states in the machine
 * and epsilons are allowed.
 * 
 * @author jbrukh
 *
 * @param <SymbolType>
 */
public interface ConstructibleAutomaton extends Automaton, Table {
}
