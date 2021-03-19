package com.atilsamancioglu.survivorbirdstarter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float birdX;
	float birdY;
	Circle birdCircle;
	BitmapFont font;
	BitmapFont font2;
	BitmapFont font3;
	int maxScore=0;
	int gameState=0;
	float velocity=1;
	int enemyNumber=4;
	int enemyVelocityDicr=7;
	int score;
	int scoreEnemyIx;

	ShapeRenderer shapeRenderer;
	Random random;
	float[] enemyX=new float[enemyNumber];
	float[] enemyOffSet1=new float[enemyNumber];
	float[] enemyOffSet2=new float[enemyNumber];
	float[] enemyOffSet3=new float[enemyNumber];

	Circle[] enemyCircle1=new Circle[enemyNumber];
	Circle[] enemyCircle2=new Circle[enemyNumber];
	Circle[] enemyCircle3=new Circle[enemyNumber];
	@Override
	public void create () {
	batch=new SpriteBatch();
	background=new Texture("background.png");
	bird=new Texture("bird.png");
	birdX=Gdx.graphics.getWidth()/(float)4.5;
	birdY=Gdx.graphics.getHeight()/(float)2.2;
	enemy1=new Texture("enemy.png");
	enemy2=new Texture("enemy.png");
	enemy3=new Texture("enemy.png");
	random=new Random();
	birdCircle=new Circle();
	shapeRenderer=new ShapeRenderer();
	font=new BitmapFont();
	font.setColor(Color.WHITE);
	font.getData().setScale(4);
	font2=new BitmapFont();
	font2.setColor(Color.WHITE);
	font2.getData().setScale(6);
	font3=new BitmapFont();
	font3.setColor(Color.WHITE);
	font3.getData().setScale(4);
		for (int i = 0; i < enemyNumber; i++) {
			ArrayList<Float> x=new ArrayList<>();
			enemyX[i]=Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/(float)12+i*Gdx.graphics.getWidth()/(float)2;
			enemyOffSet1[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOffSet2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOffSet3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

			enemyCircle1[i]=new Circle();
			enemyCircle2[i]=new Circle();
			enemyCircle3[i]=new Circle();
		}
	}
	int a=0;
	@Override
	public void render () {
	batch.begin();


		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/(float)12,Gdx.graphics.getHeight()/(float)8);
		birdCircle.set(birdX+Gdx.graphics.getWidth()/(float)24,birdY+Gdx.graphics.getHeight()/(float)16,Gdx.graphics.getWidth()/(float)40);
		font.draw(batch,String.valueOf(score),180,450);

		if (gameState==1){
			if (a%1000==0 && enemyVelocityDicr<=20){
				enemyVelocityDicr+=1;
			}
			a++;
			if (enemyX[scoreEnemyIx]<birdX){
				score++;
				if (scoreEnemyIx<3){
					scoreEnemyIx++;
				}else {
					scoreEnemyIx=0;
				}
			}
			if (Gdx.input.isTouched()){
				velocity=-10;
			}
			for (int i = 0; i < enemyNumber; i++) {
				if (enemyX[i]<-3){
					enemyX[i]+=enemyNumber*Gdx.graphics.getWidth()/(float)2;

					enemyOffSet1[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffSet2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffSet3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

				}else {
					enemyX[i]-=enemyVelocityDicr;
				}
				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/(float)2+enemyOffSet1[i],Gdx.graphics.getWidth()/(float)13,Gdx.graphics.getHeight()/(float)9);
				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/(float)2+enemyOffSet2[i],Gdx.graphics.getWidth()/(float)13,Gdx.graphics.getHeight()/(float)9);
				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/(float)2+enemyOffSet3[i],Gdx.graphics.getWidth()/(float)13,Gdx.graphics.getHeight()/(float)9);
				enemyCircle1[i].set(enemyX[i]+Gdx.graphics.getWidth()/(float)26,Gdx.graphics.getHeight()/(float)2+enemyOffSet1[i]+Gdx.graphics.getHeight()/(float)18,Gdx.graphics.getWidth()/(float)35);
				enemyCircle2[i].set(enemyX[i]+Gdx.graphics.getWidth()/(float)26,Gdx.graphics.getHeight()/(float)2+enemyOffSet2[i]+Gdx.graphics.getHeight()/(float)18,Gdx.graphics.getWidth()/(float)35);
				enemyCircle3[i].set(enemyX[i]+Gdx.graphics.getWidth()/(float)26,Gdx.graphics.getHeight()/(float)2+enemyOffSet3[i]+Gdx.graphics.getHeight()/(float)18,Gdx.graphics.getWidth()/(float)35);
			}



		if (birdY>0  && birdY<Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/(float)16){
			velocity+=1;
			birdY-=velocity;
		}else {
			gameState=2;
		}
	}else if (gameState==0){
		if (Gdx.input.isTouched()){
			gameState=1;
		}
		} else if (gameState == 2) {
			font2.draw(batch,"Game Over.Your score: "+score+"\n Tap To Play again!",50,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/3);
			if (score>maxScore){
				maxScore=score;
			}
			font3.draw(batch,"Max score: "+maxScore,Gdx.graphics.getWidth()-400,Gdx.graphics.getHeight()/4);
			if (Gdx.input.isTouched()){
				gameState=1;
				birdY=Gdx.graphics.getHeight()/(float)2.2;
				for (int i = 0; i < enemyNumber; i++) {
					ArrayList<Float> x=new ArrayList<>();
					enemyX[i]=Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/(float)12+i*Gdx.graphics.getWidth()/(float)2;
					enemyOffSet1[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffSet2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffSet3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

					enemyCircle1[i]=new Circle();
					enemyCircle2[i]=new Circle();
					enemyCircle3[i]=new Circle();
					velocity=1;
					enemyVelocityDicr=7;
					a=0;

					scoreEnemyIx=0;
					score=0;
				}
			}


		}

		for (int i = 0; i < enemyNumber; i++) {
			if (Intersector.overlaps(birdCircle,enemyCircle1[i])||Intersector.overlaps(birdCircle,enemyCircle2[i])||Intersector.overlaps(birdCircle,enemyCircle3[i])){
				gameState=2;
			}
		}

	batch.end();
	}
	
	@Override
	public void dispose () {

	}
}
