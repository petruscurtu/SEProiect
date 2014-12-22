package mta.se.game.render2d;

import mta.se.game.utils.Utils;
import mta.se.game.gui.FinishScreen;
import mta.se.game.modes.MarathonModeGem;


public abstract class MarathonScreen2D extends TetrisScreen2D{
	
	public MarathonScreen2D(String imageFolderName) {
		super(imageFolderName);
	}

	@Override
	public void resetGame() {
		if(!firstReset){
			Utils.setScreen(new FinishScreen(this,null, "Game over"));
		}else{
			super.resetGame();
		}
	}

}
