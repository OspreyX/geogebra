package geogebra.touch.gui.laf;


import geogebra.touch.FileManagerM;
import geogebra.touch.TouchApp;
import geogebra.touch.gui.TabletGUI;
import geogebra.touch.gui.elements.StandardImageButton;
import geogebra.touch.gui.elements.header.TabletHeaderPanel;
import geogebra.touch.gui.elements.stylingbar.StylingBar;
import geogebra.touch.gui.euclidian.EuclidianViewPanel;
import geogebra.touch.model.TouchModel;
import geogebra.touch.utils.OptionType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.shared.EventHandler;

public class DefaultLAF implements LookAndFeel
{
	private TabletHeaderPanel hp;

	@Override
	public void buildHeader(TabletGUI gui, TouchApp app, TouchModel touchModel, FileManagerM fm)
	{
		this.hp = new TabletHeaderPanel(gui, app, touchModel, fm);
		gui.setHeaderWidget(this.hp);
		gui.addResizeListener(this.hp);
	}

	@Override
	public void setTitle(String title)
	{
		this.hp.setTitle(title);
	}

	@Override
	public int getPanelsHeight()
	{
		return 122;
	}

	@Override
  public TabletHeaderPanel getTabletHeaderPanel()
	{
		return this.hp;
	}

	@Override
	public int getAppBarHeight()
	{
		return 62;
	}

	@Override
	public DefaultIcons getIcons()
	{
		return DefaultIcons.INSTANCE;
	}
	
	@Override
  public Type getStylBarEventType()
  {
	  return ClickEvent.getType();
  }
	
	@Override
  public EventHandler getStyleBarHandlerShowHide(final StylingBar stylingBar, final EuclidianViewPanel euclidianViewPanel)
  {
	  return new ClickHandler()
		{			
			@Override
			public void onClick(final ClickEvent event)
			{
				stylingBar.onTouchStartShowHide(event, euclidianViewPanel);
			}
		};
  }

	@Override
  public EventHandler getStyleBarButtonHandler(final StylingBar stylingBar, final StandardImageButton newButton, final String process)
  {
	  return new ClickHandler()
		{			
			@Override
			public void onClick(final ClickEvent event)
			{
				stylingBar.onTouchStartStyleBarButton(event, newButton, process);
			}
		};
  }

	@Override
  public EventHandler getOptionalButtonHandler(final StylingBar stylingBar, final StandardImageButton standardImageButton, final OptionType type)
  {
		return new ClickHandler()
		{			
			@Override
			public void onClick(final ClickEvent event)
			{
				stylingBar.onTouchStartOptionalButton(event, standardImageButton, type);
			}
		};
  }
}
