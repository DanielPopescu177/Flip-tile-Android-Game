package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actors.AnimatedImage;

/**
 * Created by daniel.popescu1709 on 2/11/2018.
 */

public class MenuStage extends State implements InputProcessor {

    private AssetManager assets;

    private Stage menuStage;
    private Group group;

    private String isPressed;
    private Image playButton,mediumButton,hardButton;
    private Image fbButton,instaButton;
    private Image musicButton,soundButton;
    private SpriteDrawable playBtn,playBtn_touched,mediumBtn,mediumBtn_touched,hardBtn,hardBtn_touched;
    private SpriteDrawable fbBtn,fbBtn_touched,instaBtn,instaBtn_touched;

    public MenuStage(GameStateManager gsm) {
        super(gsm);
        loadAssets();


        menuStage= new Stage(viewPort);

        group=new Group();

        Image background=new Image(assets.get("background.png",Texture.class));
        background.setName("background");
        background.setTouchable(Touchable.enabled);
       group.addActor(background);
        Image name=new Image(assets.get("name.png",Texture.class));
        name.setName("name");
        group.addActor(name);

        playBtn=new SpriteDrawable((new Sprite(assets.get("play.png",Texture.class))));
        playBtn_touched=new SpriteDrawable((new Sprite(assets.get("play_touched.png",Texture.class))));
        playButton=new Image(playBtn);
        //playButton.setDrawable(playBtn);
        playButton.setName("play");
        group.addActor(playButton);


        mediumBtn=new SpriteDrawable((new Sprite(assets.get("medium.png",Texture.class))));
        mediumBtn_touched=new SpriteDrawable((new Sprite(assets.get("medium_touched.png",Texture.class))));
        mediumButton=new Image(mediumBtn);
        mediumButton.setName("medium");
        group.addActor(mediumButton);

        hardBtn=new SpriteDrawable((new Sprite(assets.get("hard.png",Texture.class))));
        hardBtn_touched=new SpriteDrawable((new Sprite(assets.get("hard_touched.png",Texture.class))));
        hardButton=new Image(hardBtn);
        hardButton.setName("hard");
        group.addActor(hardButton);

        fbBtn=new SpriteDrawable((new Sprite(assets.get("fb.png",Texture.class))));
        fbBtn_touched=new SpriteDrawable((new Sprite(assets.get("fb_touched.png",Texture.class))));
        fbButton=new Image(fbBtn);
        fbButton.setName("fb");
        group.addActor(fbButton);

        instaBtn=new SpriteDrawable((new Sprite(assets.get("insta.png",Texture.class))));
        instaBtn_touched=new SpriteDrawable((new Sprite(assets.get("insta_touched.png",Texture.class))));
        instaButton=new Image(instaBtn);
        instaButton.setName("insta");
        group.addActor(instaButton);







        //group.addActor(background);
        // z e dat de ordinea in care le adaugi


        menuStage.addActor(group);

        name.setPosition(85,850);
        playButton.setPosition(190,585);
        mediumButton.setPosition(190,450);
        hardButton.setPosition(190,315);

        fbButton.setPosition(5,1190);
        instaButton.setPosition(105,1190);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(252,246,177,1);
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


            try {
                Image hitActor = (Image) menuStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null)
                    if (hitActor.getName() != null) {
                        setTouched(hitActor.getName());
                    }
            }
            catch (Exception e)
            {
              // Not going to do anything
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer==0) {
            if (isPressed != null) {
                Vector2 coord = menuStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
                try {
                    Image hitActor = (Image) menuStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                    if (hitActor != null)
                        if (hitActor.getName() != null) {
                            setUntouched(hitActor.getName());
                        }
                } catch (Exception e) {
                   Gdx.app.log("STATUS: " , e.toString());
                }
            }
          isPressed=null;

            // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot



            //creaza array de obiecte cu tot ce e in ecran ca sa le ai mereu la indemana sa nu le cauti mereu
            Array<Actor> allObj = menuStage.getActors();




        }
        return true;
    }
    private void setUntouched(String buttonName){
        if (isPressed == "play") {
            playButton.setDrawable(playBtn);
            if (buttonName == "play") {
              //  playButton.setDrawable(playBtn);
                //gsm.set(new GameStage(gsm,10));
                gsm.set(new LevelSelectV2(gsm));
            }
        } else if (isPressed == "medium") {
            mediumButton.setDrawable(mediumBtn);
            if (buttonName == "medium") {

                //gsm.set(new GameStage(gsm,10));
                gsm.set(new TestStage(gsm));
            }
        }
        else if (isPressed == "hard")
            hardButton.setDrawable(hardBtn);
        else if (isPressed == "fb")
            fbButton.setDrawable(fbBtn);
        else if (isPressed == "insta")
            instaButton.setDrawable(instaBtn);
    }
    private void setTouched(String buttonName){
        if (buttonName == "play") {
            playButton.setDrawable(playBtn_touched);
            isPressed = buttonName;
            //	hitActor.setDrawable(new SpriteDrawable((new Sprite(new Texture("play_touched.png")))));
            //image.setDrawable(new SpriteDrawable(new Sprite(newTexture)));
        } else if (buttonName == "medium") {
            mediumButton.setDrawable(mediumBtn_touched);
            isPressed = buttonName;
        } else if (buttonName== "hard") {
            hardButton.setDrawable(hardBtn_touched);
            isPressed = buttonName;
        } else if (buttonName == "fb") {
            fbButton.setDrawable(fbBtn_touched);
            isPressed = buttonName;
        } else if (buttonName == "insta") {
            instaButton.setDrawable(instaBtn_touched);
            isPressed = buttonName;
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(pointer==0) {

            Vector2 coord = menuStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
            try {
                Image hitActor = (Image) menuStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null) {
                    if (hitActor.getName() != isPressed) {
                        Gdx.app.log("You got out bitch: ", hitActor.getName());
                        setUntouched(hitActor.getName());
                        isPressed = null;
                    }
                }

            }
            catch(Exception e){
              //  setUntouched(isPressed);
              //  isPressed = null;

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
        assets.load("background.png",Texture.class);
        assets.load("name.png",Texture.class);
        assets.load("play.png",Texture.class);
        assets.load("play_touched.png",Texture.class);
        assets.load("medium.png",Texture.class);
        assets.load("medium_touched.png",Texture.class);
        assets.load("hard.png",Texture.class);
        assets.load("hard_touched.png",Texture.class);
        assets.load("fb.png",Texture.class);
        assets.load("fb_touched.png",Texture.class);
        assets.load("insta.png",Texture.class);
        assets.load("insta_touched.png",Texture.class);
        assets.load("sound.png",Texture.class);
        assets.load("sound_touched.png",Texture.class);
        assets.load("music.png",Texture.class);
        assets.load("music_touched.png",Texture.class);
        assets.finishLoading();
    }
}
