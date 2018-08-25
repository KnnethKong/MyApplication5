package com.example.kongxiangfa.myapplication5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.message).setOnClickListener(this);

    }

    @TestLoginAnnotation(value = "RightDialog")
    private void showAlert() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        RightDialog dialog = new RightDialog();
        dialog.setContext(this);
        dialog.show(fragmentTransaction, "RightDialog");
    }

    @Override
    public void onClick(View view) {
        showAlert();
    }


}
