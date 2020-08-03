package sg.edu.rp.c346.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etTelephone;
    EditText etSize;
    CheckBox checkBox;
    EditText etDay;
    EditText etTime;

    Button btReserve;
    Button btReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        etDay = findViewById(R.id.EditTextDay);
        etTime = findViewById(R.id.EditTextTime);

        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);




        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                    DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            etDay.setText(dayOfMonth + "/" + (month + 1) + "/" + year);


                        }
                    };
                     Calendar cal1 = Calendar.getInstance();


                    DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,
                            myDateListener, cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));

                    myDateDialog.show();



            }
        });



        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay+":"+minute);
                    }
                };

                Calendar time1 = Calendar.getInstance();

                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,
                        myTimeListener,time1.get(Calendar.HOUR),time1.get(Calendar.MINUTE),true);
                myTimeDialog.show();


            }

        });
        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                }
                else {
                    isSmoke = "non-smoking";
                }

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage("New Reservation"+ "\n" +"Name: "+ etName.getText().toString() + "\n"
                        + "Smoking: " + isSmoke + "\n"
                        + "Size: " + etSize.getText().toString() +"\n" + "Date: "
                        + etDay.getText().toString() + "\n" + "Time: "
                        + etTime.getText().toString());

                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "SMS sent", Toast.LENGTH_SHORT).show();

                    }
                });

                myBuilder.setNeutralButton("CANCEL",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();


            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText("");
                etTelephone.setText("");
                etSize.setText("");
                checkBox.setChecked(false);

                etDay.setText("");
                etTime.setText("");

                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay+":"+minute);
                    }
                };

                Calendar cal1 = Calendar.getInstance();

                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,
                        myTimeListener,cal1.get(Calendar.HOUR),cal1.get(Calendar.MINUTE),true);



                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDay.setText(dayOfMonth + "/" + (month+1) + "/" + year);



                    }
                };
                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,
                        myDateListener,cal1.get(Calendar.YEAR),cal1.get(Calendar.MONTH),cal1.get(Calendar.DAY_OF_MONTH));


            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        String[] day = etDay.getText().toString().split("/");
        int dayofmonth = Integer.parseInt(day[0]);
        int month = Integer.parseInt(day[1]);
        int year = Integer.parseInt(day[2]);

        String[] time = etTime.getText().toString().split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);




        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putInt("day",dayofmonth);
        prefEdit.putInt("month",month);
        prefEdit.putInt("year",year);


        prefEdit.putInt("hour",hour);
        prefEdit.putInt("minute",minute);

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int day = prefs.getInt("day",0);
        int month = prefs.getInt("month",0);
        int year = prefs.getInt("year",0);

        int hour = prefs.getInt("hour",0);
        int minute = prefs.getInt("minute",0);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                etTime.setText(hourOfDay+":"+minute);
            }
        };


        TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,
                myTimeListener,hour,minute,false);


        DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etDay.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        };

        DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,
                myDateListener,day,month,year);


    }
}