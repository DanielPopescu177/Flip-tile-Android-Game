package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.interfaces.AdHandler;
import com.mygdx.game.interfaces.FacebookLinkHandler;

import java.util.Stack;

/**
 * Created by daniel.popescu1709 on 2/14/2018.
 */

public class GameStateManager {
    private Stack<State> states;
    private Preferences prefs;
    private int maxLevel;
    private AdHandler adHandler;
    private FacebookLinkHandler mFacebookLinkHandler;
    private boolean soundON,musicON;
    public GameStateManager(AdHandler handler,FacebookLinkHandler fbHandler)
    {
        adHandler=handler;
        mFacebookLinkHandler=fbHandler; //TODO: HANDLER for IOS (open fb page)
        prefs= Gdx.app.getPreferences("levels");
        maxLevel=prefs.getInteger("maxLevel");
        soundON=prefs.getBoolean("soundON");
        musicON=prefs.getBoolean("soundOFF");
        Gdx.app.log("hello",String.valueOf(soundON));
        states=new Stack<State>();
        if(handler!=null)
        handler.showAds(0); // SHOW BANNER ADS THE WHOLE GAME
    }
   public AdHandler getAdHandler(){
        return adHandler;
   }
   public FacebookLinkHandler getFbHandler() { return mFacebookLinkHandler; }
    public void push(State state)
    {
        states.push(state);
    }

    public void pop()
    {
        states.pop().dispose();
    }

    public void onPause() {
        prefs.putInteger("maxLevel",maxLevel);
        prefs.putBoolean("soundON",soundON);
        prefs.putBoolean("musicOn",musicON);
        prefs.flush();
    }
    public void setMaxLevel(int x){
        maxLevel=x;
    }
    public boolean getMusicON(){ return musicON; }
    public boolean getSoundON(){ return soundON; }

    public void setMusicON(){ musicON = !musicON;}
    public void setSoundON(){ soundON = !soundON;}

    public int getMaxLevel(){
        return maxLevel;
    }

    public void set(State state)
    {
        states.pop().dispose();
        states.push(state);
    }
    public void update(float dt){
        states.peek().update(dt);
    }
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
