package geogebra.touch.gui.elements.ggt;

import geogebra.common.move.ggtapi.models.Material;
import geogebra.html5.main.AppWeb;
import geogebra.touch.FileManagerM;

import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;

public class VerticalMaterialPanel extends ScrollPanel
{
	private FlexTable contentPanel;
	private AppWeb app;
	private FileManagerM fm;
	private MaterialListElement lastSelected;
	private int columns = 2;

	public VerticalMaterialPanel(AppWeb app, FileManagerM fm)
	{
		this.contentPanel = new FlexTable();
		this.app = app;
		this.fm = fm;

		this.setWidget(this.contentPanel);
	}

	public void setMaterials(List<Material> materials)
	{
		this.contentPanel.clear();

		int i = 0;
		for (Material m : materials)
		{
			MaterialListElement preview = new MaterialListElement(m, this.app, this.fm, this);
			this.contentPanel.setWidget(i / this.columns, i % this.columns, preview);
			i++;
		}
	}

	@Override
	public int getOffsetHeight()
	{
		return MaterialListElement.PANEL_HEIGHT;
	}

	@Override
	public void setWidth(String width)
	{
		super.setWidth(width);
		this.contentPanel.setWidth(width);
	}

	public void unselectMaterials() {
		if(this.lastSelected!=null){
			this.lastSelected.markUnSelected();
		}
		
	}

	public void rememberSelected(MaterialListElement materialElement) {
		this.lastSelected = materialElement;
	}

	public int getColumns() {
		return columns;
	}
}
