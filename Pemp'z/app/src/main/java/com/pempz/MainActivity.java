package com.pempz;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pempz.adapter.ContactsListAdapter;
import com.pempz.data.Constant;
import com.pempz.data.Tools;
import com.pempz.model.Contact;
import com.pempz.widget.CircleTransform;
import com.pempz.widget.DividerItemDecoration;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ContactsListAdapter.OnItemClickListener {

    // @BindView(R.id.toolbar)
    Toolbar toolbar;
    // @BindView(R.id.action_search)
    Toolbar searchToolbar;
    // @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    // @BindView(R.id.progressBar)
    ProgressBar progressBar;
    // @BindView(R.id.fab)
    FloatingActionButton fab;

    long exitTime = 0;
    boolean isSearch = false;
    ContactsListAdapter adapter;
    ActionBarDrawerToggle mDrawerToggle;

    // @BindView(R.id.main_content)
    View parent_view;
    // @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    // @BindView(R.id.nav_view)
    NavigationView navigationView;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initActions();
        setupDrawerLayout();
        prepareActionBar(toolbar);

        Tools.systemBarLolipop(this);

        initRecyclerView();
    }

    private void initActions() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent_view, "FAB Clicked", Snackbar.LENGTH_SHORT).show();
                /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchToolbar = (Toolbar) findViewById(R.id.toolbar_search);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        parent_view = (View) findViewById(R.id.main_content);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        View navHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);
        image = (ImageView) navHeader.findViewById(R.id.avatar);
        Picasso.with(this).load(R.drawable.photo_female_7)
                .resize(250, 250)
                .transform(new CircleTransform())
                .into(image);
    }

    private void setupDrawerLayout() {
        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void prepareActionBar(Toolbar tb) {
        setSupportActionBar(tb);
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        if (!isSearch) {
            settingDrawer();
        }
    }

    private void settingDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ContactsListAdapter(this, Constant.getContactsData(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (!isSearch) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

            doExitApp();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(isSearch ? R.menu.search_toolbar : R.menu.main, menu);

        if (isSearch) {
            final SearchView search = (SearchView)menu.findItem(R.id.action_search).getActionView();
            search.setIconified(false);

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);

                    return true;
                }
            });

            search.setOnCloseListener(new SearchView.OnCloseListener() {

                @Override
                public boolean onClose() {
                    closeSearch();
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_search: {
                isSearch = true;
                searchToolbar.setVisibility(View.VISIBLE);

                prepareActionBar(searchToolbar);
                supportInvalidateOptionsMenu();
                return true;
            }
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        // return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        /*
        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */
        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawers();
        Snackbar.make(parent_view, item.getTitle()+ " Clicked ", Snackbar.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Contact contact = adapter.getItem(position);

        Snackbar.make(parent_view, contact.getName()+ ", " + contact.getPhone() + " Clicked ", Snackbar.LENGTH_SHORT).show();

        Intent i = new Intent(this, ContactDetailsActivity.class);
        i.putExtra(ContactDetailsActivity.EXTRA_OBJ, contact);
        startActivity(i);
    }

    private void closeSearch() {
        if (isSearch) {
            isSearch = false;

            prepareActionBar(toolbar);
            searchToolbar.setVisibility(View.GONE);
            supportInvalidateOptionsMenu();
        }
    }

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
