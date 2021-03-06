package org.geogebra.web.web.main;

import org.geogebra.common.GeoGebraConstants;
import org.geogebra.web.html5.gui.laf.GLookAndFeelI;
import org.geogebra.web.html5.gui.tooltip.ToolTipManagerW;
import org.geogebra.web.html5.gui.tooltip.ToolTipManagerW.ToolTipLinkType;
import org.geogebra.web.html5.gui.util.CancelEventTimer;
import org.geogebra.web.html5.gui.view.algebra.MathKeyboardListener;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.html5.util.ArticleElement;
import org.geogebra.web.web.util.keyboard.OnScreenKeyBoard;

public abstract class AppWFull extends AppW {

	protected AppWFull(ArticleElement ae, int dimension, GLookAndFeelI laf) {
		super(ae, dimension, laf);
	}

	@Override
	public void showKeyboard(MathKeyboardListener textField) {
		if(getLAF().isSmart()){
			return;
		}
		getAppletFrame().showKeyBoard(true, textField, false);
		if (textField != null) {
			CancelEventTimer.keyboardSetVisible();
		}
	}

	@Override
	public void showKeyboard(MathKeyboardListener textField, boolean forceShow) {
		if(getLAF().isSmart()){
			return;
		}
		getAppletFrame().showKeyBoard(true, textField, forceShow);
		if (textField != null) {
			CancelEventTimer.keyboardSetVisible();
		}
	}

	@Override
	public void updateKeyBoardField(MathKeyboardListener field) {
		OnScreenKeyBoard.setInstanceTextField(this, field);
	}

	@Override
	public void hideKeyboard() {
		getAppletFrame().showKeyBoard(false, null, false);
	}

	public void showStartTooltip() {
		if (articleElement.getDataParamShowStartTooltip()) {
			ToolTipManagerW.sharedInstance().setBlockToolTip(false);
			ToolTipManagerW.sharedInstance().showBottomInfoToolTip(
			        getPlain("NewToGeoGebra") + "<br/>"
			                + getPlain("ClickHereToGetHelp"),
			        GeoGebraConstants.QUICKSTART_URL, ToolTipLinkType.Help,
			        this);
			ToolTipManagerW.sharedInstance().setBlockToolTip(true);
		}
	}
}
