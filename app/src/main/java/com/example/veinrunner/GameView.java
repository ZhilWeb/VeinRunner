package com.example.veinrunner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;


import java.util.ArrayList;
import java.util.List;


public class GameView extends View {


    private LevelSettings currentLevelSettings;

    private int tabletHeight = 1600;
    private int tabletWidth = 2560;
    private ArrayList<Sprite> bricks = new ArrayList<Sprite>();

    private ArrayList<Sprite> ladders = new ArrayList<Sprite>();

    private ArrayList<Sprite> golds = new ArrayList<Sprite>();

    private int wGold;

    private int hGold;

    private int activeGoldIndex = 0;

    private Sprite door;

    private int wDoor;
    private int hDoor;

    private boolean isActiveDoor = false;

    private Sprite leftController;
    private Sprite topController;
    private Sprite rightController;
    private Sprite bottomController;

    private int points = 6;
    private int viewWidth;
    private int viewHeight;
    private Sprite playerHuman;

    private Sprite exitAchieved;

    private Sprite exitEarlier;

    private Timer t;

    private int playerHumanMovingType = 0;

    private int evilHumanMovingType = 0;

    private int w = 0, h = 0;

    private boolean setClearFrames = false;

    private final int timerInterval = 30;

    private boolean isGameActive = true; // Флаг активности игры

    // Интерфейс для обратного вызова
    private GameOverListener gameOverListener;

    // сеттер для слушателя
    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }

    public LevelSettings getCurrentLevelSettings(){
        return currentLevelSettings;
    }

    public GameView(Context context, LevelSettings levelSettings){
        super(context);
        this.currentLevelSettings = levelSettings;

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.brick1);

        int wBrick = b.getWidth();
        int hBrick = b.getHeight();
        Rect firstFrame = new Rect(0, 0, wBrick, hBrick);


        ArrayList<int[]> bricksCoords = currentLevelSettings.getBricksCoords();
        for(int i = 0; i < bricksCoords.size(); i++){
            bricks.add(new Sprite(bricksCoords.get(i)[0], bricksCoords.get(i)[1], 0, 0, firstFrame, b, false, true));
        }

        b = BitmapFactory.decodeResource(getResources(), R.drawable.ladder1);

        int wLadder = b.getWidth();
        int hLadder = b.getHeight();
        firstFrame = new Rect(0, 0, wLadder, hLadder);

        ArrayList<int[]> laddersCoords = currentLevelSettings.getLaddersCoords();
        for(int i = 0; i < laddersCoords.size(); i++){
            ladders.add(new Sprite(laddersCoords.get(i)[0], laddersCoords.get(i)[1], 0, 0, firstFrame, b, false, false));
        }

        b = BitmapFactory.decodeResource(getResources(), R.drawable.human2);

        w = b.getWidth() / 8;
        h = b.getHeight() / 4;
        firstFrame = new Rect(0, 0, w, h);
        int[] playerHumanCoords = currentLevelSettings.getPlayerHumanCoords();
        playerHuman = new Sprite(playerHumanCoords[0], playerHumanCoords[1], 0, 0, firstFrame, b, true, false);

        playerHuman.addFrame(new Rect(0, 0, w, h));



        b = BitmapFactory.decodeResource(getResources(), R.drawable.gold1);

        wGold = b.getWidth() / 2;
        hGold = b.getHeight();
        firstFrame = new Rect(0, 0, wGold, hGold);
        Rect secondFrame = new Rect(wGold, 0, wGold * 2, hGold);

        ArrayList<int[]> goldsCoords = currentLevelSettings.getGoldsCoords();
        golds.add(new Sprite(goldsCoords.get(0)[0], goldsCoords.get(0)[1], 0, 0, firstFrame, b, false, false));
        for(int i = 1; i < goldsCoords.size(); i++){
            golds.add(new Sprite(goldsCoords.get(i)[0], goldsCoords.get(i)[1], 0, 0, secondFrame, b, false, false));
        }

        points = golds.size();


        b = BitmapFactory.decodeResource(getResources(), R.drawable.door1);

        wDoor = b.getWidth() / 2;
        hDoor = b.getHeight();
        secondFrame = new Rect(wDoor, 0, wDoor * 2, hDoor);

        int[] doorCoords = currentLevelSettings.getDoorCoords();
        door = new Sprite(doorCoords[0], doorCoords[1], 0, 0, secondFrame, b, false, false);


        int leftControllerX = 50;
        int leftControllerY = tabletHeight - 148 - 128 - 143;

        b = BitmapFactory.decodeResource(getResources(), R.drawable.left_controller);
        int controllerWidth = b.getWidth();
        int controllerHeight = b.getHeight();
        firstFrame = new Rect(0, 0, controllerWidth, controllerHeight);
        leftController = new Sprite(leftControllerX, leftControllerY, 0, 0, firstFrame, b, false, false);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.top_controller);
        controllerWidth = b.getWidth();
        controllerHeight = b.getHeight();
        firstFrame = new Rect(0, 0, controllerWidth, controllerHeight);
        topController = new Sprite(leftControllerX + 154, leftControllerY - 154, 0, 0, firstFrame, b, false, false);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.right_controller);
        controllerWidth = b.getWidth();
        controllerHeight = b.getHeight();
        firstFrame = new Rect(0, 0, controllerWidth, controllerHeight);
        rightController = new Sprite(leftControllerX + 128 + 180, leftControllerY, 0, 0, firstFrame, b, false, false);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.bottom_controller);
        controllerWidth = b.getWidth();
        controllerHeight = b.getHeight();
        firstFrame = new Rect(0, 0, controllerWidth, controllerHeight);
        bottomController = new Sprite(leftControllerX + 154, leftControllerY + 154, 0, 0, firstFrame, b, false, false);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.exitachieved);
        int exitWidth = b.getWidth();
        int exitHeight = b.getHeight();
        firstFrame = new Rect(0, 0, exitWidth, exitHeight);
        exitAchieved = new Sprite((int)(tabletWidth / 2) - 200, (int)(tabletHeight / 2), 0, 0, firstFrame, b, false, false);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.exitearlier);
        exitWidth = b.getWidth();
        exitHeight = b.getHeight();
        firstFrame = new Rect(0, 0, exitWidth, exitHeight);
        exitEarlier = new Sprite(tabletWidth - 150, 150, 0, 0, firstFrame, b, false, false);


        t = new Timer();
        t.start();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255, 0, 12, 33); // заливаем цветом
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setTextSize(55.0f);
        p.setColor(Color.WHITE);

        // brick
        // canvas.drawARGB(250, 127, 199, 255); // background color

        if(isGameActive){
            canvas.drawText("Количество самородков: "+points, 350, 150, p);
            canvas.drawText("Уровень: " + currentLevelSettings.getLevelNumber(), 10, 150, p);

            for(int i = 0; i < bricks.size(); i++){
                bricks.get(i).draw(canvas);
            }

            for(int i = 0; i < ladders.size(); i++){
                ladders.get(i).draw(canvas);
            }


            playerHuman.draw(canvas);


            for(int i = 0; i < golds.size(); i++){
                golds.get(i).draw(canvas);
            }

            door.draw(canvas);


            // contoller
            leftController.draw(canvas);
            topController.draw(canvas);
            rightController.draw(canvas);
            bottomController.draw(canvas);

            exitEarlier.draw(canvas);
        }
        else{
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.endgamenotify),
                    (int)(tabletWidth / 2) - 500, (int)(tabletHeight / 2) - 250, p);


            exitAchieved.draw(canvas);

        }

    }

    protected void update () {
        if(!isGameActive)
            return;


        if(setClearFrames){
            playerHuman.clearFrames();
            if(playerHumanMovingType == 0){
                playerHuman.setVx(0);
//                playerHuman.setVy(0);
                playerHuman.addFrame(new Rect(0, 0, w, h));
            }
            if(playerHumanMovingType == 2){
                playerHuman.setVx(-200);
                for(int i = 0; i < 8; i++){
                    playerHuman.addFrame(new Rect(i * w, playerHumanMovingType * h, i * w + w, playerHumanMovingType * h + h));
                }
            }
            if(playerHumanMovingType == 1){
                playerHuman.setVx(200);
                for (int i = 0; i < 8; i++) {
                    playerHuman.addFrame(new Rect(i * w, playerHumanMovingType * h, i * w + w, playerHumanMovingType * h + h));
                }
            }
            if(playerHumanMovingType == 3){

                playerHuman.setVx(0);
                playerHuman.setVy(-200);
                for (int i = 0; i < 2; i++) {
                    playerHuman.addFrame(new Rect(i * w, playerHumanMovingType * h, i * w + w, playerHumanMovingType * h + h));
                }
            }
            if(playerHumanMovingType == 4){

                playerHuman.setVx(0);
                playerHuman.setVy(200);
                for (int i = 0; i < 2; i++) {
                    playerHuman.addFrame(new Rect(i * w, (playerHumanMovingType - 1) * h, i * w + w, (playerHumanMovingType - 1) * h + h));
                }
            }
            setClearFrames = false;
        }

        if(playerHumanMovingType == 3){
            boolean hasLadderIntersect = false;
            for(int i = 0; i < ladders.size(); i++){
                if(ladders.get(i).intersect(playerHuman)){
                    // есть пересечение с лестницей
                    playerHumanMovingType = 3;
//                    setClearFrames = true;
                    hasLadderIntersect = true;
                    break;
                }
            }

            if(!hasLadderIntersect){
                setClearFrames = true;
                playerHumanMovingType = 0;
            }
        }
        if(playerHumanMovingType == 4){
            boolean hasLadderIntersect = false;
            for(int i = 0; i < ladders.size(); i++){
                if(ladders.get(i).intersect(playerHuman)){
                    // есть пересечение с лестницей
                    playerHumanMovingType = 4;
//                    setClearFrames = true;
                    hasLadderIntersect = true;
                    break;
                }
            }


            if(!hasLadderIntersect){
                setClearFrames = true;
                playerHumanMovingType = 0;
            }
        }

        boolean hasBricksUpperIntersect = false;
        Sprite nearestBrick = null;
        // есть или пересечение с каким-либо нижестоящим блоком
        for(int i = 0; i < bricks.size(); i++){
            int middleBottomHumanX = (int)(playerHuman.getX() + (int)(playerHuman.getFrameWidth() / 2));
            int middleBottomHumanY = (int)(playerHuman.getY() + playerHuman.getFrameHeight());
            if(bricks.get(i).intersect(playerHuman) && playerHuman.getY() + playerHuman.getFrameHeight() + 5 > bricks.get(i).getY()
                    && playerHuman.getY() + playerHuman.getFrameHeight() < bricks.get(i).getY() - 5 + bricks.get(i).getFrameHeight()
                    && bricks.get(i).isPointInside(middleBottomHumanX, middleBottomHumanY)){
                hasBricksUpperIntersect = true;
                nearestBrick = bricks.get(i);
                break;
            }
        }

        boolean hasLadderIntersectRightLeft = false;
        for(int i = 0; i < ladders.size(); i++){
            if(ladders.get(i).intersect(playerHuman)){
                // есть пересечение с лестницей
                hasLadderIntersectRightLeft = true;
                break;
            }
        }

        if(hasBricksUpperIntersect && nearestBrick != null && playerHumanMovingType != 3){
            playerHuman.setY(nearestBrick.getY() - playerHuman.getFrameHeight());
            playerHuman.setVy(0);
        } else if ((playerHumanMovingType == 1 || playerHumanMovingType == 2) && (!hasBricksUpperIntersect && !hasLadderIntersectRightLeft)) {
            playerHuman.setVy(100);
            playerHuman.setVx(0);
            playerHumanMovingType = 0;
            setClearFrames = true;
        } else if(playerHumanMovingType == 0){
            playerHuman.setVy(100);
            playerHuman.setVx(0);
        }



        playerHuman.update(timerInterval);

        if(playerHuman.getX() + playerHuman.getFrameWidth() > tabletWidth){
            playerHuman.setX(tabletWidth - playerHuman.getFrameWidth() + 5);
            playerHuman.setVx(0);
        }
        else if(playerHuman.getX() < 0){
            playerHuman.setX(0);
            playerHuman.setVx(0);
        }

        if(playerHuman.getY() + playerHuman.getFrameHeight() > tabletHeight){
            playerHuman.setY(tabletHeight - playerHuman.getFrameHeight());
            playerHuman.setVy(0);
        }
        else if(playerHuman.getY() < 0){
            playerHuman.setY(0);
            playerHuman.setVy(0);
        }


        if(playerHuman.intersect(golds.get(activeGoldIndex))){
            // есть пересечение человечка с самородком
            Rect firstGold = new Rect(0, 0, wGold, hGold);
            Rect secondGold = new Rect(wGold, 0, wGold * 2, hGold);

            Rect firstDoor = new Rect(0, 0, wDoor, hDoor);


            if(activeGoldIndex < golds.size() - 1){
                // очистить текущий спрайт
                golds.get(activeGoldIndex).clearFrames();
                golds.get(activeGoldIndex).addFrame(secondGold);

                // отобразить следующий спрайт
                activeGoldIndex++;
                golds.get(activeGoldIndex).clearFrames();
                golds.get(activeGoldIndex).addFrame(firstGold);
            }
            else if(activeGoldIndex == golds.size() - 1){
                // очистить текущий спрайт
                golds.get(activeGoldIndex).clearFrames();
                golds.get(activeGoldIndex).addFrame(secondGold);

                // отобразить спрайт двери
                door.clearFrames();
                door.addFrame(firstDoor);
                isActiveDoor = true;
            }

        }

        for(int i = 0; i < golds.size(); i++){
            golds.get(i).update(timerInterval);
        }
        door.update(timerInterval);

        if(playerHuman.intersect(door) && isActiveDoor){
            // конец игры

            // t.cancel();
            isGameActive = false;

            // установить статус - пройден
            currentLevelSettings.setIsAchieved(true);
        }
        invalidate();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        if (!isGameActive){
            if(eventAction == MotionEvent.ACTION_DOWN && exitAchieved.isPointInside((int)event.getX(), (int)event.getY()) )
                endGame();
            return false;
        }

        if(eventAction == MotionEvent.ACTION_DOWN && exitEarlier.isPointInside((int)event.getX(), (int)event.getY()) ){
            endGame();
            return false;
        }


        boolean hasBricksUpperIntersect = false;
        Sprite nearestBrick = null;
        // есть или пересечение с каким-либо нижестоящим блоком
        for(int i = 0; i < bricks.size(); i++){
            int middleBottomHumanX = (int)(playerHuman.getX() + (int)(playerHuman.getFrameWidth() / 2));
            int middleBottomHumanY = (int)(playerHuman.getY() + playerHuman.getFrameHeight());

            if(bricks.get(i).intersect(playerHuman) && playerHuman.getY() + playerHuman.getFrameHeight() + 5 > bricks.get(i).getY()
                    && playerHuman.getY() + playerHuman.getFrameHeight() < bricks.get(i).getY() - 5 + bricks.get(i).getFrameHeight()
                    && bricks.get(i).isPointInside(middleBottomHumanX, middleBottomHumanY)){
                hasBricksUpperIntersect = true;
                nearestBrick = bricks.get(i);
                break;
            }
        }


        if (eventAction == MotionEvent.ACTION_DOWN) {

            // Движение влево
            if (leftController.isPointInside((int)event.getX(), (int)event.getY()) && playerHumanMovingType != 2) {
                for(int i = 0; i < ladders.size(); i++){
                    if(ladders.get(i).intersect(playerHuman) || hasBricksUpperIntersect){
                        // есть пересечение с лестницей
                        playerHumanMovingType = 2;
                        setClearFrames = true;
                        break;
                    }
                }

            }
            // Движение вправо
            else if (rightController.isPointInside((int)event.getX(), (int)event.getY()) && playerHumanMovingType != 1) {
                for(int i = 0; i < ladders.size(); i++){
                    if(ladders.get(i).intersect(playerHuman) || hasBricksUpperIntersect){
                        // есть пересечение с лестницей
                        playerHumanMovingType = 1;
                        setClearFrames = true;
                        break;
                    }
                }

            }
            // Движение вверх
            else if(topController.isPointInside((int)event.getX(), (int)event.getY()) && playerHumanMovingType != 3){
                for(int i = 0; i < ladders.size(); i++){
                    if(ladders.get(i).intersect(playerHuman)){
                        // есть пересечение с лестницей
                        playerHumanMovingType = 3;
                        setClearFrames = true;
                        break;
                    }
                }

            }
            else if(bottomController.isPointInside((int)event.getX(), (int)event.getY()) && playerHumanMovingType != 4 && !hasBricksUpperIntersect){
                for(int i = 0; i < ladders.size(); i++){
                    if(ladders.get(i).intersect(playerHuman)){
                        // есть пересечение с лестницей
                        playerHumanMovingType = 4;
                        setClearFrames = true;
                        break;
                    }
                }

            }
//            eventAction = event.getAction();

        }
        else{
            if(playerHumanMovingType != 0){
                setClearFrames = true;
                playerHumanMovingType = 0;

            }

        }

        return true;
    }

    private void endGame() {

        if (t != null) {
            t.cancel();
        }

        // Уведомляем слушателя о конце игры
        if (gameOverListener != null) {
            gameOverListener.onGameOver();
        }
    }

    class Timer extends CountDownTimer {
        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            update();
        }
        @Override
        public void onFinish() {
        }
    }
}
