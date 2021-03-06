package org.geogebra.web.web.main;

import org.geogebra.common.main.App;
import org.geogebra.common.main.GeoGebraPreferences;
import org.geogebra.web.web.css.GuiResources;

import com.google.gwt.storage.client.Storage;

public class GeoGebraPreferencesW extends GeoGebraPreferences {

	private static GeoGebraPreferencesW singleton;

	public static GeoGebraPreferencesW getPref() {
		if (singleton == null) {
			singleton = new GeoGebraPreferencesW();
		}
		return singleton;
	}

	public void clearPreferences() {
		Storage stockStore = null;
		stockStore = Storage.getLocalStorageIfSupported();
		if (stockStore != null) {
			stockStore.removeItem(XML_USER_PREFERENCES);
			stockStore.removeItem(XML_DEFAULT_OBJECT_PREFERENCES);
		}

	}

	public void loadXMLPreferences(final App app) {

		app.setXML(GuiResources.INSTANCE.preferencesXML().getText(), false);
	}

	public void saveXMLPreferences(App app) {
		String xml = app.getPreferencesXML();
		Storage stockStore = null;
		stockStore = Storage.getLocalStorageIfSupported();
		if (stockStore != null) {
			stockStore.setItem(XML_USER_PREFERENCES, xml);
			String xmlDef = app.getKernel().getConstruction()
			        .getConstructionDefaults().getCDXML();
			stockStore.setItem(XML_DEFAULT_OBJECT_PREFERENCES, xmlDef);
		}
	}
}
