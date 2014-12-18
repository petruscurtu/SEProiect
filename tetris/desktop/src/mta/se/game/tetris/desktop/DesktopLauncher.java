package mta.se.game.tetris.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import mta.se.game.tetris.TetrisGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;//TetrisGame.GRID *  10;
		config.height = 720;//TetrisGame.GRID * 20;
		new LwjglApplication(new TetrisGame(), config);
	}
}
