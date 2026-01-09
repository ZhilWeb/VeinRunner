package com.example.veinrunner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LevelActivity extends AppCompatActivity implements GameOverListener {
    GameView myGame;

    private int levelNumber;

    private List<LevelSettings> levels;
    private LevelSettings currentLevelSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle iLevelArgs = getIntent().getExtras();

        if(iLevelArgs != null) {
            levelNumber = Integer.parseInt(iLevelArgs.get("levelNumber").toString());
            levels = JSONHelper.importFromJSON(this);
            if(levels != null){
                currentLevelSettings = levels.stream()
                        .filter(myObj -> myObj.getLevelNumber() == levelNumber)
                        .collect(Collectors.toList()).get(0);
            }
        }
        else{
            throw new NullPointerException("Не указан номер уровня");
        }
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        // setContentView(R.layout.activity_main);
        myGame = new GameView(this, currentLevelSettings);
        myGame.setGameOverListener(this);
        setContentView(myGame);
    }

    @Override
    public void onGameOver() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(LevelActivity.this,
//                        "Игра окончена!",
//                        Toast.LENGTH_LONG).show();

                currentLevelSettings = myGame.getCurrentLevelSettings();
                // обновление настроек уровней
                for(int i = 0; i < levels.size(); i++){
                    if(levels.get(i).getLevelNumber() == currentLevelSettings.getLevelNumber()){
                        levels.set(i, currentLevelSettings);
                    }
                }

                boolean result = JSONHelper.exportToJSON(LevelActivity.this, levels);
                if(result){
//                    Toast.makeText(LevelActivity.this, "Данные сохранены", Toast.LENGTH_LONG).show();
                }
                else{
//                    Toast.makeText(LevelActivity.this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
                }

                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}