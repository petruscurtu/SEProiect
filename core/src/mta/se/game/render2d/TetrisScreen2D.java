package mta.se.game.render2d;

import static mta.se.game.model.Dimens.CELL;
import static mta.se.game.model.Dimens.COMPONENT_PAD;
import static mta.se.game.model.Dimens.GRIDHPX;
import static mta.se.game.model.Dimens.GRIDWPX;
import static mta.se.game.model.Dimens.GRID_HEIGHT;
import static mta.se.game.model.Dimens.GRID_WIDTH;
import static mta.se.game.model.Dimens.MARGIN;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import mta.se.game.model.Point;
import mta.se.game.views.TetrisScreen;

public abstract class TetrisScreen2D extends TetrisScreen {
	protected OrthographicCamera camera;
	protected SpriteBatch spriteBatch;
	protected ShapeRenderer shapeRenderer;

	protected BitmapFont gameFont;
	protected BitmapFont scoreFont;

	protected int gameFontHeight;
	protected int scoreFontHeight;

	private String mImageFolderName;
	private Notifys notifys;

	private AnimationBatch hardDropAnimations;

	// class to hold a hard drop animation
	class HardDropAnimation extends Animation {
		int startX, startY;
		int width, height;
		private Color baseColor = Color.WHITE;
		private final float alphaIntensity = 0.25f;
		private final static float SPEED = 0.3f;

		public HardDropAnimation(int x, int y, int w, int h) {
			super(SPEED);
			startX = x;
			startY = y;
			width = w;
			height = h;
		}

		@Override
		public void animate(ShapeRenderer shapeRenderer, int xOffset,
				int yOffset) {
			GraphicUtils.enableAlpha();
			shapeRenderer.setColor(baseColor.r, baseColor.g, baseColor.b, alphaIntensity 
					- (alphaIntensity * mTimer.getProgressPercent()));
			shapeRenderer.begin(ShapeType.Filled);
			float fall = startY * mTimer.getProgressPercent();
			shapeRenderer.rect(startX+xOffset,startY-fall+yOffset,width,height);
			shapeRenderer.end();
		}

	}

	public TetrisScreen2D(String imageFolderName) {
		mImageFolderName = imageFolderName;
	}

	@Override
	public void load() {
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		gameFont = new BitmapFont(Gdx.files.internal("font/gamefont.fnt"));
		scoreFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));

		gameFontHeight = (int) gameFont.getCapHeight();
		scoreFontHeight = (int) scoreFont.getCapHeight();

		notifys = new Notifys();
	}

	@Override
	public void resetGame() {
		mMatrix = new Matrix2D(mImageFolderName);
		mTetrominoStack = new TetrominoStack2D(mImageFolderName);
		mCurrentTetromino = mTetrominoStack.getNextPiece();
		hardDropAnimations = new AnimationBatch();
		System.out.println(mCurrentTetromino.getId());
		super.resetGame();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameLogic(delta);

		camera.translate(0, 0);

		camera.update();

		spriteBatch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);

		drawBackground();

		((Matrix2D) mMatrix).draw(spriteBatch, shapeRenderer, MARGIN, MARGIN);
		mMatrix.update(delta);

		// score
		GraphicUtils.drawBGBox(shapeRenderer, new Rectangle(MARGIN, GRIDHPX
				+ MARGIN + COMPONENT_PAD, GRIDWPX, scoreFontHeight
				+ COMPONENT_PAD));

		// held bg
		GraphicUtils.drawBGBox(shapeRenderer, new Rectangle(COMPONENT_PAD,
				GRIDHPX, MARGIN - (COMPONENT_PAD * 2), MARGIN));

		// next bg
		GraphicUtils
				.drawBGBox(shapeRenderer, new Rectangle(MARGIN + GRIDWPX
						+ COMPONENT_PAD, GRIDHPX, MARGIN - (COMPONENT_PAD * 2),
						MARGIN));

		// image only rendering so can call begin from here
		// (there's no clashes with shape renderer)
		spriteBatch.begin();
		((Tetromino2D) mCurrentTetromino).draw(spriteBatch, shapeRenderer,
				mMatrix, MARGIN, MARGIN);

		((Tetromino2D) mTetrominoStack.peekNextPiece()).draw(spriteBatch,
				new Point(GRID_WIDTH * CELL + MARGIN + COMPONENT_PAD,
						(int) (GRID_HEIGHT * CELL - gameFont.getCapHeight())));

		if (mTetrominoStack.getHeld() != null) {
			((Tetromino2D) mTetrominoStack.getHeld())
					.draw(spriteBatch,
							new Point(COMPONENT_PAD,
									(int) (GRID_HEIGHT * CELL - gameFont
											.getCapHeight())));
		}

		gameFont.draw(spriteBatch, "HELD", COMPONENT_PAD, MARGIN + GRIDHPX);
		gameFont.draw(spriteBatch, "LEVEL", COMPONENT_PAD, MARGIN
				+ (GRIDHPX / 2));
		gameFont.draw(spriteBatch, "NEXT", MARGIN + GRIDWPX + COMPONENT_PAD,
				MARGIN + GRIDHPX);

		// gameFont.draw(spriteBatch, "SCORE",
		// MARGIN-gameFont.getBounds("SCORE").width - COMPONENT_PAD, MARGIN +
		// GRIDHPX +gameFontHeight+(COMPONENT_PAD*2));

		// score
		scoreFont.draw(spriteBatch, "" + mMatrix.getScore(), MARGIN, MARGIN
				+ GRIDHPX + scoreFontHeight + (COMPONENT_PAD * 2));
		// level
		scoreFont.draw(spriteBatch, "" + mLevel, MARGIN / 2, MARGIN
				+ (GRIDHPX / 2) - scoreFontHeight);

		spriteBatch.end();

		notifys.draw(spriteBatch);
		notifys.update(delta);
		hardDropAnimations.animateAll(shapeRenderer, MARGIN, MARGIN);
		hardDropAnimations.update(delta);
		System.out.println(mCurrentTetromino.getWidth());

	}

	final float NOTIFICATION_LENGTH = 1.5f;

	@Override
	protected void onBackToBackComboBeaten() {
		notifys.addNotification("new max combo!" + biggestBackToBacks,
				NOTIFICATION_LENGTH);
	}

	@Override
	protected void onBackToBackComboIncrease() {
		notifys.addNotification("Combo x" + currentBackToBacks, 0.5f);
	}

	protected abstract void drawBackground();

	@Override
	public void dispose() {
		spriteBatch.dispose();
		shapeRenderer.dispose();
		((Matrix2D) mMatrix).dispose();
		((Tetromino2D) mCurrentTetromino).dispose();
		((TetrominoStack2D) mTetrominoStack).dispose();
		gameFont.dispose();
		scoreFont.dispose();
		notifys.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyTyped(char character) {
		if (character == 't') {
			notifys.addNotification("this is a long notificaion", 5);
			notifys.addNotification("this is a short notificaion", 1);
		} else if (character == 'd') {
			// set breakpoint here
			int a = 3;
			a = CELL;
		}
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.SPACE){
			int width = mCurrentTetromino.getShape()[0].length*CELL;
			hardDropAnimations.add(new HardDropAnimation(
					mCurrentTetromino.getPos().x*CELL, mCurrentTetromino.getPos().y*CELL,
					width, mCurrentTetromino
							.getShape().length*CELL));
		}
		
		return super.keyDown(keycode);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
