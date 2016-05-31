package com.pempz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pempz.adapter.PempzListAdapter;
import com.pempz.model.Pempz;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PempzDetailsActivity extends AppCompatActivity implements PempzListAdapter.OnItemClickListener {
    public static String EXTRA_OBJ = "";

    Pempz pempz;

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

        this.pempz = (Pempz) getIntent().getSerializableExtra(EXTRA_OBJ);

        setTitle("Details");
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        initComponents();
    }

    private void initComponents(){
        message = (TextView) findViewById(R.id.message);
        from_date = (TextView) findViewById(R.id.from_date);
        from_time = (TextView) findViewById(R.id.from_time);
        to_date = (TextView) findViewById(R.id.to_date);
        to_time = (TextView) findViewById(R.id.to_time);

        SimpleDateFormat date_f = new SimpleDateFormat("dd MMM yy");
        SimpleDateFormat time_f = new SimpleDateFormat("HH:mm");

        SimpleDateFormat mn = new SimpleDateFormat("MMM");
        DecimalFormat df = new DecimalFormat("00");
        DecimalFormat dy = new DecimalFormat("yy");

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
    public void onItemClick(View view, int position) {

    }
}
