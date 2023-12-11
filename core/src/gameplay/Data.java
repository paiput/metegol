package gameplay;

import com.eblp.metegol.utils.Global;

public abstract class Data {

	// Posiciones de todos los jugadores (cada palo de cada equipo)
	public static float[] yGk1 = new float[1], yGk2 = new float[1];
	public static float[] yDef1 = new float[3], yDef2 = new float[3];
	public static float[] yMid1 = new float[4], yMid2 = new float[4];
	public static float[] yFwd1 = new float[3], yFwd2 = new float[3];
	// Accion de patear de cada fila
	public static boolean kickGk1 = false, kickGk2 = false;
	public static boolean kickDef1 = false, kickDef2 = false;
	public static boolean kickMid1 = false, kickMid2 = false;
	public static boolean kickFwd1 = false, kickFwd2 = false;
	// Posicion de la pelota
	public static float xBall, yBall;
	// Pelota adentro del arco 
	public static boolean ballIsGoal = false;
	// Lado de la pelota si está adentro de un arco
	public static int ballGoalSide;
	// Marcadores 
	public static int score1 = 0, score2 = 0;
	
	public static void reset() {
		score1 = 0;
		score2 = 0;
		ballIsGoal = false;
		Global.start = false;
	}
}
