/**********************************************
 * Copyright (C) 2011 Lukas Laag
 * This file is part of svgreal.
 * 
 * svgreal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * svgreal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with svgreal.  If not, see http://www.gnu.org/licenses/
 **********************************************/
package org.vectomatic.svg.edit.client.command.add;

import org.vectomatic.dom.svg.OMSVGElement;
import org.vectomatic.dom.svg.OMSVGPoint;
import org.vectomatic.dom.svg.OMSVGRectElement;
import org.vectomatic.dom.svg.OMSVGTransform;
import org.vectomatic.dom.svg.impl.SVGElement;
import org.vectomatic.dom.svg.utils.SVGConstants;
import org.vectomatic.svg.edit.client.SvgrealApp;
import org.vectomatic.svg.edit.client.command.FactoryInstantiatorBase;
import org.vectomatic.svg.edit.client.command.IFactoryInstantiator;
import org.vectomatic.svg.edit.client.event.MouseDownProcessor;
import org.vectomatic.svg.edit.client.event.MouseMoveProcessor;
import org.vectomatic.svg.edit.client.event.MouseUpProcessor;
import org.vectomatic.svg.edit.client.model.ModelConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;

/**
 * Command factory to add new rects to the SVG model.
 * @author laaglu
 */
public class AddRectCommandFactory extends AddCommandFactoryBase implements MouseDownProcessor, MouseMoveProcessor, MouseUpProcessor {
	@SuppressWarnings("serial")
	public static final IFactoryInstantiator<AddRectCommandFactory> INSTANTIATOR = new FactoryInstantiatorBase<AddRectCommandFactory>(ModelConstants.INSTANCE.addRectCmdFactory(), ModelConstants.INSTANCE.addRectCmdFactoryDesc()) {
		@Override
		public AddRectCommandFactory create() {
			return new AddRectCommandFactory();
		}
	};
	/**
	 * The new rect element
	 */
	protected OMSVGRectElement rect;
	/**
	 * The rect first point
	 */
	protected OMSVGPoint p1;
	/**
	 * The rect second point
	 */
	protected OMSVGPoint p2;

	@Override
	public IFactoryInstantiator<?> getInstantiator() {
		return INSTANTIATOR;
	}

	@Override
	public void start(Object requester) {
		GWT.log("AddRectCommandFactory.start(" + requester + ")");
		super.start(requester);
		updateStatus(ModelConstants.INSTANCE.addRectCmdFactory1());
	}

	@Override
	public boolean processMouseDown(MouseDownEvent event) {
		if (owner != null && rect != null) {
			owner.getTwinGroup().removeChild(rect);
		}
		updateStatus(ModelConstants.INSTANCE.addRectCmdFactory2());
		owner = SvgrealApp.getApp().getActiveModel();
		OMSVGElement parent = owner.getTwinGroup();
		p1 = owner.getCoordinates(event, true);
		p2 = owner.getSvgElement().createSVGPoint(p1);
		rect = new OMSVGRectElement(p1.getX(), p1.getY(), 0, 0, 0, 0);
		OMSVGTransform xform = owner.getSvgElement().createSVGTransform();
		xform.setRotate(-owner.getRotation(), p1.getX(), p1.getY());
		rect.getTransform().getBaseVal().appendItem(xform);
		applyCssContextStyle((SVGElement) rect.getElement().cast());
		rect.getStyle().setSVGProperty(SVGConstants.CSS_VISIBILITY_PROPERTY, SVGConstants.CSS_VISIBLE_VALUE);
		parent.appendChild(rect);
		return true;
	}
	
	@Override
	public boolean processMouseUp(MouseUpEvent event) {
		if (owner != null) {
			updateStatus(ModelConstants.INSTANCE.addRectCmdFactory1());
			if (rect.getWidth().getBaseVal().getValue() == 0 || rect.getHeight().getBaseVal().getValue() == 0) {
				owner.getTwinGroup().removeChild(rect);
			} else {
				createCommand(rect);
			}
			rect = null;
			owner = null;
		}
		return true;
	}
	
	@Override
	public boolean processMouseMove(MouseMoveEvent event) {
		if (owner != null) {
			p2 = owner.getCoordinates(event, true);
			float xmin = Math.min(p1.getX(), p2.getX());
			float ymin = Math.min(p1.getY(), p2.getY());
			float xmax = Math.max(p1.getX(), p2.getX());
			float ymax = Math.max(p1.getY(), p2.getY());
			rect.getX().getBaseVal().setValue(xmin);
			rect.getY().getBaseVal().setValue(ymin);
			rect.getWidth().getBaseVal().setValue(xmax - xmin);
			rect.getHeight().getBaseVal().setValue(ymax - ymin);
		}
		return true;
	}
}
