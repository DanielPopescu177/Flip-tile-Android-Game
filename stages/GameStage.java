package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.actors.AnimatedImage;
import com.mygdx.game.actors.HealthBar;

import java.util.Random;

import javax.xml.soap.Text;

/**
 * Created by daniel.popescu1709 on 2/14/2018.
 */

public class GameStage extends State implements InputProcessor {

    private final static float BEGGIN_TIME=1f; // time before flipping at start point
    private boolean inProgress=false; // are we still showing flipping animations?

    private Label timeCounter;
    private Label.LabelStyle textStyle;
    private BitmapFont font;

    private int selectedCard;
    private int selectedCardPosition;

    private AnimatedImage anim,star1,star2,star3;
    private int[] posX,posY;
    private TextureAtlas back1,back2;
    private Array<TextureAtlas> allCardsTextures2;
    private int[] positions;
    private Texture bg;
    private Stage gameStage;
    private AssetManager assets;

    private String isPressed;
    private Group group,exitGroup;
    private Group hud;

    boolean levelFinishScreen;
    boolean gamePaused;

    private int[] moment; // 0 pt inainte sa arate cartile,1 cand le arata,2 cand a terminat de aratat
    private float showTime;
    private float[] currentTime;
    private boolean[] doneFliping;
    private int cardsNumber;
    private Array<AnimatedImage> allCards;
    private boolean[] foundCard;

    private HealthBar healthBar;
    protected int level_number;
    private Texture loadingTexture;
    private Image menuButton,levelsButton,nextButton,yesButton,noButton,pauseButton;

    public GameStage(GameStateManager gsm,int levelNumber) {

        super(gsm);
        levelFinishScreen=false;
        gamePaused=false;

        gameStage= new Stage(viewPort);
        loadingTexture=new Texture("loading.png");
        Image loading = new Image(loadingTexture);
        gameStage.addActor(loading);
        loadAssets();
        level_number=levelNumber;
        initialSetup(levelNumber);

        loadCardsTextures();



        generateCards();
        loadCards();




        //cam.translate(720,0);
        //cam.update();


        Gdx.app.log("Status: " ,"Loaded Game Stage");
        group=new Group();
        exitGroup=new Group();

        Image background=new Image(assets.get("background.png",Texture.class));
        background.setName("background");
        background.setPosition(0,-80);
        background.setTouchable(Touchable.enabled);

        menuButton=new Image(assets.get("menu.png",Texture.class));
        levelsButton=new Image(assets.get("levels.png",Texture.class));
        nextButton=new Image(assets.get("next.png",Texture.class));
        Image win=new Image(assets.get("card/win.png",Texture.class));

        levelsButton.setName("levels");
        levelsButton.setTouchable(Touchable.enabled);
        levelsButton.setPosition(1100,400);
        levelsButton.setOrigin(Align.center);

        menuButton.setName("mainmenu");
        menuButton.setTouchable(Touchable.enabled);
        menuButton.setPosition(900,400);
        menuButton.setOrigin(Align.center);

        nextButton.setName("next");
        nextButton.setTouchable(Touchable.enabled);
        nextButton.setPosition(950,500);
        nextButton.setOrigin(Align.center);

        win.setPosition(730,320);

        Animation an=new Animation(1/20f,assets.get("gamestageUI/star1.txt",TextureAtlas.class).getRegions());
        star1=new AnimatedImage(an,false,false);
        star1.setPosition(845,668);
        star1.setOrigin(Align.center);
        star1.setTouchable(Touchable.disabled);

        star2=new AnimatedImage(an,false,false);
        star2.setPosition(1008,672);
        star2.setOrigin(Align.center);
        star2.rotateBy(-16);
        star2.setTouchable(Touchable.disabled);

        star3=new AnimatedImage(an,false,false);
        star3.setPosition(1168,661);
        star3.setOrigin(Align.center);
        star3.rotateBy(-28);
        star3.setTouchable(Touchable.disabled);

        Image exitBackground2=new Image(assets.get("background.png",Texture.class));
        Image exitBackground=new Image(assets.get("card/exit.png",Texture.class));
        exitBackground.setPosition(50,1960);
        exitBackground2.setPosition(0,1440);
        exitBackground2.toBack();

        Gdx.app.log("DIMENSIUNI:",String.valueOf(Gdx.graphics.getHeight()));
        yesButton=new Image(assets.get("card/yes.png",Texture.class));
        yesButton.setName("yes");
        yesButton.setTouchable(Touchable.enabled);
        yesButton.setPosition(200,1920);
        yesButton.setOrigin(Align.center);

        noButton=new Image(assets.get("card/no.png",Texture.class));
        noButton.setName("no");
        noButton.setTouchable(Touchable.enabled);
        noButton.setPosition(420,1920);
        noButton.setOrigin(Align.center);

        exitGroup.addActor(exitBackground2);
        exitGroup.addActor(exitBackground);
        exitGroup.addActor(yesButton);
        exitGroup.addActor(noButton);

        //background.setName("background");
        //group.addActor(background);

        pauseButton=new Image(assets.get("gamestageUI/pause.png",Texture.class));
        pauseButton.setName("pause");
        pauseButton.setTouchable(Touchable.enabled);
        pauseButton.setPosition(Gdx.graphics.getWidth()-110,Gdx.graphics.getHeight()-110);
        pauseButton.setOrigin(Align.center);

        background.toBack();
        gameStage.addActor(background);
        hud.addActor(healthBar);
        hud.addActor(pauseButton);
        hud.toFront();

        group.addActor(win);
        group.addActor(menuButton);
        group.addActor(levelsButton);
        group.addActor(nextButton);
        group.addActor(star1);
        group.addActor(star2);
        group.addActor(star3);
        gameStage.addActor(group);
        gameStage.addActor(exitGroup);
        gameStage.addActor(hud);



        for(int i=0;i<cardsNumber;i++){
            gameStage.addActor(allCards.get(i));
            foundCard[i]=false;
        }
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

    }
    protected void loadCardsTextures(){

        allCardsTextures2=new Array<TextureAtlas>();
        for(int i=0;i<cardsNumber/2;i++)
        {
            allCardsTextures2.add(assets.get("card/"+String.valueOf(i)+"1.txt",TextureAtlas.class));
        }
        Gdx.app.log("Done loading atlases !  ", String.valueOf(cardsNumber));

    }
    protected void initialSetup(int levelNumber) {
        selectedCard=-1;
        foundCard=new boolean[20];
        int x=Math.round(levelNumber/14);
        cardsNumber= 4 + x * 2;

        showTime=8-(0.5f*levelNumber)+(x*7); // sa arate cartile x sec
        if(levelNumber<50)
            showTime /= 2;
        Gdx.app.log("cards number: ", String.valueOf(cardsNumber));
        currentTime=new float[20];
        moment=new int[20];
        doneFliping=new boolean[20];
        allCards=new Array<AnimatedImage>();
        posX=new int[20];
        posY=new int[20];

        back1=assets.get("card/cards.txt",TextureAtlas.class);
        back2=assets.get("card/cards2.txt",TextureAtlas.class);
        //  front1=assets.get("card/front.txt",TextureAtlas.class);
        //   front2=assets.get("card/front2.txt",TextureAtlas.class);

        setPositions();


        hud=new Group();
        font=new BitmapFont();
        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        textStyle.fontColor= Color.BLACK;
        String _text="Time before flipping back ; " + String.valueOf(currentTime);
        timeCounter=new Label(_text,textStyle);
        timeCounter.setBounds(0,30f,120,10);
        timeCounter.setFontScale(2.5f,2.5f);
        timeCounter.toFront();

        healthBar=new HealthBar(assets.get("healthbar.png",Texture.class),assets.get("healthbar_outline.png",Texture.class));


    }
    protected void setPositions(){
        posX[0]=210;posY[0]=480;
        posX[1]=390;posY[1]=480;
        posX[2]=210;posY[2]=640;
        posX[3]=390;posY[3]=640;
        posX[4]=210;posY[4]=320;
        posX[5]=390;posY[5]=320;
        posX[6]=30;posY[6]=480;
        posX[7]=570;posY[7]=480;
        posX[8]=30;posY[8]=640;
        posX[9]=570;posY[9]=640;
        posX[10]=30;posY[10]=320;
        posX[11]=570;posY[11]=320;
        posX[12]=210;posY[12]=800;
        posX[13]=390;posY[13]=800;
        posX[14]=30;posY[14]=800;
        posX[15]=570;posY[15]=800;
        posX[16]=210;posY[16]=960;
        posX[17]=390;posY[17]=960;
        posX[18]=30;posY[18]=960;
        posX[19]=570;posY[19]=960;
    }
    private boolean levelFinished(){
        for(int i=0;i<cardsNumber;i++)
            if(!foundCard[i])
                return false;
        return true;
    }
    protected boolean isTaken(int x){
        int aparition=0;
        for(int i=0;i<cardsNumber;i++){
            if(positions[i]==x)
                aparition++;
        }
        if(aparition==2)
            return true;
        else
            return false;
    }
    protected void generateCards(){
        Random rand=new Random();
        positions=new int[20];
        for(int i=0;i<20;i++)
            positions[i]= -1;
        for(int i=0;i<cardsNumber;i++)
        {
            int p=rand.nextInt(cardsNumber/2);
            if( !isTaken(p) ){
                positions[i]=p;
            }
            else{
                i--;
            }
        }
        for(int i=0;i<20;i++)
            Gdx.app.log(String.valueOf(i)+ " : ",String.valueOf(positions[i]));
    }
    protected void loadCards(){
        for(int i=0;i<cardsNumber;i++)
        {
            Animation an=new Animation(1/30f,back1.getRegions());
            AnimatedImage animImg=new AnimatedImage(an,false,false);
            animImg.setPosition(posX[i],posY[i]);
            animImg.setName(String.valueOf(i));
            animImg.setTouchable(Touchable.enabled);
            allCards.add(animImg);
        }
    }



    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
        {

            // gsm.set(new MenuStage(gsm));
        }
    }

    @Override
    public void update(float dt) {



        handleInput();

      if(!gamePaused) {

          for (int i = 0; i < cardsNumber; i++) {
              anim = allCards.get(i);

              if (moment[i] == 0 && currentTime[i] < BEGGIN_TIME) {
                  currentTime[i] += dt;
              } else if (moment[i] == 0 && currentTime[i] >= BEGGIN_TIME) {


                  currentTime[i] = 0;
                  moment[i] = 1;
                  anim.start();
              }
              if (moment[i] == 1 && (currentTime[i] < showTime)) {

                  if (anim.animOver() && !doneFliping[i]) {
                      doneFliping[i] = true;
                      Animation anim2 = new Animation(1 / 40f, allCardsTextures2.get(positions[i]).getRegions());
                      anim.startOver(anim2, 0, true);
                      //       Gdx.app.log("STATUS: ", "Intorc cu fata cartea");
                  }

                  currentTime[i] += dt;
                  if (i == 0) {
                      float width = 560f - currentTime[0] / showTime * 560f;
                      if (width < 25)
                          width = 25;
                      healthBar.setWidth(width);
                  }
              } else if (moment[i] == 1 && (currentTime[i] > showTime)) {

                  moment[i] = 2;

                  if (anim.animOver() && doneFliping[i]) {
                      doneFliping[i] = false;
                      //        Gdx.app.log("STATUS: ", "Schimb animatia");
                      //  Animation anim2 = new Animation(1 / 40f, allCardsTextures2.get(positions[i]).getRegions());
                      anim.startOver(null, 0, false);

                  }
              } else if (moment[i] == 2 && anim.animOver()) {

                  moment[i] = 3;
                  Animation anim2 = new Animation(1 / 40f, back1.getRegions());
                  anim.startOver(anim2, 0, true);

              } else if (moment[i] == 4 && anim.animOver()) {
                  moment[i] = 5;
                  Animation anim2 = new Animation(1 / 40f, allCardsTextures2.get(positions[i]).getRegions());
                  anim.startOver(anim2, 0, true);
              }
              if (moment[i] == 5 && anim.animOver()) {
                  if (selectedCard == -1)
                      if (!foundCard[i] && !anim.turnLater) {
                          turnBack(anim, i, 1f);

                          // inProgress=false;
                      } else if (!foundCard[i] && anim.turnLater) {
                          turnBack(anim, i, 1.2f);
                          Gdx.app.log("STATUS: ", "Water fuck!");

                      } else
                          inProgress = false;
              } else if (moment[i] == 7 && anim.animOver()) {
                  Gdx.app.log("STATUS: ", "Finishing flipping! i= " + String.valueOf(i));
                  Animation anim2 = new Animation(1 / 40f, back1.getRegions());
                  anim.startOver(anim2, 0, true);
                  moment[i] = 3;
                  inProgress = false;

              }


              if (anim.turnLater && anim.animOver())

                  // turnBack(anim,i,0f);
                  anim.turnLater = false;
          }

          if (levelFinished() && !levelFinishScreen) {
              levelFinishScreen = true;
              if (gsm.getMaxLevel() < level_number + 1)
                  gsm.setMaxLevel(level_number + 1);
              //finish game,show win screen
              group.toFront();
              MoveToAction moveAction = new MoveToAction();
              moveAction.setPosition(-720f, 0f);
              moveAction.setDuration(0.6f);
              group.addAction(moveAction);
              star1.startDelayed(1.2f);
              star2.startDelayed(1.7f);
              star3.startDelayed(2.2f);
              //    star1.startOver(null,0.5f,false);
          }


          Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      }
        gameStage.act(dt);
        gameStage.draw();
    }
    private void flip(AnimatedImage an,int i){
        if(moment[i]==3 && selectedCard==-1 &&!inProgress) {
            selectedCard=positions[i];
            selectedCardPosition=i;
            Animation anim2 = new Animation(1 / 40f, back1.getRegions());
            an.startOver(anim2,0, false);
            moment[i]=4;
            inProgress=true;
        }
        else if(moment[i]==3 && selectedCard!=-1)
        {
            if(selectedCard==positions[i]){

                Gdx.app.log("STATUS: ","Two of the same found! " + String.valueOf(i) + " " + String.valueOf(selectedCardPosition));
                Animation anim2 = new Animation(1 / 40f, back1.getRegions());
                an.startOver(anim2,0, false);
                moment[i]=4;
                foundCard[i]=true;
                foundCard[selectedCardPosition]=true;
                selectedCard = -1;
                selectedCardPosition=-1;

            }
            else{

                Animation anim2 = new Animation(1 / 40f, back1.getRegions());
                an.startOver(anim2,0, false);
                moment[i]=4;
                Gdx.app.log("STATUS: ","Wrong! Fliping back ( "+String.valueOf(selectedCardPosition) + "  and  "  + String.valueOf(i));

                //turnBack(an,i,0);
                AnimatedImage toTrun=allCards.get(selectedCardPosition);

                toTrun.turnLater=true;
                //turnBack(toTrun,selectedCardPosition,1f);
                selectedCard=-1;
                selectedCardPosition=-1;


            }
        }
    }
    private void turnBack(AnimatedImage an ,int i,float delayed)
    {
        Animation anim2 = new Animation(1 / 40f, allCardsTextures2.get(positions[i]).getRegions());
        an.startOver(anim2,delayed, false);
        moment[i]=7;
    }

    @Override
    public void render(SpriteBatch sb) {


    }

    @Override
    public void dispose() {
        Gdx.app.log("Status : ", "Disposed game stage assets");
        loadingTexture.dispose();
        assets.dispose();
        gameStage.clear();
        gameStage.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.BACK){

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.BACK || keycode==Input.Keys.A){
             togglePauseGame();
        }
        return true;
    }
    private void togglePauseGame(){
        if(!gamePaused) {
            exitGroup.toFront();
            MoveToAction moveAction = new MoveToAction();
            moveAction.setPosition(0f, -1440f);
            moveAction.setDuration(0.3f);
            exitGroup.addAction(moveAction);
            gamePaused = true;
        }
        else {
            exitGroup.toFront();
            MoveToAction moveAction = new MoveToAction();
            moveAction.setPosition(0f, 1440f);
            moveAction.setDuration(0.3f);
            exitGroup.addAction(moveAction);
            gamePaused = false;
        }
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // ar trebui facut doar un vector2(sau doi pt 2 touchuri cred) temp pt a nu crea mereu cate unul
        if(pointer==0) {
            Vector2 coord = gameStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
            try {
                AnimatedImage hitActor = (AnimatedImage) gameStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot


            }
            catch(Exception e){
                Image hitActor = (Image) gameStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null)
                    if (hitActor.getName() != null) {
                        setTouched(hitActor, hitActor.getName());
                    }
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (pointer == 0) {

            Vector2 coord = gameStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
            try {
                AnimatedImage hitActor = (AnimatedImage) gameStage.hit(coord.x, coord.y, true);
                if(hitActor!=null)
                    if(hitActor.getName()!=null) {
                        {

                            try {
                                int x = Integer.parseInt(hitActor.getName());
                                flip(hitActor, x);
                            } catch (Exception e) {
                                return true;
                            }
                        }
                    }
            }
            catch (Exception e)
            {
                Image hitActor = (Image) gameStage.hit(coord.x, coord.y, true);
                // Tot ce nu e animatedimage (vor fi butoane) va fi gandit aici
                setUntouched(hitActor,hitActor.getName());
            }

        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(pointer==0) {
            Vector2 coord = gameStage.screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
            try {
                AnimatedImage hitActor = (AnimatedImage) gameStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot

            }
            catch(Exception e){
                Image hitActor = (Image) gameStage.hit(coord.x, coord.y, true); // daca vrei sa verifice doar ce e touchable pui true,acum verifica tot
                if (hitActor != null)
                    if (hitActor.getName() != isPressed) {
                        setUntouched(hitActor, hitActor.getName());
                    }
            }
        }
        return true;
    }

    private void setUntouched(Image button,String name){
        if(isPressed==name){
            if(name=="levels") {
                gsm.set(new LevelSelectV2(gsm));
                if(gsm.getAdHandler()!=null)
                    gsm.getAdHandler().showAds(10);
            }
            else if(name=="mainmenu"){
                gsm.set(new MenuStageV2(gsm));
                if(gsm.getAdHandler()!=null)
                    gsm.getAdHandler().showAds(10);
            }
            else if(name=="next") {
                gsm.set(new GameStage(gsm,level_number+1));
            }
            else if(name=="no") {
                noButton.clearActions();
                noButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.01f)));
                noButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.01f));
                togglePauseGame();

            }
            else if(name=="yes"){
                yesButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
                yesButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
                gsm.set(new MenuStageV2(gsm));
            }
            else if(name=="pause"){
                pauseButton.clearActions();
                pauseButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.01f)));
                pauseButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.01f));
                togglePauseGame();
            }
        }
        // do this for every button
        else if(isPressed=="levels") {

            levelsButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            levelsButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
             isPressed=null;
        }
        else if(isPressed=="mainmenu"){
            menuButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            menuButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }
        else if(isPressed=="next"){
            nextButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            nextButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }
        else if(isPressed=="yes"){
            yesButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            yesButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }
        else if(isPressed=="no"){
            noButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            noButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
            isPressed=null;
        }
        else if(isPressed=="pause"){
            pauseButton.addAction(Actions.parallel(Actions.scaleTo(1f,1f,0.2f)));
            pauseButton.addAction(Actions.color(new Color(1f,1f,1f,1f),0.2f));
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
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void loadAssets(){
        assets=new AssetManager();
        assets.load("background.png",Texture.class);
        assets.load("gamestageUI/star1.txt", TextureAtlas.class);
        assets.load("card/cards.txt", TextureAtlas.class);   //card/ ar trebui salvat in preferences in android
        assets.load("card/cards2.txt",TextureAtlas.class);
        assets.load("card/00.txt",TextureAtlas.class);
        assets.load("card/01.txt",TextureAtlas.class);
        assets.load("card/10.txt",TextureAtlas.class);
        assets.load("card/11.txt",TextureAtlas.class);
        assets.load("card/20.txt",TextureAtlas.class);
        assets.load("card/21.txt",TextureAtlas.class);
        assets.load("card/win.png",Texture.class);
        assets.load( "card/exit.png",Texture.class);
        assets.load("card/yes.png",Texture.class);
        assets.load("card/no.png",Texture.class);
        assets.load("menu.png",Texture.class);
        assets.load("levels.png",Texture.class);
        assets.load("next.png",Texture.class);
        assets.load("gamestageUI/pause.png",Texture.class);
        assets.load("healthbar.png",Texture.class);
        assets.load("healthbar_outline.png",Texture.class);
        assets.finishLoading();
    }
}
