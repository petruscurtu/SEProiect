package mta.se.game.tetris.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics;

import mta.se.game.tetris.tetris;
import static mta.se.game.utils.Dimens.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tetris";
		config.width = DESKTOP_WIDTH;
		config.height = DESKTOP_HEIGHT;
		new LwjglApplication(new tetris(),config);
	}
}
