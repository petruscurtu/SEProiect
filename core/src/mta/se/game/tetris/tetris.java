package mta.se.game.tetris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

import mta.se.game.gui.MainMenu;
import mta.se.game.modes.MarathonModeGem;
import mta.se.game.modes.SprintModeGem;

public class tetris extends Game {

	@Override
	public void create() {
		//FileTextureData.copyToPOT=true;
		Texture.setEnforcePotImages(false);
		//setScreen(new VectorScreen());
		//setScreen(new GlowScreen());
		//setScreen(new Screen3D());
	//	setScreen(new MainMenu());
		setScreen(new SprintModeGem());
	}
}
