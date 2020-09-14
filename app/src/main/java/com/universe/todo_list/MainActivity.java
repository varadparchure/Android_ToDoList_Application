package com.universe.todo_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvitems=findViewById(R.id.lvItems);


        readItems();


        itemsAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);

        lvitems.setAdapter(itemsAdapter);  //in this list view show me the items using adapter

        setupListViewListener();

    }


    public void onAddItem(View v) {
        EditText etNewItem = findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();  //get the text
        itemsAdapter.add(itemText);   //add to adapter
        etNewItem.setText("");
        writeItems();
    }



    public void setupListViewListener() {
        lvitems.setOnItemLongClickListener
                (
                new AdapterView.OnItemLongClickListener() {   //items loaded in an adapter (adapter view)
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);
                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)

                        writeItems();
                        return true;
                    }

                });
    }



    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}