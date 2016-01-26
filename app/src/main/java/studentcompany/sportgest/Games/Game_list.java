package studentcompany.sportgest.Games;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Team;

public class Game_list extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Game> games;
    private Game_DAO gameDao;
    //private Game_DAO gameDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list_fragment);
        mRecyclerView = (RecyclerView) findViewById(R.id.games_recycler_view);

        /*try {
            gameDao = new Game_DAO(getApplicationContext());
            games = gameDao.getAll();
            if(games.isEmpty()) {

                noElems();
                //insertUserTest(playerDao);
                //players = playerDao.getAll();
            }


        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
*/
        games = new ArrayList<Game>();
        games.add(new Game(new Team(1), new Team(2), 0, "A - B", 3, 1, 40f));
        games.add(new Game(new Team(3), new Team(4), 0, "C - D", 1, 1, 50f));
        games.add(new Game(new Team(1), new Team(3), 0, "A - C", 2, 3, 40f));
        games.add(new Game(new Team(2), new Team(4), 0, "D - B", 3, 2, 40f));

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Games_Adapter(games);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void noElems(){

       /* LinearLayout l = (LinearLayout)findViewById(R.id.linear);
        l.setVisibility(View.GONE);

        TextView t= (TextView)findViewById(R.id.without_elems);
        t.setVisibility(View.VISIBLE);
        */
    }


}
