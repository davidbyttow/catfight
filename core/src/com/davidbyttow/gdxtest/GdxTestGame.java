package com.davidbyttow.gdxtest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GdxTestGame extends Game {
	public SpriteBatch batcher;

	@Override
	public void create () {
		batcher = new SpriteBatch();
		Assets.load();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	
	@Override
	public void dispose () {
		batcher.dispose();
	}
}
