package com.example.veinrunner;

import java.util.ArrayList;
import java.util.Arrays;

public class LevelSettings {

    private int levelNumber;
    private boolean isAchieved = false;
    private ArrayList<int[]> bricksCoords = new ArrayList<>();

    private ArrayList<int[]> laddersCoords = new ArrayList<>();

    private ArrayList<int[]> goldsCoords = new ArrayList<>();

    private int[] playerHumanCoords = new int[]{};

    private int[] doorCoords = new int[]{};


    public LevelSettings(int levelNumber, boolean isAchieved,
                         ArrayList<int[]> bricksCoords, ArrayList<int[]> laddersCoords,
                         ArrayList<int[]> goldsCoords, int[] playerHumanCoords, int[] doorCoords){

        this.levelNumber = levelNumber;
        this.isAchieved = isAchieved;

        for(int i = 0; i < bricksCoords.size(); i++){
            this.bricksCoords.add(bricksCoords.get(i).clone());
        }
        for(int i = 0; i < laddersCoords.size(); i++){
            this.laddersCoords.add(laddersCoords.get(i).clone());
        }
        for(int i = 0; i < goldsCoords.size(); i++){
            this.goldsCoords.add(goldsCoords.get(i).clone());
        }
        this.playerHumanCoords = playerHumanCoords;
        this.doorCoords = doorCoords;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public boolean getIsAchieved() {
        return isAchieved;
    }

    public ArrayList<int[]> getBricksCoords() {
        return bricksCoords;
    }

    public ArrayList<int[]> getLaddersCoords() {
        return laddersCoords;
    }

    public ArrayList<int[]> getGoldsCoords() {
        return goldsCoords;
    }

    public int[] getPlayerHumanCoords() {
        return playerHumanCoords;
    }

    public int[] getDoorCoords() {
        return doorCoords;
    }

    public void setIsAchieved(boolean isAchieved){
        this.isAchieved = isAchieved;
    }

}
