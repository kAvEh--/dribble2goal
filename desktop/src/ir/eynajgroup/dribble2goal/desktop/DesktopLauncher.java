package ir.eynajgroup.dribble2goal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ir.eynajgroup.dribble2goal.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Game";
        cfg.width = 1024;
        cfg.height = 576;

		new LwjglApplication(new MyGame(), cfg);
	}
}
