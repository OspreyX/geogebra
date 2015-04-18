package org.geogebra.desktop.geogebra3D.input3D.zspace;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import org.geogebra.common.awt.GPoint;
import org.geogebra.common.euclidian3D.Input3D;
import org.geogebra.common.euclidian3D.Input3D.DeviceType;
import org.geogebra.common.geogebra3D.euclidian3D.EuclidianView3D;
import org.geogebra.common.kernel.Matrix.Coords;
import org.geogebra.common.main.App;


/**
 * controller with specific methods from leonar3do input system
 * @author mathieu
 *
 */
public class InputZSpace3D implements Input3D {

	
	private Socket socket;
	

	private double[] mousePosition;
	
	private double[] mouseOrientation;
	
	private boolean isRightPressed, isLeftPressed;
	
	
	
	private double[] glassesPosition;
	
	private double eyeSeparation;
	
	/**
	 * constructor
	 */
	public InputZSpace3D() {
		
		// 3D mouse position
		mousePosition = new double[3];
		
		// 3D mouse orientation
		mouseOrientation = new double[4];
		
		// glasses position
		glassesPosition = new double[3];
		
		
		
		socket = new Socket();
	}
	
	
	public boolean update(Point panelPosition, Dimension panelDimension){
	
		// set view port and check if changed
		boolean viewPortChanged = socket.setViewPort(panelPosition, panelDimension);
		
		// check if new message
		if (socket.getData() || viewPortChanged){
			
			
			
			// mouse position
			mousePosition[0] = socket.stylusX;
			mousePosition[1] = socket.stylusY;
			mousePosition[2] = socket.stylusZ;
			
			
			
			// mouse position
			mouseOrientation[0] = 1;//socket.birdOrientationX;
			mouseOrientation[1] = 0;//socket.birdOrientationY;
			mouseOrientation[2] = 0;//socket.birdOrientationZ;
			mouseOrientation[3] = 0;//socket.birdOrientationW;

			
			// right button
			isRightPressed = socket.buttonRight;
			
			// left button
			isLeftPressed = socket.buttonLeft;
			
			 
			
			
			
			// eye separation
			eyeSeparation = socket.getEyeSeparation();//(socket.leftEyeX - socket.rightEyeX) * screenHalfWidth;

			// glasses position
			glassesPosition[0] = socket.leftEyeX;//socket.leftEyeX * screenHalfWidth + eyeSeparation/2;
			glassesPosition[1] = socket.leftEyeY;//socket.leftEyeY * screenHalfWidth;
			glassesPosition[2] = socket.leftEyeZ;//socket.leftEyeZ * screenHalfWidth;

			
			return true;
			
		}
		
			
		
		return false;
		
	}
	
	public double[] getMouse3DPosition(){
		return mousePosition;
	}
	
	public double[] getMouse3DOrientation(){
		return mouseOrientation;
	}
	
	public boolean isRightPressed(){
		return isRightPressed;
	}
	
	public boolean isLeftPressed(){
		return isLeftPressed;
	}

	public double[] getGlassesPosition(){
		return glassesPosition;
	}
	
	public double getEyeSeparation(){
		return eyeSeparation;
	}
	
	public boolean useInputDepthForHitting(){
		return false;
	}
	
	public boolean useMouseRobot(){
		return true;
	}
	
//	@Override
//	public float getMouse2DX(){
//		return 0f;
//	}
//	
//	@Override
//	public float getMouse2DY(){
//		return 0f;
//	}
//	
//	@Override
//	public float getMouse2DFactor(){
//		return 1f;
//	}
	
	public DeviceType getDeviceType(){
		return DeviceType.PEN;
	}
	
	public boolean hasMouse(EuclidianView3D view3D){
		return view3D.hasMouse2D();
	}
	
	public boolean currentlyUseMouse2D(){
		return false;
	}
	
	public void setLeftButtonPressed(boolean flag){
		// not used for leo
	}
	
	public boolean getLeftButton(){
		return socket.buttonLeft;
	}

	public void setPositionXYOnPanel(double[] absolutePos, Coords panelPos, 
			double screenHalfWidth, double screenHalfHeight, int panelPositionX, int panelPositionY,
			int panelDimW, int panelDimH) {
		// transform has been done before
		panelPos.setX(absolutePos[0]);
		panelPos.setY(absolutePos[1]);
		panelPos.setZ(absolutePos[2]);
		
	}
	
	public boolean useScreenZOffset(){
		return false;
	}
	
	public boolean isStereoBuffered() {
		return true;
	}
	
	public boolean useInterlacedPolarization(){
		return false;
	}
	
	public boolean useCompletingDelay(){
		return false;
	}
	
}