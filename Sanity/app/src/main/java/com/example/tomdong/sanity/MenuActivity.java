package com.example.tomdong.sanity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tomdong.sanity.dummy.DummyContent;
import com.google.firebase.auth.FirebaseAuth;

import Model.BudgetModel;
import Model.CategoryModel;
import Model.Variable;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BudgetFragment.OnListFragmentInteractionListener,
        CategoryFragment.OnListFragmentInteractionListener,
        TransactionPickerFragment.OnFragmentInteractionListener,
        OverviewFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        OverviewFragment overviewFragment = new OverviewFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                overviewFragment,
                overviewFragment.getTag()
        ).commit();

        sendNotification();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        ((TextView)findViewById(R.id.textViewUsername)).setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Overview");

            OverviewFragment overviewFragment = new OverviewFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    overviewFragment,
                    overviewFragment.getTag()
            ).commit();

        } else if (id == R.id.nav_mng_bgt) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Manage Budgets");

            BudgetFragment budgetFragment = new BudgetFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    budgetFragment,
                    budgetFragment.getTag()
            ).commit();

            Log.d("Manager", "Manage Budgets!!!");

        } else if (id == R.id.nav_mng_cat) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Manage Category");

            CategoryFragment categoryFragment = new CategoryFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    categoryFragment,
                    categoryFragment.getTag()
            ).commit();

            Log.d("Manager", "Manage Categories!!!");

        } else if (id == R.id.nav_mng_trans) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Manage Transactions");

            TransactionPickerFragment transactionPickerFragment = new TransactionPickerFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    transactionPickerFragment,
                    transactionPickerFragment.getTag()
            ).commit();

            Log.d("Manager", "Manage Transactions!!!");

        } else if (id == R.id.nav_ana_cat) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Spending Behavior");

            AnalysisFragment analysisFragment = new AnalysisFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    analysisFragment,
                    analysisFragment.getTag()
            ).commit();

            Log.d("Manager", "Spending Behavior!!!");

        } else if (id == R.id.nav_ana_trans) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Spending Trend");

            TrendFragment trendFragment = new TrendFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    trendFragment,
                    trendFragment.getTag()
            ).commit();

            Log.d("Manager", "Spending Trend");

        } else if (id == R.id.nav_change_pw) {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.change_fandthreshhold_inpu_log, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptView);
            final EditText edit_thresh=promptView.findViewById(R.id.edit_thresh_hold);
            final EditText edit_freq=promptView.findViewById(R.id.edit_frequency);
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int idx) {
                            if(edit_freq.getText().toString().isEmpty() || edit_thresh.getText().toString().isEmpty()) return;
                            Variable.GetInstance().setmFrequency(Integer.parseInt(edit_freq.getText().toString()));
                            Variable.GetInstance().setmThreshold(Double.parseDouble(edit_thresh.getText().toString()));

                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create an alert dialog
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();

        } else if (id == R.id.nav_log_out) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Are you sure to log out?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getApplicationContext(), "good", Toast.LENGTH_SHORT).show();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void sendNotification() {
        String text = "";
        for(String s: CategoryModel.GetInstance().getNotification()){
            text += s;
        }
        for(String s: BudgetModel.GetInstance().getNotification()){
            text += s;
        }
        if(!text.isEmpty()){
            int id = 1;
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.app_icon);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            //设置小图标
            mBuilder.setSmallIcon(R.drawable.app_icon);
            //设置大图标
            mBuilder.setLargeIcon(bitmap);
            //设置标题
            mBuilder.setContentTitle("Sanity Reminding");
            //设置通知正文
            mBuilder.setContentText(text);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(id++, mBuilder.build());
        }

    }


}
