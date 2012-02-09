/**********************************************
 * Copyright (C) 2011 Lukas Laag
 * This file is part of vectomatic2.
 * 
 * vectomatic2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * vectomatic2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with vectomatic2.  If not, see http://www.gnu.org/licenses/
 **********************************************/
package org.vectomatic.svg.edit.client.event;

import org.vectomatic.svg.edit.client.SVGWindow;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event to broadcast the activation of a window
 * @author laaglu
 */
public class ActivateWindowEvent extends GwtEvent<ActivateWindowHandler> {
	/**
	 * Handler type.
	 */
	private static Type<ActivateWindowHandler> TYPE;

	private final SVGWindow window;

	public ActivateWindowEvent(SVGWindow window) {
		this.window = window;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ActivateWindowHandler> getAssociatedType() {
		if (TYPE == null) {
			TYPE = new Type<ActivateWindowHandler>();
		}
		return TYPE;
	}

	/**
	 * Ensures the existence of the handler hook and then returns it.
	 * 
	 * @return returns a handler hook
	 */
	public static Type<ActivateWindowHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<ActivateWindowHandler>();
		}
		return TYPE;
	}

	@Override
	protected void dispatch(ActivateWindowHandler handler) {
		handler.onActivate(this);
	}

	public SVGWindow getWindow() {
		return window;
	}

}
