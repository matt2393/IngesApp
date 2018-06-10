package com.inages.ingesapp.addeditInge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.inages.ingesapp.IngesActivity;
import com.inages.ingesapp.R;

public class AddEditINGEActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_INGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_inge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String INGEId = getIntent().getStringExtra(IngesActivity.EXTRA_INGE_ID);

        setTitle(INGEId == null ? "Añadir ingeniero" : "Editar ingeniero");

        AddEditINGEFragment addEditINGEFragment = (AddEditINGEFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_INGE_container);
        if (addEditINGEFragment == null) {
            addEditINGEFragment = AddEditINGEFragment.newInstance(INGEId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_INGE_container, addEditINGEFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




}
