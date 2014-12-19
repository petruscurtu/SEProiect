package mta.se.game.render2d;

import static mta.se.game.utils.Dimens.COMPONENT_PAD;
import static mta.se.game.utils.Dimens.GRIDHPX;
import static mta.se.game.utils.Dimens.GRIDWPX;
import static mta.se.game.utils.Dimens.MARGIN;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import mta.se.game.utils.Utils;
import mta.se.game.gui.FinishScreen;

public abstract class SprintGame2D extends TetrisScreen2D {

	public SprintGame2D(String imageFolderName) {
		super(imageFolderName);
	}

	float time = 0;

	final int FINISH_LINE_COUNT = 40;

	@Override
	public void resetGame() {
		super.resetGame();
		time = 0;

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		spriteBatch.begin();
		scoreFont.draw(spriteBatch, Utils.secondsToMins((int) time), MARGIN
				+ (GRIDWPX / 2), MARGIN + GRIDHPX + scoreFontHeight
				+ (COMPONENT_PAD * 2));
		scoreFont.draw(spriteBatch, 40-mMatrix.getLineCount()+"",((int) Gdx.graphics.getWidth()/2-(scoreFont.getSpaceWidth()/2)), MARGIN-scoreFontHeight);
		spriteBatch.end();
		time += delta;
		if (mMatrix.getLineCount() >= FINISH_LINE_COUNT) {
			System.out.println("time: " + time);
			Utils.setScreen(new FinishScreen(this, null, "Finished!"));
			
		}
	}

}
