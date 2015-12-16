package studentcompany.sportgest.Trainings;

import android.content.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.Exercises.ExerciseTestData;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.Training;
import studentcompany.sportgest.domains.TrainingExercise;

public class TrainingTestData {

    Exercise_DAO exercise_dao;
    Training_DAO training_dao;
    Team_DAO team_dao;
    Training_Exercise_DAO training_exercise_dao;

    public TrainingTestData(Context context) {
        exercise_dao = new Exercise_DAO(context);
        training_dao = new Training_DAO(context);
        team_dao = new Team_DAO(context);
        training_exercise_dao = new Training_Exercise_DAO(context);
        String lorem = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas" +
                " porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus" +
                " lectus malesuada libero, sit amet commodo magna eros quis urna.\n" +
                "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac" +
                " turpis egestas. Proin pharetra nonummy pede. Mauris et orci.\n" +
                "Aenean nec lorem. In porttitor. Donec laoreet nonummy augue.\n" +
                "Suspendisse dui purus, scelerisque at, vulputate vitae, pretium mattis, nunc. " +
                "Mauris eget neque at sem venenatis eleifend. Ut nonummy.";
        try {
            if(team_dao.numberOfRows() == 0){
                team_dao.insert(new Team(-1, "Best Team A", lorem, "logoA.jpg", 2015, 0));
                team_dao.insert(new Team(-1, "Best Team B", lorem, "logoB.jpg", 2015, 0));
                team_dao.insert(new Team(-1, "Best Team C", lorem, "logoC.jpg", 2015, 0));
                team_dao.insert(new Team(-1, "Best Team D", lorem, "logoD.jpg", 2015, 0));
            }
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            training_dao.insert(new Training(-1, "Title", lorem, 201512, 100, team_dao.getById(1)));
            if(exercise_dao.numberOfRows() == 0){
                new ExerciseTestData(context);
            }
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(1), exercise_dao.getById(1), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(2), exercise_dao.getById(1), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(3), exercise_dao.getById(1), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(4), exercise_dao.getById(1), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(5), exercise_dao.getById(1), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(1), exercise_dao.getById(2), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(8), exercise_dao.getById(3), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(1), exercise_dao.getById(4), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(2), exercise_dao.getById(4), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(3), exercise_dao.getById(4), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(1), exercise_dao.getById(5), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(2), exercise_dao.getById(5), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(1), exercise_dao.getById(6), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(5), exercise_dao.getById(6), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(7), exercise_dao.getById(7), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(4), exercise_dao.getById(8), 10));
            training_exercise_dao.insert(new TrainingExercise(-1, training_dao.getById(5), exercise_dao.getById(9), 10));
        }catch (GenericDAOException ex){
            System.err.println(TrainingTestData.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(TrainingTestData.class.getName()).log(Level.WARNING, null, ex);
            ex.printStackTrace();
        }
    }
}
