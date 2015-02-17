package mta.se.game.tetris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

import mta.se.game.modes.MarathonModeGem;
import mta.se.game.modes.SprintModeGem;
import mta.se.game.tetris.MainMenu;

public class Tetris extends Game {

	@Override
	public void create() {
		
		setScreen(new MainMenu());
	}
}
