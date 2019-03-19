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
import android.widget.Toast;

import java.util.Calendar;


public class addMeal extends AppCompatActivity {
    private DBManager dbManager;

    private TextView date;
    private TextView time;
    private String food;
    private EditText description,comment;
    String recipeName;
    private Recipe currentRecipe;

    private Calendar currentTime = Calendar.getInstance();
    private int hour = currentTime.get(Calendar.HOUR_OF_DAY);
    private int minute = currentTime.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        Intent intent = getIntent();
        String dateIntent = intent.getStringExtra("date");

        Intent intent2 = getIntent();
        recipeName = intent2.getStringExtra("recipeName");
        currentRecipe = RecipeManager.getRecipeByName(recipeName);

        dbManager = new DBManager(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meal_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                food = parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        date = findViewById(R.id.dateView);
        date.setText(dateIntent);

        time = findViewById(R.id.timeView);
        time.setText(hour + ":" + minute);

        description = findViewById(R.id.descriptionText);
        comment = findViewById(R.id.commentText);
        if (currentRecipe.getName() != null) {
            description.setText(currentRecipe.getName(), TextView.BufferType.EDITABLE);
        } else {
            description.setText("");
        }
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
        mTimePicker = new TimePickerDialog(addMeal.this, new TimePickerDialog.OnTimeSetListener() {
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
        dbManager.insert(food, description.getText().toString(), date.getText().toString(), time.getText().toString(),
                comment.getText().toString());
        Intent intent = new Intent(addMeal.this,
                RecipeDetailActivity.class);
        intent.putExtra("recipeName", recipeName);
        startActivity(intent);
        finish();
        Toast.makeText(this,"Diary added",Toast.LENGTH_LONG).show();
    }

    public void onCancelClick(View view) {
        Intent intent = new Intent(addMeal.this,
                DiaryActivity.class);
        startActivity(intent);
        finish();
    }
}
