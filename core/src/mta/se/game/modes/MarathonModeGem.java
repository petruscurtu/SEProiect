package mta.se.game.modes;

import mta.se.game.render2d.MarathonScreen2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MarathonModeGem extends MarathonScreen2D {
	
	public MarathonModeGem(String imageFolderName) {
		
		super(imageFolderName);
	}

	Texture bg;

	@Override
	public void load() {
		super.load();
		bg = new Texture(Gdx.files.internal("bg2.png"));

	}

	@Override
	protected void drawBackground() {
		spriteBatch.begin();
		spriteBatch.draw(bg, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		spriteBatch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		bg.dispose();

	}
}
