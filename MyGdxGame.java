package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.interfaces.AdHandler;
import com.mygdx.game.interfaces.FacebookLinkHandler;
import com.mygdx.game.stages.GameStateManager;
import com.mygdx.game.stages.MenuStageV2;

public class MyGdxGame extends ApplicationAdapter {
	private GameStateManager gsm;
	private SpriteBatch batch;
	public AdHandler adHandler;
	FacebookLinkHandler mFacebookLinkHandler;
    // boolean toggle;

	public MyGdxGame(AdHandler handler,FacebookLinkHandler facebookLinkHandler){
		if(facebookLinkHandler==null)
			Gdx.app.log(" WAWRNING! " , "NO FB HANDLER FOUND");
		mFacebookLinkHandler=facebookLinkHandler;
		adHandler=handler;
	}

	@Override
	public void create () {

		batch = new SpriteBatch();
		gsm=new GameStateManager(adHandler,mFacebookLinkHandler);
		gsm.push(new MenuStageV2(gsm));
	}

	@Override
	public void render() {
	/*	if(Gdx.input.justTouched()){
			handler.showAds(toggle);
			toggle=!toggle;
		}
	*/
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void pause() {
		super.pause();
		gsm.onPause();
	}


	@Override
	public void dispose () {

	}


}
