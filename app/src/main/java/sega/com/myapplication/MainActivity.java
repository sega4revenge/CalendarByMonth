package sega.com.myapplication;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String mTimeZone;
    private CalendarController mController;
    final int DETAIL = -1;
    final int CURRENT = 0;
    final int AGENDA = 1;
    final int DAY = 2;
    final int WEEK = 3;
    final int MONTH = 4;
    final int EDIT = 5;
    final int MAX_VALUE = 5;
    final long CREATE_EVENT = 1L;

    // Simple view of an event
    final long VIEW_EVENT = 1L << 1;

    // Full detail view in read only mode
    final long VIEW_EVENT_DETAILS = 1L << 2;

    // full detail view in edit mode
    final long EDIT_EVENT = 1L << 3;

    final long DELETE_EVENT = 1L << 4;

    final long GO_TO = 1L << 5;

    final long LAUNCH_SETTINGS = 1L << 6;

    final long EVENTS_CHANGED = 1L << 7;

    final long SEARCH = 1L << 8;

    // User has pressed the home key
    final long USER_HOME = 1L << 9;

    // date range has changed, update the title
    final long UPDATE_TITLE = 1L << 10;

    // select which calendars to display
    final long LAUNCH_SELECT_VISIBLE_CALENDARS = 1L << 11;
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.main_activity);
        long timeMillis = -1;
        int viewType = -1;
        final Intent intent = getIntent();
        if (icicle != null) {

        } else {
            String action = intent.getAction();
            if (Intent.ACTION_VIEW.equals(action)) {
                // Open EventInfo later

            }

            if (timeMillis == -1) {
                timeMillis = Utils.timeFromIntentInMillis(intent);
            }
        }
        initFragments(timeMillis, viewType, icicle);

        mController = CalendarController.getInstance(this);
        mController.sendEvent(this, GO_TO, null, null, -1, MONTH);
    }
    private void initFragments(long timeMillis, int viewType, Bundle icicle) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            Fragment miniMonthFrag = new MonthByWeekFragment(timeMillis, true);
        ft.replace(R.id.mini_month, miniMonthFrag);

        setMainPane(ft, R.id.main_pane, viewType, timeMillis, true);
        ft.commit(); // this needs to be after setMainPane()



    }
    private void setMainPane(
            FragmentTransaction ft, int viewId, int viewType, long timeMillis, boolean force) {


        // Remove this when transition to and from month view looks fine.

        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check if our previous view was an Agenda view
        // TODO remove this if framework ever supports nested fragments

        // Create new fragment
        Fragment frag = null;
        Fragment secFrag = null;

        frag = new MonthByWeekFragment(timeMillis, false);

        // Update the current view so that the menu can update its look according to the
        // current view.



        // Show date only on tablet configurations in views different than Agenda

        // Clear unnecessary buttons from the option menu when switching from the agenda view


        boolean doCommit = false;
        if (ft == null) {
            doCommit = true;
            ft = fragmentManager.beginTransaction();
        }


            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);


        ft.replace(viewId, frag);

/*
        if (doCommit) {
            if (DEBUG) {
                Log.d(TAG, "setMainPane AllInOne=" + this + " finishing:" + this.isFinishing());
            }
            ft.commit();
        }*/
    }
}
