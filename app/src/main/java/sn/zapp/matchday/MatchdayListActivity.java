package sn.zapp.matchday;

public class MatchdayListActivity{
//        extends AppCompatActivity
//        implements MatchdayListFragment.OnListFragmentInteractionListener, BackHandledFragment.BackHandlerInterface{
//
//    private BackHandledFragment selectedFragment;
//
//    private Toolbar toolbar = null;
//    private FloatingActionButton fab = null;
//    private ZappRealmDBManager realmDBManager = null;
//    private static Handler handler;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_matchday1);
//        handler = new Handler();
//
//        realmDBManager = new ZappRealmDBManager();
//
//        MatchdayListFragment fragment = new MatchdayListFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.container, fragment).commit();
//
//        setToolbar((Toolbar) findViewById(R.id.toolbar_matchday));
//        getToolbar().setTitle("Spieltage");
//        setSupportActionBar(getToolbar());
//
//        setFab((FloatingActionButton) findViewById(R.id.fab));
//        getFab().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openMatchdayView(null, Action.CREATE);
//            }
//        });
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }
//    private void deleteMatchday(Matchday item) {
//        realmDBManager.deleteMatchday(item);
//    }
//
//    private void openMatchdayView(Matchday matchday, Action action) {
//        //TODO Popup Member auswählen nur die nehmen
//        ZappApplication.setViewStatePenalty(action.name());
//        ZappApplication.setViewStateScore(action.name());
//        final MatchdayFragment fragment = new MatchdayFragment();
//        fragment.setMatchday(matchday);
//        fragment.setViewState(action);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                fab.hide();
//            }
//        }, 350);
//
//    }
//
//    public FloatingActionButton getFab() {
//        return fab;
//    }
//
//    public void setFab(FloatingActionButton fab) {
//        this.fab = fab;
//    }
//
//    public Toolbar getToolbar() {
//        return toolbar;
//    }
//
//    public void setToolbar(Toolbar toolbar) {
//        this.toolbar = toolbar;
//    }
//
//    @Override
//    public void onListFragmentInteraction(Matchday item, Action action) {
//        if (action.equals(Action.DELETE))
//            deleteMatchday(item);
//        else openMatchdayView(item, action);
//    }
//    @Override
//    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
//        this.selectedFragment = backHandledFragment;
//    }
//    @Override
//    public void onBackPressed() {
//        if(selectedFragment == null || !selectedFragment.onBackPressed()) {
//            // Selected fragment did not consume the back press event.
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        getFab().show();
//        getToolbar().setTitle("Spieltage");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        getFab().show();
//        getToolbar().setTitle("Spieltage");
//    }
//
//    @Override
//    public boolean onNavigateUp() {
//        realmDBManager.close();
//        toolbar = null;
//        Runtime.getRuntime().gc();
//        return super.onNavigateUp();
//    }
//
//    @Override
//    protected void onDestroy() {
//        realmDBManager.close();
//        toolbar = null;
//        Runtime.getRuntime().gc();
//        super.onDestroy();
//    }
}
