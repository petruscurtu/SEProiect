package mta.se.game.render2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class GraphicUtils {
	
	public static void drawBGBox(ShapeRenderer shapeRenderer,Rectangle rect){
		//transparent bg
		shapeRenderer.setColor(new Color(.1f, .1f, .1f, .3f));
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.box(rect.x, rect.y, 0, rect.width, rect.height, 0);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		//outline
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.box(rect.x, rect.y, 0, rect.width, rect.height, 0);
		shapeRenderer.end();
	}
	public static void drawBorderBox(ShapeRenderer shapeRenderer,Rectangle rect){
		//TODO draw border box
	}
	
	public static void enableAlpha(){
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	public static void disableAlpha() {
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	
	
}
