package mta.se.game.model;

import static mta.se.game.model.Dimens.CELL;
import static mta.se.game.model.Dimens.GRID_HEIGHT;
import static mta.se.game.model.Dimens.GRID_WIDTH;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import mta.se.game.model.Dimens;
import mta.se.game.model.Point;
import mta.se.game.model.Tetromino;
import mta.se.game.render2d.Animation;
import mta.se.game.render2d.AnimationBatch;
import mta.se.game.render2d.GraphicUtils;
import mta.se.game.controller.matrixController;

public abstract class Matrix {

	protected static int[][] matrix = new int[GRID_HEIGHT][GRID_WIDTH];
    
	private int score = 0;
	private int mLineCount = 0;
	matrixController contr=new matrixController();

	public AnimationBatch animationBatch = new AnimationBatch();

	public class LineClearAnimation extends Animation {
		final public static float animationTime = 1;
		// a number below 1 that holds the alpha start - will determine
		// transparency
		final public static float animationIntensity = 0.3f;
		int yPos;

		public LineClearAnimation(int y) {
			super(animationTime);
			yPos = y;
		}

		public int getY() {
			return yPos;
		}

		public void animate(ShapeRenderer shapeRenderer, int xOffset,
				int yOffset) {
			GraphicUtils.enableAlpha();

			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(new Color(1, 1, 1, animationIntensity
					- (animationIntensity * mTimer.getProgressPercent())));
			shapeRenderer.rect(xOffset, yPos * CELL + yOffset, CELL
					* Dimens.GRID_WIDTH, CELL);
			shapeRenderer.end();
			GraphicUtils.disableAlpha();

		}
	}

	void debugLoad() {
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < matrix[y].length - 1; x++) {
				if (Tetromino.random.nextInt(2) == 1) {
					matrix[y][x] = Tetromino.random.nextInt(7);
				}
			}
		}
	}

	boolean isValid(final Tetromino t) {
		boolean response=contr.isValid(t);
		return response;
	}

	public boolean isValid(Tetromino tetromino, Point move) {
		boolean response=contr.isValid(tetromino, move);
		return response;
	}

	public boolean isValid(int[][] shape, Point pos) {
		boolean response=contr.isValid(shape, pos);
		return response;
	}

	public boolean isValid(int[][] shape, Point cPos, Point move) {
		boolean response=contr.isValid(shape, cPos, move);
		return response;
	}

	public boolean isGameOver() {
		boolean response=contr.isGameOver();
		return response;
	}

	public int checkClears(int level) {
		// find all lines that are full
		ArrayList<Integer> fullLines = new ArrayList<Integer>();
		for (int i = matrix.length - 1; i >= 0; i--) {
			boolean isFull = true;
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					isFull = false;
					break;
				}
			}
			if (isFull) {
				fullLines.add(i);
			}
		}
		for (int i = 0; i < fullLines.size(); i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[fullLines.get(i)][j] = 0;
			}
		}

		// shift down all lines at cleared line and above to
		// over write it
		for (int i = 0; i < fullLines.size(); i++) {
			for (int y = fullLines.get(i); y < matrix.length - 1; y++) {
				shiftGridDownTo(y);
			}
			animationBatch.add(new LineClearAnimation(fullLines.get(i)));
			mLineCount++;

		}
		increaseScore(fullLines.size(), level);

		return fullLines.size();
	}

	public void update(float delta) {
		animationBatch.update(delta);
	}

	// score algorythm
	private void increaseScore(int lines, int level) {
		score=contr.increaseScore(lines, level);
	}

	void shiftGridDownTo(int overwritePos) {
		for (int i = 0; i < matrix[0].length; i++) {
			matrix[overwritePos][i] = matrix[overwritePos + 1][i];
		}
	}

	public void setCell(Point pos, int num) {
		matrix[pos.y][pos.x] = num;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLineCount() {
		return mLineCount;
	}

	public void setLineCount(int lineCount) {
		this.mLineCount = lineCount;
	}

	public static int[][] getMatrix() {
		// TODO Auto-generated method stub
		return matrix;
	}

}
