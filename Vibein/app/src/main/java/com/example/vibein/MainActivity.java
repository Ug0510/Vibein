package com.example.vibein;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.WindowManager;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    private void setTextViewColor(TextView tw, int... color_main) {
        TextPaint paint = tw.getPaint();
        float width = paint.measureText(tw.getText().toString());

        Shader shader = new LinearGradient(0,0,width,tw.getTextSize(),color_main,null,Shader.TileMode.CLAMP);
        tw.getPaint().setShader(shader);
        tw.setTextColor(color_main[0]);
    }


    public void perform(View view) {
        Intent intent = new Intent(this, SongsList.class);
        startActivity(intent);
    }
}