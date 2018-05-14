package lukaszdusza.pl.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        final Button returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openActivity(MainActivity.class);
            }
        });

        readFile(getFileName());
    }

    public void openActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public String getFileName() {
        String fileName = MainActivity.FILE_NAME;
        Log.d("list", fileName);
        return fileName;
    }

    public void readFile(String fileName) {
        try {
            File file = getMyFile(fileName);
            //   CompositionJson.readFile(file);
            JSONObject json = CompositionJson.toJsonFormatFromArray(CompositionJson.toCostList(file));

            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "nie znaleziono pliku!", Toast.LENGTH_LONG).show();
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
}
