package pl.com.nur.kosmos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by MacBuka on 03.02.15.
 */
public class Assets implements Disposable {

    public final AssetManager menager = new AssetManager();

    public void load() {
        menager.load("kosmos/astroid1.png", Texture.class);
        menager.load("kosmos/astroid2.png", Texture.class);
        menager.load("kosmos/orzelLeci.png", Texture.class);
        menager.load("kosmos/orzelLewo.png", Texture.class);
        menager.load("kosmos/orzelPrawo.png", Texture.class);
        menager.load("kosmos/orzelStop.png", Texture.class);

       // menager.load("music.mp3", Music.class);
        menager.load("jump.wav", Sound.class);
    }


    @Override
    public void dispose() {
        menager.dispose();

    }

}
