package ivaniuminov.com.remindme;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity{

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem splashItem = menu.getItem(R.id.action_spalsh);
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
                    .replace(R.id.container, splashFragment)
                    .addToBackStack(null).commit();
        }
    }
}
