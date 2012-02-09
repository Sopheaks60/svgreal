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
package org.vectomatic.svg.edit.client.gxt.binding;

import org.vectomatic.svg.edit.client.gxt.form.PaintField;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.google.gwt.core.client.GWT;

/**
 * Binding class for PaintField
 * @author laaglu
 */
public class PaintFieldBinding extends DelayedBindingBase {
	private Listener<FieldEvent> afterEditListener;

	public PaintFieldBinding(PaintField field, String property) {
		super(field, property);
		afterEditListener = new Listener<FieldEvent>() {
			@Override
			public void handleEvent(FieldEvent be) {
				commitChanges();
			}			
		};
	}
	
	@Override
	public void bind(ModelData model) {
		GWT.log("PaintFieldBinding.bind(" + model + ")");
		super.bind(model);
		field.addListener(Events.AfterEdit, afterEditListener);
		currentValue = model.get(property);
	}
	
	@Override
	public void unbind() {
		field.removeListener(Events.AfterEdit, afterEditListener);
		super.unbind();
	}
}
