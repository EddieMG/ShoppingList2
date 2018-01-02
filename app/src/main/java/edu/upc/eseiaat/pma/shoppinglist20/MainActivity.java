package edu.upc.eseiaat.pma.shoppinglist20;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import junit.framework.Assert;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends AppCompatActivity {

   ArrayList<HashMap<String,String>>data;
    private String[] titleArray,subItemArray;
    private ListView list;
    private Button btn_add;
    private EditText edit_item;
    private SimpleAdapter adapter;
//  private HashMap<String,String> datum;
    private AlertDialog.Builder alert;
    private Integer PreuTotal;
    private TextView Total;
    public int  contador = 0;
    private static final int MAX_BYTES = 8000;
    private static final  String FILENAME = "shoppinglist.txt";

 private void writeItemList(){
         try {
             FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
             for (int i = 0; i < contador; i++) {

                 HashMap<String, String> hash = data.get(i);
                 String title = hash.get("title");
                 String preu = hash.get("preu");

                 String line = String.format("%s;%s\n", title, preu);
                 fos.write(line.getBytes());
                 //s.writeChar('l');
             }

             fos.close(); // ... and close.
             fos.close();

         } catch (FileNotFoundException e) {
             Log.e("Eddie", "writeItemList: FileNotFoundException");
         } catch (IOException e) {
             Log.e("Eddie", "writeItemList: IOException ");
         }
     }


   private  void readItemList() {
       try {
           FileInputStream fis = openFileInput(FILENAME);
           byte[] buffer = new byte[MAX_BYTES];
           int nread = fis.read(buffer);
           if (nread > 0) {
               String content = new String(buffer, 0, nread);
               String[] lines = content.split("\n");
               for (String line : lines) {
                   String[] parts = line.split(";");
                   String title = parts[0];
                   String preu = parts[1];

                   HashMap<String, String> datum2 = new HashMap<String, String>();
                   datum2.put("title", title);
                   datum2.put("preu", preu);
                   //PreuTotal = PreuTotal + Integer.parseInt(preu.);
                   //Total.setText("Preu Total: " + PreuTotal + "€");
                   if (!title.isEmpty()) {
                       Log.e("Eddie", "datum");
                       data.add(datum2);
                       adapter.notifyDataSetChanged();
                       contador++;
                   }
               }
           }
           fis.close();
       } catch (FileNotFoundException e) {
           Log.i("pauek", "readItemList: FileNotFoundException");
       } catch (IOException e) {
           Log.e("pauek", "readItemList: IOException");
       }

         // Assert.assertEquals(data.hashCode(), data.hashCode());
        //  Assert.assertEquals(data.toString(), data.toString());
        //  Assert.assertTrue(data.equals(data));
   }

    @Override
    public void onStop() {

        super.onStop();

        writeItemList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PreuTotal=0;
        list = (ListView)findViewById(R.id.list);
        btn_add = (Button)findViewById(R.id.btn_add);
        data = new ArrayList<HashMap<String,String>>();

        Total=(TextView) findViewById(R.id.textView);


        adapter= new SimpleAdapter(this,data,android.R.layout.simple_list_item_2,new String[]{"title","preu"},new int[]{android.R.id.text1,android.R.id.text2});
        list.setAdapter(adapter);
        readItemList();

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> list, View view, int pos, long id) {
                maybeRemoveItem(pos);
                return true;
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              alert = new AlertDialog.Builder(MainActivity.this);
                Context context = alert.getContext();
                final LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(100, 0, 100, 0);


                final TextView text1=new TextView(context);
                final EditText editText1 = new EditText(context);
                final TextView text2=new TextView(context);
                final EditText editText = new EditText(context);
//                text1.setText("Nom");
                editText.setHint("Nom");
                editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

                layout.addView(text1,layoutParams);

                layout.addView(editText,layoutParams);

//                text2.setText("Preu");
                editText1.setHint("Preu");
                editText1.setInputType(2002);
                layout.addView(text2,layoutParams);
                layout.addView(editText1,layoutParams);
                alert.setTitle("Nou element");
                alert.setView(layout);
                alert.setPositiveButton("Introduir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {



                        String Nom = editText.getText().toString();
                        String Preu = editText1.getText().toString();
                        HashMap<String,String> datum2 = new HashMap<String, String>();
                        datum2.put("title",Nom);
                        datum2.put("preu",Preu+" €");
                        PreuTotal=PreuTotal+ Integer.parseInt(Preu);
                        Total.setText("Preu Total: "+PreuTotal+"€");
                        if (!Nom.isEmpty()){
                            Log.e("Eddie","datum");
                            data.add(datum2);
                            adapter.notifyDataSetChanged();
                            contador++;
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                alert.show();
            }
        });}


    private void maybeRemoveItem(final int pos) {

                data.remove(pos);
                adapter.notifyDataSetChanged();
            }


    }

