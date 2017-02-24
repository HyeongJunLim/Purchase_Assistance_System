package MVC.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EuclidianDistanceValue {
	private static EuclidianDistanceValue instance = new EuclidianDistanceValue();
	public static EuclidianDistanceValue getInstance()	{
		return instance;
	}
	
	public Map<Integer, double[][]> toEuclidianDistanceValue(HandGesture handGesture) {
		double[][] rightEuclidianDistance = new double[5][5];
		double[][] leftEuclidianDistance = new double[5][5];
		
		Map<Integer, double[][]> euclidianDistanceList = new HashMap<Integer,double[][]>();
		
		if( handGesture.hasRight() || handGesture.hasLeft() )	{
			for(int i=0; i<5; i++)	{
				for(int j=0; j<5; j++)	{
					rightEuclidianDistance[i][j] = Math.sqrt( Math.abs( Math.pow( handGesture.getRightX(i, j)-handGesture.getRightPalmPosition().getX() , 2) +
																		Math.pow( handGesture.getRightY(i, j)-handGesture.getRightPalmPosition().getY() , 2) +
																		Math.pow( handGesture.getRightZ(i, j)-handGesture.getRightPalmPosition().getZ() , 2)
																	  )
															);
					leftEuclidianDistance[i][j] = Math.sqrt( Math.abs( Math.pow( handGesture.getLeftX(i, j)-handGesture.getLeftPalmPosition().getX() , 2) +
																	   Math.pow( handGesture.getLeftY(i, j)-handGesture.getLeftPalmPosition().getY() , 2) +
																	   Math.pow( handGesture.getLeftZ(i, j)-handGesture.getLeftPalmPosition().getZ() , 2)
																	  )
															);
				}
			}
		}
		
		euclidianDistanceList.put(0, rightEuclidianDistance);
		euclidianDistanceList.put(1, leftEuclidianDistance);
		
		return euclidianDistanceList;
	}
	
	public Map<Integer, double[][]> toEuclidianDistanceValue(TransHandGesture transHandGesture) {
		double[][] rightEuclidianDistance = new double[5][5];
		double[][] leftEuclidianDistance = new double[5][5];
		
		
		Map<Integer, double[][]> euclidianDistanceList = new HashMap<Integer,double[][]>();
		
		for(int i=0; i<5; i++)	{
			for(int j=0; j<5; j++)	{
				rightEuclidianDistance[i][j] = Math.sqrt( Math.abs( Math.pow( transHandGesture.getRHandX(i, j) - transHandGesture.getRPalmPositionX() , 2 ) +
																	Math.pow( transHandGesture.getRHandY(i, j) - transHandGesture.getRPalmPositionY() , 2 ) +
																	Math.pow( transHandGesture.getRHandZ(i, j) - transHandGesture.getRPalmPositionZ() , 2 )
															  	  )
													  	);
				leftEuclidianDistance[i][j] = Math.sqrt( Math.abs( Math.pow( transHandGesture.getLHandX(i, j) - transHandGesture.getLPalmPositionX() , 2 ) +
																   Math.pow( transHandGesture.getLHandY(i, j) - transHandGesture.getLPalmPositionY() , 2 ) +
																   Math.pow( transHandGesture.getLHandZ(i, j) - transHandGesture.getLPalmPositionZ() , 2 )
															  	  )
													  	);
			}
		}
		
		euclidianDistanceList.put(0, rightEuclidianDistance);
		euclidianDistanceList.put(1, leftEuclidianDistance);
		
		return euclidianDistanceList;
	}
	
	
}
