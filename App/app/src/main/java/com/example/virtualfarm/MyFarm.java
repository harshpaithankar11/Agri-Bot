package com.example.virtualfarm;

import static java.lang.String.valueOf;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MyFarm extends AppCompatActivity {

    private GridLayout field;
    private TextView date;
    Plant_Popup popUpClass1;
    Button dateBtn;
    TableLayout GridTable;
    int year,month,day;
    String Date1;




    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_farm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        popUpClass1 = new Plant_Popup();


        datePicker();
        GridTable= findViewById(R.id.tableGrid1);

        int paddingPx = (int) (110);
        int paddingPxH = (int) (330);
        int clrt= Color.BLACK;
        GradientDrawable clr = new GradientDrawable();
        clr.setColor(Color.LTGRAY); // Background color
        clr.setStroke(2, Color.BLACK);

        for (int i=1;i<4;i++){
            TableRow tableRow = new TableRow(this);
            GridTable.addView(tableRow);
            for (int j=1;j<4;j++){
                TextView text =new TextView(this);
                text.setText("G"+i+j);
                text.setPadding(paddingPxH, paddingPx, paddingPxH, paddingPx);
                text.setBackground(clr);
                text.setTextColor(clrt);
                tableRow.addView(text);
                ;
                int I = i;
                int J= j;

                text.setOnTouchListener((v, event) -> {
                    String pos= I + valueOf(J);
                    popUpClass1.showPopupWindow(v,pos, Date1);
                    return false;
                });
            }

        }


    }
    void datePicker(){
        date=findViewById(R.id.SelectedDate);
        dateBtn=findViewById(R.id.date_btn);
        dateBtn.setOnClickListener(v -> {

            final Calendar c = Calendar.getInstance();

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(

                    MyFarm.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        if ((monthOfYear+1)>9){
                            Date1=""+dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
                        }
                        else{
                            Date1=""+dayOfMonth + "-0" + (monthOfYear+1) + "-" + year;
                        }

                        date.setText(Date1);

                    },
                    year, month, day);
            datePickerDialog.show();
        });

    }
}