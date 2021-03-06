/* 
GeoGebra - Dynamic Mathematics for Everyone
http://www.geogebra.org

This file is part of GeoGebra.

This program is free software; you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by 
the Free Software Foundation.

 */

package org.geogebra.common.kernel.statistics;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.algos.AlgoBarChart;
import org.geogebra.common.kernel.algos.DrawInformationAlgo;
import org.geogebra.common.kernel.arithmetic.NumberValue;
import org.geogebra.common.kernel.commands.Commands;
import org.geogebra.common.kernel.geos.GeoBoolean;
import org.geogebra.common.util.Cloner;

/**
 * @author G. Sturr
 * @version 2011-06-21
 */

public class AlgoBinomialDistBarChart extends AlgoBarChart {

	/**
	 * @param cons
	 *            construction
	 * @param label
	 *            label
	 * @param n
	 *            number of trials
	 * @param p
	 *            probability of success
	 */
	public AlgoBinomialDistBarChart(Construction cons, String label,
			NumberValue n, NumberValue p) {
		super(cons, label, n, p, null, null,
				AlgoBarChart.TYPE_BARCHART_BINOMIAL);
	}

	/**
	 * @param cons
	 *            construction
	 * @param label
	 *            label
	 * @param n
	 *            number of trials
	 * @param p
	 *            probability of success
	 * @param isCumulative
	 *            cumulative
	 */
	public AlgoBinomialDistBarChart(Construction cons, String label,
			NumberValue n, NumberValue p, GeoBoolean isCumulative) {
		super(cons, label, n, p, null, isCumulative,
				AlgoBarChart.TYPE_BARCHART_BINOMIAL);
	}

	private AlgoBinomialDistBarChart(NumberValue n, NumberValue p,
			GeoBoolean isCumulative, NumberValue a, NumberValue b,
			double[] vals, double[] borders, int N) {
		super(n, p, null, isCumulative, AlgoBarChart.TYPE_BARCHART_BINOMIAL, a,
				b, vals, borders, N);
	}

	@Override
	public Commands getClassName() {
		return Commands.BinomialDist;
	}

	@Override
	public DrawInformationAlgo copy() {
		GeoBoolean b = (GeoBoolean) this.getIsCumulative();
		if (b != null) {
			b = (GeoBoolean) b.copy();
		}

		return new AlgoBinomialDistBarChart((NumberValue) this.getP1()
				.deepCopy(kernel), (NumberValue) this.getP2().deepCopy(kernel),
				b, (NumberValue) this.getA().deepCopy(kernel),
				(NumberValue) this.getB().deepCopy(kernel),
				Cloner.clone(getValues()), Cloner.clone(getLeftBorder()),
				getIntervals());
	}

}
