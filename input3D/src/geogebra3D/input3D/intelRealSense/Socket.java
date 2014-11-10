package geogebra3D.input3D.intelRealSense;

import intel.rssdk.PXCMCapture;
import intel.rssdk.PXCMCaptureManager;
import intel.rssdk.PXCMHandConfiguration;
import intel.rssdk.PXCMHandConfiguration.GestureHandler;
import intel.rssdk.PXCMHandData.GestureData;
import intel.rssdk.PXCMHandData;
import intel.rssdk.PXCMHandModule;
import intel.rssdk.PXCMPoint3DF32;
import intel.rssdk.PXCMPoint4DF32;
import intel.rssdk.PXCMPointF32;
import intel.rssdk.PXCMSenseManager;
import intel.rssdk.PXCMSession;
import intel.rssdk.pxcmStatus;
import geogebra.common.main.App;



public class Socket {


	private static double SCREEN_REAL_DIM_FACTOR = 1/0.4;
	private static int SAMPLES = 7;

	public enum Gestures {PINCH, SPREAD, FIST};

	/** bird x position */
	public double birdX;
	/** bird y position */
	public double birdY;
	/** bird z position */
	public double birdZ;

	public double birdOrientationX, birdOrientationY, birdOrientationZ, birdOrientationW;

	public double leftEyeX, leftEyeY, leftEyeZ;
	public double rightEyeX, rightEyeY, rightEyeZ;
	public double glassesCenterX, glassesCenterY, glassesCenterZ;
	public double glassesOrientationX, glassesOrientationY, glassesOrientationZ, glassesOrientationW;

	public double bigButton, smallButton, vibration;

	/** says if it has got a message from leo */
	public boolean gotMessage = false;      

	private PXCMSenseManager senseMgr;
	private pxcmStatus sts;
	private PXCMHandData handData;
	
	private DataAverage dataAverage;
	
	private class DataAverage{
		
		private int samples;
		private int index;
		
		private float[] worldX, worldY, worldZ;
		private float worldXSum, worldYSum, worldZSum;
		
		private float[] handOrientationX, handOrientationY, handOrientationZ, handOrientationW;
		private float handOrientationXSum, handOrientationYSum, handOrientationZSum, handOrientationWSum;
		
		public DataAverage(int samples){
			this.samples = samples;
			index = 0;
			
			worldX = new float[samples];
			worldY = new float[samples];
			worldZ = new float[samples];
			
			worldXSum = 0f;
			worldYSum = 0f;
			worldZSum = 0f;
			
			handOrientationX = new float[samples];
			handOrientationY = new float[samples];
			handOrientationZ = new float[samples];
			handOrientationW = new float[samples];
			
			handOrientationXSum = 0f;
			handOrientationYSum = 0f;
			handOrientationZSum = 0f;
			handOrientationWSum = 0f;
			

		}
		
		public void addData(float wx, float wy, float wz,
				float ox, float oy, float oz, float ow){
			
			worldXSum -= worldX[index];
			worldYSum -= worldY[index];
			worldZSum -= worldZ[index];
			
			worldX[index] = wx;
			worldY[index] = wy;
			worldZ[index] = wz;
			
			worldXSum += worldX[index];
			worldYSum += worldY[index];
			worldZSum += worldZ[index];
			
			
			handOrientationXSum -= handOrientationX[index];
			handOrientationYSum -= handOrientationY[index];
			handOrientationZSum -= handOrientationZ[index];
			handOrientationWSum -= handOrientationW[index];

			handOrientationX[index] = ox;
			handOrientationY[index] = oy;
			handOrientationZ[index] = oz;
			handOrientationW[index] = ow;

			handOrientationXSum += handOrientationX[index];
			handOrientationYSum += handOrientationY[index];
			handOrientationZSum += handOrientationZ[index];
			handOrientationWSum += handOrientationW[index];

			index++;
			if (index >= samples){
				index = 0;
			}
		}
		
		public double getWorldX(){
			return -worldXSum * SCREEN_REAL_DIM_FACTOR / samples;
		}
		
		public double getWorldY(){
			return worldYSum * SCREEN_REAL_DIM_FACTOR / samples;
		}
		
		public double getWorldZ(){
			return (worldZSum / samples - 0.2f) * SCREEN_REAL_DIM_FACTOR;
		}
		
		public double getHandOrientationX(){
			return handOrientationXSum / samples;
		}
		
		public double getHandOrientationY(){
			return handOrientationYSum / samples;
		}
		
		public double getHandOrientationZ(){
			return handOrientationZSum / samples;
		}
		
		public double getHandOrientationW(){
			return handOrientationWSum / samples;
		}
		
	
		
		private Gestures gesture = Gestures.SPREAD;
		
		private int handId = 0;
		
		public void setGesture(int id, String name){
			
			App.debug(id+" : "+name);
			
			if (handId == 0){
				handId = id;
			}else{
				if (handId != id){
					App.error("id : "+id);
					return;
				}
			}
			
			switch(name.charAt(0)){
			case 'f':
				/*
				if (name.equals("fist")){
					gesture = Gestures.FIST;
				}
				*/
				break;
			case 's':
				if (name.equals("spreadfingers")){
					gesture = Gestures.SPREAD;
				}
				break;
			case 't':
				if (name.equals("two_fingers_pinch_open")){
					gesture = Gestures.PINCH;
				}
				break;
			default:
				//gesture = Gestures.SPREAD;
				break;

			}
			
			App.debug(""+gesture);
		}
		
		public Gestures getGesture(){
			return gesture;
		}
		
	}


	public Socket() {

		App.debug("Try to connect realsense...");

		// Create session
		PXCMSession session = PXCMSession.CreateInstance();
		if (session == null) {
			App.error("Failed to create a session instance\n");
			return;
		}

		senseMgr = session.CreateSenseManager();
		if (senseMgr == null) {
			App.error("Failed to create a SenseManager instance\n");
			return;
		}

		PXCMCaptureManager captureMgr = senseMgr.QueryCaptureManager();
		captureMgr.FilterByDeviceInfo("RealSense", null, 0);

		sts = senseMgr.EnableHand(null);
		if (sts.compareTo(pxcmStatus.PXCM_STATUS_NO_ERROR)<0) {
			App.error("Failed to enable HandAnalysis\n");
			return;
		}

		dataAverage = new DataAverage(SAMPLES);

		sts = senseMgr.Init();
		if (sts.compareTo(pxcmStatus.PXCM_STATUS_NO_ERROR)>=0) {
			PXCMHandModule handModule = senseMgr.QueryHand(); 
			PXCMHandConfiguration handConfig = handModule.CreateActiveConfiguration(); 
			handConfig.EnableAllGestures();
			handConfig.EnableAllAlerts();
			GestureHandler handler = new GestureHandler() {
				@Override
				public void OnFiredGesture(GestureData data) {
					//App.debug(""+data.name+" -- "+data.handId);
					dataAverage.setGesture(data.handId, data.name);
				}
			};
			handConfig.SubscribeGesture(handler);
			handConfig.ApplyChanges();
			handConfig.Update();
			
			handData = handModule.CreateOutput();
			
			
			connected = true;
		}

		App.debug("connected to RealSense: "+connected);
		
	}





	private boolean connected = false;




	public boolean getData(){

		if (!connected)
			return false;

		sts = senseMgr.AcquireFrame(true);
		if (sts.compareTo(pxcmStatus.PXCM_STATUS_NO_ERROR)<0){
			gotMessage = false;
			senseMgr.ReleaseFrame();
			return false;
		};

		PXCMCapture.Sample sample = senseMgr.QueryHandSample();

		// Query and Display Joint of Hand or Palm
		handData.Update(); 

		PXCMHandData.IHand hand = new PXCMHandData.IHand(); 
		sts = handData.QueryHandData(PXCMHandData.AccessOrderType.ACCESS_ORDER_NEAR_TO_FAR, 0, hand);

		if (sts.compareTo(pxcmStatus.PXCM_STATUS_NO_ERROR) >= 0) {
			//PXCMPointF32 image = hand.QueryMassCenterImage();
			PXCMPoint3DF32 world = hand.QueryMassCenterWorld();
			PXCMPoint4DF32 palmOrientation = hand.QueryPalmOrientation();

			/*
			System.out.println("Palm Center : ");
			System.out.print("   Image Position: (" + image.x + "," +image.y + ")");
			System.out.println("   World Position: (" + world.x + "," + world.y + "," + world.z + ")");
			*/
		
			/*
			birdX = -world.x * SCREEN_REAL_DIM_FACTOR;
			birdY = world.y * SCREEN_REAL_DIM_FACTOR;
			birdZ = (world.z-0.3) * SCREEN_REAL_DIM_FACTOR;
			*/
			
			dataAverage.addData(world.x, world.y, world.z,
					palmOrientation.x, palmOrientation.y, palmOrientation.z, palmOrientation.w);
			birdX = dataAverage.getWorldX();
			birdY = dataAverage.getWorldY();
			birdZ = dataAverage.getWorldZ();
			
			/*
			birdOrientationX = dataAverage.getHandOrientationX();
			birdOrientationY = dataAverage.getHandOrientationY();
			birdOrientationZ = dataAverage.getHandOrientationZ();
			birdOrientationW = dataAverage.getHandOrientationW();
			*/
			
			switch(dataAverage.getGesture()){
			case PINCH:
				smallButton = 1;
				bigButton = 0;
				break;
			case FIST:
				smallButton = 0;
				bigButton = 1;
				break;
			default:
				smallButton = 0;
				bigButton = 0;
				break;
			}

			gotMessage = true;
			
		}else{
			gotMessage = false;
		}

		/*
		// alerts
		int nalerts = handData.QueryFiredAlertsNumber();
		//System.out.println("# of alerts is " + nalerts);

		// gestures
		int ngestures = handData.QueryFiredGesturesNumber();
		//System.out.println("# of gestures at frame is " + ngestures);
		 
		 */

		senseMgr.ReleaseFrame();

		


		return true;
	}

	/*
    public void onMessage(String msg) {

        try {
			byte[] buffer = Base64.decode(msg);
			ByteBuffer bb = ByteBuffer.wrap(buffer);
			bb.order(ByteOrder.LITTLE_ENDIAN);

			// ignore leo version
			bb.getDouble();

			// bird position
			birdX = bb.getDouble();
			birdY = bb.getDouble();
			birdZ = bb.getDouble();

			// bird orientation
			birdOrientationX = bb.getDouble();
			birdOrientationY = bb.getDouble();
			birdOrientationZ = bb.getDouble();
			birdOrientationW = bb.getDouble();

			//glasses
			leftEyeX = bb.getDouble(); 
			leftEyeY = bb.getDouble(); 
			leftEyeZ = bb.getDouble();

			rightEyeX = bb.getDouble(); 
			rightEyeY = bb.getDouble(); 
			rightEyeZ = bb.getDouble();

			glassesCenterX = bb.getDouble(); 
			glassesCenterY = bb.getDouble(); 
			glassesCenterZ = bb.getDouble();

			glassesOrientationX = bb.getDouble(); 
			glassesOrientationY = bb.getDouble(); 
			glassesOrientationZ = bb.getDouble(); 
			glassesOrientationW = bb.getDouble();

			//buttons
			bigButton = bb.getDouble(); 
			smallButton = bb.getDouble(); 

			//vibration
			vibration = bb.getDouble();


			gotMessage = true;


		} catch (IOException e) {
			e.printStackTrace();
		}         

    }
	 */



}
