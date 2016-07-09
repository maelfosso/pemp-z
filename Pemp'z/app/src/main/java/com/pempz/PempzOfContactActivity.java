package com.pempz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pempz.adapter.FragmentAdapter;
import com.pempz.adapter.PempzListAdapter;
import com.pempz.data.Tools;
import com.pempz.fragment.ConversationsFragment;
import com.pempz.fragment.PempzFragment;
import com.pempz.model.Contact;
import com.pempz.model.Pempz;
import com.pempz.widget.CircleTransform;
import com.pempz.widget.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PempzOfContactActivity extends AppCompatActivity implements
        AppBarLayout.OnOffsetChangedListener ,PempzListAdapter.OnItemClickListener {
    public static final String EXTRA_OBJ = "com.pempz";

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    AppBarLayout appBarLayout;
    LinearLayout tbLayout;

    Contact contact;


    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private PempzListAdapter adapter;
    private List<Pempz> items;

    View view;


    boolean isSearch = false;
    boolean isTbLayoutVisible = true;

    private PempzFragment f_pempz;
    private ConversationsFragment f_conversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        initComponents();

        contact = (Contact) getIntent().getSerializableExtra(EXTRA_OBJ);

        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.tb_name)).setText(contact.getName());
        ((TextView) findViewById(R.id.tb_phone)).setText(contact.getPhone());

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((TextView)findViewById(R.id.name)).setText(contact.getName());
        ((TextView)findViewById(R.id.phone)).setText(contact.getPhone());
        Picasso.with(this).load(getResources().getIdentifier(contact.getPhoto(), null, getPackageName()))
                .resize(150, 150)
                .transform(new CircleTransform())
                .into((ImageView)findViewById(R.id.photo));
        /*
        if (viewPager != null) {
            setupViewPager(viewPager);
        } */
        Tools.systemBarLolipop(this);

        initActions();
    }

    private void initComponents() {
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tbLayout = (LinearLayout) findViewById(R.id.tb_lyt);
        /* tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager); */
        
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initActions() {
        appBarLayout.addOnOffsetChangedListener(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Log.d("Pempz", "OnCreateView --- Size ..." + items.size());
        adapter = new PempzListAdapter(this, (contact.getPempz()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    /* private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        if (f_pempz == null) {
            f_pempz = new PempzFragment();

            Bundle args = new Bundle();
            args.putSerializable(PempzFragment.KEY_PEMPZ, contact);
            f_pempz.setArguments(args);
        }
        if (f_conversations == null) {
            f_conversations = new ConversationsFragment();
        }

        adapter.addFragment(f_conversations, "Conversations");
        adapter.addFragment(f_pempz, "Pemp'z");

        viewPager.setAdapter(adapter);
    } */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(isSearch ? R.menu.search_toolbar : R.menu.contact_details, menu);

        return true; // super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleToolbarLayoutVisibility(percentage);
    }

    private void handleToolbarLayoutVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!isTbLayoutVisible) {
                startAlphaAnimation(tbLayout, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                isTbLayoutVisible = true;
            }

        } else {

            if (isTbLayoutVisible) {
                startAlphaAnimation(tbLayout, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                isTbLayoutVisible = false;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onItemClick(View view, int position) {
        Pempz pempz = adapter.getItem(position);

        Intent i = new Intent(this, PempzDetailsActivity.class);
        i.putExtra(PempzDetailsActivity.EXTRA_OBJ, pempz);
        startActivity(i);
    }
}
