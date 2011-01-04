package org.brukhman.jfa.automaton;

import com.google.common.base.Preconditions;

/**
 * A traverser of transition tables.  Subclass this with desired
 * functionality.  
 * 
 * @author jbrukh
 *
 */
abstract class TableTraverser<T extends Table> {
	
	// FIELDS //
	
	protected T table;
	
	/**
	 * Create a new instance.
	 * 
	 * @param table
	 */
	public TableTraverser( T table ) {
		Preconditions.checkNotNull(table);
		this.table = table;
	}
	
	/**
	 * Get the table.
	 */
	public final T getTable() {
		return table;
	}
	
}
