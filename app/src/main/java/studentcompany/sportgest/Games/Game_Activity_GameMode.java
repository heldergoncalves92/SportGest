package studentcompany.sportgest.Games;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.SynchronousQueue;

import studentcompany.sportgest.Players.Player_Fragment_List;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Event_Category_DAO;
import studentcompany.sportgest.daos.Event_DAO;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.GenericDAO;
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

public class Game_Activity_GameMode extends AppCompatActivity implements Player_Fragment_List.OnItemSelected, GameMode_Event_Fragment_List.OnItemSelected {

    private List<Player> inGame, onBench;
    private List<EventCategory> events;
    private Player_DAO playerDao;
    private Squad_Call_DAO squadCallDao;
    private Event_DAO event_dao;
    private Game_DAO game_dao;
    private Event_Category_DAO event_category_dao;

    private long baseGameID;

    private FragmentManager mFragmentManager;
    private Player_Fragment_List mList_inGame = new Player_Fragment_List();
    private Player_Fragment_List mList_onBench = new Player_Fragment_List();
    private GameMode_Event_Fragment_List mList_Events = new GameMode_Event_Fragment_List();
    private static final String TAG = "GAME_GAME_MODE_ACTIVITY";
    private static final int ON_BENCH = 1, IN_GAME = 2, EVENT = 3;

    private SurfaceView field;
    private SurfaceHolder holder;
    private Paint paint = new Paint(Paint.DITHER_FLAG);

    private int posx=0,posy=0;
    private long event_id=-1, player_id=-1;
    private EventCategory eventCategory=null;
    private Player player=null;

    private int tag = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_game_mode);


        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                baseGameID = extras.getLong("GAME");

            } else
                baseGameID = -1;

        } else {
            baseGameID = savedInstanceState.getLong("baseGameID");
        }


        field = (SurfaceView)findViewById(R.id.game_field);
        holder = field.getHolder();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);


        field.setBackgroundColor(Color.TRANSPARENT);
        field.setZOrderOnTop(true); //necessary
        holder.setFormat(PixelFormat.TRANSPARENT);

        field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    eventCategory = mList_Events.getCurrentItem();
                    player = mList_inGame.getCurrentItem();
                    if (holder.getSurface().isValid() && player != null && eventCategory != null) {
                        Canvas canvas = holder.lockCanvas();
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        canvas.drawCircle(event.getX(), event.getY(), 30, paint);
                        posx = (int) ((event.getX()*1000) / v.getWidth());
                        posy = (int) ((event.getY()*1000) / v.getWidth());
                        holder.unlockCanvasAndPost(canvas);

                        try {

                            Event eventz = new Event(eventCategory.getName() == null ? "" : eventCategory.getName(),20,posx,posy,eventCategory,new Game(baseGameID),player);

                            long idz = event_dao.insert(eventz);
                            if(idz<0)
                                throw new GenericDAOException();
                            mList_Events.unselect_Item();
                            eventCategory = null;
                            Toast.makeText(getApplicationContext(), R.string.game_mode_add_eventcategory_success, Toast.LENGTH_SHORT).show();
                        } catch (GenericDAOException e) {
                            Toast.makeText(getApplicationContext(), R.string.game_mode_add_eventcategory_failed, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
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
            onBench = new ArrayList<Player>();
            inGame = squadCallDao.getPlayersBy_GameID(baseGameID);

            events = event_category_dao.getAll();
            if(inGame == null){
                insertTest();
                inGame = squadCallDao.getPlayersBy_GameID(baseGameID);
                events = event_category_dao.getAll();
                //finish();
                //return;
            }

            if(inGame.size() > 5) {
                while(inGame.size() != 5){
                    onBench.add(inGame.get(5));
                    inGame.remove(5);
                }
            }

            mList_inGame.setList(inGame);
            mList_onBench.setList(onBench);

            mList_inGame.setTag(IN_GAME);
            mList_onBench.setTag(ON_BENCH);

            mList_Events.setList(events);
            mList_Events.setTag(EVENT);

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

        fragmentTransaction.commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("baseGameID", baseGameID);
    }



    private void insertTest(){
        Team_DAO team_dao = new Team_DAO(getApplicationContext());
        Player_DAO player_dao = new Player_DAO(getApplicationContext());
        Game_DAO game_dao = new Game_DAO(getApplicationContext());
        Squad_Call_DAO squad_call_dao = new Squad_Call_DAO(getApplicationContext());
        long id;
        Player p;

        try {
            Team team = new Team("Santa Maria","Uma equipa fantástica!!","default.jpg",2015,0);
            long teamID = team_dao.insert(team);
            team.setId(teamID);

            Game game = new Game(team,team, new Date().getTime(), "", -1 , -1, 40.0f);
            long gameID = game_dao.insert(game);
            game.setId(gameID);

            EventCategory event = new EventCategory("Goal");
            gameID = event_category_dao.insert(event);

            event = new EventCategory("Yellow Card");
            event_category_dao.insert(event);

            event = new EventCategory("Foul");
            event_category_dao.insert(event);

            event = new EventCategory("Explosion");
            event_category_dao.insert(event);

            p = new Player("Jocka", "João Alberto", "Portuguesa", "Solteiro", "1222-1-23", 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 2, team, null);
            id = player_dao.insert(p);
            p.setId(id);
            id = squad_call_dao.insert(new Pair<>(p , game));

            p = new Player("Fabinho", "Fábio Gomes", "Portuguesa", "Solteiro", "1222-1-23", 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 4, team, null);
            id = player_dao.insert(p);
            p.setId(id);
            id = squad_call_dao.insert(new Pair<>(p , game));

            p = new Player("Jorge D.", "Jorge Duarte", "Portuguesa", "Solteiro", "1231-2-3", 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 3, team, null);
            id = player_dao.insert(p);
            p.setId(id);
            id = squad_call_dao.insert(new Pair<>(p , game));

            p = new Player("Nel", "Manuel Arouca", "Portuguesa", "Solteiro", "1231-2-3", 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, team, null);
            id = player_dao.insert(p);
            p.setId(id);
            id = squad_call_dao.insert(new Pair<>(p , game));


        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

    }

    public void itemSelected(int position, int tag){


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

}
