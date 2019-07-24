package stabby;

import java.awt.Dimension;

import trinity.Game;
import trinity.Key;
import trinity.Level;
import trinity.Render;
import trinity.Twin;

public class Cartridge extends Level {

	{

		clear();
		Key.resetKeys();
		Render.fixDisplay(new Dimension(200, 100));

		Game.currentName = "stabby";

		importImage("ally.png");
		importImage("enemy.png");
		
		importImage("arms.png");
		importImage("blade_1.png");
		importImage("blade_2.png");
		importImage("slash.png");
		//importImage("daggers.png");
		
		
		
		
		Level foo = new Level();
		levels.add(foo);
		
		new Ally(new Twin(10,10), foo);
		
		new Ally(new Twin(10,30), foo);
		
		new Ally(new Twin(10,50), foo);
		
		
		new Enemy(new Twin(60,10), foo);
		
		new Enemy(new Twin(60,30), foo);
		
		new Enemy(new Twin(60,50), foo);
		
		
	}

}
