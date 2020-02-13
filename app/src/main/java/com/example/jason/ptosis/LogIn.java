package com.example.jason.ptosis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

        String username,phone;
        private Button ok;
        EditText editTextUsername,editTextPhone;
        private CheckBox saveLoginCheckBox;
        private SharedPreferences loginPreferences;
        private SharedPreferences.Editor loginPrefsEditor;
        private Boolean saveLogin;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_log_in);

            ok = (Button)findViewById(R.id.loginbutton);
            ok.setOnClickListener(this);
            editTextUsername = (EditText)findViewById(R.id.name);
            editTextPhone = (EditText)findViewById(R.id.phone);
            saveLoginCheckBox = (CheckBox)findViewById(R.id.remembermebutton);
            loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();

            saveLogin = loginPreferences.getBoolean("saveLogin", false);
            if (saveLogin == true) {
                editTextUsername.setText(loginPreferences.getString("username", ""));
                editTextPhone.setText(loginPreferences.getString("phone", ""));
                saveLoginCheckBox.setChecked(true);
            }
        }

        public void onClick(View view) {
            if (view == ok) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editTextPhone.getWindowToken(), 0);


                username = editTextUsername.getText().toString();
                phone = editTextPhone.getText().toString();

                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("phone", phone);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                doSomethingElse();
            }
        }



        public void doSomethingElse() {

            Intent intent = new Intent(LogIn.this, MainActivity.class);

            intent.putExtra("username", username);
            intent.putExtra("phone", editTextPhone.getText().toString());
            startActivity(intent);



        }

}




