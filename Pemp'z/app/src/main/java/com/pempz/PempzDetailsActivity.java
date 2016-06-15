package com.pempz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pempz.adapter.MainChipViewAdapter;
import com.pempz.adapter.PempzListAdapter;
import com.pempz.model.Contact;
import com.pempz.model.Pempz;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.ChipViewAdapter;
import com.plumillonforge.android.chipview.OnChipClickListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PempzDetailsActivity extends AppCompatActivity implements PempzListAdapter.OnItemClickListener {
    public static String EXTRA_OBJ = "";

    Pempz pempz;

    ChipView chips;
    TextView message;
    TextView from_date;
    TextView from_time;
    TextView to_date;
    TextView to_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pempz_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.pempz = (Pempz) getIntent().getSerializableExtra(EXTRA_OBJ);

        setTitle("Details");
        initComponents();
    }

    private void initComponents(){
        chips = (ChipView) findViewById(R.id.chip_contacts);
        chips.setChipSidePadding(0);
        // chips.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        List<Chip> chipList = new ArrayList<>();

        ChipViewAdapter adapter = new MainChipViewAdapter(this, 0);
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

        message = (TextView) findViewById(R.id.message);
        from_date = (TextView) findViewById(R.id.from_date);
        from_time = (TextView) findViewById(R.id.from_time);
        to_date = (TextView) findViewById(R.id.to_date);
        to_time = (TextView) findViewById(R.id.to_time);

        SimpleDateFormat date_f = new SimpleDateFormat("dd MMM yy");
        SimpleDateFormat time_f = new SimpleDateFormat("HH:mm");

        Calendar f = Calendar.getInstance();
        f.setTime(pempz.getFrom());
        Calendar t = Calendar.getInstance();
        t.setTime(pempz.getTo());

        String f_date = date_f.format(f.getTime()); // f.get(Calendar.DATE) + " " + mn.format(f.getTime()) + " " + dy.format(f.getTime());
        String f_time = time_f.format(f.getTime());
        String t_date = date_f.format(t.getTime());
        String t_time = time_f.format(t.getTime());

        message.setText(pempz.getMessage());
        from_date.setText(f_date);
        from_time.setText(f_time);
        to_date.setText(t_date);
        to_time.setText(t_time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pempz_details, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(this, PempzActivity.class);
                i.putExtra(PempzActivity.KEY_ACTION, "Edit");
                i.putExtra(PempzActivity.KEY_PEMPZ, pempz);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
