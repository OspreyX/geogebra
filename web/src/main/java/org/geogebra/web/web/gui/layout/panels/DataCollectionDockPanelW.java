package org.geogebra.web.web.gui.layout.panels;


import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.web.gui.GuiManagerW;
import org.geogebra.web.web.gui.layout.DockPanelW;

import com.google.gwt.user.client.ui.Widget;


public class DataCollectionDockPanelW extends DockPanelW {
	
	public DataCollectionDockPanelW(AppW app) {
		super(AppW.VIEW_DATA_COLLECTION, // view id
				"DataCollection", // view title phrase
				null, // toolbar string
				false, // style bar?
				-1 // menu order
		);
	}

	@Override
	protected Widget loadComponent() {
		return ((GuiManagerW) app.getGuiManager()).getDataCollectionView();
	}

	@Override
	public void showView(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void focusGained() {
		((GuiManagerW) app.getGuiManager()).updateDataCollectionView();
	}
}
