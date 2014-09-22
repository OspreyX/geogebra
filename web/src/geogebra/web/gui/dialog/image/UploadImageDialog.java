package geogebra.web.gui.dialog.image;

import geogebra.html5.main.AppW;
import geogebra.web.gui.util.VerticalSeparator;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class UploadImageDialog extends DialogBox implements
        ClickHandler {

	protected HorizontalPanel mainPanel;
	protected VerticalPanel listPanel;
	protected VerticalPanel imagePanel;
	protected FlowPanel bottomPanel;
	protected SimplePanel inputPanel;
	protected UploadImagePanel uploadImagePanel;
	protected AppW app;
	protected Button insertBtn;
	protected Button cancelBtn;
	protected Label upload;
	
	String previewHeight;
	String previewWidth;

	public UploadImageDialog(AppW app, String previewWidth, String previewHeight) {
		this.app = app;
		this.previewWidth = previewWidth;
		this.previewHeight = previewHeight;
		initGUI();
		initActions();
		setLabels();
		center();
		uploadClicked();
	}

	protected void initGUI() {
		add(mainPanel = new HorizontalPanel());

		mainPanel.add(listPanel = new VerticalPanel());
		listPanel.add(upload = new Label(""));
		// listPanel.add(webcam = new Label(""));
		listPanel.setSpacing(10);

		mainPanel.add(new VerticalSeparator(200));
		mainPanel.setSpacing(5);
		mainPanel.add(imagePanel = new VerticalPanel());

		imagePanel.add(inputPanel = new SimplePanel());
		inputPanel.setHeight("180px");
		inputPanel.setWidth("240px");

		uploadImagePanel = new UploadImagePanel(this, app, previewWidth, previewHeight);
		imagePanel.add(bottomPanel = new FlowPanel());

		bottomPanel.add(insertBtn = new Button(""));
		bottomPanel.add(cancelBtn = new Button(""));
		insertBtn.setEnabled(false);

		bottomPanel.setStyleName("DialogButtonPanel");
		addStyleName("GeoGebraPopup");
		addStyleName("image");
		setGlassEnabled(true);
	}

	protected void initActions() {
		insertBtn.addClickHandler(this);
		cancelBtn.addClickHandler(this);
		upload.addClickHandler(this);
	}

	public void setLabels() {
		getCaption().setText(app.getMenu("Image"));
		upload.setText(app.getMenu("File"));
		insertBtn.setText(app.getPlain("OK"));
		cancelBtn.setText(app.getMenu("Cancel"));
	}

	protected void uploadClicked() {
		upload.addStyleDependentName("highlighted");
		inputPanel.setWidget(uploadImagePanel);
	}
	
	public void imageAvailable() {
		insertBtn.setEnabled(true);
	}

	public void imageUnavailable() {
		insertBtn.setEnabled(false);
	}

}