package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.actors.AnimatedButton;
import com.mygdx.game.actors.AnimatedImage;

/**
 * Created by daniel.popescu1709 on 2/22/2018.
 */

public class TestStage extends State implements InputProcessor {

    private Stage testStage;
    AnimatedButton animImg;
    private String isPressed;
    private SequenceAction fadeButton,unfadeButton;



    Image b1;

    public TestStage(GameStateManager gsm){
        super(gsm);
        testStage=new Stage(viewPort);

        fadeButton=new SequenceAction(Actions.parallel(Actions.scaleTo(0.7f,0.7f,0.3f),Actions.color(new Color(0.9f,0.05f,0.05f,3f),0.2f)), Actions.scaleTo(0.7f,0.7f,0.3f),Actions.scaleTo(0.85f,0.85f,0.3f));
        unfadeButton=new SequenceAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f),Actions.color(new Color(1f,1f,1f,1f),0.2f)));

        Image background=new Image(new Texture("back_logo.png"));
        background.setName("background");
      //  background.setPosition(0,360);
        background.setTouchable(Touchable.enabled);

        TextureAtlas back1=new TextureAtlas(Gdx.files.internal("buttons/b1.txt"));
        Animation an=new Animation(1/120f,back1.getRegions());
        animImg=new AnimatedButton(an,false);
        animImg.setPosition(100,100);
        animImg.setName("alo");
        animImg.setTouchable(Touchable.enabled);

        b1 = new Image(new Texture("buttons/b_1.png"));
        b1.setPosition(100,300);
        b1.setName("b1");
        b1.setOrigin(Align.center);  // VERY IMPORTANT




       testStage.addActor(background);
       testStage.addActor(animImg);
       testStage.addActor(b1);


        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor( 0.05f, 0.05f, 0.05f, 1 );
        testStage.act(Gdx.graphics.getDeltaTime());
        testStage.draw();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer == 0) {

            Vector2 coord = testStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
            try {
                AnimatedButton hitActor = (AnimatedButton) testStage.hit(coord.x, coord.y, true);
                if (hitActor != null)
                    if (hitActor.getName() != null) {
                        setTouched(hitActor,hitActor.getName());
                        Gdx.app.log("STATUS: ", "te-am prins ai apasat");
                    }
            } catch (Exception e) {
                Image hitActor = (Image) testStage.hit(coord.x,coord.y,true);
                setTouchedImage(hitActor,hitActor.getName());
            }

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer == 0) {

            Vector2 coord = testStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
            try {
                AnimatedButton hitActor = (AnimatedButton) testStage.hit(coord.x, coord.y, true);
                if (hitActor != null)
                    if (hitActor.getName() != null) {

                        setUntouched(hitActor,hitActor.getName());
                    }
            } catch (Exception e) {
                Gdx.app.log("STATUS: ", e.toString());
                Image hitActor = (Image) testStage.hit(coord.x, coord.y, true);
                // Tot ce nu e animatedimage (vor fi butoane) va fi gandit aici
                setUnTouched(hitActor,hitActor.getName());
            }

        }
        return true;

}
    private void setUnTouched(Image button,String name){
        if(isPressed==name){
             if(name=="b1") {
                Gdx.app.log("STATUS: ","Un faded button!11");
              //  b1.addAction(unfadeButton);
                 //isPressed=null;
            }

        }
        else if(isPressed=="alo") {
            animImg.startOver(true);
            Gdx.app.log("STATUS: " ,"Untouched button");
            isPressed=null;
        }   // do this for every button
        else if(isPressed=="b1") {
            Gdx.app.log("STATUS: ","Un faded button!1");
                    b1.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
                    b1.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }


    }

    private void setUntouched(AnimatedButton button,String name){

        if(isPressed==name){
            if(name=="alo") {
                Gdx.app.log("STATUS: ", "AI APASAT PLAY");
                button.startOver(true);
               gsm.set(new MenuStage(gsm));
            }

        }
        else if(isPressed=="alo"){
            animImg.startOver(true);
        }
        else if(isPressed=="b1"){
            Gdx.app.log("STATUS: ","Un faded button!2");
            b1.addAction(unfadeButton);
        }
        isPressed=null;




    }
    private void setTouched(AnimatedButton button,String name){
        button.startOver(false);
        isPressed=name;

    }
    private void setTouchedImage(Image button,String name){

        button.addAction(Actions.sequence(Actions.parallel(Actions.scaleTo(0.8f,0.8f,0.1f),Actions.color(new Color(0.7f,0.7f,0.7f,1f),0.1f)),Actions.scaleTo(0.9f,0.9f,0.1f),Actions.scaleTo(0.8f,0.8f,0.1f)));


// button.addAction(fadeButton);
        fadeButton.restart();
        isPressed=name;
        //TODO :  add isPRessed
    }



    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(pointer==0) {

            Vector2 coord = testStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
            try {
                AnimatedButton hitActor = (AnimatedButton) testStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null) {
                    if (hitActor.getName() != isPressed) {
                        Gdx.app.log("You got out bitch: ", hitActor.getName() );
                        setUntouched(hitActor,hitActor.getName());
                    }
                }

            }
            catch(Exception e){

                Image hitActor = (Image) testStage.hit(coord.x, coord.y, true);
                // Tot ce nu e animatedimage (vor fi butoane) va fi gandit aici
                setUnTouched(hitActor,hitActor.getName());
            }
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
