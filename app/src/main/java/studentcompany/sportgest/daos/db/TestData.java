package studentcompany.sportgest.daos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Event_Category_DAO;
import studentcompany.sportgest.daos.Event_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Position_DAO;
import studentcompany.sportgest.daos.Record_DAO;
import studentcompany.sportgest.daos.Role_DAO;
import studentcompany.sportgest.daos.Role_Permission_DAO;
import studentcompany.sportgest.daos.Permission_DAO;
import studentcompany.sportgest.daos.Squad_Call_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.User_Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Event;
import studentcompany.sportgest.domains.EventCategory;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.PlayerPosition;
import studentcompany.sportgest.domains.Position;
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.Permission;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.Training;
import studentcompany.sportgest.domains.TrainingExercise;
import studentcompany.sportgest.domains.User;

public class TestData {
    Permission_DAO permission_dao;
    Role_DAO role_dao;
    Role_Permission_DAO role_permission_dao;
    User_DAO user_dao;
    Team_DAO team_dao;
    Position_DAO position_dao;
    Player_DAO player_dao;
    Game_DAO game_dao;
    Squad_Call_DAO squad_call_dao;
    Event_Category_DAO event_category_dao;
    Event_DAO event_dao;
    User_Team_DAO user_team_dao;
    Training_DAO training_dao;
    Exercise_DAO exercise_dao;
    Attribute_DAO attribute_dao;
    Record_DAO record_dao;
    Attribute_Exercise_DAO attribute_exercise_dao;
    Training_Exercise_DAO training_exercise_dao;

    public TestData(Context context) {
        permission_dao = new Permission_DAO(context);
        role_dao = new Role_DAO(context);
        role_permission_dao = new Role_Permission_DAO(context);
        user_dao = new User_DAO(context);
        team_dao = new Team_DAO(context);
        position_dao = new Position_DAO(context);
        player_dao = new Player_DAO(context);
        game_dao = new Game_DAO(context);
        squad_call_dao = new Squad_Call_DAO(context);
        event_category_dao = new Event_Category_DAO(context);
        event_dao = new Event_DAO(context);
        user_team_dao = new User_Team_DAO(context);
        training_dao = new Training_DAO(context);
        exercise_dao = new Exercise_DAO(context);
        attribute_dao = new Attribute_DAO(context);
        record_dao = new Record_DAO(context);
        attribute_exercise_dao = new Attribute_Exercise_DAO(context);
        training_exercise_dao = new Training_Exercise_DAO(context);

        try {

            // Users
            Permission perm1 = new Permission("View Users List");
            Permission perm2 = new Permission("Edit Users");
            perm1.setId(permission_dao.insert(perm1));
            perm2.setId(permission_dao.insert(perm2));

            Permission perm3 = new Permission("View Matches");
            perm3.setId(permission_dao.insert(perm3));
            Permission perm4 = new Permission("Edit Matches");
            perm4.setId(permission_dao.insert(perm3));
            permission_dao.insert(new Permission("View Players List"));
            permission_dao.insert(new Permission("Edit Players"));
            permission_dao.insert(new Permission("View Exercises"));
            permission_dao.insert(new Permission("Edit Exercises"));
            permission_dao.insert(new Permission("View Trainings"));
            permission_dao.insert(new Permission("Edit Trainings"));


            // Roles
            Role role1 = new Role("User Manager");
            Role role2 = new Role("Match Planner");
            role1.setId(role_dao.insert(role1));
            role2.setId(role_dao.insert(role2));
            role_dao.insert(new Role("Physio"));
            role_dao.insert(new Role("Player"));
            role_dao.insert(new Role("Coach"));
            role_dao.insert(new Role("Administrator"));
            role_dao.insert(new Role("Scout"));
            role_dao.insert(new Role("Chairman"));
            role_dao.insert(new Role("Supporter"));
            role_dao.insert(new Role("Waterboy"));
            role_permission_dao.insert(new Pair<Role, Permission>(role1,perm1));
            role_permission_dao.insert(new Pair<Role, Permission>(role1,perm2));
            role_permission_dao.insert(new Pair<Role, Permission>(role2,perm3));
            role_permission_dao.insert(new Pair<Role, Permission>(role2,perm4));


            // Users

            User u1 = new User(0,"user0","password",null,"António Joaquim","user0@email.com",role1);
            User u2 = new User(1,"user1","password",null,"João Dias","user1@email.com",role2);
            User u3 = new User(2,"user2","password",null,"Maria Andrade","user2@email.com",role2);
            User u4 = new User(3,"user3","password",null,"José Alves","user3@email.com",role1);

            user_dao.insert(u1);
            user_dao.insert(u2);
            user_dao.insert(u3);
            user_dao.insert(u4);

            // Teams

            Team team1 = new Team(1,"Cabo Verde","Equipa A",null,2015,0);
            Team team2 = new Team(2,"SC Braga / AAUM","SC Braga e AAUM",null,2015,0);
            Team team3 = new Team(3,"Gualtar","Na primeira divisão",null,2015,1);
            Team team4 = new Team(4,"Fafe","Equipa Feminina",null,2015,1);
            Team team5 = new Team(5,"Portugal","Equipa A",null,2015,0);
            team_dao.insert(team1);
            team_dao.insert(team2);
            team_dao.insert(team3);
            team_dao.insert(team4);
            team_dao.insert(team5);

            // Users Team

            Pair<User,Team> pa1 = new Pair<>(u1,team5);
            Pair<User,Team> pa2 = new Pair<>(u2,team5);
            Pair<User,Team> pa3 = new Pair<>(u3,team1);
            Pair<User,Team> pa4 = new Pair<>(u4,team1);
            user_team_dao.insert(pa1);
            user_team_dao.insert(pa2);
            user_team_dao.insert(pa3);
            user_team_dao.insert(pa4);


            // Positions

            Position pos_gk = new Position("Goalkeeper");
            Position pos_def = new Position("Defender");
            Position pos_w = new Position("Winger");
            Position pos_piv = new Position("Pivot");

            pos_gk.setId(position_dao.insert(pos_gk));
            pos_def.setId(position_dao.insert(pos_def));
            pos_w.setId(position_dao.insert(pos_w));
            pos_piv.setId(position_dao.insert(pos_piv));



            // Players
            PlayerPosition p16_gk = new PlayerPosition(1,new Player(6),pos_gk,10);
            ArrayList<PlayerPosition> ppar = new ArrayList<>(); ppar.add(p16_gk);

            PlayerPosition p17_gk = new PlayerPosition(2,new Player(7),pos_w,10);
            PlayerPosition p18_gk = new PlayerPosition(3,new Player(7),pos_piv,10);
            ArrayList<PlayerPosition> ppar2 = new ArrayList<>(); ppar.add(p17_gk); ppar.add(p18_gk);

            Player pl1 = new Player(1,"André", "André Alberto", "Cape Verde", "Single", "1990-2-3", 176 ,70.4f , "Travessa do Rossio", "Male", null, "player1@email.com", "Direito", 2, new Team(1), null);
            Player pl2 = new Player(2,"Miguel", "Givanildo Miguel", "Cape Verde", "Married", "1990-2-3", 170 ,83 , "Rua de Avintes", "Male", null, "player1@email.com", "Direito", 4, new Team(1), null);
            Player pl3 = new Player(3,"Jorge D.", "Jorge Davide Ramirez", "Cape Verde", "Single", "1990-2-3", 180 ,73.6f , "Avenida dos Amigos", "Male", null, "player1@email.com", "Esquerdo", 3, new Team(1), null);
            Player pl4 = new Player(4,"Nel", "Manuel Arouca", "Cape Verde", "Married", "1990-2-3", 194 ,69.69f , "Centro", "Male", null, "player1@email.com", "Direito", 1, new Team(1), null);
            Player pl5 = new Player(5,"Mário S.", "Mário Silva Soares", "Cape Verde", "Married", "1990-2-3", 194 ,69.69f , "Oito Fontes", "Male", null, "player1@email.com", "Direito", 6, new Team(1), null);

            Player pl6 = new Player(6,"Bebé", "Euclides Gomes Vaz", "Portugal", "Single", "1983-05-19", 173 ,66.00f , "Lisboa", "Male", null, "bebe@slbenfica.pt", "Esquerdo", 1, team5, ppar);
            Player pl7 = new Player(7,"Ricardinho", "Ricardo Filipe Silva Duarte Braga", "Portugal", "Married", "1985-09-03", 165 ,68.00f , "Gondomar", "Male", null, "player1@email.com", "Esquerdo", 10, team5, ppar2);
            Player pl8 = new Player(8,"Fábio C.", "Fábio Miguel Valadares Cecílio", "Portugal", "Single", "1990-2-3", 194 ,69.69f , "Travessa do Morro", "Male", null, "player1@email.com", "Direito", 22, team5, null);
            Player pl9 = new Player(9,"Pedro Cary", "Pedro Cary", "Portugal", "Married", "1990-2-3", 194 ,69.69f , "Travessa do Morro", "Male", null, "player1@email.com", "Direito", 6, team5, null);
            Player pl10 = new Player(10,"T. Brito", "Tiago Brito", "Portugal", "Married", "1990-2-3", 194 ,69.69f , "Travessa do Morro", "Male", null, "player1@email.com", "Direito", 13, team5, null);
            Player pl11 = new Player(11,"Paulinho", "Paulinho", "Portugal", "Married", "1990-2-3", 194 ,69.69f , "Travessa do Morro", "Male", null, "player1@email.com", "Direito", 2, team5, null);
            Player pl12 = new Player(12,"Djô", "Manuel Arouca", "Portugal", "Married", "1990-2-3", 194 ,69.69f , "Travessa do Morro", "Male", null, "player1@email.com", "Direito", 8, team5, null);
            Player pl13 = new Player(13,"Cardinal", "Cardinal", "Portugal", "Married", "1990-2-3", 194 ,69.69f , "Travessa do Morro", "Male", null, "player1@email.com", "Direito", 7, team5, null);

            player_dao.insert(pl1);
            player_dao.insert(pl2);
            player_dao.insert(pl3);
            player_dao.insert(pl4);
            player_dao.insert(pl5);
            player_dao.insert(pl6);
            player_dao.insert(pl7);
            player_dao.insert(pl8);
            player_dao.insert(pl9);
            player_dao.insert(pl10);
            player_dao.insert(pl11);
            player_dao.insert(pl12);
            player_dao.insert(pl13);


            // Games

            Game game1 = new Game( 1,team5, team1, 0, "Portugal wins with a solid victory against Cape Verde", 4, 0, 40f);

            game_dao.insert(game1);
            game_dao.insert(new Game( 2,new Team(1),new Team(3), 0, "A - C", 3, 1, 40f));
            game_dao.insert(new Game( 3,new Team(3),new Team(2), 0, "C - B", 6, 2, 40f));
            game_dao.insert(new Game( 4,new Team(1),new Team(5), 0, "D - B", 2, 2, 40f));
            game_dao.insert(new Game( 5,new Team(4),new Team(1), 0, "D - A", 0, 0, 40f));

            // Squad Call

            Pair<Player,Game> sq5_1 = new Pair<>(pl6,game1);
            Pair<Player,Game> sq5_2 = new Pair<>(pl7,game1);
            Pair<Player,Game> sq5_3 = new Pair<>(pl8,game1);
            Pair<Player,Game> sq5_4 = new Pair<>(pl9,game1);
            Pair<Player,Game> sq5_5 = new Pair<>(pl10,game1);
            Pair<Player,Game> sq5_6 = new Pair<>(pl11,game1);
            Pair<Player,Game> sq5_7 = new Pair<>(pl12,game1);
            Pair<Player,Game> sq5_8 = new Pair<>(pl13,game1);

            Pair<Player,Game> sq1_1 = new Pair<>(pl1,game1);
            Pair<Player,Game> sq1_2 = new Pair<>(pl2,game1);
            Pair<Player,Game> sq1_3 = new Pair<>(pl3,game1);
            Pair<Player,Game> sq1_4 = new Pair<>(pl4,game1);
            Pair<Player,Game> sq1_5 = new Pair<>(pl5,game1);

            squad_call_dao.insert(sq5_1);
            squad_call_dao.insert(sq5_2);
            squad_call_dao.insert(sq5_3);
            squad_call_dao.insert(sq5_4);
            squad_call_dao.insert(sq5_5);
            squad_call_dao.insert(sq5_6);
            squad_call_dao.insert(sq5_7);
            squad_call_dao.insert(sq5_8);

            squad_call_dao.insert(sq1_1);
            squad_call_dao.insert(sq1_2);
            squad_call_dao.insert(sq1_3);
            squad_call_dao.insert(sq1_4);
            squad_call_dao.insert(sq1_5);

            // Event Categories

            EventCategory evc1 = new EventCategory(1,"Goal", R.color.purple_300,true);
            EventCategory evc2 = new EventCategory(2,"Foul", R.color.blue_300,true);
            EventCategory evc3 = new EventCategory(3,"Yellow Card", R.color.yellow_300,true);
            EventCategory evc4 = new EventCategory(4,"Red Card", R.color.red_300,true);
            EventCategory evc5 = new EventCategory(5,"Free Kick", R.color.orange_300,true);
            EventCategory evc6 = new EventCategory(6,"Corner", R.color.green_300,true);
            EventCategory evc7 = new EventCategory(7,"Offensive Play", R.color.cyan_300,false);

            event_category_dao.insert(evc1);
            event_category_dao.insert(evc2);
            event_category_dao.insert(evc3);
            event_category_dao.insert(evc4);
            event_category_dao.insert(evc5);
            event_category_dao.insert(evc6);
            event_category_dao.insert(evc7);

            // Events

            Event ev1 = new Event(1,"Goal",45,200,200,evc1,game1,pl7);
            Event ev2 = new Event(2,"Goal",192,210,380,evc1,game1,pl7);
            Event ev3 = new Event(3,"Foul",210,510,700,evc2,game1,pl9);
            Event ev4 = new Event(4,"Goal",260,200,200,evc3,game1,pl10);
            Event ev5 = new Event(5,"Goal",308,200,200,evc5,game1,pl11);
            Event ev6 = new Event(6,"Goal",458,200,200,evc7,game1,pl8);
            Event ev7 = new Event(7,"Goal",802,200,200,evc1,game1,pl9);
            Event ev8 = new Event(8,"Goal",950,200,200,evc1,game1,pl12);
            Event ev9 = new Event(9,"Goal",990,200,200,evc1,game1,pl13);



            // Attributes

            attribute_dao.insert(new Attribute(1, Attribute.QUANTITATIVE, "Stamina", 0));
            attribute_dao.insert(new Attribute(2, Attribute.QUANTITATIVE, "Pace", 0));
            attribute_dao.insert(new Attribute(3, Attribute.QUANTITATIVE, "Power", 0));
            attribute_dao.insert(new Attribute(4, Attribute.QUANTITATIVE, "Balance", 0));
            attribute_dao.insert(new Attribute(5, Attribute.QUANTITATIVE, "Strength", 0));
            attribute_dao.insert(new Attribute(6, Attribute.QUANTITATIVE, "Jumping", 0));
            attribute_dao.insert(new Attribute(7, Attribute.QUANTITATIVE, "Heading", 0));

            attribute_dao.insert(new Attribute(8, Attribute.RATIO, "Throws", 0));
            attribute_dao.insert(new Attribute(9, Attribute.RATIO, "Free Kicks", 0));
            attribute_dao.insert(new Attribute(10, Attribute.RATIO, "Penalties", 0));

            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Agressividade", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Antecipacao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Bravura", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Compustura", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Concentracao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Decisoes", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Determincacao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Imprevisibilidade", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Indice de trabalho", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Lideranca", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Posicionamento", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Sem bola", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Trabalho de equipa", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Visao de jogo", 0));

            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Comando de area", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Comunicacao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Exentricidade", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Jogo aereo", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Jogo maos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Pontape", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Primeiro toque", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Reflexos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Saidas", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Tendencia sair punhos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Um para um", 0));

            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Cabeceamento", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Cantos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Cruzamentos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Desarme", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Finalizacao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Finta", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Lancamentos longos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Marcacao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Passe", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Remates de longe", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Tecnica", 0));

            // Exercises

            Exercise e1 = new Exercise(1,"Shooting","Outside the box",2);
            exercise_dao.insert(e1);
            exercise_dao.insert(new Exercise(2, "Sprinting", "Around the field", 5));
            exercise_dao.insert(new Exercise(3, "Passing", "On center of the field", 4));
            exercise_dao.insert(new Exercise(4, "Side Attacks", "Wingers", 10));
            exercise_dao.insert(new Exercise(5, "Park the bus", "Increase the defensive system", 7));
            exercise_dao.insert(new Exercise(6, "Corner Kicks Left", "Left side", 10));
            exercise_dao.insert(new Exercise(7, "Corner Kicks Right", "Right side", 10));
            exercise_dao.insert(new Exercise(8, "Standing Tackles", "Standing Tackles", 4));
            exercise_dao.insert(new Exercise(9, "Sliding Tackles", "Sliding Tackles", 4));


            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(1), e1));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(2), e1));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(3), e1));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(4), e1));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(5), e1));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(6), e1));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(7), e1));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(8), e1));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(9), e1));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(10), e1));

            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(2), exercise_dao.getById(2)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(3), exercise_dao.getById(2)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(4), exercise_dao.getById(2)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(5), exercise_dao.getById(2)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(9), exercise_dao.getById(2)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(10), exercise_dao.getById(2)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(1), exercise_dao.getById(3)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(8), exercise_dao.getById(3)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(1), exercise_dao.getById(4)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(2), exercise_dao.getById(4)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(3), exercise_dao.getById(4)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(1), exercise_dao.getById(5)));





            // Training

            Training tr1 = new Training(1,"Monday","Stamina improvement",20150123,60,team5);
            Training tr2 = new Training(2,"Pre-Match","Stamina improvement",20150123,60,team5);
            Training tr3 = new Training(3,"Relax","Stamina improvement",20150123,60,team5);
            Training tr4 = new Training(4,"Finishing","Stamina improvement",20150123,60,team5);

            training_dao.insert(tr1);
            training_dao.insert(tr2);
            training_dao.insert(tr3);
            training_dao.insert(tr4);


            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(1), exercise_dao.getById(1), 4));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(2), exercise_dao.getById(1), 3));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(3), exercise_dao.getById(1), 2));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(4), exercise_dao.getById(1), 2));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(1), exercise_dao.getById(2), 2));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(1), exercise_dao.getById(4), 3));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(2), exercise_dao.getById(4), 2));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(3), exercise_dao.getById(4), 1));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(1), exercise_dao.getById(5), 2));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(2), exercise_dao.getById(5), 5));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(1), exercise_dao.getById(6), 3));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(4), exercise_dao.getById(8), 1));

































        }catch (GenericDAOException ex){
            ex.printStackTrace();
        }
    }
}

