package lukaszdusza.pl.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class NewCostActivity extends AppCompatActivity {

     static SelectedDate selectedDate;
     static String currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cost);


        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
            }
        });

        final CalendarView calendarView = findViewById(R.id.calendarView);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            //bez funkcji.
            //    Date tempDate = new Date(view.getDate());
            //    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            //    currentDate = sdf.format(tempDate);

             //funkcja z klasy
             currentDate = CompositionJson.newDate(view.getDate());

                selectedDate = new SelectedDate(year,month,dayOfMonth);
                Toast.makeText(getApplicationContext(), selectedDate.toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("selectedDate", selectedDate);
                intent.putExtra("currentDate", currentDate);
                openActivity(MainActivity.class);
                Log.d("czas", intent.getSerializableExtra("selectedDate").toString());
            }
        });

    }

    public void openActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

}
