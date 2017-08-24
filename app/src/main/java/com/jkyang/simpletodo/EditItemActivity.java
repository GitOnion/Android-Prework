package com.jkyang.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText etEditInput;
    int itemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String itemText = getIntent().getStringExtra("itemText").toString();
        itemPos = getIntent().getIntExtra("itemPos", 0);
        etEditInput = (EditText)findViewById(R.id.etEditInput);
        etEditInput.setText(itemText);

    }
    public void onSubmitChange(View v) {
        Intent commit = new Intent();
        commit.putExtra("newText", etEditInput.getText().toString());
        commit.putExtra("itemPos", itemPos);
        setResult(RESULT_OK, commit); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

}
