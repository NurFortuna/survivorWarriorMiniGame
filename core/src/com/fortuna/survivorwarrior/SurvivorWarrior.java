package com.fortuna.survivorwarrior;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;

import org.w3c.dom.Text;

import java.util.Random;

public class SurvivorWarrior extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture elf;
	Texture stone;
	float elfX=0;
	float elfY=0;
	int gameState=0;
	float velocity=0;
	float gravity=0.1f;
	float enemyY=0;
	float enemyVelocity=4;
	int score=0;
	int scoredEnemy=0;

	Random random;
	BitmapFont bitmapFont;
	BitmapFont bitmapFont2;

	Circle elfCircle;
	ShapeRenderer shapeRenderer;

	int numberOfEnemies=10;


	float [] enemyX=new float[numberOfEnemies];

	float [] enemyOffSet=new float[numberOfEnemies];

	Circle[] enemyCircles;


	float distance=0;
	Music music;


	@Override
	public void create () {

		music = Gdx.audio.newMusic(Gdx.files.internal("Christmas synths.mp3"));
		batch=new SpriteBatch();
		background=new Texture("bg.png");
		elf=new Texture("elf.png");
		stone=new Texture("stone.png");
		bitmapFont=new BitmapFont();
		bitmapFont.setColor(Color.WHITE);
		bitmapFont.getData().setScale(4);

		bitmapFont2=new BitmapFont();
		bitmapFont2.setColor(Color.WHITE);
		bitmapFont2.getData().setScale(6);

		distance=Gdx.graphics.getWidth()/6;

		random=new Random();


		elfCircle=new Circle();
		shapeRenderer=new ShapeRenderer();

		enemyCircles=new Circle[numberOfEnemies];


		for(int i=0;i<numberOfEnemies;i++){

			enemyOffSet[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

			enemyX[i]=Gdx.graphics.getWidth()-stone.getWidth()/2 + i*distance;

			enemyCircles[i]=new Circle();

		}

		Random random=new Random();
		int randomNumber=random.nextInt(500);

		enemyY=randomNumber;


		elfX=Gdx.graphics.getWidth()/2-elf.getHeight()/2;
		elfY=Gdx.graphics.getHeight()/3;


	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState==1){


			if(enemyX[scoredEnemy]<Gdx.graphics.getWidth()/2-elf.getHeight()/2){
				score++;

				if(scoredEnemy<9){
					scoredEnemy++;
				}
				else {
					scoredEnemy=0;
				}
			}


			music.play();

			if(Gdx.input.justTouched()){
				velocity=-4;
			}

			for(int i=0;i<numberOfEnemies;i++){

				if(enemyX[i]<Gdx.graphics.getWidth()/10) {

					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 300);

				}
				else{

					enemyX[i]=enemyX[i]-enemyVelocity;

				}

				batch.draw(stone,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffSet[i],Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/5);

				enemyCircles[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth()/20,Gdx.graphics.getHeight()/2+enemyOffSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/20);
			}

			if(elfY>0){

				velocity=velocity+gravity;
				elfY=elfY-velocity;

			}
			else{
				gameState=2;
			}
		}
		else if(gameState==0){
			if(Gdx.input.justTouched()){
				gameState=1;
			}

		}
		else if(gameState==2){


			bitmapFont2.draw(batch,"Game Over! Tap To Play Again",100,Gdx.graphics.getHeight()/2+200);


			if(Gdx.input.justTouched()){
				gameState=1;
			}

			elfY=Gdx.graphics.getHeight()/3;

			for(int i=0;i<numberOfEnemies;i++){

				enemyOffSet[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-800);

				enemyX[i]=Gdx.graphics.getWidth()-stone.getWidth()/2 + i*distance*3;

				enemyCircles[i]=new Circle();

			}

			velocity=0;
			score=0;

		}

		batch.draw(elf,elfX,elfY,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
		bitmapFont.draw(batch,String.valueOf(score),100,200);

		elfCircle.set(elfX+223,elfY+100,Gdx.graphics.getWidth()/45);


		batch.end();

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(elfCircle.x,elfCircle.y,elfCircle.radius);



		for(int i=0;i<numberOfEnemies;i++){

			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/40+28,Gdx.graphics.getHeight()/10+enemyOffSet[i]+Gdx.graphics.getHeight()/2,Gdx.graphics.getWidth()/55);

			if(Intersector.overlaps(elfCircle,enemyCircles[i])){
				gameState=2;

			}



		}

		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
