package pl.com.nur.kosmos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by MacBuka on 03.02.15.
 */
public class Orzel extends Rectangle {
    ArrayList kierLotu;

    private int kierunekLotu=0;

    public Orzel(Assets assets){
        kierLotu = new ArrayList<Texture>();

        kierLotu.add(assets.menager.get("kosmos/orzelLeci.png", Texture.class));
        kierLotu.add(assets.menager.get("kosmos/orzelLewo.png", Texture.class));
        kierLotu.add(assets.menager.get("kosmos/orzelPrawo.png", Texture.class));
        kierLotu.add(assets.menager.get("kosmos/orzelStop.png", Texture.class));

        this.height = ((Texture) kierLotu.get(0)).getHeight();  // wiekosc naszej maply jest taka jak obrazka
        this.width = ((Texture) kierLotu.get(0)).getWidth();   // szerokosc tekstury
        x=width/2;
    }

    public void draw(SpriteBatch batch) {
        if (kierunekLotu == 0) {  // prosto
            batch.draw((Texture) kierLotu.get(kierunekLotu), x, y);
        }
        else if (kierunekLotu == 1) { // lewo
            batch.draw((Texture) kierLotu.get(kierunekLotu), x, y);
        }
        else if (kierunekLotu == 2) {  // prawo
            batch.draw((Texture) kierLotu.get(kierunekLotu), x, y);
        }
        else {  //stop
            batch.draw((Texture) kierLotu.get(kierunekLotu), x, y);
        }

    }

    public void setkierunekLotu(int kierunekLotu){
        this.kierunekLotu = kierunekLotu;
    }

}
