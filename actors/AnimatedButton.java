package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by daniel.popescu1709 on 2/22/2018.
 */

public class AnimatedButton extends Image{

    private Animation animation = null;
    private float stateTime = 0;
    public boolean backwords;
    TextureRegion[] frames;
    //private float currentTime;
    private boolean start;

    public AnimatedButton(Animation animation,boolean start) {
        super((TextureRegion)animation.getKeyFrame(0));
        // currentTime=0;
        this.start=start;
        backwords=false;
        this.animation = animation;
    }
    public void start(){
        start=true;
    }
    public boolean animOver()
    {
        //Gdx.app.log("HELLO: ",String.valueOf(stateTime));
        return(animation.isAnimationFinished(stateTime));
    }
    public void startOver(boolean _backwords)
    {
       if(_backwords) {
           stateTime = 0.4f;
           backwords = true;
       }
       else{
           start=true;
           stateTime = 0f;
           backwords=false;
       }
        //  currentTime=0;
    }
    @Override
    public void act(float delta)
    {
        if(!backwords) {
            if (start) {
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
