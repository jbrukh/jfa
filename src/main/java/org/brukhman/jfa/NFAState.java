package org.brukhman.jfa;

/**
 * An NFA state, which can be labeled with an integer.
 * 
 * @author jbrukh
 *
 */
public class NFAState extends State<Integer>{
	
	/**
	 * Create a new instance.
	 * 
	 * @param name
	 */
	public NFAState( Integer name ) {
		super(name);
	}
}
