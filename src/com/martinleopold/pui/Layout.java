/*
 * Copyright (C) 2013 Martin Leopold <m@martinleopold.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.martinleopold.pui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Martin Leopold <m@martinleopold.com>
 */
final class Layout {
	int width, height;
	int paddingX, paddingY;
	int columnWidth;
	
	int nextX, nextY; // where to place the next widget *and* it's padding
	
	int currentColumnX; // columnX . padding . widget
	int currentColumnWidth; // width to place widgets and padding in
	int currentRowHeight; // height of row including widgets and padding
	
	int windowPaddingX, windowPaddingY; 
	
	enum Action { NewRow, NewColumn, AddWidget };
	List<Action> actions; // sequence of layout actions
	List<Widget> elements; // list of added widgets
	
	Layout(int width, int height, int paddingX, int paddingY, int columnWidth) {
		this.width = width;
		this.height = height;
		this.paddingX = paddingX;
		this.paddingY = paddingY;
		this.columnWidth = columnWidth;
		
		reset();
		
		// window padding
		if (width - 2*paddingX > 0 && height - 2*paddingY > 0) {
			windowPaddingX = paddingX;
			windowPaddingY = paddingY;
			width -= 2*paddingX;
			height -= 2*paddingY;
		}
	}
	
	void add(Widget w) {
		Rect r = w.layoutRect;
		
		int totalWidth = r.width + 2*paddingX; // total widget width (including padding)
		int toalHeight = r.height + 2*paddingY; // total widget height (including padding)
		
		// ceck if we flow out at the bottom
		if (nextY + toalHeight > height) {
//			System.out.println("try new column");
			newColumn();
		}
		
		// check if it fits in the current line
		if (nextX + totalWidth <= currentColumnX + currentColumnWidth) {
			// place it
			w.setPosition(windowPaddingX + nextX + paddingX, windowPaddingY + nextY + paddingY);
			elements.add(w); // add to list of layouted elements
			actions.add(Action.AddWidget);
			System.out.println("placing in layout x:" + r.x + " y:" + r.y);
			// track row height
			if (toalHeight > currentRowHeight) currentRowHeight = toalHeight;
			// widget was placed
			nextX += totalWidth;
			// warn if we flow out to the right
			if (r.x + totalWidth > width) {
				System.out.println("Warning: Widget is placed outside of the window.");
			}
		} else if (nextX == currentColumnX) { // check if we are at the beginning of a line
//			System.out.println("make column wider");
			// we are at the beginning of a line and it doesn't fit
			currentColumnWidth = totalWidth; // make this column wider
			add(w); // try again
		} else { // it doesn't fit in the current line
//			System.out.println("try new row");
			newRow();
			add(w);
		}
	}
	
	void newRow() {
		nextX = currentColumnX;
		nextY += currentRowHeight;
		currentRowHeight = 0;
		actions.add(Action.NewRow);
	}
	
	void newColumn() {
		currentRowHeight = 0;
		currentColumnX += currentColumnWidth;
		currentColumnWidth = columnWidth;
		nextX = currentColumnX;
		nextY = 0;
		actions.add(Action.NewColumn);
	}
	
	// TODO this is recorded as layout action
	void setColumnWidth(int w) {
		columnWidth = w;
		currentColumnWidth = w;
	}
	
//	void remove(Widget e) {
//		if (elements.remove(e)) {
//			reLayout();
//		}
//	}
	
	void reset() {
		nextX = 0;
		nextY = 0;
		currentColumnX = 0; 
		currentColumnWidth = columnWidth; 
		currentRowHeight = 0;
		
		elements = new ArrayList<Widget>();
		actions = new ArrayList<Action>();
	}
	
	void reLayout() {
		List<Action> oldActions = actions; // save actions
		List<Widget> oldElements = elements; // save elements
		
		reset(); // reset layout
		
		Iterator<Widget> widgets = oldElements.iterator();
		for (Action a : oldActions) {
			switch (a) {
				case NewRow:
					newRow();
					break;
				case NewColumn:
					newColumn();
					break;
				case AddWidget:
					add(widgets.next());
					break;
			}
		}
	}
}