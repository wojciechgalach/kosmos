package pl.com.nur.kosmos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.util.ArrayList;

public class Kosmos extends ApplicationAdapter {
	SpriteBatch batch;
    BitmapFont fontPunkty;
    BitmapFont gameover;

    Assets assets;  //kalsa z zasobami
    float accelerometerX = 0.0f;
    float accelerometerY = 0.0f;
    private Music music;  // do muzyki
    private Sound destroyed;
    Orzel orzel;
    static public Level level;
    ArrayList<Asteroid>arrayAsteroid;
    private int width;
    private int height;
    private float timeAddAster = 2;  // czas dodac
    private float timeFromAdd;   // czas od ostatiego dodania
    private int punkt=0;
    private int przegrane = 0;
    private int coIleZmianaLevel = 20;
    private boolean przegrales = false;
    private int pozwolenieNaRuch = 1;





	
	@Override
	public void create () {
        fontPunkty = new BitmapFont();
        gameover = new BitmapFont();
        level = new Level();

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

		batch = new SpriteBatch();

        assets = new Assets();  // inicjujemy zasoby
        assets.load();         // ladujemy zasoby
        assets.menager.finishLoading();  // czekamy zeby wszystkie zasoby sie zaladowaly  !!!!!!

        if (assets.menager.update())  // jezeli zasoby sie zaladowaly assets.update zakoczylo sie to mozna uruchomic dalej
        {
            load();
        }
	}

    private void load() {
       // music = assets.menager.get("music.mp3", Music.class);
       // music.play();
        destroyed = assets.menager.get("jump.wav", Sound.class);

        orzel = new Orzel(assets);

     //   asteroid = new Asteroid(assets);
        arrayAsteroid = new ArrayList<Asteroid>();
            Asteroid ast = new Asteroid(assets);
                arrayAsteroid.add(ast);  // dodajemy 1 aster
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        losujAster();
        kolizja();

        batch.begin();

        renderAstero();
        sterowanieOrlem();

        orzel.draw(batch);

        for (Asteroid as : arrayAsteroid) // rysujemy asteroidy
            as.draw(batch);

        fontPunkty.draw(batch, "punkty zdobyte  " + punkt + "     karne punkty  " + przegrane ,
                0 ,height- fontPunkty.getXHeight()-5 );

        if(przegrales==true) {
            gameover.draw(batch, "Game Over", orzel.x, orzel.y);
            //    Gdx.app.log("przegrałeś ", "");
        }

        batch.end();
	}

    private void kolizja() {
        for (int i=0; i< arrayAsteroid.size(); i++) {
            if(orzel.overlaps(arrayAsteroid.get(i)))
            {
                arrayAsteroid.remove(i);
                punkt++;
                destroyed.play();
                if (punkt % coIleZmianaLevel == 0)
                {
                    level.setPoziom(level.getPoziom()+1);
                }
            }
        }
    }

    private void renderAstero() {

       for(int i=0; i<arrayAsteroid.size(); i++){
           if(arrayAsteroid.get(i).y < -200){   // jak wyjedzie poza ekran to kasowac asteroide
               arrayAsteroid.remove(i);
               przegrane++;

               if(punkt/przegrane<0.1)
               {
                   // przegrałeś
                   przegrales = true;
                   pozwolenieNaRuch = 0;

                   for(int j=0; j<arrayAsteroid.size(); j++)
                   {   // zatrzymamy wszystkie asteroidy jak przegra
                       arrayAsteroid.get(j).setSpeed(0);
                   }
               }
           }
            arrayAsteroid.get(i).y = arrayAsteroid.get(i).y - arrayAsteroid.get(i).getSpeed();  // przerysowanie w nowym miejscu
       }

        // klizje miedzy asteroidami i przekazywanie miedzy nimi predkosci
        for(int i=0; i<arrayAsteroid.size(); i++){
            for(int j=i+1; j<arrayAsteroid.size(); j++){
                if(arrayAsteroid.get(i).overlaps(arrayAsteroid.get(j))){
                    float tempI;
                    tempI = arrayAsteroid.get(i).getSpeed();
                    arrayAsteroid.get(i).setSpeed(arrayAsteroid.get(j).getSpeed());
                    arrayAsteroid.get(j).setSpeed(tempI);
                }
            }
        }
    }

    private void losujAster() {

        timeFromAdd += Gdx.graphics.getDeltaTime();

    //    Gdx.app.log("czas jaki uplynol", " = "+ timeFromAdd);
    //    Gdx.app.log("czas zadeklarowane", " = "+ timeAddAster);

        if(timeFromAdd >= timeAddAster && przegrales == false){
            Asteroid ast = new Asteroid(assets);
            arrayAsteroid.add(ast);
            timeFromAdd=0;

           for(int i=0; i<arrayAsteroid.size(); i++) {
               for (int j = i + 1; j < arrayAsteroid.size(); j++) {
                   if (arrayAsteroid.get(i).overlaps(arrayAsteroid.get(j))) {
                       arrayAsteroid.remove(j);  // zdejmujemy go aby nie bylo nalozonych textur
              //         Gdx.app.log("jestem w if", " zabieram astero bo koliza ");
                       timeFromAdd=timeAddAster;
                   }
               }
           }

        }
    }

    private void sterowanieOrlem(){
        accelerometerX = Gdx.input.getAccelerometerX() * pozwolenieNaRuch;  // jak przegra zrobimy pozwolenieNaRuch=0 i nie ruszy sie statek
        accelerometerY = Gdx.input.getAccelerometerY() * pozwolenieNaRuch;

        Gdx.app.log("accelerometerX ", " = "+ accelerometerX);
        Gdx.app.log("accelerometerY", " = "+ accelerometerY);


        /**
         * zmiany kierunek lotu (Textury)
         */
        if(accelerometerY<0.0){
            orzel.setkierunekLotu(3);
            orzel.x +=accelerometerX;
            orzel.y +=accelerometerY;
        }
        if(accelerometerY>0.0 && accelerometerX<=0.3 && accelerometerX>=-0.3){
            orzel.setkierunekLotu(0);
            orzel.y +=accelerometerY;
        }
        if(accelerometerY>0.0 && accelerometerX < - 0.3){
            orzel.setkierunekLotu(1);
            orzel.x +=accelerometerX;
            orzel.y +=accelerometerY;
        }

        if(accelerometerY>0.0 && accelerometerX >  0.3){
            orzel.setkierunekLotu(2);
            orzel.x +=accelerometerX;
            orzel.y +=accelerometerY;
        }

        if(orzel.x <= 0 - orzel.width/2){  // tak aby 1/2 orla byla na eranie
            orzel.x= 0 - orzel.width/2;
        }
        if(orzel.x >= width - orzel.width/2 ){
            orzel.x= width - orzel.width/2;
        }
        if(orzel.y <= 0 - orzel.height/2){  // tak aby 1/2 orla byla na eranie
            orzel.y= 0 - orzel.height/2; // - orzel.width/2;
        }
        if(orzel.y >= height - orzel.height*2 ){
            orzel.y= height - orzel.height*2;
        }
    }
}
