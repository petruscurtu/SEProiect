package mta.se.game.render2d;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import mta.se.game.controller.Timer;

public abstract class Animation {
	protected Timer mTimer;
	
	public Animation(float animationLength) {
		mTimer = new Timer(animationLength);
	}

	public boolean doneAnimating() {
		return mTimer.isFinished();
	}
	
	public void update(float delta){
		mTimer.tick(delta);
		
	}

	public abstract void animate(ShapeRenderer shapeRenderer, int xOffset,
			int yOffset);
}