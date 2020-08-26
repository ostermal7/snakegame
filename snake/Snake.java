package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static final String HEAD_SIGN="\uD83D\uDC7E";
    private static final String BODY_SIGN="\u26AB";
    private List<GameObject> snakeParts=new ArrayList<>();
    public boolean isAlive=true;
    private Direction direction=Direction.LEFT;
    public Snake(int x,int y){
        snakeParts.add(new GameObject(x,y));
        snakeParts.add(new GameObject(x+1,y));
        snakeParts.add(new GameObject(x+2,y));
    }
    public void draw(Game game){
        for (int i = 0; i <snakeParts.size(); i++) {
            if (i == 0) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y,Color.NONE, HEAD_SIGN,isAlive? Color.GREEN:Color.RED,75);
            }else {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y,Color.NONE,BODY_SIGN,isAlive?Color.GREEN:Color.RED,75);
            }
        }
    }
    public void setDirection(Direction direction){
        if (direction==direction.LEFT && this.direction!=direction.RIGHT){
            if (snakeParts.get(0).y==snakeParts.get(1).y){
                return;
            }
            this.direction=direction.LEFT;
        }
        if (direction==direction.RIGHT && this.direction!=direction.LEFT){
            if (snakeParts.get(0).y==snakeParts.get(1).y){
                return;
            }
            this.direction=direction.RIGHT;
        }
        if (direction==direction.UP && this.direction!=direction.DOWN){
            if (snakeParts.get(0).x==snakeParts.get(1).x){
                return;
            }
            this.direction=direction.UP;
        }
        if (direction==direction.DOWN && this.direction!=direction.UP){
            if (snakeParts.get(0).x==snakeParts.get(1).x){
                return;
            }
            this.direction=direction.DOWN;
        }
        
    }
    public void move(Apple apple){
        GameObject newHead=createNewHead();
        if (newHead.x >= SnakeGame.WIDTH || newHead.x < 0 || newHead.y >= SnakeGame.HEIGHT || newHead.y < 0) {
            isAlive = false;
            return;
        }
        if (checkCollision(newHead)){
            isAlive=false;
            return;
        }
        if (newHead.x==apple.x && newHead.y==apple.y){
            apple.isAlive=false;
            snakeParts.add(0,newHead);

        }  else {
            snakeParts.add(0,newHead);
            removeTail();
        }

    }
    public GameObject createNewHead(){
        GameObject gameObject=null;
        if (direction == Direction.LEFT){
            gameObject=new GameObject(snakeParts.get(0).x-1,snakeParts.get(0).y);
        }
        else if (direction==Direction.RIGHT){
            gameObject=new GameObject(snakeParts.get(0).x+1,snakeParts.get(0).y);
        }
        else if (direction==Direction.DOWN){
            gameObject=new GameObject(snakeParts.get(0).x,snakeParts.get(0).y+1);
        }
        else if (direction==Direction.UP){
            gameObject=new GameObject(snakeParts.get(0).x,snakeParts.get(0).y-1);
        }
        return gameObject;
    }
    public void removeTail(){
        snakeParts.remove(snakeParts.size()-1);
    }
    public boolean checkCollision(GameObject gameObject){
        boolean answer=false;
        for (GameObject snakePart : snakeParts) {
            if (gameObject.x==snakePart.x && gameObject.y==snakePart.y) {
                answer = true;
                break;
            }
        }
        return answer;
    }

    public int getLength() {
        return snakeParts.size();
    }
}
