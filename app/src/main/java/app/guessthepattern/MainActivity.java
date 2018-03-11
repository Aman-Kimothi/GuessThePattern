package app.guessthepattern;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
//import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import static android.R.attr.value;


public class MainActivity extends AppCompatActivity {

    final Handler handler = new Handler();

    View color[] = new View[9] ,last ;
    TableLayout table;
    TextView tv , instructions;
    Button watch, play;
    String computer = "", user = "";
    static int ll=1;
    int LEVEL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = (TableLayout) findViewById(R.id.tableLayout1);
        watch = (Button) findViewById(R.id.watch);
        play = (Button) findViewById(R.id.play);
        tv = (TextView) findViewById(R.id.textView);
        instructions = (TextView) findViewById(R.id.instructions);

        String level = "  Level " + LEVEL;
        tv.setText(level);
        tv.setTextSize(43);

        color[0] = findViewById(R.id.color_1);color[0].setTag(1);
        color[0].setBackgroundColor(getResources().getColor(R.color.colorAccent));
        color[1] = findViewById(R.id.color_2);color[1].setTag(1);
        color[1].setBackgroundColor(Color.BLUE);
        color[2] = findViewById(R.id.color_3);color[2].setTag(2);
        color[2].setBackgroundColor(Color.CYAN);
        color[3] = findViewById(R.id.color_4);color[3].setTag(3);
        color[3].setBackgroundColor(Color.BLACK);
        color[4] = findViewById(R.id.color_5);color[4].setTag(4);
        color[4].setBackgroundColor(Color.MAGENTA);
        color[5] = findViewById(R.id.color_6);color[5].setTag(5);
        color[5].setBackgroundColor(Color.YELLOW);
        color[6] = findViewById(R.id.color_7);color[6].setTag(6);
        color[6].setBackgroundColor(Color.RED);
        color[7] = findViewById(R.id.color_8);color[7].setTag(7);
        color[7].setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        color[8] = findViewById(R.id.color_9);color[8].setTag(8);
        color[8].setBackgroundColor(Color.GRAY);

        instructions.setText("Press the button 'WATCH' to see the pattern");

        for (View v : color)
            v.getBackground().setAlpha(80);

        play.setEnabled(false);

        watch.setOnClickListener(new OnClickListener () {
            @Override
            public void onClick(View v) {

                instructions.setText("");
                v.getBackground().setAlpha(255);
                play.setEnabled(false);
                check(LEVEL, 0);
                play.setEnabled(true);
                watch.setEnabled(false);
            }
        });

        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                instructions.setText("");
                Log.d("Combination : ", "" + computer);
                user = "";
            }
        });


        for (View v : color) {

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    setall();
                    v.getBackground().setAlpha(255);
                    String temp = "" + v.getTag();

                    Log.d("UID  UID \t\t : ", temp);
//                    int last_no = (Integer.parseInt(temp.substring(temp.length() - 1)) + 3) % 10;
                    Log.d("ID", "" + temp);
                    user += temp + " ";

                    Log.d("user \t\t : ", user);
                    Log.d("computer \t : ", computer);

                    if (user.equals(computer)) {

                        LEVEL += 1;
                        String level = "  Level " + LEVEL;
                        tv.setText(level);
                        tv.setTextSize(43);
                        reset();
                        setall();
                        MainActivity.super.onStart();
                    }

                    if (!user.equals(computer.substring(0, user.length()))) {

                        Log.d("user sub \t\t : ", "__" + user + "__");
                        Log.d("computer sub \t : ", "__" + computer.substring(0, user.length()) + "__");
                        reset();
                        setall();
//                        Log.d("\n\nuser sub \t\t : ", "__" + user + "__");
//                        Log.d("computer sub \t : ", "__" + computer.substring(0, user.length()) + "__");

                    }

                }

            });
        }
        super.onStart();

    }

    public void reset() {

        if(!user.equals(computer))
            Toast.makeText(this, "Wrong Pattern !! \n\n  TRY AGAIN !", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, " Congratulations , you've advanced to the next round ", Toast.LENGTH_LONG).show();
        play.setEnabled(false);
        watch.setEnabled(true);
        user = computer = "";
        Log.d("Reset", " done");

        instructions.setText("Press the button 'WATCH' to see the pattern");

    }

    public void setall() {

        for(View v : color)
            v.getBackground().setAlpha(80);
    }
    public void check(int level, int last) {

        color[last].getBackground().setAlpha(80);
        if (level == 0) {
            instructions.setText("Press the button 'PLAY' to guess the pattern");
            return;
        }
        int rand;

        do {
            rand = 0 + (int) (Math.random() * 8);
        } while (rand == last);

        computer += rand + " ";
        Log.d(" i :", "" + level);
        Log.d(" random :", "" + rand);
        Log.d("Outside Handler ", "" + color[rand].getBackground().getAlpha());
        color[rand].getBackground().setAlpha(255);
        final int num = level, random = rand;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Log.d("Inside Handler ", "" + color[random].getBackground().getAlpha());
                check(num - 1, random);
            }
        }, 500);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("level", LEVEL);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        LEVEL = savedInstanceState.getInt("level");
    }
}
