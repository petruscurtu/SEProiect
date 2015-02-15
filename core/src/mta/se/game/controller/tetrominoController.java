package mta.se.game.controller;

import java.util.Random;

import mta.se.game.model.Matrix;
import mta.se.game.model.Point;
import mta.se.game.model.RotationStateList;
import mta.se.game.model.Tetromino;
import mta.se.game.controller.Timer;
import mta.se.game.model.Tetromino;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class tetrominoController {
	
	final float KEY_HOLD_DELAY = 0.2f;

	final float SPEED_MIN = .45f;
	final float SPEED_MAX = .02f;

	private final Timer leftHeldTimer = new Timer(KEY_HOLD_DELAY);
	private final Timer rightHeldTimer = new Timer(KEY_HOLD_DELAY);

	// speed of the tetromino once a key is held down
	final float HOLD_SPEED = 0.09f;
	private final Timer keyHoldSpeedTimer = new Timer(HOLD_SPEED);

	public static final Point DOWN = new Point(0, -1);
	public static final Point LEFT = new Point(-1, 0);
	public static final Point RIGHT = new Point(1, 0);
	public static final Point UP = new Point(0, 1);

	protected static final boolean ghost = true;

	protected int mId;

	protected Point mPos = new Point(0, 0);
	private boolean done = false;

	public static Random random = new Random();

	private boolean downHeld;
	
	public int getNextRotationState(int[][][] rotationStates,int mCurrentRotationState) {
		if (rotationStates.length > 0) {
			if (mCurrentRotationState < rotationStates.length - 1) {
				return mCurrentRotationState + 1;
			}
			return 0;
		}
		return 0;
	}

	public boolean rotateClockwise(Matrix matrix,Tetromino t,Point mPos,
			int[][][]rotationStates,int mCurrentRotationState) {
		if (matrix.isValid(getNextShape(rotationStates,mCurrentRotationState), mPos)) {
			mCurrentRotationState = getNextRotationState(rotationStates,mCurrentRotationState);
			return true;
		}
		Tetromino tmp = new Tetromino(t);
		tmp.setCurrentRotationState(getNextRotationState(rotationStates,mCurrentRotationState));
		//wall kicks
		if (matrix.isValid(tmp, LEFT)) {
			move(mPos,LEFT);
			mCurrentRotationState = getNextRotationState(rotationStates,mCurrentRotationState);
			return true;
		} else if (matrix.isValid(tmp, RIGHT)) {
			move(mPos,RIGHT);
			mCurrentRotationState = getNextRotationState(rotationStates,mCurrentRotationState);
			return true;
		}else if(matrix.isValid(tmp,UP)){
			move(mPos,UP);
			mCurrentRotationState = getNextRotationState(rotationStates,mCurrentRotationState);
			return true;
		}

		return false;
	}
	
	public int[][] getNextShape(int[][][] rotationStates,int mCurrentRotationState) {
		return rotationStates[getNextRotationState(rotationStates,mCurrentRotationState)];
	}
	
	public int[][] getShape(int[][][] rotationStates,int mCurrentRotationState) {
		return rotationStates[mCurrentRotationState];
	}
	
	public Point getPos() {
		return mPos;
	}
	
	public void move(Point original,Point move) {
		original.x += move.x;
		original.y += move.y;
	}
	
	public void hardDrop(Matrix matrix,Tetromino t,int[][][] rotationStates,
			int mCurrentRotationState,Point mPos) {
		mPos = getHardDropPos(matrix,t,mPos);
		addToMatrix(matrix,rotationStates,mCurrentRotationState);
		
	}

	public Point getHardDropPos(Matrix matrix,Tetromino t,Point mPos) {
		Point movement = new Point(0, 0);
		while (matrix.isValid(t, movement)) {
			movement.y--;
		}
		Point pos = new Point(mPos);
		pos.add(movement);
		pos.y++;
		return pos;
	}

	public void print(int[][][] rotationStates,int mCurrentRotationState) {
		int[][] shape = getShape(rotationStates,mCurrentRotationState);
		for (int y = 0; y < shape.length; y++) {
			for (int x = 0; x < shape[y].length; x++) {
				if (shape[y][x] != 0) {
					System.out.print("[]");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}

	public void addToMatrix(Matrix matrix,int[][][] rotationStates,int mCurrentRotationState) {
		int[][] shape = getShape(rotationStates,mCurrentRotationState);
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[i].length; j++) {
				if (shape[i][j] == 1) {
					int x = getPos().x + j;
					int y = getPos().y + i;
					matrix.setCell(new Point(x, y), mId + 1);
				}
			}
		}
		done=true;
	}

	public void update(Matrix matrix, float delta,Tetromino t,Point mPos) {
		leftHeldTimer.tick(delta);
		rightHeldTimer.tick(delta);
		keyHoldSpeedTimer.tick(delta);

		if (leftHeldTimer.isFinished() && matrix.isValid(t, Tetromino.LEFT)
				&& keyHoldSpeedTimer.isFinished()) {
			move(mPos,Tetromino.LEFT);
		}
		if (rightHeldTimer.isFinished()
				&& matrix.isValid(t, Tetromino.RIGHT)
				&& keyHoldSpeedTimer.isFinished()) {
			move(mPos,Tetromino.RIGHT);
		}
		if (downHeld && matrix.isValid(t, DOWN)
				&& keyHoldSpeedTimer.isFinished()) {
			move(mPos,DOWN);
		}
		if (keyHoldSpeedTimer.isFinished()) {
			keyHoldSpeedTimer.reset();
		}

	}

	public int getCubeCount(int[][] shape) {
		int total = 0;
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				if (shape[i][j] == 1) {
					total++;
				}
			}
		}
		return total;
	}

	public float getSpeed(int level) {
		float p = ((float) level) / 10;
		float r = SPEED_MIN - SPEED_MAX;
		float speed = r * p;
		return SPEED_MIN - speed; // invert cus smaller is faster
	}
	
	public int getWidth(int[][]shape){
		int width = 0;
		for (int i = 0; i < shape.length; i++) {
			int smallest = 999;
			int biggest = -1;
			for (int j = 0; j < shape[i].length; j++) {
				if(shape[i][j] == 1){
					if(shape[i][j] < smallest){
						smallest =shape[i][j]; 
					}
					if(shape[i][j] > biggest){
						biggest = shape[i][j]; 
					}
				}
			}
			int cwidth = biggest - smallest; 
			if(width < cwidth){
				width = cwidth;
			}
		}
		return width;
	}

}
