package mta.se.game.controller;

import static mta.se.game.model.Dimens.CELL;
import static mta.se.game.model.Dimens.GRID_HEIGHT;
import static mta.se.game.model.Dimens.GRID_WIDTH;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import mta.se.game.model.Matrix;
import mta.se.game.model.Point;
import mta.se.game.model.Tetromino;
import mta.se.game.model.Matrix.LineClearAnimation;
import mta.se.game.render2d.AnimationBatch;

public class matrixController {
	
	private int score = 0;
	private int mLineCount = 0;
	Matrix mMatrix;
	
	public boolean isValid(final Tetromino t,int[][] matrix) {
		final int[][] shape = t.getShape();
		Point pos = t.getPos();

		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] == 1) {
					int xPos = j + pos.x;

					if (xPos < 0 || xPos >= GRID_WIDTH) {
						return false;
					}
					int yPos = i + pos.y;
					if (yPos < 0 || yPos >= GRID_HEIGHT) {
						return false;
					}
					if (matrix[yPos][xPos] != 0) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public boolean isValid(Tetromino tetromino, Point move,int [][]matrix) {
		Point cPos = tetromino.getPos();
		Tetromino tmp = new Tetromino(tetromino.getId());
		tmp.setCurrentRotationState(tetromino.getCurrentRotationState());
		tmp.setPos(new Point(cPos.x + move.x, cPos.y + move.y));
		return isValid(tmp,matrix);
	}

	public boolean isValid(int[][] shape, Point pos,int[][] matrix) {
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] == 1) {
					int xPos = j + pos.x;

					if (xPos < 0 || xPos >= GRID_WIDTH) {
						return false;
					}
					int yPos = i + pos.y;
					if (yPos < 0 || yPos >= GRID_HEIGHT) {
						return false;
					}
					if (matrix[yPos][xPos] != 0) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public boolean isValid(int[][] shape, Point cPos, Point move,int[][] matrix) {
		Point newPos = new Point(cPos.x + move.x, cPos.y + move.y);
		return isValid(shape, newPos,matrix);
	}

	public boolean isGameOver(int[][] matrix) {
		for (int i = 1; i < 3; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[GRID_HEIGHT - i][j] != 0) {
					return true;
				}
			}
		}
		return false;
	}


	// score algorythm
	public int increaseScore(int lines, int level) {
		switch (lines) {
		case 1:
			score += level * 40 + 40;
			break;
		case 2:
			score += level * 100 + 100;
			break;
		case 3:
			score += level * 300 + 300;
			break;
		case 4:
			score += level * 1200 + 1200;
			break;
		default:
			break;
		}
		return score;
	}
	
	void shiftGridDownTo(int overwritePos,int[][] matrix) {
		for (int i = 0; i < matrix[0].length; i++) {
			matrix[overwritePos][i] = matrix[overwritePos + 1][i];
		}
	}


}
