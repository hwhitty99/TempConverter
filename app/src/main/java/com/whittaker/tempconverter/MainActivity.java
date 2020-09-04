package com.whittaker.tempconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View,OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MainActivity extends AppCompatActivity
implements TextView.OnEditorActionListener, View.OnClickListener {

    private EditText fahrEditText;
    private TextView celciusTextView;

    private SharedPreferences savedValues;

    private String fahrString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fahrEditText = (EditText) findViewById(R.id.fNum);
        celciusTextView = (TextView) findViewById(R.id.cNum);

        fahrEditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = savedValues.edit();
        editor.putString("fahrString", fahrString);
        editor.apply();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        fahrString = savedValues.getString("fahrString", "");

        // set the bill amount on its widget
        fahrEditText.setText(fahrString);

        // calculate and display
        fahrToCelcius();
    }


    public void fahrToCelcius() {
        String fahrString = fahrEditText.getText().toString();
        float fahrFloat, celciusFloat;

        if (fahrString.equals("")) {
            fahrFloat = 0;
        } else {
            fahrFloat = Float.parseFloat(fahrString);
        }

        //calculate Fahrenheint to Celcius
        celciusFloat = (fahrFloat - 32) * 5/9;

        //display Celcius
        celciusTextView.setText((int) celciusFloat);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            fahrToCelcius();
        }
        return false;
    }

}
