/* 
GeoGebra - Dynamic Mathematics for Everyone
http://www.geogebra.org

This file is part of GeoGebra.

This program is free software; you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by 
the Free Software Foundation.

*/

/*
 * AlgoAngleVector.java
 *
 * Created on 30. August 2001, 21:37
 */

package geogebra.common.kernel.algos;

import geogebra.common.euclidian.draw.DrawAngle;
import geogebra.common.kernel.Construction;
import geogebra.common.kernel.StringTemplate;
import geogebra.common.kernel.Matrix.Coords;
import geogebra.common.kernel.geos.GeoAngle;
import geogebra.common.kernel.geos.GeoElement;
import geogebra.common.kernel.kernelND.GeoDirectionND;
import geogebra.common.kernel.kernelND.GeoPointND;
import geogebra.common.kernel.kernelND.GeoVectorND;

public abstract class AlgoAngleVectorsND extends AlgoAngle {

	protected GeoVectorND v, w; // input
    protected GeoAngle angle; // output           

    public AlgoAngleVectorsND(
            Construction cons,
            String label,
            GeoVectorND v,
            GeoVectorND w) {
    	
    	this(cons, label, v, w, null);
    }
    
    public AlgoAngleVectorsND(
        Construction cons,
        String label,
        GeoVectorND v,
        GeoVectorND w, GeoDirectionND orientation) {
        super(cons);
        setInput(v, w, orientation);
        angle = newGeoAngle(cons);
        setInputOutput(); // for AlgoElement

        // compute angle
        compute();
        angle.setLabel(label);
    }

    
    // for AlgoElement
    @Override
	protected void setInputOutput() {
        input = new GeoElement[2];
        input[0] = (GeoElement) v;
        input[1] = (GeoElement) w;

        setOutputLength(1);
        setOutput(0,angle);
        setDependencies(); // done by AlgoElement
    }
    
	/**
	 * set inputs
	 * @param v first vector
	 * @param w second vector
	 * @param orientation orientation
	 */
	protected void setInput(GeoVectorND v, GeoVectorND w, GeoDirectionND orientation){
		this.v = v;
		this.w = w;
	}

    public GeoAngle getAngle() {
        return angle;
    }
    public GeoVectorND getv() {
        return v;
    }
    public GeoVectorND getw() {
        return w;
    }


    @Override
	public String toString(StringTemplate tpl) {
        // Michael Borcherds 2008-03-30
        // simplified to allow better Chinese translation
        return loc.getPlain("AngleBetweenAB",v.getLabel(tpl),w.getLabel(tpl));

    }
    

    

	@Override
	public boolean updateDrawInfo(double[] m, double[] firstVec, DrawAngle drawable) {
		GeoPointND vertex = v.getStartPoint();
		if (centerIsNotDrawable(vertex)){
			return false;
		}

		
		Coords origin = drawable.getCoordsInView(vertex);
		if (!drawable.inView(origin)) {
			return false;
		}
		
		
		Coords direction = drawable.getCoordsInView(v.getCoordsInD(3));
		if (!drawable.inView(direction)) {
			return false;
		}

		// origin
		m[0] = origin.get()[0];
		m[1] = origin.get()[1];		

		// first vec
		firstVec[0] = direction.getX();
		firstVec[1] = direction.getY();
		
		return true;

	}

	
	@Override
	public boolean getCoordsInD3(Coords[] drawCoords){
		GeoPointND vertex = v.getStartPoint();
		if (centerIsNotDrawable(vertex)){
			return false;
		}			
			
		drawCoords[0] = vertex.getInhomCoordsInD(3);
		drawCoords[1] = v.getCoordsInD(3);
		drawCoords[2] = w.getCoordsInD(3);
		
		return true;
	}
	
	// TODO Consider locusequability
	

	
}