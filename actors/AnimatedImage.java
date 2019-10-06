package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by daniel.popescu1709 on 2/14/2018.
 */

public class AnimatedImage extends Image
{
    protected Animation animation = null;
    private float stateTime = 0;
    public boolean backwords;
    public boolean turnLater;
    TextureRegion[] frames;
    //private float currentTime;
    private boolean go;

    public AnimatedImage(Animation animation,boolean start,boolean backwords) {
        super((TextureRegion)animation.getKeyFrame(0));
       // currentTime=0;
        go=start;
        turnLater=!start;
        this.backwords=backwords;
        this.animation = animation;
    }
    public void start(){
        go=true;
    }
    public void startDelayed(float delay) {
        go=true;
        stateTime=0-delay;
        Gdx.app.log("PULA MEA: " , String.valueOf(backwords));
    }
    public boolean animOver()
    {
        //Gdx.app.log("HELLO: ",String.valueOf(stateTime));
        if(!backwords)
        return(animation.isAnimationFinished(stateTime));
        else {
       //    Gdx.app.log("STATUS PT BACKWORDS STATE TIME =: ", String.valueOf(stateTime));
            if(stateTime-0.015f<=0f)

                return true;
        }
        return false;
    }
    public void startOver(Animation anim,float delayed,boolean _backwords)
    {
        backwords=_backwords;
        if (anim != null)
        this.animation=anim;
        if(!backwords)
        stateTime=0-delayed;
        else
            stateTime=0.4f;
      //  currentTime=0;
         go=true;
    }
    @Override
    public void act(float delta)
    {
        if(!backwords) {
            if (go) {
                if (stateTime < 0)
                    stateTime += delta;
                else {
                    //   Gdx.app.log("stateTime: ", String.valueOf(stateTime));
                    ((TextureRegionDrawable) getDrawable()).setRegion((TextureRegion) animation.getKeyFrame(stateTime += delta, false));

                }
            }
        }
        else{
            if(stateTime-delta>0) {
                // Gdx.app.log("stateTime: ", String.valueOf(stateTime));
                ((TextureRegionDrawable) getDrawable()).setRegion((TextureRegion) animation.getKeyFrame(stateTime -= delta, false));

            }
        }

        super.act(delta);
    }
}