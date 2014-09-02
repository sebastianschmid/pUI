package com.martinleopold.pui.examples;

import com.martinleopold.pui.*;
import java.util.ArrayList;
import java.util.Vector;

import processing.core.*;

public class ToggleTest extends PApplet {
	PUI ui;
	Slider s;
	ArrayList<Widget<?>> group1;
	
	boolean group1active = false;

        @Override
	public void setup() {
		  size(500, 500);
		  noSmooth();
		  
		  ui = PUI.init(this).size(300, height);
		  ui.font("NewMedia Fett.ttf"); // set font
		  
		  group1 = new ArrayList<Widget<?>>();
		  group1.add( new Label(ui, "Toggle Menu Test"));
		  group1.add( new Slider(ui) );
		  group1.add( new Button(ui) );
		  group1.add( new FlowControl(ui, FlowControl.LINEBREAK));
		  
		  
		  ui.addLabel("Toggles");
		  ui.newRow();
		  ui.addToggle().label("t1").calls("toggleGroup1");
		  ui.addDivider();
		  
		  
		  ui.addLabel("Sliders");
		  s = ui.addSlider().label("s1").calls("sliderValue").max(100);
		  ui.addSlider().label("s2").sets("s2Value");
//		  Slider pinnedSlider;
//		  pinnedSlider = new Slider(ui);
		  // pinnedSlider.position(100, 100).value(50);
//		  pinnedSlider.label("pinned");
//		  pinnedSlider.position(130, 350);
//		  ui.add(pinnedSlider, false);
		  ui.addSlider().label("s5");
		  ui.addSlider().label("s3");
		  ui.addDivider();
		  
		  ui.addLabel("VSliders"); ui.newRow();
		  ui.addVSlider().label("v1").calls("vSliderValue");
		  ui.addVSlider().label("v2");
		  
		  ui.newColumn();
		  ui.addLabel("Dividers");
		  ui.addDivider();
		  ui.addDivider();
		  ui.addDivider();
		  
		  ui.addLabel("Labels"); ui.newRow();
		  ui.addLabel("small").small(); ui.newRow();
		  ui.addLabel("medium").medium(); ui.newRow();
		  ui.addLabel("large").large();
		  
		  ui.addDivider();
		  ui.addLabel("Keys"); ui.newRow();
		  ui.addLabel("space: toggle UI").small();
		  ui.addLabel("n: next theme").small();
		  ui.addLabel("b: previous theme").small();
		  ui.addLabel("g: toggle grid").small();
		  
		  ui.addDivider();
		  ui.addColorPicker();
	}
	
	synchronized void toggleGroup1(Toggle t) {
		if ( t.isPressed() ) {
			ui.addAll(4, group1);
		} else {
			ui.removeAll(group1);
		}
	  println("t1 toggled. value=" + t.isPressed());
	}

        @Override
	public void draw() {
		stroke(255);
		if (mousePressed) {
			line(mouseX, mouseY, pmouseX, pmouseY);
		}
	}
        
        public static void main(String[] args) {
                PApplet.main(new String[] { "com.martinleopold.pui.examples.ToggleTest" });
        }
}
