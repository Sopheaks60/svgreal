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
package org.vectomatic.svg.edit.client.load;

import org.vectomatic.dom.svg.utils.OMSVGParser;
import org.vectomatic.dom.svg.utils.ParserException;
import org.vectomatic.file.File;
import org.vectomatic.file.FileReader;
import org.vectomatic.file.events.LoadEndEvent;
import org.vectomatic.file.events.LoadEndHandler;
import org.vectomatic.svg.edit.client.AppConstants;
import org.vectomatic.svg.edit.client.AppMessages;
import org.vectomatic.svg.edit.client.SvgrealApp;

/**
 * Class to load files into the application
 * @author laaglu
 */
public class FileLoadRequest extends LoadRequestBase {
	private File file;

	public FileLoadRequest(File file) {
		this.file = file;
		this.title = file.getName();
	}

	@Override
	public void load() {
		final FileReader reader = new FileReader();
		reader.addLoadEndHandler(new LoadEndHandler() {
			
			@Override
			public void onLoadEnd(LoadEndEvent event) {
				SvgrealApp app = SvgrealApp.getApp();
				try {
					app.addWindow(OMSVGParser.parse(reader.getStringResult()), FileLoadRequest.this);
				} catch(ParserException e) {
					app.info(AppConstants.INSTANCE.openLocalMenuItem(), AppMessages.INSTANCE.loadErrorMessage(file.getName(), e.getMessage()));
				}
				
			}
		});
		reader.readAsText(file);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof FileLoadRequest) {
			FileLoadRequest r = (FileLoadRequest)o;
			return file.getName().equals(r.file.getName()) && file.getSize() == r.file.getSize();
		}
		return false;
	}
	@Override
	public int hashCode() {
		return file.getName().hashCode();
	}

}
