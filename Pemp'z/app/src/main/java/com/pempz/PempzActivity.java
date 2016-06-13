package com.pempz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.pempz.model.Pempz;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.ThemeManager;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.Switch;
/*import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;*/
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
// import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PempzActivity extends AppCompatActivity implements
        View.OnClickListener {

    public static String KEY_ACTION = "com.pempz.PempzActivity.KEY_ACTION";
    public static String KEY_PEMPZ = "com.pempz.PEMPZ";

    View parent_view;

    Toolbar toolbar;
    ImageButton cancel;
    ImageButton ok;

    EditText message;

    Switch full_day;
    Button start_date;
    Button start_time;
    Button end_date;
    Button end_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pempz);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setDisplayHomeAsUpEnabled(true);
        // ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */

        initActions();

    }

    private void initActions() {

        cancel = (ImageButton)toolbar.findViewById(R.id.cancel_action);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ok = (ImageButton)toolbar.findViewById(R.id.ok_action);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ok clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

        message = (EditText)findViewById(R.id.message);


        SimpleDateFormat date_f = new SimpleDateFormat("dd MMM yy");
        SimpleDateFormat time_f = new SimpleDateFormat("HH:mm");
        Calendar c = Calendar.getInstance();

        start_date = (Button)findViewById(R.id.start_date);
        start_date.setText(date_f.format(c.getTime()));
        start_date.setOnClickListener(this);

        start_time = (Button)findViewById(R.id.start_time);
        start_time.setText(time_f.format(c.getTime()));
        start_time.setOnClickListener(this);

        end_date = (Button)findViewById(R.id.end_date);
        end_date.setText(date_f.format(c.getTime()));
        end_date.setOnClickListener(this);

        end_time = (Button)findViewById(R.id.end_time);
        end_time.setText(time_f.format(c.getTime()));
        end_time.setOnClickListener(this);

        full_day = (Switch)findViewById(R.id.full_day);
        full_day.setChecked(false);
        /*full_day.setOncheckListener(new Switch.OnCheckListener() {
            @Override
            public void onCheck(Switch aSwitch, boolean state) {
                if (state == true) {
                    start_time.setVisibility(View.INVISIBLE);
                    end_time.setVisibility(View.INVISIBLE);
                }
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        Dialog.Builder builder = null;
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;

        switch (view.getId()) {
            case R.id.start_date:

                builder = new DatePickerDialog.Builder(isLightTheme ? R.style.Material_App_Dialog_TimePicker_Light : R.style.Material_App_Dialog_TimePicker){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog)fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        start_date.setText(date);

                        // Toast.makeText(getBaseContext(), "Date is " + date, Toast.LENGTH_SHORT).show();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        // Toast.makeText(getBaseContext(), "Cancelled" , Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.positiveAction("OK")
                        .negativeAction("CANCEL");
                break;
            case R.id.start_time:
                builder = new TimePickerDialog.Builder(isLightTheme ? R.style.Material_App_Dialog_TimePicker_Light : R.style.Material_App_Dialog_TimePicker, 24, 00){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        TimePickerDialog dialog = (TimePickerDialog)fragment.getDialog();
                        start_time.setText(dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()));

                        // Toast.makeText(getBaseContext(), "Time is " + dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), Toast.LENGTH_SHORT).show();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getBaseContext(), "Cancelled" , Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.positiveAction("OK")
                        .negativeAction("CANCEL");

            case R.id.end_date:
                builder = new DatePickerDialog.Builder(isLightTheme ? R.style.Material_App_Dialog_TimePicker_Light : R.style.Material_App_Dialog_TimePicker){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog)fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        end_date.setText(date);

                        // Toast.makeText(getBaseContext(), "Date is " + date, Toast.LENGTH_SHORT).show();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        // Toast.makeText(getBaseContext(), "Cancelled" , Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.positiveAction("OK")
                        .negativeAction("CANCEL");
                break;

            case R.id.end_time:
                builder = new TimePickerDialog.Builder(isLightTheme ? R.style.Material_App_Dialog_TimePicker_Light : R.style.Material_App_Dialog_TimePicker, 24, 00){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        TimePickerDialog dialog = (TimePickerDialog)fragment.getDialog();
                        end_time.setText(dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()));

                        // Toast.makeText(getBaseContext(), "Time is " + dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), Toast.LENGTH_SHORT).show();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        // Toast.makeText(getBaseContext(), "Cancelled" , Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.positiveAction("OK")
                        .negativeAction("CANCEL");
        }

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(this.getSupportFragmentManager(), null);
    }

}
