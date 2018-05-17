package lukaszdusza.pl.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView titleApp;
    private TextView titleAppMonth;
    private TextView textViewDate;
    private TextView textViewSum;
    private TextView textViewSumAll;
    private TextView textViewSumMonth;
    private TextView textViewList;
    private EditText editTextSubject;
    private EditText editTextCost;
    private EditText editTextDate;
    private String currentDate;
    static final String FILE_NAME = "costs.txt";

    public static final String TAG = "MainActivity";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d("appl", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleApp = findViewById(R.id.titleApp);
        titleAppMonth = findViewById(R.id.titleAppMonth);
        editTextSubject = findViewById(R.id.expenseEditText);
        editTextCost = findViewById(R.id.costEditText);
        editTextDate = findViewById(R.id.editTextDate);
        textViewDate = findViewById(R.id.textViewDate);
        textViewSum = findViewById(R.id.textViewSum);
        textViewSumMonth = findViewById(R.id.textViewSumMonth);
        textViewSumAll = findViewById(R.id.textViewSumAll);
        textViewList = findViewById(R.id.textViewList);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonCheckDay = findViewById(R.id.buttonCheckDay);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        titleApp.setText("Wydatki od początku wprowadzania: ");
        titleAppMonth.setText("Wydatki z bieżącego miesiąca: ");
        textViewDate.setText("Wydatek na dzień:");
        textViewSumAll.setText(" " + getAllDayCostSum() + " zł");
        textViewSumMonth.setText(" " + getMonthCostSum() + " zł");
    //  editTextDate.setText(currentDate);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveToDB (
                        editTextDate.getText().toString(),
                        editTextSubject.getText().toString(),
                        editTextCost.getText().toString()
                );

                createNewCost(editTextDate.getText().toString(), editTextSubject.getText().toString(), editTextCost.getText().toString());
                textViewSumAll.setText(" " + getAllDayCostSum() + " zł");
                textViewSumMonth.setText(" " + getMonthCostSum() + " zł");
                textViewSum.setText("Suma wydatków: \n" + getCurrentDayCostSum() + " zł");



            }
        });

        buttonCheckDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile(FILE_NAME);

                try {
                    textViewList.setText(CompositionJson
                            .presentAllSubjectsFromListByCurrentDate(CompositionJson
                                    .toCostList(getMyFile(FILE_NAME)), editTextDate.getText().toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                openActivity(ListActivity.class);

                //loadDB();
                openListActivity(ListActivity.class, loadDB());
            }


        });

        Button statButton = findViewById(R.id.statButton);
        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(StatActivity.class);
             //   openActivity(NewCostActivity.class);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile(FILE_NAME);
                textViewSumAll.setText(" " + getAllDayCostSum() + " zł");
                textViewSumMonth.setText(" " + getMonthCostSum() + " zł");
            }
        });

        if(savedInstanceState != null) {
            currentDate = savedInstanceState.getString("currentdate");
            editTextDate.setText(currentDate);
        } else {
            currentDate = CompositionJson.newDate();
            editTextDate.setText(currentDate);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("appl", "onRestoreInstanceState");
    //    Log.d("appl", savedInstanceState.toString());
        currentDate = savedInstanceState.getString("currentdate");
        editTextDate.setText(currentDate);

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("appl", "onSaveInstanceState");
        outState.putString("currentdate", currentDate);
    //    Log.d("appl", outState.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        Log.d("appl", "onStart");


        super.onStart();

    }

    @Override
    protected void onResume() {
        Log.d("appl", "onResume");
        super.onResume();

    }

    @Override
    protected void onPause() {
        Log.d("appl", "onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("appl", "onStop");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("appl", "onDestroy");

        super.onDestroy();

    }

    public void createNewCost(String date, String subject, String cost) {
        Costs costs = new Costs();

        try {
            costs.setDate(date);
            costs.setSubject(subject);
            costs.setCost(Integer.parseInt(cost));

            String content = CompositionJson.parseToStringRow(costs);
            saveToFile(content);
            editTextSubject.setText("");
            editTextCost.setText("");
        } catch ( NumberFormatException e) {
            System.err.println("uzupełnij wszystkie pola!");
        }
    }

    public void saveToFile(String content) {

        StringBuilder result = new StringBuilder().append(content).append("\n");
        FileOutputStream outputStream;

            try {
                    outputStream = openFileOutput(FILE_NAME, Context.MODE_APPEND);
                    outputStream.write(result.toString().getBytes());
                    outputStream.close();
                    Toast.makeText(getBaseContext(), "zapisano", Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void readFile(String fileName) {

        try {
            File file = getMyFile(fileName);
         //   CompositionJson.readFile(file);
            JSONObject json = CompositionJson.toJsonFormatFromArray(CompositionJson.toCostList(file));

        //    System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "nie znaleziono pliku!", Toast.LENGTH_LONG).show();
        }

    }

    public boolean deleteFile(String fileName) {

        File file = getMyFile(fileName);

        if(file != null) {
            file.delete();
            Toast.makeText(getBaseContext(), "skasowano plik!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(getBaseContext(), "nie znaleziono pliku!", Toast.LENGTH_LONG).show();
            System.out.println("file not exist!");
            return false;
        }
    }


    public File getMyFile(String fileName) {
        File directory = getApplicationContext().getFilesDir();
        File file = new File(directory, fileName);

        if(file.exists()) {
            return file;
        } else {
            Toast.makeText(getBaseContext(), "nie znaleziono pliku!", Toast.LENGTH_LONG).show();
            System.out.println("file not exist!");
            return null;
        }
    }

    public String getCurrentDayCostSum() {
        try {
           return CompositionJson.getCurrentDayCostSum(CompositionJson.toCostList(getMyFile(FILE_NAME)), editTextDate.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
            return "nie ma kosztów lub nie znaleziono rekordów.";
        }
    }

    public String getAllDayCostSum() {
        try {
            return CompositionJson.getAllDayCostSum(CompositionJson.toCostList(getMyFile(FILE_NAME)));
        } catch (IOException e) {
            e.printStackTrace();
            return "nie ma kosztów lub nie znaleziono rekordów.";
        }
    }

    public String getMonthCostSum() {
        try {
            return CompositionJson.getMonthCostSum(CompositionJson.toCostList(getMyFile(FILE_NAME)));
        } catch (IOException e) {
            e.printStackTrace();
            return "nie ma kosztów lub nie znaleziono rekordów.";
        }
    }

   public void openListActivity(Class c, List<Costs> costs) {

        Intent intent = new Intent(this, c);
        intent.putExtra("list", (Serializable) costs);
        Log.d("intent", intent.getSerializableExtra("list").toString());
        startActivity(intent);
    }

    public void openActivity(Class c) {

        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

//    public void dbIntent(List<Costs> costs) {
//        Intent intent = new Intent();
//        intent.putExtra("list", (Serializable) costs);
//        Log.d("intent", intent.getSerializableExtra("list").toString());
//    }


    public List<Costs> loadDB() {
        SQLiteDatabase sqLiteDatabase = getBaseContext().openOrCreateDatabase("sqlite-test-1.db", MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("create table if not exists costs(date TEXT, subject TEXT, cost TEXT)");
       // sqLiteDatabase.execSQL("insert into costs values('2018-01-01', 'bulki', 100);");
       // sqLiteDatabase.execSQL("insert into costs values(?, ?, ?);", new String[] { dat, sub, cos});

        Cursor cursor = sqLiteDatabase.rawQuery("select * from costs", null);
        List<Costs> costsDB = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {

                String date = cursor.getString(0);
                String subject = cursor.getString(1);
                int cost = cursor.getInt(2);
                costsDB.add(new Costs(date, subject, cost));
            } while(cursor.moveToNext());

          //  Toast.makeText(MainActivity.this, date + subject + cost, Toast.LENGTH_SHORT).show();

        }
        cursor.close();
        sqLiteDatabase.close();

        for ( Costs c: costsDB) {
            Log.d("sqlbase", c.toString());
        }

        return costsDB;
    }

    public  void saveToDB(String dat, String sub, String cos) {
        String[] elements = {dat, sub, cos};
        SQLiteDatabase sqLiteDatabase = getBaseContext().openOrCreateDatabase("sqlite-test-1.db", MODE_PRIVATE,null);
      //  sqLiteDatabase.execSQL("drop table costs");
        sqLiteDatabase.execSQL("create table if not exists costs(date TEXT, subject TEXT, cost TEXT)");
        // sqLiteDatabase.execSQL("insert into costs values('2018-01-01', 'bulki', 100);");
        sqLiteDatabase.execSQL("insert into costs values(?, ?, ?);", elements );
        sqLiteDatabase.close();
        Log.d("sqlbase", elements.toString());
    }


}
