package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;


/**
 * Created by daniel.popescu1709 on 2/20/2018.
 */

public class LevelSelectV2 extends State implements InputProcessor {
   // DIFFERENCE FROM V1 : You move the group instead of moving the camera so you can keep the background in the same place
    private AssetManager assets;
    private Group levels,levelsText,container;
    private Stage levelStage;
    private String isPressed;
    private Vector3 last_touch_down = new Vector3();
    private int y_top_limit;
    private int maxLevel;

    public LevelSelectV2(GameStateManager gsm) {
        super(gsm);

        maxLevel=gsm.getMaxLevel();

        levelStage=new Stage(viewPort);
        assets=new AssetManager();
        loadAssets();
        levels=new Group();
        levelsText=new Group();
        container=new Group();
      //  BitmapFont font=assets.get("font.ttf", BitmapFont.class);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle textStyle = new Label.LabelStyle();
        Label.LabelStyle textStyle2 = new Label.LabelStyle();
        textStyle2.font=font;
        textStyle.font = font;
        textStyle.fontColor= Color.RED;
        textStyle2.fontColor= new Color(0.843f,0.501f,0.09f,1f);
        Image background=new Image(assets.get("background.png",Texture.class));
        background.setName("background");
        background.setTouchable(Touchable.enabled);



        for(int i=0;i<=28;i++){
            Image level;
            Label levelNumber;
            if(i>maxLevel) {
                if (i % 4 == 0) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("redleaf.png", Texture.class)))));
                    level.setPosition(40, i * 200);
                } else if (i % 4 == 1) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("redleaf.png", Texture.class)))));
                    level.setPosition(240, i * 200 - 200);
                } else if (i % 4 == 2) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("redleaf2.png", Texture.class)))));
                    level.setPosition(280, i * 200);
                } else {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("redleaf2.png", Texture.class)))));
                    level.setPosition(480, i * 200 - 200);
                }
                String _text=String.valueOf(i+1);
                level.setTouchable(Touchable.disabled);
                levelNumber=new Label(_text,textStyle);
            }
            else{
                if (i % 4 == 0) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("leaf.png", Texture.class)))));
                    level.setPosition(40, i * 200);
                } else if (i % 4 == 1) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("leaf.png", Texture.class)))));
                    level.setPosition(240, i * 200 - 200);
                } else if (i % 4 == 2) {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("leaf2.png", Texture.class)))));
                    level.setPosition(280, i * 200);
                } else {
                    level = new Image(new SpriteDrawable((new Sprite(assets.get("leaf2.png", Texture.class)))));
                    level.setPosition(480, i * 200 - 200);
                }
                level.setTouchable(Touchable.enabled);
                String _text=String.valueOf(i+1);

                levelNumber=new Label(_text,textStyle2);
            }

            level.setName(String.valueOf(i));




            if(i>10) {
                if (i % 4 == 0 || i % 4 == 1)
                    levelNumber.setBounds(level.getX() + 50, level.getY(), 200, 200);
                else
                    levelNumber.setBounds(level.getX() + 90, level.getY(), 200, 200);
            }
            else {
                if (i % 4 == 0 || i % 4 == 1)
                    levelNumber.setBounds(level.getX() + 60, level.getY(), 200, 200);
                else
                    levelNumber.setBounds(level.getX() + 100, level.getY(), 200, 200);
            }
            levelNumber.setFontScale(3f,3f);
            levelNumber.toFront();


            levelNumber.setTouchable(Touchable.disabled);
            levelNumber.setName("t" + String.valueOf(i));

            level.setOrigin(Align.center);
            levelsText.addActor(levelNumber);
            levels.addActor(level);
        }
        int reper=maxLevel-3;
        if (reper<0)
            reper=0;
        y_top_limit = Math.round(levels.findActor(String.valueOf(reper)).getY());
       // Gdx.app.log("hello world", " BANEL NICOLITA");
        container.addActor(levels);
        container.addActor(levelsText);
        container.moveBy(0,(float)-y_top_limit);
        levelStage.addActor(background);
        levelStage.addActor(container);
     //   levelStage.addActor(levelsText);
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
            //cam.translate(new_position);
            container.moveBy(new_position.x,-new_position.y);

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


        int y_bottom_limit = -1;


        if( -container.getY() +position.y < y_bottom_limit || -container.getY() +position.y > y_top_limit ) {


            return true;


        }
        else
            return false;
    }



    public void loadAssets(){
        assets=new AssetManager();
      //  assets.load("font.ttf",BitmapFont.class);
        assets.load("background.png",Texture.class);
        assets.load("leaf.png",Texture.class);
        assets.load("redleaf.png",Texture.class);

        assets.load("leaf2.png",Texture.class);
        assets.load("redleaf2.png",Texture.class);

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
            gsm.set(new MenuStageV2(gsm));
        }
        return true;
    }

    private void setTouched(Image button,String name) {

        if (name != "background" && name != "name") {

            button.addAction(Actions.sequence(Actions.parallel(Actions.scaleTo(0.95f, 0.95f, 0.1f), Actions.color(new Color(0.7f, 0.7f, 0.7f, 1f), 0.1f)), Actions.scaleTo(1f, 1f, 0.1f), Actions.scaleTo(0.95f, 0.8f, 0.1f)));
            isPressed = name;
          // Gdx.app.log("AM PUS ISPRESSED :  " , isPressed);
          //  Label textPress = levelsText.findActor("t"+name);
          //  textPress.addAction(Actions.sequence(Actions.parallel(Actions.scaleTo(0.95f, 0.95f, 0.1f), Actions.color(new Color(0.7f, 0.7f, 0.7f, 1f), 0.1f)), Actions.scaleTo(1, 1f, 0.1f), Actions.scaleTo(0.95f, 0.8f, 0.1f)));


        }
    }
    private void setUntouched(Image button,String name){
        if(isPressed==name ) {

        if(isPressed=="back"){
            //make a back button maybe
        }
        else {

            button.addAction(Actions.parallel(Actions.scaleTo(1f, 1f, 0f)));
            button.addAction(Actions.color(new Color(1f, 1f, 1f, 1f), 0f));
            if(Integer.parseInt(button.getName())<=maxLevel) // doar daca a ajuns la nivelul ala sa poata da click
                gsm.set(new GameStage(gsm,Integer.parseInt(button.getName())));

            }
        }
        // do this for every button
        else {
            Gdx.app.log("STATUSONE:  " , isPressed);
            Image toUnpress = levels.findActor(isPressed);
            toUnpress.addAction(Actions.parallel(Actions.scaleTo(1f, 1f, 0f)));
            toUnpress.addAction(Actions.color(new Color(1f, 1f, 1f, 1f), 0f));
        }





    }



        @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        // ar trebui facut doar un vector2(sau doi pt 2 touchuri cred) temp pt a nu crea mereu cate unul
        if (pointer == 0) {
            Vector2 coord = levelStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));

            try {
                Image hitActor = (Image) levelStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null)
                    setTouched(hitActor,hitActor.getName());
                if (hitActor.getName()=="background")
                    last_touch_down.set(0, screenY, 0);
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
                        setUntouched(hitActor,hitActor.getName());


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
                if (hitActor != null) {
                    if (hitActor.getName() != isPressed) {
                        Gdx.app.log("You got out bitch: ", hitActor.getName());
                        setUntouched(hitActor,hitActor.getName());
                    }
                }
            }
            catch(Exception e){
                isPressed=null;
            }
        }
        if(isPressed==null)
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



