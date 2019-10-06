package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by daniel.popescu1709 on 2/14/2018.
 */

public abstract class State {

    protected OrthographicCamera cam;
    protected GameStateManager gsm;
    public FitViewport viewPort;



  //  public StretchViewport viewPort;
    public State(GameStateManager gsm)
    {



       cam=new OrthographicCamera();
        cam.setToOrtho(false, 720,1280);

        viewPort=new FitViewport(720,1280,cam);

      //  viewPort=new StretchViewport(720,1280,cam);
        this.gsm=gsm;
    }


    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

}