package ivaniuminov.com.remindme;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ivaniuminov.com.remindme.adapter.TabAdapter;
import ivaniuminov.com.remindme.dialog.AddingTaskDialogFragment;
import ivaniuminov.com.remindme.fragment.SplashFragment;


public class MainActivity extends AppCompatActivity implements AddingTaskDialogFragment.AddingTaskListener{

    private static final int LAYOUT = R.layout.main;
    FragmentManager fragmentManager;
    PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        preferenceHelper = PreferenceHelper.getInstance();
        preferenceHelper.init(this);

        fragmentManager = getFragmentManager();

        runSplash();

        setIU();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem splashItem = menu.findItem(R.id.action_spalsh);
        splashItem.setChecked(preferenceHelper.getBoolValue(PreferenceHelper.SPLASH_IS_VISIBLE));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_spalsh){
            item.setChecked(!item.isChecked());
            preferenceHelper.setBoolValue(PreferenceHelper.SPLASH_IS_VISIBLE, item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }

    private void runSplash(){

        if (!preferenceHelper.getBoolValue(PreferenceHelper.SPLASH_IS_VISIBLE)) {

            SplashFragment splashFragment = new SplashFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, splashFragment)
                    .addToBackStack(null).commit();
        }
    }

    private void setIU(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.current_task)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.done_task)));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabAdapter tabAdapter = new TabAdapter(fragmentManager, 2);

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                addingTaskDialogFragment.show(fragmentManager, "AddingTaskDialogFragment");
            }
        });
    }

    @Override
    public void onTaskAdded() {
        Toast.makeText(this, "Task added", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, "Adding task cancelled", Toast.LENGTH_LONG).show();
    }
}
