package pl.com.nur.kosmos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by MacBuka on 03.02.15.
 */
public class Asteroid extends Rectangle {

    Assets assets;
    private Texture astero;
    private Texture astero1;
    private Texture astero2;
    private int nrAstero = 0;
    private float wysokosc = Gdx.graphics.getHeight();
    private float szerokosc= Gdx.graphics.getWidth();
    private float speed;

    public Asteroid(Assets assets){
        this.assets = assets;

        losujAst();

        this.height = astero. getHeight();
        this.width = astero.getWidth();
    }

    private void losujAst()
    {
        astero1 = assets.menager.get("kosmos/astroid1.png", Texture.class);
        astero2 =(assets.menager.get("kosmos/astroid2.png", Texture.class));
        speed = (MathUtils.random(2, 8)/2) * (Kosmos.level.getPoziom());
        nrAstero =  MathUtils.random(2);
        if (nrAstero<=1)
            astero = astero1;
        else
            astero = astero2;
        x = MathUtils.random(0-width/2, szerokosc-width/2);
        y = MathUtils.random(wysokosc +100, wysokosc +  MathUtils.random(500));  // beda pojawac sie w ruznym czasie
    }

    public void draw(SpriteBatch batch){
        batch.draw(astero, x, y);
    }

    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed){
        this.speed=speed;
    }
}
