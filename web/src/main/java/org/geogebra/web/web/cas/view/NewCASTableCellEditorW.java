package org.geogebra.web.web.cas.view;

import java.util.ArrayList;
import java.util.List;

import org.geogebra.common.cas.view.CASTableCellEditor;
import org.geogebra.common.euclidian.event.PointerEventType;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.main.App;
import org.geogebra.common.util.AsyncOperation;
import org.geogebra.web.html5.gui.inputfield.AutoCompleteW;
import org.geogebra.web.html5.gui.util.ClickStartHandler;
import org.geogebra.web.html5.gui.view.algebra.GeoContainer;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.html5.main.DrawEquationWeb;
import org.geogebra.web.web.gui.view.algebra.EquationEditor;
import org.geogebra.web.web.gui.view.algebra.EquationEditorListener;
import org.geogebra.web.web.gui.view.algebra.ScrollableSuggestionDisplay;

import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class NewCASTableCellEditorW extends Label implements
        CASTableCellEditor, CASEditorW,
 EquationEditorListener, GeoContainer {

	// private AutoCompleteTextFieldW textField;
	private CASTableW table;
	private AppW app;
	private EquationEditor editor;
	private String input;
	private final SpanElement seMayLaTex;
	private CASTableControllerW ml;

	public NewCASTableCellEditorW(CASTableW table, AppW app,
	        final CASTableControllerW ml) {
		this.app = app;
		this.table = table;
		this.ml = ml;
		this.editor = new EquationEditor(app, this);
		this.seMayLaTex = DOM.createSpan().cast();
		DrawEquationWeb.drawEquationAlgebraView(seMayLaTex, "", true);
		EquationEditor.updateNewStatic(seMayLaTex);
		DrawEquationWeb.editEquationMathQuillGGB(this, seMayLaTex, true);
		this.getElement().appendChild(seMayLaTex);
		this.getElement().addClassName("hasCursorPermanent");
		this.addDomHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				event.stopPropagation();

			}
		}, MouseUpEvent.getType());

		ClickStartHandler.init(this, new ClickStartHandler() {

			@Override
			public void onClickStart(int x, int y, PointerEventType type) {
				editor.setFocus(true);
			}
		});

		/*
		 * textField = new AutoCompleteTextFieldW(0, app, true, null, true);
		 * textField.setCASInput(true); textField.setAutoComplete(true);
		 * textField.requestToShowSymbolButton();
		 * textField.showPopupSymbolButton(true);
		 * textField.addKeyPressHandler(new KeyPressHandler() {
		 * 
		 * @Override public void onKeyPress(KeyPressEvent event) { if
		 * (!textField.isSuggestionJustHappened()) { new
		 * KeyListenerW(ml).onKeyPress(event); } if (event.getCharCode() == 10
		 * || event.getCharCode() == 13) { event.preventDefault(); }
		 * textField.setIsSuggestionJustHappened(false); } });
		 * 
		 * textField.addBlurHandler(ml);
		 */
		// FIXME experimental fix for CAS in other languages, broken in r27612
		// This will update the CAS commands also
		app.updateCommandDictionary();
		Timer tim = new Timer() {
			@Override
			public void run() {
				editor.setFocus(true);
			}
		};
		tim.schedule(2000);


	}

	public int getInputSelectionEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getInputSelectionStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getInputSelectedText() {
		// TODO Auto-generated method stub
		return "";
	}

	public String getInput() {
		// TODO Auto-generated method stub
		return this.input;
	}

	public void setInputSelectionStart(int selStart) {
		// TODO Auto-generated method stub

	}

	public void setInputSelectionEnd(int selEnd) {
		// TODO Auto-generated method stub

	}

	public AutoCompleteW getWidget() {
		return this;
	}

	public void setLabels() {
		// TODO
	}

	public void setInput(String input) {
		editor.setText(input);
	}

	public void clearInputText() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean getAutoComplete() {
		return true;
	}

	@Override
	public List<String> resetCompletions() {
		return editor.resetCompletions();
	}

	@Override
	public List<String> getCompletions() {
		return editor.getCompletions();
	}

	@Override
	public void setFocus(boolean b) {
		editor.setFocus(true);
	}

	@Override
	public void insertString(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toggleSymbolButton(boolean toggled) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<String> getHistory() {
		// TODO Auto-generated method stub
		return editor.getHistory();
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return input;
	}

	@Override
	public void setText(String s) {
		editor.setText(s);

	}

	@Override
	public boolean isSuggesting() {
		return editor.getSug().isSuggestionListShowing();
	}

	@Override
	public void requestFocus() {
		setFocus(true);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public SpanElement getLaTeXSpan() {
		return this.seMayLaTex;
	}

	@Override
	public void updatePosition(ScrollableSuggestionDisplay sug) {
		sug.setPositionRelativeTo(this);

	}

	@Override
	public GeoElement getGeo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hideSuggestions() {
		return editor.hideSuggestions();
	}

	@Override
	public boolean stopNewFormulaCreation(String input2, String latex,
	        AsyncOperation callback) {
		// TODO Auto-generated method stub
		App.debug("STOPPED" + input2 + "," + latex);
		this.editor.addToHistory(input2, latex);
		this.input = input2;
		this.ml.handleEnterKey(false, false, app);
		return false;
	}

	@Override
	public boolean popupSuggestions() {
		return editor.popupSuggestions();
	}

	@Override
	public boolean stopEditing(String latex) {
		// TODO Auto-generated method stub
		App.debug("STOPPED" + latex);
		return false;
	}

	@Override
	public void scrollIntoView() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean shuffleSuggestions(boolean down) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public App getApplication() {
		return app;
	}

	@Override
	public Widget toWidget() {
		return this;
	}
}
