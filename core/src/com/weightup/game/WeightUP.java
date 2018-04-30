package com.weightup.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;



public class WeightUP extends ApplicationAdapter implements ApplicationListener{
	SpriteBatch batch;
	Preferences  preferences;
	int highScoreValue;
	int adShown=0;
	int gameMode=0;
	int slow=4;
	int fast=11;
	//	CgkIi_aG_pIeEAIQAQ
	float screenWidth;
	float screenHeight;
	float cloudVelocity=5;
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
	float planeVelocity=5;
	float planeX;
	int interval=1000;
	float babyfallvelocity=0;
	float balloonupvelocity=0;
	int numberOfTimesAdShown=0;
	int score10=0;

//<a href="http://www.transparentpng.com//details/play-button-images-png_12818.html">transparentpng.com</a>

	//ShapeRenderer shapeRenderer;
	Rectangle[] airplaneRectangles;
	Circle balloonCircle;
	Rectangle babyRectangle;
	Rectangle wireRectangle;

	Texture backGround;
	Texture backGround1;
	Texture backGround2;
	Texture backGround3;
	Texture backGround4;
	Texture backGround5;
	Texture backGround6;
	Texture cloud1;
	Texture cloud2;
	Texture balloonOnly;
	Texture balloonWire;
	Texture weight;
	Texture airplane;
	//Texture gameOverScreen;
	//Texture rateMe;
	//Texture clarafell;
	//Texture tapAndEvade;
	Texture startScreenMessage;

	BitmapFont bitmapFont;
	BitmapFont bitmapFontHighScore;
	FreeTypeFontGenerator freeTypeFontGenerator;
	FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    FreeTypeFontGenerator.FreeTypeFontParameter parameterHighScore;


	long startTime=0;

	Sound babyFall;
	Sound babyLaugh;
	//Sound jump;
	Music bcgmusic;
	Sound bgMusicNormal;
    public AdService adService;

	private long id2;


	private Stage stage;
	private Texture myTexture;
	private TextureRegion myTextureRegion;
	private TextureRegionDrawable myTexRegionDrawable;
	private ImageButton button;
	private Texture myTexturePlay;
	private TextureRegion myTextureRegionPlay;
	private TextureRegionDrawable myTexRegionDrawablePlay;

	private ImageButton buttonNormal;

	private Texture myTextureMusic;
	private TextureRegion myTextureRegionMusic;
	private TextureRegionDrawable myTexRegionDrawableMusic;
	private ImageButton buttonMusic;


	RateIntent rateIntent;
	private String stringScore=null;

	public WeightUP(AdService ads, RateIntent rateIntent){
        adService=ads;
        this.rateIntent = rateIntent;

    }
//<a href="http://www.transparentpng.com//details/play-button-hd-image_12811.html">transparentpng.com</a>
//<a href="http://www.transparentpng.com//details/baby-girl-clipart-images_514.html">transparentpng.com</a>
//Sound effects obtained from https://www.zapsplat.com“
	@Override
	public void create () {
		batch = new SpriteBatch();
		screenHeight=Gdx.graphics.getHeight();
		screenWidth=Gdx.graphics.getWidth();






		preferences	= Gdx.app.getPreferences("HighScore");
		highScoreValue=preferences.getInteger("HScore",0);

		//shapeRenderer = new ShapeRenderer();
		airplaneRectangles=new Rectangle[3];
		babyRectangle= new Rectangle();
		wireRectangle=new Rectangle();
		balloonCircle=new Circle();

		backGround=new Texture("background.png");
		backGround1 =new Texture("15.png");
		backGround2 =new Texture("25.png");
		backGround3 =new Texture("35.png");
		backGround4 =new Texture("45.png");
		backGround5 =new Texture("55.png");
		backGround6 =new Texture("65.png");
		cloud1=new Texture("cloud.png");
		cloud2=new Texture("cloud.png");
		balloonOnly=new Texture("balloon_only.png");
		balloonWire=new Texture("balloon_wire.png");
		weight=new Texture("baby_girl.png");
		airplane=new Texture("aircraft.png");
		//gameOverScreen=new Texture("gameoverscreen.png");
		//rateMe = new Texture("rateme.png");
		//clarafell= new Texture("clarafell.png");
		startScreenMessage= new Texture("startscreenmsg.png");
		//tapAndEvade= new Texture("tapandevade.png");

		distanceBetweenClouds = screenHeight/4;

        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("data/abrilfatface.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameterHighScore = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=80;
        parameter.color= Color.BLACK;
        bitmapFont = freeTypeFontGenerator.generateFont(parameter);

        parameterHighScore.size=140;

        parameterHighScore.color=Color.FOREST;
        bitmapFontHighScore= freeTypeFontGenerator.generateFont(parameterHighScore);





		babyFall = Gdx.audio.newSound(Gdx.files.internal("data/babyfall.mp3"));
		babyLaugh =  Gdx.audio.newSound(Gdx.files.internal("data/babylaugh.mp3"));
		//jump= Gdx.audio.newSound(Gdx.files.internal("data/jump.mp3"));
		bcgmusic= Gdx.audio.newMusic(Gdx.files.internal("fastandslow.mp3"));
		bgMusicNormal= Gdx.audio.newSound(Gdx.files.internal("backgroundmusic.mp3"));

		balloonY=screenHeight*3/4-balloonOnly.getHeight()/2;
		randomPlaneOffset = new Random();
		for (int i =0;i<1;i++) {
			airplaneRectangles[i]= new Rectangle();
			randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());
		}
		planeX=0;

		for (int i =0; i<5;i++){
			cloudY1[i]= screenHeight+i*distanceBetweenClouds;
			//cloudY2[i]= screenHeight/2+300+i*distanceBetweenClouds;


		}

		myTexture = new Texture(Gdx.files.internal("rateme.png"));
		myTextureRegion = new TextureRegion(myTexture);
		myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		button = new ImageButton(myTexRegionDrawable);
		button.setBounds(10,screenHeight-myTexture.getHeight()-10,myTexture.getWidth(),myTexture.getHeight());//Set the button up
		myTexturePlay = new Texture(Gdx.files.internal("easy.png"));
		myTextureRegionPlay = new TextureRegion(myTexturePlay);
		myTexRegionDrawablePlay = new TextureRegionDrawable(myTextureRegionPlay);
		buttonNormal = new ImageButton(myTexRegionDrawablePlay);
		buttonNormal.setBounds(screenWidth/4-myTexturePlay.getWidth()/3,screenHeight/2-myTexturePlay.getHeight()/3-5,myTexturePlay.getWidth()/3,myTexturePlay.getHeight()/3);

		myTextureMusic = new Texture(Gdx.files.internal("hard.png"));
		myTextureRegionMusic = new TextureRegion(myTextureMusic);
		myTexRegionDrawableMusic = new TextureRegionDrawable(myTextureRegionMusic);
		buttonMusic = new ImageButton(myTexRegionDrawableMusic);
		buttonMusic.setBounds(screenWidth*3/4,screenHeight/2-myTexturePlay.getHeight()/3-5,myTexturePlay.getWidth()/3,myTexturePlay.getHeight()/3);



		stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
		stage.addActor(button);

		stage.addActor(buttonMusic);
		stage.addActor(buttonNormal);//Add the button to the stage to perform rendering and take input.
		Gdx.input.setInputProcessor(stage);
		Gdx.app.log("Create method", "accessed");
		button.addListener(new EventListener()
		{
			@Override
			public boolean handle(Event event)
			{	if(Gdx.input.justTouched()) {
					Gdx.app.log("Button", "clicked");
					rateIntent.startIntent();

				}
				return true;
			}
		});

		buttonNormal.addListener(new EventListener()
		{
			@Override
			public boolean handle(Event event)
			{if(Gdx.input.justTouched()) {
				Gdx.app.log("Button1", "clicked");
				gameMode=0;
				startTime = System.currentTimeMillis();

				gameStartAgain();

			}
				return true;
			}
		});
		buttonMusic.addListener(new EventListener()
		{
			@Override
			public boolean handle(Event event)
			{if(Gdx.input.justTouched()) {
				Gdx.app.log("Button2", "clicked");
				gameMode=1;
				gameStartAgain();
				startTime = System.currentTimeMillis();


			}
				return true;
			}
		});

	}

	@Override
	public void render () {

		batch.begin();
		if (score>=0 && score <3){
			batch.draw(backGround1,0,0,screenWidth,screenHeight);
		}else if (score>=4 && score <6){
			batch.draw(backGround2,0,0,screenWidth,screenHeight);
		}
		else if (score>=6 && score <8){
			batch.draw(backGround3,0,0,screenWidth,screenHeight);
		}
		else if (score>=8 && score <10){
			batch.draw(backGround4,0,0,screenWidth,screenHeight);
		}
		else if (score>=10 && score <12){
			batch.draw(backGround5,0,0,screenWidth,screenHeight);
		}else if (score>=12){
			batch.draw(backGround6,0,0,screenWidth,screenHeight);
		}
		else{
			batch.draw(backGround,0,0,screenWidth,screenHeight);
		}


		if(gameState==1) {

			button.setVisible(false);
			buttonMusic.setVisible(false);
			buttonNormal.setVisible(false);
			score=(int) ((System.currentTimeMillis()-startTime)/1000);
			bitmapFont.draw(batch,String.valueOf(score),100,200);

			if (Gdx.input.justTouched()) {
					balloonVelocity -= 40;
					//if (gameMode==0){
					 //long id = jump.play(1.0f);}
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
				balloonVelocity+=(gravity-1);
				balloonY-=balloonVelocity;
			}
			else {
				gameState=2;
				babyFall.play(1.0f);

			}
			if (gameMode==0) {

				if (planeX > screenWidth + 5) {
					if (score < 45) {
						cloudVelocity += 1;
						planeVelocity += 1;
					} else {
						cloudVelocity += 0.15;
						planeVelocity += 0.15;
					}

					for (int i = 0; i < 1; i++) {
						randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());
						if (i == 1) {
							while (!(randomPlaneHeight[i] < randomPlaneHeight[i - 1] - airplane.getHeight()
									|| randomPlaneHeight[i] > randomPlaneHeight[i - 1] + airplane.getHeight())) {
								randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());

							}

						}
						if (i == 2) {
							while (!((randomPlaneHeight[i] < randomPlaneHeight[i - 1] - airplane.getHeight()
									|| randomPlaneHeight[i] > randomPlaneHeight[i - 1] + airplane.getHeight())
									&& ((randomPlaneHeight[i] < randomPlaneHeight[i - 2] - airplane.getHeight()
									|| randomPlaneHeight[i] > randomPlaneHeight[i - 2] + airplane.getHeight()))))
								randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());
						}
					}

					planeX = -5;
				}
			}
			else if (gameMode==1){
				if (score >= 1 && score <= 5) {
					planeVelocity = slow;
					cloudVelocity = slow;
				} else if (score >= 5 && score < 14) {
					planeVelocity = fast;
					cloudVelocity = fast;
				}else if (score >= 14 && score <19) {
					planeVelocity = slow;
					cloudVelocity = slow;

				}else if (score >= 19 && score <27) {
					planeVelocity = fast;
					cloudVelocity = fast;

				}else if (score >= 27 && score <35) {
					planeVelocity = slow;
					cloudVelocity = slow;
				}else if (score >= 35 && score <42) {
					planeVelocity = fast;
					cloudVelocity = fast;
				}else if (score >= 42 && score <49) {
					planeVelocity = slow;
					cloudVelocity = slow;
				}else if (score >= 49 && score <72) {
					planeVelocity = fast;
					cloudVelocity = fast;
				}else if (score >= 72 && score <79) {
					planeVelocity = slow;
					cloudVelocity = slow;
				}else if (score >= 79 && score <90) {
					planeVelocity = fast;
					cloudVelocity = fast;
				}else if (score >= 90 && score <95) {
					planeVelocity = slow;
					cloudVelocity = slow;
				}else if (score >= 95 && score < 103) {
					planeVelocity = fast;
					cloudVelocity = fast;
				}else if (score >= 103 && score < 109) {
					planeVelocity = slow;
					cloudVelocity = slow;
				}else if (score >= 109 && score <117) {
					planeVelocity = fast;
					cloudVelocity = fast;
				}else if (score >= 117 && score < 125) {
					planeVelocity = slow;
					cloudVelocity = slow;
				}else if (score >= 125 && score < 133) {
					planeVelocity = fast;
					cloudVelocity = fast;
				}else if (score >= 133 && score < 138) {
					planeVelocity = slow;
					cloudVelocity = slow;
				}else if (score >= 138 && score <141) {
					planeVelocity = fast;
					cloudVelocity = fast;
				}else if (score >= 141 && score < 143) {
					planeVelocity = fast;
					cloudVelocity = fast;
					gameState=2;
				}
				if (planeX > screenWidth + 5) {

					for (int i = 0; i < 1; i++) {
						randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());
						if (i == 1) {
							while (!(randomPlaneHeight[i] < randomPlaneHeight[i - 1] - airplane.getHeight()
									|| randomPlaneHeight[i] > randomPlaneHeight[i - 1] + airplane.getHeight())) {
								randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());

							}

						}
						if (i == 2) {
							while (!((randomPlaneHeight[i] < randomPlaneHeight[i - 1] - airplane.getHeight()
									|| randomPlaneHeight[i] > randomPlaneHeight[i - 1] + airplane.getHeight())
									&& ((randomPlaneHeight[i] < randomPlaneHeight[i - 2] - airplane.getHeight()
									|| randomPlaneHeight[i] > randomPlaneHeight[i - 2] + airplane.getHeight()))))
								randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());
						}
					}

					planeX = -5;
				}
			}

			planeX = planeX + planeVelocity;
			batch.draw(airplane, planeX, randomPlaneHeight[0]);
			airplaneRectangles[0]=new Rectangle(planeX, randomPlaneHeight[0],airplane.getWidth(),airplane.getHeight());
			//batch.draw(airplane,planeX-100,randomPlaneHeight[1]);
			//airplaneRectangles[1]=new Rectangle(planeX-100, randomPlaneHeight[1],airplane.getWidth(),airplane.getHeight());
			//batch.draw(airplane,planeX-200,randomPlaneHeight[2]);
			//airplaneRectangles[2]=new Rectangle(planeX-200, randomPlaneHeight[2],airplane.getWidth(),airplane.getHeight());







			batch.draw(balloonOnly,screenWidth/2-balloonOnly.getWidth()/2,balloonY);
			balloonCircle=new Circle(screenWidth/2-balloonOnly.getWidth()/2+balloonOnly.getWidth()/2,balloonY+balloonOnly.getHeight()/2+10,balloonOnly.getWidth()/2);
			batch.draw(balloonWire,screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10);
			wireRectangle=new Rectangle(screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10,balloonWire.getWidth(),balloonWire.getHeight());
			batch.draw(weight,screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10);
			babyRectangle=new Rectangle(screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10,weight.getWidth(),weight.getHeight());




		}
		else if(gameState==0){

			button.setVisible(false);
			buttonNormal.setVisible(true);
			buttonMusic.setVisible(true);
			batch.draw(startScreenMessage,screenWidth/2-startScreenMessage.getWidth()/2,screenHeight/8);
			//batch.draw(tapAndEvade,screenWidth/2-tapAndEvade.getWidth()/2,balloonY+balloonOnly.getHeight());
			/*if (Gdx.input.justTouched()){

				startTime = System.currentTimeMillis();
				gameState=1;
				babyLaugh.play(1.0f);

			}*/

			batch.draw(balloonOnly,screenWidth/2-balloonOnly.getWidth()/2,balloonY);
			balloonCircle=new Circle(screenWidth/2-balloonOnly.getWidth()/2+balloonOnly.getWidth()/2,balloonY+balloonOnly.getHeight()/2+10,balloonOnly.getWidth()/2);
			batch.draw(balloonWire,screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10);
			wireRectangle=new Rectangle(screenWidth/2-balloonWire.getWidth()/2,balloonY-balloonWire.getHeight()+10,balloonWire.getWidth(),balloonWire.getHeight());
			batch.draw(weight,screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10);
			babyRectangle=new Rectangle(screenWidth/2-weight.getWidth()/2-20,balloonY-balloonWire.getHeight()-weight.getHeight()+10,weight.getWidth(),weight.getHeight());

		}
		else if(gameState==2) {

			button.setVisible(true);

			bcgmusic.stop();
			bgMusicNormal.stop(id2);


			boolean backPressed = Gdx.input.isButtonPressed(Input.Keys.BACK);

			if (adShown == 0 && numberOfTimesAdShown%3==0) {
				adService.showInterstitial();
				numberOfTimesAdShown = 0;
				adShown = 1;
			}


			balloonupvelocity -= gravity;
			babyfallvelocity += (gravity + 4);
			balloonCircle = new Circle();
			babyRectangle = new Rectangle();
			airplaneRectangles = new Rectangle[3];
			//if (balloonY<=balloonWire.getHeight()+weight.getHeight()){
			float tempY = balloonY;
			tempY -= balloonupvelocity;
			batch.draw(balloonOnly, screenWidth / 2 - balloonOnly.getWidth() / 2, tempY);
			//balloonCircle=new Circle(screenWidth/2-balloonOnly.getWidth()/2+balloonOnly.getWidth()/2,balloonY+balloonOnly.getHeight()/2+10,balloonOnly.getWidth()/2);
			batch.draw(balloonWire, screenWidth / 2 - balloonWire.getWidth() / 2, tempY - balloonWire.getHeight() + 10);

			//}
			//if (balloonY+balloonOnly.getHeight()/2>=screenHeight){
			float tempYbaby = balloonY;
			tempYbaby -= babyfallvelocity;
			//batch.draw(balloonOnly,screenWidth/2-balloonOnly.getWidth()/2,tempY);
			//balloonCircle=new Circle(screenWidth/2-balloonOnly.getWidth()/2+balloonOnly.getWidth()/2,balloonY+balloonOnly.getHeight()/2+10,balloonOnly.getWidth()/2);
			//batch.draw(balloonWire,screenWidth/2-balloonWire.getWidth()/2,tempY-balloonWire.getHeight()+10);
			batch.draw(weight, screenWidth / 2 - weight.getWidth() / 2 - 20, tempYbaby - balloonWire.getHeight() - weight.getHeight() + 10);
			//}

			//batch.draw(clarafell, screenWidth / 2 - clarafell.getWidth() / 2, screenHeight / 2 + gameOverScreen.getHeight() / 2 + clarafell.getHeight() / 2 + 10);

			//batch.draw(gameOverScreen, screenWidth / 2 - gameOverScreen.getWidth() / 2, screenHeight / 2 - gameOverScreen.getHeight() / 2);
			//batch.draw(rateMe,screenWidth/2-rateMe.getWidth()/2,screenHeight/2-gameOverScreen.getHeight()/2-rateMe.getHeight()-screenWidth/4);
			if (score > highScoreValue) {
				preferences.putInteger("HScore", score);
				highScoreValue = score;
				preferences.flush();
			}
			bitmapFont.draw(batch, String.valueOf(score), 100, 200);
			bitmapFontHighScore.draw(batch, String.valueOf(highScoreValue), screenWidth / 2, 200);
			if (score>=15 && score <25){
				stringScore = "You reached level Purple! That's nice.";
			}else if (score>=25 && score <35){
				stringScore = "You reached level Lemon! Great Going!";
			}
			else if (score>=35 && score <45){
				stringScore = "You reached level Blue! Its time to challenge your friends!";
			}
			else if (score>=45 && score <55){
				stringScore = "You reached level Grass! Unbeatable, perhaps?";
			}
			else if (score>=55 && score <65){
				stringScore ="You reached level Sun! I dont believe it!";
			}else if (score>=65 && score <140){
				stringScore = "You reached level Unknown! This is infinity!";
			}
			else if (score>140) {
				stringScore = "You WON! Amazing!";
			}else{
				stringScore = "You can do better! Keep going!";
			}
			bitmapFont.draw(batch, stringScore, 0, screenHeight*3/4,screenWidth-10,1,true);
			if (Gdx.input.justTouched()){
				gameStartAgain();
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

		if (gameState==0) {

			button.setDisabled(false);
			stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
			stage.draw();
		}
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
			Intersector.overlaps(babyRectangle, airplaneRectangles[i]
		}*/

		for (int i=0;i<1;i++){

			//shapeRenderer.rect(planeX-(i)*100,randomPlaneHeight[i],airplane.getWidth(),airplane.getHeight());

			if (airplaneRectangles[i]!=null) {
				if ((Intersector.overlaps(balloonCircle, airplaneRectangles[i])) ||
						(Intersector.overlaps(babyRectangle, airplaneRectangles[i])) ||
								(Intersector.overlaps(wireRectangle,airplaneRectangles[i]))
						) {
					gameState = 2;

					babyFall.play(1.0f);
				}
			}
		}
		//shapeRenderer.end();


	}

	@Override
	public void dispose() {
		batch.dispose();
		backGround.dispose();
		freeTypeFontGenerator.dispose();
		stage.dispose();
		bcgmusic.dispose();
		//jump.dispose();
		babyFall.dispose();
		babyLaugh.dispose();
		bgMusicNormal.dispose();

	}

	@Override
	public void pause() {
		gameState=2;

	}


	public void gameStartAgain(){
		++numberOfTimesAdShown;
		babyLaugh.play(1.0f);

		startTime = System.currentTimeMillis();
		if (gameMode==0){
			id2=bgMusicNormal.loop();
		}else{
			bcgmusic.play();
		}
		if (Gdx.input.isCatchBackKey()){
			gameState=0;
		}


		gameState = 1;
		balloonVelocity = 0;
		cloudVelocity = 4;
		planeVelocity = 4;
		balloonupvelocity = 0;
		babyfallvelocity = 0;
		adShown = 0;
		balloonY = screenHeight * 3 / 4 - balloonOnly.getHeight() / 2;

		for (int i = 0; i < 1; i++) {
			airplaneRectangles[i] = new Rectangle();
			randomPlaneHeight[i] = randomPlaneOffset.nextFloat() * (screenHeight - airplane.getHeight());
		}
		planeX = -screenWidth;


		for (int i = 0; i < 5; i++) {
			cloudY1[i] = screenHeight + i * distanceBetweenClouds;
			//cloudY2[i]= screenHeight/2+300+i*distanceBetweenClouds;
		}
	}

	@Override
	public void resume() {
		super.resume();
	}
}


