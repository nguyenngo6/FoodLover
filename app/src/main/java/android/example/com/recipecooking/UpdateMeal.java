package android.example.com.recipecooking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class UpdateMeal extends AppCompatActivity {
    private int id;
    private String food;
    private EditText description,comment;
    private TextView date,time;
    private DBManager dbManager;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        Intent intent = getIntent();
        //get Extra from Intent
        id = intent.getIntExtra(DBHelper.COL_ID,-1);
        food = intent.getStringExtra(DBHelper.COL_FOOD);
        String descriptionStr = intent.getStringExtra(DBHelper.COL_DESCRIPTION);
        String dateStr = intent.getStringExtra(DBHelper.COL_DATE);
        String timeStr = intent.getStringExtra(DBHelper.COL_TIME);
        String commentStr = intent.getStringExtra(DBHelper.COL_COMMENT);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meal_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(food)){
                spinner.setSelection(i);
                break;
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                food = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        date = findViewById(R.id.dateView);
        date.setText(dateStr);

        time = findViewById(R.id.timeView);
        time.setText(timeStr);

        description = findViewById(R.id.descriptionText);
        description.setText(descriptionStr);

        comment = findViewById(R.id.commentText);
        comment.setText(commentStr);

        //open database
        dbManager = new DBManager(this);
        dbManager.open();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void DatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.show();
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1++;
                date.setText(i2 + "-" + i1 + "-" + i);
            }
        });
    }


    public void TimePicker(View view) {

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(UpdateMeal.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public void onDoneClick(View view) {
        dbManager.open();
        dbManager.update(id, food, description.getText().toString(), date.getText().toString(), time.getText().toString(),
                comment.getText().toString());
        Intent intent = new Intent(UpdateMeal.this,
                DiaryActivity.class);
        startActivity(intent);
        finish();
    }

    public void onCancelClick(View view) {
        Intent intent = new Intent(UpdateMeal.this,
                DiaryActivity.class);
        startActivity(intent);
        finish();
    }
}
