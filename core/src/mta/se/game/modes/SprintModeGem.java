package mta.se.game.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import mta.se.game.render2d.SprintGame2D;

public class SprintModeGem extends SprintGame2D{
	Texture bg;
	
	public SprintModeGem(String ImageFolder) {
		super("gem");
	}
	
	@Override
	public void load() {
		super.load();
		bg = new Texture(Gdx.files.internal("bg2.png"));
		
	}
	@Override
	protected void drawBackground() {
		spriteBatch.begin();
		spriteBatch.draw(bg, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		spriteBatch.end();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		bg.dispose();
		
	}

}
