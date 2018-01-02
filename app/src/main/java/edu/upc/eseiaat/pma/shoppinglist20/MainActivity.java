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


import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_BYTES =8000 ;
    ArrayList<HashMap<String,String>>data;
    private String[] titleArray,subItemArray;
    private ListView list;
    private Button btn_add;
    private EditText edit_item;
    private SimpleAdapter adapter;
//    private HashMap<String,String> datum;
    private AlertDialog.Builder alert;
    private Integer PreuTotal;
    private TextView Total;
//    private void writeItemList(){
//
//       try {
//           FileOutputStream fos =openFileOutput(FILENAME, Context.MODE_PRIVATE);
//            DataOutputStream dout = new DataOutputStream(fos);
//            dout.write(data); // Save line count
//            dout.flush(); // Flush stream ...
//            dout.close(); // ... and close.
//           fos.close();
//       } catch (FileNotFoundException e) {
//           Log.e("Eddie","writeItemList: FileNotFoundException");
////
//      } catch (IOException e) {
//           Log.e("Eddie","writeItemList: IOException ");
////
//       }
////
//   }
////
//
//    private  void readItemList(){
//        itemlist= new ArrayList<>();
//        try {
//            FileInputStream fis= openFileInput(FILENAME);
//            byte [] buffer= new byte[MAX_BYTES];
//            int nread=fis.read(buffer);
//            if(nread>0 ) {
//                String content = new String(buffer, 0, nread);
//                String[] lines = content.split("\n");
//                for (String line : lines) {
//                    String[] parts = line.split(";");
//                    itemlist.add(new ShoppingItem(parts[0], parts[1].equals("true")));
//
//                }
//            }
//            fis.close();
//        } catch (FileNotFoundException e) {
//            Log.i("Eddie","readItemList: FileNotFoundException ");
//
//        } catch (IOException e) {
//            Log.e("Eddie","readItemList: IOException ");
//
//        }
//
//
//    }
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
                            data.add(datum2);
                            adapter.notifyDataSetChanged();

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

