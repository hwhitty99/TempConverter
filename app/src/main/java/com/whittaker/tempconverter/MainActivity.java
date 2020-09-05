package com.whittaker.tempconverter;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MainActivity extends Activity
        implements OnEditorActionListener {

    private EditText fahrenheitEditText;
    private TextView celsiusTextView;
    private SharedPreferences savedValues;
    private String fahrenheitString = "";
    private String celsiusString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fahrenheitEditText = (EditText) findViewById(R.id.fNum);
        celsiusTextView = (TextView) findViewById(R.id.cNum);

        fahrenheitEditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = savedValues.edit();
        editor.putString("fahrenheitString", fahrenheitString);
        editor.putString("celsiusString", celsiusString);
        editor.apply();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        fahrenheitString = savedValues.getString("fahrenheitString", "");
        celsiusString = savedValues.getString("celsiusString", String.valueOf(0.0f));

        // set the fahrenheit degrees on its widget
        fahrenheitEditText.setText(fahrenheitString);

        // calculate and display
        fahrToCelsius();
    }


    public void fahrToCelsius() {
        fahrenheitString = fahrenheitEditText.getText().toString();
        float fahrFloat;
        float celsiusFloat;

        if (fahrenheitString.equals("")) {
            fahrFloat = 0;
        } else {
            fahrFloat = Float.parseFloat(fahrenheitString);
        }

        //calculate Fahrenheit to Celsius
        celsiusFloat = (fahrFloat - 32) * 5/9;
        celsiusString = Float.toString(celsiusFloat);

        //display Celsius
        celsiusTextView.setText(celsiusString);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            fahrToCelsius();
        }
        return false;
    }
}
