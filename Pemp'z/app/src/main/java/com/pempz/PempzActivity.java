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
import com.pempz.adapter.MainChipViewAdapter;
import com.pempz.model.Contact;
import com.pempz.model.Pempz;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.ChipViewAdapter;
import com.plumillonforge.android.chipview.OnChipClickListener;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PempzActivity extends AppCompatActivity implements
        View.OnClickListener {

    public static String KEY_ACTION = "com.pempz.PempzActivity.KEY_ACTION";
    public static String KEY_PEMPZ = "com.pempz.PEMPZ";

    String action;
    Pempz pempz;

    Toolbar toolbar;
    ImageButton cancel;
    ImageButton ok;

    ChipView chips;
    EditText message;

    Switch full_day;
    Button start_date;
    Button start_time;
    Button end_date;
    Button end_time;

    EditText ringTime;
    EditText waitingTimeSendingMessage;

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

        this.pempz = (Pempz) getIntent().getSerializableExtra(KEY_PEMPZ);
        this.action = getIntent().getStringExtra(KEY_ACTION);

        initActions();
    }

    private void initActions() {
        chips = (ChipView) findViewById(R.id.chip_contacts);
        chips.setChipSidePadding(0);
        // chips.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        List<Chip> chipList = new ArrayList<>();

        ChipViewAdapter adapter = new MainChipViewAdapter(this, 1);
        chips.setAdapter(adapter);

        chipList.add(new Contact("Ange KENNE", "678594210", "@drawable/photo_female_3"));
        chipList.add(new Contact("Audrey SCHOUAME", "694859927", "@drawable/photo_female_5"));
        chipList.add(new Contact("Cyprien KENGNE", "655418952", "@drawable/photo_male_4"));
        chipList.add(new Contact("Naoko KOTERA", "694582166", "@drawable/photo_female_6"));
        chips.setChipList(chipList);
        chips.setOnChipClickListener(new OnChipClickListener() {
            @Override
            public void onChipClick(Chip chip) {

            }
        });

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
        Log.d("PEMPZ", this.pempz.toString());

        message = (EditText)findViewById(R.id.message);
        message.setText(this.pempz.getMessage());

        SimpleDateFormat date_f = new SimpleDateFormat("dd MMM yy");
        SimpleDateFormat time_f = new SimpleDateFormat("HH:mm");
        Calendar c = Calendar.getInstance();

        start_date = (Button)findViewById(R.id.start_date);
        start_date.setText(date_f.format(this.pempz.getFrom()));
        start_date.setOnClickListener(this);

        start_time = (Button)findViewById(R.id.start_time);
        start_time.setText(time_f.format(this.pempz.getFrom()));
        start_time.setOnClickListener(this);

        end_date = (Button)findViewById(R.id.end_date);
        end_date.setText(date_f.format(this.pempz.getTo()));
        end_date.setOnClickListener(this);

        end_time = (Button)findViewById(R.id.end_time);
        end_time.setText(time_f.format(this.pempz.getTo()));
        end_time.setOnClickListener(this);

        full_day = (Switch)findViewById(R.id.full_day);
        full_day.setChecked(false);
        // TODO : check if is about full one or two or more than two days

        /*full_day.setOncheckListener(new Switch.OnCheckListener() {
            @Override
            public void onCheck(Switch aSwitch, boolean state) {
                if (state == true) {
                    start_time.setVisibility(View.INVISIBLE);
                    end_time.setVisibility(View.INVISIBLE);
                }
            }
        });*/

        ringTime = (EditText) findViewById(R.id.ring_time);

        waitingTimeSendingMessage = (EditText) findViewById(R.id.waiting_time_sending_message);
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
                break;

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
                break;
        }

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(this.getSupportFragmentManager(), null);
    }

}
