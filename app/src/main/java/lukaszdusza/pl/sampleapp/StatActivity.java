package lukaszdusza.pl.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        final Button returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
            }
        });
        getFileName();
    }

    public void openActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public String getFileName() {
        String fileName = MainActivity.FILE_NAME;
        Log.d("stat", fileName);
        return fileName;
    }
}

