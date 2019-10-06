package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

/**
 * Created by daniel.popescu1709 on 2/11/2018.
 */

public class HealthBar extends Actor {
    //Sprite sprite=new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
    private NinePatch healthSprite,container;

    private float width;

    public HealthBar(Texture health,Texture containerSprite){
        healthSprite=new NinePatch(health,12,12,12,12);
        container=new NinePatch(containerSprite,0,0,0,0);
        width=560f;
    }

    @Override
    protected void positionChanged() {
       // sprite.setPosition(getX(),getY());
        super.positionChanged();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        container.draw(batch, 80, 990, 560, 100);

        healthSprite.draw(batch, 80, 990, width, 100);
      //  sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void setWidth(float width){

        this.width=width;}
}

    //TODO: Dispose texture (Or add textures via constructor so you can destroy it in assets manager game stage

