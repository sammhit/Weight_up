package com.weightup.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import java.util.logging.Handler;

public class WeightUP extends ApplicationAdapter {
	SpriteBatch batch;

	float screenWidth;
	float screenHeight;
	float cloudVelocity=4;
	float balloonVelocity=0;
	int scoringAirplane=0;
	int score=0;
	float balloonY=0;
	float gravity=2;
	float distanceBetweenClouds;
	int numberOfClouds=5;
	float[] cloudY1= new float[numberOfClouds];
	float[] cloudY2= new float[numberOfClouds];
	int gameState=0;
	int numberOfPlanes=3;
	Random randomPlaneOffset;
	float[] randomPlaneHeight=new float[numberOfPlanes];
	float planeVelocity=4;
	float planeX;
	int interval=1000;
	float babyfallvelocity=0;
	float balloonupvelocity=0;


	//ShapeRenderer shapeRenderer;
	Rectangle[] airplaneRectangles;
	Circle balloonCircle;
	Rectangle babyRectangle;
	Rectangle wireRectangle;

	Texture backGround;
	Texture cloud1;
	Texture cloud2;
	Texture balloonOnly;
	Texture balloonWire;
	Texture weight;
	Texture airplane;
	Texture gameOverScreen;
	//Texture rateMe;
	Texture clarafell;
	Texture tapAndEvade;
	Texture startScreenMessage;

	BitmapFont bitmapFont;
	long startTime=0;
//<a href="http://www.transparentpng.com//details/baby-girl-clipart-images_514.html">transparentpng.com</a>
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		screenHeight=Gdx.graphics.getHeight();
		screenWidth=Gdx.graphics.getWidth();

		//shapeRenderer = new ShapeRenderer();
		airplaneRectangles=new Rectangle[3];
		babyRectangle= new Rectangle();
		wireRectangle=new Rectangle();
		balloonCircle=new Circle();

		backGround=new Texture("background.png");
		cloud1=new Texture("cloud.png");
		cloud2=new Texture("cloud.png");
		balloonOnly=new Texture("balloon_only.png");
		balloonWire=new Texture("balloon_wire.png");
		weight=new Texture("baby_girl.png");
		airplane=new Texture("aircraft.png");
		gameOverScreen=new Texture("gameoverscreen.png");
		//rateMe = new Texture("rateme.png");
		clarafell= new Texture("clarafell.png");
		startScreenMessage= new Texture("startscreenmsg.png");
		tapAndEvade= new Texture("tapandevade.png");

		distanceBetweenClouds = screenHeight/4;

		bitmapFont = new BitmapFont();
		bitmapFont.setColor(Color.GOLD);
		bitmapFont.getData().setScale(10);


		balloonY=screenHeight*3/4-balloonOnly.getHeight()/2;
		randomPlaneOffset = new Random();
		for (int i =0;i<3;i++) {
			airplaneRectangles[i]= new Rectangle();
			randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());
		}
		planeX=0;

		for (int i =0; i<5;i++){
			cloudY1[i]= screenHeight+i*distanceBetweenClouds;
			//cloudY2[i]= screenHeight/2+300+i*distanceBetweenClouds;


		}



	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(backGround,0,0,screenWidth,screenHeight);
		if(gameState==1) {
			score= (int) ((System.currentTimeMillis()-startTime)/1000);
			bitmapFont.draw(batch,String.valueOf(score),100,200);

			if (Gdx.input.justTouched()){
				balloonVelocity-=50;
			}


			for (int i = 0; i < 5; i++) {
				if (cloudY1[i] < -screenHeight / 4) {
					cloudY1[i] += 5 * distanceBetweenClouds;
				} else {
					cloudY1[i] = cloudY1[i] - cloudVelocity;
				}

				batch.draw(cloud1, screenWidth / 2 - 350, cloudY1[i]);
				batch.draw(cloud2, screenWidth / 2 + 300, cloudY1[i]);
			}
			if (balloonY>balloonWire.getHeight()+weight.getHeight() && balloonY+balloonOnly.getHeight()/2<screenHeight){
				balloonVelocity+=gravity;
				balloonY-=balloonVelocity;
			}
			else {
				gameState=2;

			}


			if (planeX-200 > screenWidth + 5) {
				cloudVelocity+=.5;
				planeVelocity+=.5;
				for (int i =0;i<3;i++) {
					randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());
					if (i==1){
						while(!(randomPlaneHeight[i]<randomPlaneHeight[i-1]-airplane.getHeight()
								|| randomPlaneHeight[i]>randomPlaneHeight[i-1]+airplane.getHeight())){
							randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());

						}

					}
					if(i==2){
						while (!((randomPlaneHeight[i]<randomPlaneHeight[i-1]-airplane.getHeight()
								|| randomPlaneHeight[i]>randomPlaneHeight[i-1]+airplane.getHeight())
								&& ((randomPlaneHeight[i]<randomPlaneHeight[i-2]-airplane.getHeight()
								|| randomPlaneHeight[i]>randomPlaneHeight[i-2]+airplane.getHeight()))))
							randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());
					}
				}

				planeX = -airplane.getWidth();
			}

			planeX = planeX + planeVelocity;
			batch.draw(airplane, planeX, randomPlaneHeight[0]);
			airplaneRectangles[0]=new Rectangle(planeX, randomPlaneHeight[0],airplane.getWidth(),airplane.getHeight());
			batch.draw(airplane,planeX-100,randomPlaneHeight[1]);
			airplaneRectangles[1]=new Rectangle(planeX-100, randomPlaneHeight[1],airplane.getWidth(),airplane.getHeight());
			batch.draw(airplane,planeX-200,randomPlaneHeight[2]);
			airplaneRectangles[2]=new Rectangle(planeX-200, randomPlaneHeight[2],airplane.getWidth(),airplane.getHeight());

			batch.draw(balloonOnly,screenWidth/2-balloonOnly.getWidth()/2,balloonY);
			balloonCircle=new Circle(screenWidth/2-balloonOnly.getWidth()/2+balloonOnly.getWidth()/2,balloonY+balloonOnly.getHeight()/2+10,balloonOnly.getWidth()/2);
			batch.draw(balloonWire,screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10);
			wireRectangle=new Rectangle(screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10,balloonWire.getWidth(),balloonWire.getHeight());
			batch.draw(weight,screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10);
			babyRectangle=new Rectangle(screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10,weight.getWidth(),weight.getHeight());




		}
		else if(gameState==0){
			batch.draw(startScreenMessage,screenWidth/2-startScreenMessage.getWidth()/2,screenHeight/4);
			batch.draw(tapAndEvade,screenWidth/2-tapAndEvade.getWidth()/2,balloonY+balloonOnly.getHeight());
			if (Gdx.input.justTouched()){
				startTime = System.currentTimeMillis();
				gameState=1;

			}

			batch.draw(balloonOnly,screenWidth/2-balloonOnly.getWidth()/2,balloonY);
			balloonCircle=new Circle(screenWidth/2-balloonOnly.getWidth()/2+balloonOnly.getWidth()/2,balloonY+balloonOnly.getHeight()/2+10,balloonOnly.getWidth()/2);
			batch.draw(balloonWire,screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10);
			wireRectangle=new Rectangle(screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10,balloonWire.getWidth(),balloonWire.getHeight());
			batch.draw(weight,screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10);
			babyRectangle=new Rectangle(screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10,weight.getWidth(),weight.getHeight());

		}
		else if(gameState==2){
			balloonupvelocity-=gravity;
			babyfallvelocity+=(gravity+4);
			//if (balloonY<=balloonWire.getHeight()+weight.getHeight()){
				float tempY = balloonY;
				tempY-=balloonupvelocity;
				batch.draw(balloonOnly,screenWidth/2-balloonOnly.getWidth()/2,tempY);
				//balloonCircle=new Circle(screenWidth/2-balloonOnly.getWidth()/2+balloonOnly.getWidth()/2,balloonY+balloonOnly.getHeight()/2+10,balloonOnly.getWidth()/2);
				batch.draw(balloonWire,screenWidth/2-balloonWire.getWidth()/2,tempY-balloonWire.getHeight()+10);

			//}
			//if (balloonY+balloonOnly.getHeight()/2>=screenHeight){
				float tempYbaby =balloonY;
				tempYbaby-=babyfallvelocity;
				//batch.draw(balloonOnly,screenWidth/2-balloonOnly.getWidth()/2,tempY);
				//balloonCircle=new Circle(screenWidth/2-balloonOnly.getWidth()/2+balloonOnly.getWidth()/2,balloonY+balloonOnly.getHeight()/2+10,balloonOnly.getWidth()/2);
				//batch.draw(balloonWire,screenWidth/2-balloonWire.getWidth()/2,tempY-balloonWire.getHeight()+10);
				batch.draw(weight,screenWidth/2-weight.getWidth()/2-20,tempYbaby-balloonWire.getHeight()-weight.getHeight()+10);
			//}

			batch.draw(clarafell,screenWidth/2-clarafell.getWidth()/2,screenHeight/2+gameOverScreen.getHeight()/2+clarafell.getHeight()/2+10);
			batch.draw(gameOverScreen,screenWidth/2-gameOverScreen.getWidth()/2,screenHeight/2-gameOverScreen.getHeight()/2);
			//batch.draw(rateMe,screenWidth/2-rateMe.getWidth()/2,screenHeight/2-gameOverScreen.getHeight()/2-rateMe.getHeight()-screenWidth/4);

			bitmapFont.draw(batch,String.valueOf(score),100,200);

			if (Gdx.input.justTouched()){
				startTime=System.currentTimeMillis();
				gameState=1;
				balloonVelocity=0;
				cloudVelocity=4;
				planeVelocity=4;
				balloonupvelocity=0;
				babyfallvelocity=0;

				balloonY=screenHeight*3/4-balloonOnly.getHeight()/2;

				for (int i =0;i<3;i++) {
					airplaneRectangles[i]= new Rectangle();
					randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());
				}
				planeX=0;
				bitmapFont.draw(batch,String.valueOf(score),100,200);

				for (int i =0; i<5;i++){
					cloudY1[i]= screenHeight+i*distanceBetweenClouds;
					//cloudY2[i]= screenHeight/2+300+i*distanceBetweenClouds;


				}

			}

		}
/*
		batch.draw(balloonOnly,screenWidth/2-balloonOnly.getWidth()/2,balloonY);
		balloonCircle=new Circle(screenWidth/2-balloonOnly.getWidth()/2+balloonOnly.getWidth()/2,balloonY+balloonOnly.getHeight()/2+10,balloonOnly.getWidth()/2);
		batch.draw(balloonWire,screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10);
		wireRectangle=new Rectangle(screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10,balloonWire.getWidth(),balloonWire.getHeight());
		batch.draw(weight,screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10);
		babyRectangle=new Rectangle(screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10,weight.getWidth(),weight.getHeight());
*/

		batch.end();
/*
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BROWN);
		shapeRenderer.circle(screenWidth/2-balloonOnly.getWidth()/2+balloonOnly.getWidth()/2,balloonY+balloonOnly.getHeight()/2+10,balloonOnly.getWidth()/2);
		shapeRenderer.rect(screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10,balloonWire.getWidth(),balloonWire.getHeight());
		shapeRenderer.rect(screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10,weight.getWidth(),weight.getHeight());
		if (Intersector.overlaps(balloonCircle,airplaneRectangles[0]) ||
				Intersector.overlaps(balloonCircle,airplaneRectangles[1]) ||
				Intersector.overlaps(balloonCircle,airplaneRectangles[2]) ||
				Intersector.overlaps(wireRectangle,airplaneRectangles[0]) ||
				Intersector.overlaps(wireRectangle,airplaneRectangles[1]) ||
				Intersector.overlaps(wireRectangle,airplaneRectangles[2]) ||
				Intersector.overlaps(babyRectangle,airplaneRectangles[0]) ||
				Intersector.overlaps(babyRectangle,airplaneRectangles[1]) ||
				Intersector.overlaps(babyRectangle,airplaneRectangles[2])){

			Gdx.app.log("Collision Occured","Yes");
			wire intersector
			Intersector.overlaps(wireRectangle,airplaneRectangles[i])
		}*/

		for (int i=0;i<3;i++){
			//shapeRenderer.rect(planeX-(i)*100,randomPlaneHeight[i],airplane.getWidth(),airplane.getHeight());
			if (Intersector.overlaps(balloonCircle,airplaneRectangles[i]) ||
					Intersector.overlaps(babyRectangle,airplaneRectangles[i])){
				gameState=2;
			}
		}
		//shapeRenderer.end();


	}

	@Override
	public void dispose() {
		batch.dispose();
		backGround.dispose();
	}
}
