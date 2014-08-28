package geogebra.touch.main;

import geogebra.html5.gui.History;
import geogebra.html5.gui.laf.GLookAndFeel;
import geogebra.html5.util.ArticleElement;
import geogebra.touch.FileManager;
import geogebra.web.gui.app.GeoGebraAppFrame;
import geogebra.web.main.AppWapplication;

/**
 * App for tablets and phones
 *
 */
public class AppT extends AppWapplication {

	private History history;
	protected FileManager fm;
	
	public AppT(final ArticleElement article, final GeoGebraAppFrame geoGebraAppFrame,
            final boolean undoActive, final int dimension, final GLookAndFeel laf) {
	    super(article, geoGebraAppFrame, undoActive, dimension, laf);
    }
	
	public AppT(final ArticleElement article, final GeoGebraAppFrame geoGebraAppFrame, final int dimension, final GLookAndFeel laf) {
		super(article, geoGebraAppFrame, dimension, laf);
	}
	
	

	public History getHistory() {
	    if(this.history == null){
	    	this.history = new History();
	    }
	    return this.history;
    }
	
	/**
	 * different behavior for phone and tablet
	 * @return FileManagerInterface
	 */
	public FileManager getFileManager() {
		return null;
	}
}