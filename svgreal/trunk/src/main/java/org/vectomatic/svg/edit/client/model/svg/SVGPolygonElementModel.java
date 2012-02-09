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
package org.vectomatic.svg.edit.client.model.svg;

import java.util.ArrayList;
import java.util.List;

import org.vectomatic.dom.svg.impl.SVGElement;
import org.vectomatic.dom.svg.impl.SVGPolygonElement;
import org.vectomatic.dom.svg.utils.SVGConstants;
import org.vectomatic.svg.edit.client.AppBundle;
import org.vectomatic.svg.edit.client.command.EditGeometryCommandFactory;
import org.vectomatic.svg.edit.client.command.EditTransformCommandFactory;
import org.vectomatic.svg.edit.client.command.IFactoryInstantiator;
import org.vectomatic.svg.edit.client.engine.SVGModel;
import org.vectomatic.svg.edit.client.inspector.SVGPointsSectionFactory;
import org.vectomatic.svg.edit.client.model.MetaModel;
import org.vectomatic.svg.edit.client.model.MetadataBase;
import org.vectomatic.svg.edit.client.model.ModelCategory;
import org.vectomatic.svg.edit.client.model.ModelConstants;

/**
 * Polygon model class.
 * @author laaglu
 */
public class SVGPolygonElementModel extends SVGAnimatedPointsModelBase {
	private static MetaModel<SVGElement> metaModel;

	public SVGPolygonElementModel(SVGModel owner, SVGPolygonElement element, SVGPolygonElement twin) {
		super(owner, element, twin);
	}
	
	@Override
	public MetaModel<SVGElement> getMetaModel() {
		return getPolygonElementMetaModel();
	}
	
	public static MetaModel<SVGElement> getPolygonElementMetaModel() {
		if (metaModel == null) {
			metaModel = new MetaModel<SVGElement>();

			ModelConstants constants = ModelConstants.INSTANCE;

			ModelCategory<SVGElement> geometricCategory = new ModelCategory<SVGElement>(
					ModelCategory.GEOMETRY, 
					constants.geometry(), 
					SVGPointsSectionFactory.INSTANCE);
			MetadataBase<SVGPoints, SVGElement> points = new SVGPointsMetadata(
					SVGConstants.SVG_POINTS_ATTRIBUTE, 
					constants.polygonVertices(),
					EditGeometryCommandFactory.INSTANTIATOR,
					SVGPoints.POINTS_VALIDATOR);
			geometricCategory.addMetadata(points);
			IFactoryInstantiator<?>[][] contextMenuFactories = new IFactoryInstantiator<?>[][] {
				{
					EditGeometryCommandFactory.INSTANTIATOR,
					EditTransformCommandFactory.INSTANTIATOR
				}
			};
			List<ModelCategory<SVGElement>> categories = new ArrayList<ModelCategory<SVGElement>>();
			categories.add(SVGNamedElementModel.getGlobalCategory());
			categories.add(SVGElementModel.getDisplayCategory());
			categories.add(geometricCategory);
			categories.add(SVGStyledElementModel.createStrokeFillCategory(
					new String[] {
						SVGConstants.CSS_FILL_PROPERTY,
						SVGConstants.CSS_FILL_OPACITY_PROPERTY,
						SVGConstants.CSS_FILL_RULE_PROPERTY,
						SVGConstants.CSS_STROKE_PROPERTY,
						SVGConstants.CSS_STROKE_OPACITY_PROPERTY,
						SVGConstants.CSS_STROKE_WIDTH_PROPERTY,
						SVGConstants.CSS_STROKE_LINECAP_PROPERTY,
						SVGConstants.CSS_STROKE_LINEJOIN_PROPERTY,
						SVGConstants.CSS_STROKE_MITERLIMIT_PROPERTY,
						SVGConstants.CSS_STROKE_DASHARRAY_PROPERTY,
						SVGConstants.CSS_STROKE_DASHOFFSET_PROPERTY
					}));
			categories.add(SVGElementModel.getTransformCategory());
			metaModel.init(
				constants.polygon(),
				AppBundle.INSTANCE.polygon(), 
				categories,
				contextMenuFactories);

		}
		return metaModel;
	}
}
