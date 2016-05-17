package com.pempz;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.pempz.adapter.FragmentAdapter;
import com.pempz.data.Tools;
import com.pempz.fragment.ConversationsFragment;
import com.pempz.fragment.PempzFragment;
import com.pempz.model.Contact;

public class ContactDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_OBJ = "com.pempz";

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    Contact contact;

    private PempzFragment f_pempz;
    private ConversationsFragment f_conversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        initComponents();

        contact = (Contact) getIntent().getSerializableExtra(EXTRA_OBJ);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        collapsingToolbarLayout.setTitle(contact.getName());

        ((ImageView) findViewById(R.id.image)).setImageResource(getResources().getIdentifier(
                contact.getPhoto(), null, getPackageName()));

        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        Tools.systemBarLolipop(this);


        initActions();
    }

    private void initComponents() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    private void initActions() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setCurrentItem(1);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        if (f_pempz == null) {
            f_pempz = new PempzFragment();
        }
        if (f_conversations == null) {
            f_conversations = new ConversationsFragment();
        }

        adapter.addFragment(f_pempz, "Pemp'z");
        adapter.addFragment(f_conversations, "Conversations");

        viewPager.setAdapter(adapter);
    }
}
