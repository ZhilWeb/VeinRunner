package com.example.veinrunner;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class LevelListActivity extends AppCompatActivity {

    private List<LevelSettings> levels;


    ActivityResultLauncher<Intent> mGetRegisterLevelList = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        if(intent != null) {

                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_level_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GridLayout levelListLayout = (GridLayout)findViewById(R.id.levelListGridLayout);
        levelListLayout.setRowCount(5);
        levelListLayout.setColumnCount(3);
//        levelListLayout.setLayoutParams(new GridLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT));

        levels = JSONHelper.importFromJSON(LevelListActivity.this);
        if(levels != null){
            for(int i = 0; i < levels.size(); i++){
                Button levelButton = new Button(this);
                // levelButton.setLayoutParams(new GridLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
                levelButton.setText(String.valueOf(i + 1));
                levelButton.setTag(String.valueOf(i + 1));
                levelButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                levelButton.setTextSize(32);
                levelButton.setWidth(300);
                levelButton.setHeight(300);
                levelButton.setOnClickListener(this::getLevelByNum);
                if(levels.get(i).getIsAchieved()){
                    levelButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
                    levelButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                }
                levelListLayout.addView(levelButton);
            }
        }
        else{
            setDefaultLevelsSettings();
            levels = JSONHelper.importFromJSON(LevelListActivity.this);
            if(levels != null){
                for(int i = 0; i < levels.size(); i++){
                    Button levelButton = new Button(this);
                    // levelButton.setLayoutParams(new GridLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
                    levelButton.setText(String.valueOf(i + 1));
                    levelButton.setTag(String.valueOf(i + 1));
                    levelButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    levelButton.setTextSize(32);
                    levelButton.setWidth(300);
                    levelButton.setHeight(300);
                    levelButton.setOnClickListener(this::getLevelByNum);
                    if(levels.get(i).getIsAchieved()){
                        levelButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
                        levelButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    }
                    levelListLayout.addView(levelButton);
                }
            }
        }


//        setDefaultLevelsSettings();
    }

    public void returnToMain(View view){
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }

    public void getLevelByNum(View view){
        Intent iLevel = new Intent(LevelListActivity.this, LevelActivity.class);
        iLevel.putExtra("levelNumber", view.getTag().toString());
        mGetRegisterLevelList.launch(iLevel);
    }

    public void setDefaultLevelsSettings(){
        LevelOneSettings firstLevelPart = new LevelOneSettings();
        LevelTwoSettings secondLevelPart = new LevelTwoSettings();
        LevelSettings firstLevel = new LevelSettings(1, false,
                firstLevelPart.getBricksCoords(), firstLevelPart.getLaddersCoords(),
                firstLevelPart.getGoldsCoords(), new int[]{1280, 1600 - 148 - 128},
                new int[]{2200, 220});
        LevelSettings secondLevel = new LevelSettings(2, false,
                secondLevelPart.getBricksCoords(), secondLevelPart.getLaddersCoords(),
                secondLevelPart.getGoldsCoords(), new int[]{1280, 1600 - 148 - 128},
                new int[]{2200, 220});
        ArrayList<LevelSettings> levelsSettings = new ArrayList<>();
        levelsSettings.add(firstLevel);
        levelsSettings.add(secondLevel);
        boolean result = JSONHelper.exportToJSON(this, levelsSettings);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
    }
}