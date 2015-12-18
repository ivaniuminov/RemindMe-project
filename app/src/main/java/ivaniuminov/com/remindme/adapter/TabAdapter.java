package ivaniuminov.com.remindme.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import ivaniuminov.com.remindme.fragment.CurrentTaskFragment;
import ivaniuminov.com.remindme.fragment.DoneTaskFragment;

public class TabAdapter extends FragmentStatePagerAdapter{

    private int numberOfTabs;

    public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;

    private CurrentTaskFragment currentTaskFragment;
    private DoneTaskFragment doneTaskFragment;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;

        currentTaskFragment = new CurrentTaskFragment();
        doneTaskFragment = new DoneTaskFragment();
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case CURRENT_TASK_FRAGMENT_POSITION:
                return currentTaskFragment;
            case DONE_TASK_FRAGMENT_POSITION:
                return doneTaskFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
