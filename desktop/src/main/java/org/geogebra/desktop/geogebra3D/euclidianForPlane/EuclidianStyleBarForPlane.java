package org.geogebra.desktop.geogebra3D.euclidianForPlane;

import java.util.ArrayList;

import org.geogebra.common.euclidian.EuclidianConstants;
import org.geogebra.common.euclidian.EuclidianView;
import org.geogebra.common.geogebra3D.euclidianForPlane.EuclidianViewForPlaneCompanion;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.desktop.euclidian.EuclidianStyleBarD;
import org.geogebra.desktop.gui.util.MyToggleButton;

/**
 * StyleBar for view for plane
 * 
 * @author matthieu
 *
 */
public class EuclidianStyleBarForPlane extends EuclidianStyleBarD {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MyToggleButton btnCenterAndOrientation;

	/**
	 * Common constructor.
	 * 
	 * @param ev
	 */
	public EuclidianStyleBarForPlane(EuclidianViewForPlaneD ev) {
		super(ev);
	}

	@Override
	protected void addGraphicsDecorationsButtons() {
		// add(btnShowAxes);
		add(btnShowGrid);
	}

	@Override
	protected void addBtnRotateView() {

		add(btnCenterAndOrientation);

	}

	@Override
	protected boolean isVisibleInThisView(GeoElement geo) {
		return geo.isVisibleInViewForPlane();
	}

	@Override
	protected void processSource(Object source, ArrayList<GeoElement> targetGeos) {

		if (source.equals(btnCenterAndOrientation)) {
			EuclidianViewForPlaneCompanion companion = (EuclidianViewForPlaneCompanion) ((EuclidianView) ev)
					.getCompanion();
			companion.updateCenterAndOrientationRegardingView();
			companion.updateScaleRegardingView();
		} else
			super.processSource(source, targetGeos);
	}

	@Override
	protected void createButtons() {

		super.createButtons();

		// ========================================
		// button
		btnCenterAndOrientation = new MyToggleButton(
				app.getScaledIcon("standard_view.gif"), iconHeight) {

			private static final long serialVersionUID = 1L;

			@Override
			public void update(Object[] geos) {
				// always show this button unless in pen mode
				this.setVisible(mode != EuclidianConstants.MODE_PEN);
			}
		};
		btnCenterAndOrientation.addActionListener(this);

	}

	@Override
	public void setLabels() {
		super.setLabels();
		btnCenterAndOrientation.setToolTipText(loc
				.getPlainTooltip("stylebar.ViewDefault"));

	}

	@Override
	public void updateGUI() {
		super.updateGUI();

		btnCenterAndOrientation.removeActionListener(this);
		btnCenterAndOrientation.setSelected(false);
		btnCenterAndOrientation.addActionListener(this);

	}

}
