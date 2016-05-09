package com.walmart.com.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import static com.walmart.com.todoapp.R.id.lvItem;

public class MainActivity extends AppCompatActivity {
    List<String> items;
    ArrayAdapter<String> itemAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(lvItem);
        readItems();
        itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemAdapter);
        setupListViewListener();

    }

    private void setupListViewListener() {
          lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public void onAddItem(View view) {
        EditText text = (EditText)findViewById(R.id.etNewItem);
        String itm = text.getText().toString();
        itemAdapter.add(itm);
        writeItems();
        text.setText("");
    }
    public void readItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo1.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        }catch(IOException e) {
            items = new ArrayList<>();
            e.printStackTrace();
        }
    }

    public void writeItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo1.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        }catch(IOException e) {
            e.printStackTrace();
        }

    }
}
