/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.martinleopold.pui;

import java.util.Map;

/**
 *
 * @author sebastianschmid
 */
public class FlowControl extends Widget<FlowControl> {

	public static final int LINEBREAK = 0b1;
	public static final int COLUMNBREAK = 0b10;
	
	int type = 0;
	
	public FlowControl(PUI pui, int width, int height) {
		super(pui, width, height);
		throw new Error();
	}
	
	/**
	 *
	 * @param pui
	 * @param type
	 */
	public FlowControl(PUI pui, int type) {
		super(pui, 0, 0);
		this.type = type;
	}
	
	/**
	 *
	 * @param pui
	 * @param type
	 * @param args
	 * Array of Key => Value pairs to be passed by specification for the current
	 * Control Element.
	 */
	public FlowControl(PUI pui, int type, Map args ){
		super(pui, 0, 0);
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
