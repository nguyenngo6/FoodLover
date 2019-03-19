package android.example.com.recipecooking;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DiaryActivity extends AppCompatActivity {
    private DBManager dbManager;
    private String[] from = new String[]{
            DBHelper.COL_FOOD,
            DBHelper.COL_TIME,
            DBHelper.COL_DATE,
            DBHelper.COL_DESCRIPTION
    };
    private int[] to = new int[]{
            R.id.lFood,
            R.id.lTime,
            R.id.lDate,
            R.id.lDescription
    };

    private Cursor cursor;
    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        dbManager = new DBManager(this);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);

        header = findViewById(R.id.headerText);
        header.setText(formattedDate);

    }

    @Override
    protected void onResume(){
        super.onResume();
        showItemList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onHeaderClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.show();
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1++;
                header.setText(i2 + "-" + i1 + "-" + i);
                showItemList();
            }
        });

    }


    private void showItemList() {
        dbManager.open();
        String where = "date = ?";
        String[] whereArgs = new String[] {header.getText().toString()};
        String order = "time";
        cursor = dbManager.select(where, whereArgs, order);
        if (cursor.getCount() == 0) {
            setVisibility(R.id.list, View.INVISIBLE);
        }
        else{
            ListView list = findViewById(R.id.list);
            list.setVisibility(View.VISIBLE);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.content_view,
                    cursor, from, to,1);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position,
                                        long id) {
                    showUpdateDialog(view,position);
                }
            });
        }
    }

    private void showUpdateDialog(View view, int position) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        cursor.moveToPosition(position);
        dialog.setTitle("Update/Delete " + cursor.getString(2));
        dialog.setMessage("Do you want to update or delete");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE,
                "Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.delete(cursor.getInt(0));
                        showItemList();
                    }
                });

        dialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                "Update",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DiaryActivity.this,
                                UpdateMeal.class);
                        intent.putExtra(DBHelper.COL_ID, cursor.getInt(0));
                        intent.putExtra(DBHelper.COL_FOOD, cursor.getString(1));
                        intent.putExtra(DBHelper.COL_DESCRIPTION, cursor.getString(2));
                        intent.putExtra(DBHelper.COL_DATE, cursor.getString(3));
                        intent.putExtra(DBHelper.COL_TIME, cursor.getString(4));
                        intent.putExtra(DBHelper.COL_COMMENT, cursor.getString(5));
                        startActivity(intent);
                    }
                });
        dialog.show();
    }

    private void setVisibility(int id, int visibility) {
        View view = findViewById(id);
        view.setVisibility(visibility);
    }

    public void onAddClick(View view) {
        Intent intent = new Intent(DiaryActivity.this, addMeal.class);
        intent.putExtra("date", header.getText().toString());
        startActivity(intent);
    }
}
