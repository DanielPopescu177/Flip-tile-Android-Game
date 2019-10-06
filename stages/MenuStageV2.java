package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actors.AnimatedImage;

/**
 * Created by daniel.popescu1709 on 2/11/2018.
 */

public class MenuStageV2 extends State implements InputProcessor {

    private AssetManager assets;

    private Stage menuStage;
    private Group group;

    private String isPressed;
    private Image playButton,mediumButton,hardButton;
    private Image fbButton,instaButton;
    private Image musicButton,soundButton;
    private SpriteDrawable soundOnDrawable,soundOffDrawable,musicOnDrawable,musicOffDrawable;

    public MenuStageV2(GameStateManager gsm) {
        super(gsm);
        loadAssets();


        menuStage= new Stage(viewPort);

        group=new Group();

        Image background=new Image(assets.get("backy.jpg",Texture.class));
        background.setName("background");
        background.setTouchable(Touchable.enabled);
        group.addActor(background);

     //   Image name=new Image(assets.get("name.png",Texture.class));
       // name.setName("name");
        //group.addActor(name);




        playButton=new Image(assets.get("play.png",Texture.class));
        playButton.setName("play");
        playButton.setOrigin(Align.center);
        group.addActor(playButton);

        mediumButton=new Image(assets.get("medium.png",Texture.class));
        mediumButton.setName("medium");
        mediumButton.setOrigin(Align.center);
        group.addActor(mediumButton);


       // group.addActor(hardButton);

        fbButton=new Image(assets.get("fb.png",Texture.class));
        fbButton.setName("fb");
        fbButton.setOrigin(Align.center);
        group.addActor(fbButton);

        instaButton=new Image(assets.get("insta.png",Texture.class));
        instaButton.setName("insta");
        instaButton.setOrigin(Align.center);
        group.addActor(instaButton);

        musicOnDrawable=new SpriteDrawable((new Sprite(assets.get("music_on.png",Texture.class))));
        musicOffDrawable=new SpriteDrawable((new Sprite(assets.get("music_off.png",Texture.class))));

        if(gsm.getMusicON())
        musicButton=new Image(musicOnDrawable);
        else
        musicButton=new Image(musicOffDrawable);

        musicButton.setName("music");
        musicButton.setOrigin(Align.center);
        group.addActor(musicButton);

        soundOnDrawable=new SpriteDrawable((new Sprite(assets.get("sound_on.png",Texture.class))));
        soundOffDrawable=new SpriteDrawable((new Sprite(assets.get("sound_off.png",Texture.class))));

        if(gsm.getSoundON())
           soundButton=new Image(soundOnDrawable);
        else
            soundButton=new Image(soundOffDrawable);


        soundButton.setName("sound");
        soundButton.setOrigin(Align.center);
        group.addActor(soundButton);



        //group.addActor(background);
        // z e dat de ordinea in care le adaugi


        menuStage.addActor(group);

       // name.setPosition(85,850);
        playButton.setPosition(160,500);
        mediumButton.setPosition(190,365);
    //    hardButton.setPosition(190,315);

        fbButton.setPosition(5,1050);
        instaButton.setPosition(105,1050);

        musicButton.setPosition(625,5);
        soundButton.setPosition(525,5);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

    }

    @Override
    public void render(SpriteBatch sb) {
      //  Gdx.gl.glClearColor(252,246,177,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       Gdx.gl.glClearColor(0.988f,0.964f,0.694f,1);
        menuStage.act(Gdx.graphics.getDeltaTime());
        menuStage.draw();

    }
    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.BACK){

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.BACK){
            Gdx.app.exit();
        }
        return true;
    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // ar trebui facut doar un vector2(sau doi pt 2 touchuri cred) temp pt a nu crea mereu cate unul
        if(pointer==0) {
            Vector2 coord = menuStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));

                Image hitActor = (Image) menuStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null)
                    if (hitActor.getName() != null) {
                        setTouched(hitActor,hitActor.getName());
                    }

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer==0) {
            if (isPressed != null) {
                Vector2 coord = menuStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));

                    Image hitActor = (Image) menuStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                    if (hitActor != null)
                        if (hitActor.getName() != null) {
                            setUntouched(hitActor,hitActor.getName());
                        }
            }
            isPressed=null;

            // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot



            //creaza array de obiecte cu tot ce e in ecran ca sa le ai mereu la indemana sa nu le cauti mereu
            //Array<Actor> allObj = menuStage.getActors();




        }
        return true;
    }
    private void setUntouched(Image button,String name){
        if(isPressed==name){
            if(name=="play") {
                gsm.set(new LevelSelectV2(gsm));
                playButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
                playButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
                isPressed=null;
            }
            else if(name=="medium") {
                mediumButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
                mediumButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
                isPressed=null;
            }
            else if(name=="sound") {
                gsm.setSoundON();
                soundButton.clearActions();
                soundButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
                soundButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
                if(gsm.getSoundON())
                    soundButton.setDrawable(soundOnDrawable);
                else
                    soundButton.setDrawable(soundOffDrawable);


            }
            else if(name=="music") {
                gsm.setMusicON();
                musicButton.clearActions();
                musicButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
                musicButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
                if(gsm.getMusicON())
                    musicButton.setDrawable(musicOnDrawable);
                else
                    musicButton.setDrawable(musicOffDrawable);

            }
            else if(name=="fb") {
                gsm.getFbHandler().openFacebookPage(
                        "fb://facewebmodal/f?href=https://www.facebook.com/forgotten.studio", "https://www.facebook.com/forgotten.studio");
                fbButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
                fbButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
                isPressed=null;
            }
            else if(name=="insta") {
                gsm.getFbHandler().openInstagramPage(
                    "http://instagram.com/_u/forgottenstudio","https://www.instagram.com/forgottenstudio"
                );
                instaButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
                instaButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
                isPressed=null;
            }
        }
        // do this for every button
        else if(isPressed=="play") {

            playButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            playButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
           // isPressed=null;
        }
        else if(isPressed=="medium"){
            mediumButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            mediumButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }
        else if(isPressed=="hard"){
            hardButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            hardButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }
        else if(isPressed=="fb"){
            fbButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            fbButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }
        else if(isPressed=="insta"){
            instaButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            instaButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }
        else if(isPressed=="sound") {
            soundButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            soundButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }
        else if (isPressed=="music") {
            musicButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            musicButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }



    }
    private void setTouched(Image button,String name){

        if(name!="background" && name!="name") {
            button.addAction(Actions.sequence(Actions.parallel(Actions.scaleTo(0.8f, 0.8f, 0.1f), Actions.color(new Color(0.7f, 0.7f, 0.7f, 1f), 0.1f)), Actions.scaleTo(0.9f, 0.9f, 0.1f), Actions.scaleTo(0.8f, 0.8f, 0.1f)));
            isPressed = name;

        }

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(pointer==0) {

            Vector2 coord = menuStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));

                Image hitActor = (Image) menuStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null) {
                    if (hitActor.getName() != isPressed) {
                        Gdx.app.log("You got out bitch: ", hitActor.getName());
                        setUntouched(hitActor,hitActor.getName());
                    }
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

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        Gdx.app.log("Status : ", "Disposed menu assets");
        playButton.remove();
        assets.dispose();
        //  menuStage.clear();
        menuStage.dispose();
    }
    public void loadAssets(){
        assets=new AssetManager();
        assets.load("backy.jpg",Texture.class);
        assets.load("name.png",Texture.class);
        assets.load("play.png",Texture.class);

        assets.load("medium.png",Texture.class);


        assets.load("fb.png",Texture.class);

        assets.load("insta.png",Texture.class);

        assets.load("sound_on.png",Texture.class);
        assets.load("sound_off.png",Texture.class);
        assets.load("music_on.png",Texture.class);
        assets.load("music_off.png",Texture.class);
        assets.finishLoading();
    }
}
