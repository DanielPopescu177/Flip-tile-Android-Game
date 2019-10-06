package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;


/**
 * Created by daniel.popescu1709 on 2/20/2018.
 */

public class LevelSelect extends State implements InputProcessor {

    private AssetManager assets;
    private Group levels,levelsText;
    private Stage levelStage;
    private String isPressed;
    private Vector3 last_touch_down = new Vector3();

    private int maxLevel;

    public LevelSelect(GameStateManager gsm) {
        super(gsm);
        maxLevel=gsm.getMaxLevel();
        levelStage=new Stage(viewPort);
        assets=new AssetManager();
        loadAssets();
        levels=new Group();
        levelsText=new Group();

        BitmapFont font=new BitmapFont();
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = font;


        for(int i=0;i<=28;i++){
            Image level;
            if(i>maxLevel) {
                if (i % 4 == 0) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("redleaf.png", Texture.class)))));
                    level.setPosition(0, i * 200);
                } else if (i % 4 == 1) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("redleaf.png", Texture.class)))));
                    level.setPosition(200, i * 200 - 200);
                } else if (i % 4 == 2) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("redleaf2.png", Texture.class)))));
                    level.setPosition(300, i * 200);
                } else {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("redleaf2.png", Texture.class)))));
                    level.setPosition(500, i * 200 - 200);
                }
                textStyle.fontColor= Color.RED;
            }
            else{
                if (i % 4 == 0) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("leaf.png", Texture.class)))));
                    level.setPosition(0, i * 200);
                } else if (i % 4 == 1) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("leaf.png", Texture.class)))));
                    level.setPosition(200, i * 200 - 200);
                } else if (i % 4 == 2) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("leaf2.png", Texture.class)))));
                    level.setPosition(300, i * 200);
                } else {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("leaf2.png", Texture.class)))));
                    level.setPosition(500, i * 200 - 200);
                }
                textStyle.fontColor= Color.WHITE;
            }

            level.setName(String.valueOf(i));
            level.setTouchable(Touchable.enabled);


            String _text=String.valueOf(i);

            Label levelNumber=new Label(_text,textStyle);
            levelNumber.setBounds(level.getX()+75,level.getY(),200,200);
            levelNumber.setFontScale(5f,5f);
            levelNumber.toFront();


            levelNumber.setTouchable(Touchable.disabled);
            levelsText.addActor(levelNumber);
            levels.addActor(level);
        }

        levelStage.addActor(levels);
        levelStage.addActor(levelsText);
        levelStage.act();

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
        levelStage.act(Gdx.graphics.getDeltaTime());
        levelStage.draw();
    }

    @Override
    public void dispose() {
        Gdx.app.log("Status : ", "Disposed level select assets");

        assets.dispose();
        //  menuStage.clear();
        levelStage.dispose();
    }

    private void moveCamera( int touch_x, int touch_y ) {

        Vector3 new_position = getNewCameraPosition( 720, touch_y );

        if( !cameraOutOfLimit( new_position ) ) {
            new_position.x=0;
            cam.translate(new_position);

        }

        last_touch_down.set( 720, touch_y, 0);
       // cam.update();

    }
    private Vector3 getNewCameraPosition( int x, int y ) {


        Vector3 new_position = last_touch_down;

        if(last_touch_down.y<y) {
            new_position.sub(x, y, 0);
            new_position.y = -new_position.y;

        }
        else{
            new_position.sub(x, y, 0);
            new_position.y = -new_position.y;

        }
      //  new_position.add( new Vector3(0,cam.position.y,0) );

        return new_position;
    }
    private boolean cameraOutOfLimit( Vector3 position ) {


        int y_bottom_limit = 640;
        int y_top_limit = 5000;

         if( cam.position.y +position.y < y_bottom_limit || cam.position.y +position.y > y_top_limit ) {


             return true;


         }
        else
            return false;
    }



    public void loadAssets(){
        assets=new AssetManager();
        assets.load("leaf.png",Texture.class);
        assets.load("redleaf.png",Texture.class);
        assets.load("leaf_touched.png",Texture.class);
        assets.load("leaf2.png",Texture.class);
        assets.load("redleaf2.png",Texture.class);
        assets.load("leaf2_touched.png",Texture.class);
        assets.finishLoading();
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
            gsm.set(new MenuStage(gsm));
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        last_touch_down.set(0, screenY, 0);
        // ar trebui facut doar un vector2(sau doi pt 2 touchuri cred) temp pt a nu crea mereu cate unul
        if (pointer == 0) {
            Vector2 coord = levelStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));

            try {
                Image hitActor = (Image) levelStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null)
                    isPressed = hitActor.getName();
            } catch (Exception e) {

            }



        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer==0) {

            Vector2 coord = levelStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
            try {
                Image hitActor = (Image) levelStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null)
                    if (hitActor.getName() != null && hitActor.getName()==isPressed) {
                         if(Integer.parseInt(hitActor.getName())<=maxLevel) // doar daca a ajuns la nivelul ala sa poata da click
                         gsm.set(new GameStage(gsm,Integer.parseInt(hitActor.getName())));

                    }
            }
            catch(Exception e){
                Gdx.app.log("WTF: ",e.toString());
            }
        }
        isPressed=null;



        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
     //   Gdx.app.log("Status:" ,"Trying to move the camera");
        if(pointer==0) {

            Vector2 coord = levelStage.screenToStageCoordinates(new Vector2((float) x, (float) y));
            try {
                Image hitActor = (Image) levelStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null)
                    if (hitActor.getName() != null && hitActor.getName()==isPressed) {


                    }
            }
            catch(Exception e){
                isPressed=null;
            }
        }
        moveCamera( x, y );
        return true;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
      //  Gdx.app.log("SCROLLED: " , String.valueOf(amount));
        return false;
    }
}



