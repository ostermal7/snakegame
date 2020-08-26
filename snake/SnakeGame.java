package com.javarush.games.snake;
import com.javarush.engine.cell.*;
public class SnakeGame extends Game{
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL=28;
    private int score;
    public void initialize(){
        setScreenSize(WIDTH,HEIGHT);
        createGame();
    }
    private void createGame(){
        snake=new Snake(WIDTH/2,HEIGHT/2);
        score=0;
        setScore(score);
        createNewApple();
        isGameStopped=false;
        drawScene();
        turnDelay=300;
        setTurnTimer(turnDelay);
    }
    private void drawScene(){
        for (int i = 0; i <WIDTH; i++) {
            for (int j = 0; j <HEIGHT; j++) {
                setCellValueEx(i,j,Color.AQUAMARINE,"");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive){
            score+=5;
            setScore(score);
            turnDelay=turnDelay-10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if (!snake.isAlive){
            gameOver();
        }
        if (snake.getLength()>GOAL){
            win();
        }
        drawScene();
    }
    public void onKeyPress(Key key){
        if (key==Key.UP){
            snake.setDirection(Direction.UP);
        }
        if (key==Key.DOWN){
            snake.setDirection(Direction.DOWN);
        }
        if (key==Key.RIGHT){
            snake.setDirection(Direction.RIGHT);
        }
        if (key==Key.LEFT){
            snake.setDirection(Direction.LEFT);
        }
        if ((key == key.SPACE && isGameStopped) == true){
                createGame();

        }
    }
    private void createNewApple(){
        apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
            while (snake.checkCollision(apple)){
                apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
            }

    }
    private void gameOver(){
        stopTurnTimer();
        isGameStopped=true;
        showMessageDialog(Color.RED,"GAME OVER",Color.BLACK,30);
    }
    private void win(){
        stopTurnTimer();
        isGameStopped=true;
        showMessageDialog(Color.BLUE,"YOU WIN",Color.BLACK,30);
    }
}
