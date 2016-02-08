package studentcompany.sportgest.Games;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;


import studentcompany.sportgest.Exercises.Exercise_Activity_ListView;
import studentcompany.sportgest.Players.Player_Activity_ListView;
import studentcompany.sportgest.Players.Player_Fragment_List;
import studentcompany.sportgest.R;
import studentcompany.sportgest.Users.CreateRole_Activity;
import studentcompany.sportgest.Users.RolesListActivity;
import studentcompany.sportgest.Users.User_Activity_ListView;
import studentcompany.sportgest.daos.Event_Category_DAO;
import studentcompany.sportgest.daos.Event_DAO;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Squad_Call_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Event;
import studentcompany.sportgest.domains.EventCategory;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

public class Game_Activity_GameMode extends AppCompatActivity implements Player_Fragment_List.OnItemSelected, GameMode_Event_Fragment_List.OnItemSelected, Game_Fragment_GameMode_History.OnItemSelected {

    private List<Player> inGame, onBench;
    private List<EventCategory> events;
    private List<Event> historyEvents;
    private Player_DAO playerDao;
    private Squad_Call_DAO squadCallDao;
    private Event_DAO event_dao;
    private Game_DAO game_dao;
    private Event_Category_DAO event_category_dao;

    private long baseGameID, baseTeamID;
    private Team home,away;
    private Game game;

    private FragmentManager mFragmentManager;
    private Player_Fragment_List mList_inGame = new Player_Fragment_List();
    private Player_Fragment_List mList_onBench = new Player_Fragment_List();
    private GameMode_Event_Fragment_List mList_Events = new GameMode_Event_Fragment_List();
    private Game_Fragment_GameMode_History mHistoryEvents = new Game_Fragment_GameMode_History();
    private static final String TAG = "GAME_GAME_MODE_ACTIVITY";
    private static final int ON_BENCH = 1, IN_GAME = 2, EVENT = 3, HISTORY_GAME = 4;

    private SurfaceView field;//,field2;
    private SurfaceHolder holder;//,holder2;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paint2 = new Paint(Paint.DITHER_FLAG);
    private Paint paintborder = new Paint(Paint.DITHER_FLAG);

    private int posx=0,posy=0, height=1,width=1;
    private float x=0, y=0;
    private long event_id=-1, player_id=-1;
    private int seconds = 0; // For the Events
    private EventCategory eventCategory=null;
    private Player player=null;

    private int tag = -1;

    private DrawerLayout mDrawerLayout;

    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int minute, int second) {
            seconds = minute*60 + second;
            drawAndInsert(true,true);
        }
    };

    java.util.TimerTask task = new ClearScreenTask();

    public class ClearScreenTask extends TimerTask{

        public ClearScreenTask(){};
        @Override
        public void run() {
            clearScreen();
        }
    }

    private void clearScreen() {

        if(holder!=null) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                DrawLogos(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }


    private void drawAndInsert(boolean drawPrevious, boolean toInsert) {

        if(eventCategory == null)
            return;

        try {

            if(toInsert) {
                int ts = eventCategory.hasTimestamp() ? seconds : 0;
                posx = (int) ((x * 1000) / width);
                posy = (int) ((y * 1000) / height);
                Event eventz = new Event(eventCategory.getName() == null ? "" : eventCategory.getName(), ts, posx, posy, eventCategory, new Game(baseGameID), player);

                long idz = event_dao.insert(eventz);
                if (idz < 0)
                    throw new GenericDAOException();

                //historyEvents.add(eventz);
                eventz.setId(idz);
                mHistoryEvents.insert_Item(eventz);

                mList_Events.unselect_Item();
            }
            // Inserted? let's draw!
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            //Draw Logos
            DrawLogos(canvas);

            // Draw the event with special border
            paintborder.setColor((eventCategory.getColor()) | 0xFF000000); // invert the color for the border
            paintborder.setStrokeWidth(3.0f);
            paintborder.setStyle(Paint.Style.STROKE);
            paint.setColor(eventCategory.getColor());
            canvas.drawCircle(x, y, 18, paintborder);
            canvas.drawCircle(x, y, 15, paint);


            if(drawPrevious) {
                // Draw all the previous events of this type
                for (Event ev : historyEvents)
                    if (ev.getEventCategory().getId() == eventCategory.getId())
                        canvas.drawCircle((ev.getPosx() * width) / 1000, (ev.getPosy() * height) / 1000, 15, paint);
            }
            holder.unlockCanvasAndPost(canvas);

            eventCategory = null;



            if (task != null)
                task.cancel();// Cancel the running clear task

            if(toInsert) {

                int cur = mHistoryEvents.getCurrentPos();
                if(cur >= 0)
                    mHistoryEvents.unselect_Item(cur);
                mHistoryEvents.hideButtons();
                //Call to clear the screen after 4s
                task = new ClearScreenTask();
                new java.util.Timer().schedule(task, 4000);

                Toast.makeText(getApplicationContext(), R.string.game_mode_add_eventcategory_success, Toast.LENGTH_SHORT).show();
            }
        } catch (GenericDAOException e) {
            if(toInsert)
                Toast.makeText(getApplicationContext(), R.string.game_mode_add_eventcategory_failed, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_game_mode_main);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_game_mode);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_game_mode);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }


        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                baseGameID = extras.getLong("GAME");
                baseTeamID= extras.getLong("GAME");

            } else {
                baseGameID = 0;
                baseTeamID = 0;
            }
        } else {
            baseGameID = savedInstanceState.getLong("baseGameID");
            baseTeamID = savedInstanceState.getLong("baseTeamID");
        }

        //Some verifications
        if(baseGameID <=0 || baseTeamID <=0) {
            Toast.makeText(this, "Invalid call!!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        field = (SurfaceView)findViewById(R.id.game_field);
        //field2 = (SurfaceView)findViewById(R.id.game_field_2);
        field.setBackgroundColor(Color.TRANSPARENT);
        //field2.setBackgroundColor(Color.TRANSPARENT);
        holder = field.getHolder();
        //holder2 = field2.getHolder();

        //paint2.setAlpha(100);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);


        paint2.setDither(true);
        paint2.setAntiAlias(true);

        field.setZOrderOnTop(true); //necessary
        //field2.setZOrderOnTop(false); //necessary
        //field.setZOrderMediaOverlay(true);
        holder.setFormat(PixelFormat.TRANSPARENT);



        // Draw the logos
        holder.addCallback(new SurfaceHolder.Callback() {

            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                // TODO Auto-generated method stub

            }

            public void surfaceCreated(SurfaceHolder holderr) {

                holder = holderr;


                //field2.setZOrderOnTop(true); //necessary
                holder.setFormat(PixelFormat.TRANSPARENT);

                Canvas canvasTeams = holder.lockCanvas();
                canvasTeams.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                DrawLogos(canvasTeams);

                holder.unlockCanvasAndPost(canvasTeams);

            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }


        });


        field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    eventCategory = mList_Events.getCurrentItem();
                    x = event.getX();
                    y = event.getY();
                    width = v.getWidth();
                    height = v.getHeight();
                    player = mList_inGame.getCurrentItem();
                    if (holder.getSurface().isValid() && player != null && eventCategory != null) {

                        if (eventCategory.hasTimestamp()) {
                            DurationPicker dp = new DurationPicker(Game_Activity_GameMode.this, myCallBack, 0, 0);
                            dp.setTitle(R.string.minutesAndseconds);
                            dp.show();/*
                            TimePickerDialog rt = new TimePickerDialog(Game_Activity_GameMode.this, myCallBack, 0, 0, true);
                            rt.setTitle(R.string.minute);
                            rt.show();*/

                        } else
                            drawAndInsert(true,true);
                    }
                }
                return true;
            }
        });


        try {
            //Initializations
            squadCallDao = new Squad_Call_DAO(getApplicationContext());
            event_category_dao = new Event_Category_DAO(getApplicationContext());
            event_dao = new Event_DAO(getApplicationContext());
            game_dao = new Game_DAO(getApplicationContext());
            playerDao = new Player_DAO(getApplicationContext());


            game = game_dao.getById(baseGameID);
            home = game.getHome_team();
            away = game.getVisitor_team();


            List<Player> allPlayers = squadCallDao.getPlayersBy_GameID(baseGameID);
            onBench = new ArrayList<Player>();
            inGame = new ArrayList<Player>();
            events = event_category_dao.getAll();


            long homeID = home.getId();
            long visitorID = away.getId();

            if(allPlayers != null)
                for(Player p: allPlayers)
                    if(p.getTeam().getId() == homeID)
                        onBench.add(p);

            mList_inGame.setList(inGame);
            mList_onBench.setList(onBench);

            mList_inGame.setTag(IN_GAME);
            mList_onBench.setTag(ON_BENCH);

            mList_Events.setList(events);
            mList_Events.setTag(EVENT);

            historyEvents = new ArrayList<Event>();

            mHistoryEvents.setList(historyEvents);
            mHistoryEvents.setTag(HISTORY_GAME);


        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.game_InGame_list , mList_inGame);
        fragmentTransaction.add(R.id.game_OnBench_list , mList_onBench);
        fragmentTransaction.add(R.id.game_EventCategory_list , mList_Events);
        fragmentTransaction.add(R.id.game_History_list , mHistoryEvents);

        fragmentTransaction.commit();
    }

    private void DrawLogos(Canvas canvasTeams) {
        // Draw team logos on the field
        if (home.getLogo() == null || home.getLogo().equals("")) {
            drawFromDrawable(true, canvasTeams);
        } else {
            Bitmap homebitmap = getImageBitmap(home.getLogo());
            canvasTeams.drawBitmap(homebitmap, 0,0, paint2);
        }

        if (away.getLogo() == null || away.getLogo().equals("")) {
            drawFromDrawable(false, canvasTeams);
        } else {
            Bitmap awaybitmap = getImageBitmap(away.getLogo());
            canvasTeams.drawBitmap(awaybitmap, 0,0, paint2);
        }

    }

    private void drawFromDrawable(boolean home, Canvas canvas) {

        int wid = canvas.getWidth(), hei = canvas.getHeight();

        Drawable myDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myDrawable = getResources().getDrawable(home ? R.drawable.team1 : R.drawable.team2, this.getTheme());
        } else {
            myDrawable = getResources().getDrawable(home ? R.drawable.team1 : R.drawable.team2);
        }

        int centerX = home ? (int)((float)wid / 4.0f) : (int)((float)wid * (3.0f/4.0f));
        int centerY = (int)((float)hei * (3.0f/4.0f));

        float margin = 0.5f;

        int left = centerX - (int)(margin * (float)wid / 8.0f);
        int right = centerX + (int)(margin * (float)wid / 8.0f);

        int top = centerY - (int)(margin * (float)wid / 8.0f);
        int bottom = centerY + (int)(margin * (float)wid / 8.0f);

        myDrawable.setBounds(left, top, right, bottom);
        myDrawable.setAlpha(100);
        myDrawable.draw(canvas);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("baseGameID", baseGameID);
    }



    public void itemSelected(int position, int tag){

        //From HistoryGameMode
        if(tag == HISTORY_GAME) {
            mHistoryEvents.showButtons();
            Event ev = historyEvents.get(position);
            eventCategory = ev.getEventCategory();
            x = (ev.getPosx() * width) / 1000;
            y = (ev.getPosy() * height) / 1000;
            drawAndInsert(false,false);
        }
    }

    public void itemDesselected(int position, int tag){

        //From HistoryGameMode
        if(tag == HISTORY_GAME) {
            mHistoryEvents.hideButtons();
            clearScreen();
        }
    }

    public void itemSelected(int position){

    }

    public void swapPlayers(View v){
        Player p, p2;

        int selected_InGame, selectedOnBench;

        selected_InGame = mList_inGame.has_Selection();
        selectedOnBench = mList_onBench.has_Selection();

        //Swap
        if (selected_InGame != -1 && selectedOnBench != -1) {
            mList_inGame.unselect_Item(selected_InGame);
            mList_onBench.unselect_Item(selectedOnBench);

            p = mList_inGame.removeItem(selected_InGame);
            p2 = mList_onBench.removeItem(selectedOnBench);

            mList_inGame.insert_Item(p2);
            mList_onBench.insert_Item(p);


            //Move to onBench
        }else if (selected_InGame != -1){
            mList_inGame.unselect_Item(selected_InGame);
            p = mList_inGame.removeItem(selected_InGame);
            mList_onBench.insert_Item(p);


            //Move to inGame
        }else if (selectedOnBench != -1){
            mList_onBench.unselect_Item(selectedOnBench);
            p = mList_onBench.removeItem(selectedOnBench);
            mList_inGame.insert_Item(p);

        }
    }


    public void deleteHistory(View v){

        Event e;
        int selected_Item = mHistoryEvents.has_Selection();

        if(selected_Item != -1){
            try {

                mHistoryEvents.unselect_Item(selected_Item);
                e = mHistoryEvents.removeItem(selected_Item);
                event_dao.delete(e);
                clearScreen();
                mHistoryEvents.hideButtons();

            } catch (GenericDAOException e1) {
                e1.printStackTrace();
            }
        }
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.drawer_roles:
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), RolesListActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.drawer_Users:
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), User_Activity_ListView.class);
                                startActivity(intent);
                                return true;
                            case R.id.drawer_Players:
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), Player_Activity_ListView.class);
                                startActivity(intent);
                                return true;
                            case R.id.drawer_Exercise:
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), Exercise_Activity_ListView.class);
                                startActivity(intent);
                                return true;
                        }

                        //menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    public Bitmap getImageBitmap(String name){
        //name=name+"."+extension;
        try{

            ContextWrapper cw = new ContextWrapper(this);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath=new File(directory,name);
            FileInputStream fis = new FileInputStream(mypath);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            return b;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private static String colorToHexString(int color) {
        return String.format("#%06X", 0xFFFFFFFF & color);
    }

}
