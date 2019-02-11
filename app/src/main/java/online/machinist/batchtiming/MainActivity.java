package online.machinist.batchtiming;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button addDate,testTime;
    Spinner spin;
    private String seleted_Value;
    String received_Day,received_time,total_time;
    TextView textView;
    TimePicker addTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // addDate=findViewById(R.id.addDate);
        spin=findViewById(R.id.getDate);
        addTime=findViewById(R.id.getTime);
        testTime=findViewById(R.id.saveTime);
        textView=findViewById(R.id.retrieved_Data);

        ArrayAdapter<CharSequence>arrayAdapter=ArrayAdapter.createFromResource(this,R.array.Days,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);

        spin.setOnItemSelectedListener(this);

        final ContentValues values=new ContentValues();

        DataBaseHelper object=new DataBaseHelper( this);
        final SQLiteDatabase writableDatabase=object.getWritableDatabase();

        SQLiteDatabase readableDatabase = object.getReadableDatabase();
        String columns[]={"_id","day","time"};
        final Cursor cursor = readableDatabase.query("batchTime", columns, null, null, null, null, null);
        if( cursor != null && cursor.moveToFirst() ){
            received_Day = cursor.getString( cursor.getColumnIndex("day"));
            received_time = cursor.getString( cursor.getColumnIndex("time"));


            cursor.close();
        }
        Toast.makeText(this,"Received Day: "+received_Day,Toast.LENGTH_SHORT).show();
        textView.setText("Day: "+received_Day+"\n"+"Time: "+received_time);
       /* addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });*/

        testTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = String.valueOf(addTime.getCurrentHour());
                String min = String.valueOf(addTime.getCurrentMinute());
                total_time= String.valueOf(hour+"hr"+min+"min");

                Log.d("TOTAL MOTAL", "onClick: "+total_time);
                /*List<String> list=new ArrayList<>();
                list.add()*/
                if (received_Day==null && received_time==null)
                {
                    values.put("day",seleted_Value);
                    values.put("time",total_time);

                    long result=writableDatabase.insert("batchTime",null,values);
                    if (result>=0)
                    {
                        Toast.makeText(getApplicationContext(),"Data Added Successfully ",Toast.LENGTH_LONG).show();
                    }
                    else {Toast.makeText(getApplicationContext(),"There was an error please report ",Toast.LENGTH_LONG).show(); }
                }
                else
                {
                    values.put("day", "" + seleted_Value + " " + received_Day);
                    values.put("time", "" + total_time + " " + received_time);

                    //TODO update  String.valueOf(1) with suitable id

                    long checkerResult = writableDatabase.update("batchTime", values, "_id= ? ", new String[]{String.valueOf(1)});

                    if (checkerResult >= 0) {
                        Toast.makeText(getApplicationContext(), " Updated", Toast.LENGTH_SHORT).show();


                    } else
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         seleted_Value=parent.getItemAtPosition(position).toString();
        Toast.makeText(this,seleted_Value,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
