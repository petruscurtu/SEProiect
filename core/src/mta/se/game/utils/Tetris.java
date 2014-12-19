package mta.se.game.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import mta.se.game.gui.MainMenu;
import mta.se.game.modes.gem.MarathonModeGem;
import mta.se.game.modes.gem.SprintModeGem;

public class Tetris extends Game {

	@Override
	public void create() {
		
		Texture.setEnforcePotImages(false);
		//setScreen(new VectorScreen());
		//setScreen(new GlowScreen());
		//setScreen(new Screen3D());
	//	setScreen(new MainMenu());
		setScreen(new SprintModeGem());
	}
}
