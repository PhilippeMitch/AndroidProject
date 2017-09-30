package com.example.fr_philippe.simpletodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.lang.String;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems=(ListView)findViewById(R.id.lvItems);
        items = new ArrayList<>();
        itemAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemAdapter);
        items.add("First item");
        items.add("Second item");
    }

    public void onAddItem(View v){
        EditText eNewItem=(EditText)findViewById(R.id.editItem);
        String itemText= eNewItem.getText().toString();
        itemAdapter.add(itemText);
        eNewItem.setText("");
    }

    public void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item,int pos,long id){
                items.remove(pos);
                itemAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void readItems(){
        File fileDir=getFilesDir();
        File toDoFile= new File(fileDir,"todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void writeItems(){
        File filesDir=getFilesDir();
        File todoFile=new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
