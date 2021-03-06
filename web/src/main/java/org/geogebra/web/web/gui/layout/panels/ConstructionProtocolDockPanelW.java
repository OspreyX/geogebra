package org.geogebra.web.web.gui.layout.panels;

import org.geogebra.common.main.App;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.web.gui.layout.DockPanelW;
import org.geogebra.web.web.gui.util.StyleBarW;
import org.geogebra.web.web.gui.view.consprotocol.ConstructionProtocolViewW;

import com.google.gwt.resources.client.ResourcePrototype;
import com.google.gwt.user.client.ui.Widget;

public class ConstructionProtocolDockPanelW extends DockPanelW{

	private static final long serialVersionUID = 1L;
	private StyleBarW cpStyleBar;

	/**
	 * @param app
	 */
	public ConstructionProtocolDockPanelW(AppW app) {
		super(
			App.VIEW_CONSTRUCTION_PROTOCOL, 	// view id
			"ConstructionProtocol", 					// view title phrase 
			null,	// toolbar string
			true,					// style bar?
			7,						// menu order
			'L' // ctrl-shift-L
		);
		

		this.app = app;
		this.setShowStyleBar(true);
		this.setEmbeddedSize(300);
	}

	@Override
	protected Widget loadComponent() {
		setViewImage(getResources().styleBar_ConstructionProtocol());
		return ((ConstructionProtocolViewW) app.getGuiManager().getConstructionProtocolView()).getCpPanel();
	}

	@Override
	protected Widget loadStyleBar() {
		if (cpStyleBar == null) {
			cpStyleBar = ((ConstructionProtocolViewW) app.getGuiManager().getConstructionProtocolView()).getStyleBar();
		}
		return cpStyleBar; 
		//return ((ConstructionProtocolView)app.getGuiManager().getConstructionProtocolView()).getStyleBar();
	}
	
	@Override
    public void showView(boolean b) {
	    // TODO Auto-generated method stub
	    
    }
	
	@Override
    public ResourcePrototype getIcon() {
		return getResources().menu_icon_construction_protocol();
	}
	
}
