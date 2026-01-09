package com.example.veinrunner;

import java.util.ArrayList;

public class LevelOneSettings {

    private int tabletHeight = 1600;
    private int tabletWidth = 2560;
    private ArrayList<int[]> bricksCoords = new ArrayList<>();

    private ArrayList<int[]> laddersCoords = new ArrayList<>();

    private ArrayList<int[]> goldsCoords = new ArrayList<>();

    public LevelOneSettings(){
        int bricksLineCount = 0;
        while (bricksLineCount * 95 < tabletWidth){
            bricksCoords.add(new int[]{bricksLineCount * 95, tabletHeight-148});
            bricksLineCount++;
        }

        for(int i = 0; i < 5; i++){
            bricksCoords.add(new int[]{200 + i * 95, tabletHeight / 3 - 250});
        }

        for(int i = 0; i < 10; i++){
            bricksCoords.add(new int[]{i * 95, tabletHeight - 608});
        }
        for(int i = 0; i < 5; i++){
            bricksCoords.add(new int[]{i * 95 + 1065, tabletHeight - 608});
        }

        for(int i = 0; i < 4; i++){
            bricksCoords.add(new int[]{tabletWidth - (i + 1) * 95, tabletHeight - 608});
        }
        for(int i = 0; i < 5; i++){
            bricksCoords.add(new int[]{tabletWidth / 2 + 200 + i * 95, tabletHeight / 2 - 180});
        }
        for(int i = 0; i < 6; i++){
            bricksCoords.add(new int[]{tabletWidth - (i + 1) * 95 - 130, tabletHeight / 2 - 400});
        }




        for(int i = 0; i < 4; i++){
            laddersCoords.add(new int[]{960, tabletHeight - 148 - (i + 1) * 120});
        }
        for(int i = 0; i < 4; i++){
            laddersCoords.add(new int[]{tabletWidth - 380 - 110, tabletHeight - 148 - (i + 1) * 120});
        }
        for(int i = 0; i < 4; i++){
            laddersCoords.add(new int[]{tabletWidth / 2 + 90, tabletHeight - 608 - (i + 1) * 120});
        }
        for(int i = 0; i < 2; i++){
            laddersCoords.add(new int[]{tabletWidth - 220 - 590, tabletHeight / 2 - 180 - (i + 1) * 120});
        }
        for(int i = 0; i < 5; i++){
            laddersCoords.add(new int[]{tabletWidth - 120, tabletHeight - 608 - (i + 1) * 120});
        }
        for(int i = 0; i < 6; i++){
            laddersCoords.add(new int[]{90, tabletHeight - 608 - (i + 1) * 120});
        }
        for(int i = 0; i < 6; i++){
            laddersCoords.add(new int[]{200 + 475, tabletHeight - 608 - (i + 1) * 120});
        }

        goldsCoords.add(new int[]{2560 - 120 - 120, 1600 - 608 - 60});
        goldsCoords.add(new int[]{400, 220});

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
}
