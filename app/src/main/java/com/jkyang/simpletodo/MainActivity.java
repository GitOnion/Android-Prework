package com.jkyang.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int reqCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItem();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItem();
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItem();
                        return true;
                    }
                });
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id){
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        String itemText = itemsAdapter.getItem(pos);
                        i.putExtra("itemText", itemText);
                        i.putExtra("itemPos", pos);
                        startActivityForResult(i, reqCode);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == reqCode) {
            if (resultCode == RESULT_OK) {
                String newText = data.getExtras().getString("newText");
                int itemPos = data.getExtras().getInt("itemPos", 0);
                items.remove(itemPos);
                itemsAdapter.insert(newText, itemPos);
                itemsAdapter.notifyDataSetChanged();
                writeItem();
            }
        }
    }

    private void readItem(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItem(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
