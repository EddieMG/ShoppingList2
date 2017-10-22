package edu.upc.eseiaat.pma.shoppinglist20;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>>data;
    private String[] titleArray,subItemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView)findViewById(R.id.listview);

        data = new ArrayList<HashMap<String, String>>();
        titleArray=new String[]{"Test1","Test2"};
        subItemArray=new String[]{"SubTest1","SubTest2"};

        for(int i=0;i<titleArray.length;i++){
            HashMap<String,String> datum = new HashMap<String, String>();
            datum.put("title", titleArray[i]);
            datum.put("preu", subItemArray[i]);
            data.add(datum);
        }

        SimpleAdapter adapter= new SimpleAdapter(this,data,android.R.layout.simple_list_item_2,new String[]{"title","preu"},new int[]{android.R.id.text1,android.R.id.text2});
        lv.setAdapter(adapter);
    }
}