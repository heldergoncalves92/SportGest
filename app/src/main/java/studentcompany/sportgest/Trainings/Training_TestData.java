package studentcompany.sportgest.Trainings;

import android.content.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.Exercises.Exercise_TestData;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.Training;
import studentcompany.sportgest.domains.TrainingExercise;

public class Training_TestData {

    Exercise_DAO exercise_dao;
    Training_DAO training_dao;
    Team_DAO team_dao;
    Training_Exercise_DAO training_exercise_dao;

    public Training_TestData(Context context) {
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
            training_dao.insert(new Training(-1, "Training 1", lorem, 201512, 100, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 2", lorem, 201512, 99, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 3", lorem, 201512, 98, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 4", lorem, 201512, 97, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 5", lorem, 201512, 96, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 6", lorem, 201512, 95, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 7", lorem, 201512, 94, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 8", lorem, 201512, 93, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 9", lorem, 201512, 92, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 10", lorem, 201512, 91, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 11", lorem, 201512, 90, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 12", lorem, 201512, 89, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 13", lorem, 201512, 88, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 14", lorem, 201512, 87, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 15", lorem, 201512, 86, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 16", lorem, 201512, 85, team_dao.getById(1), 0));
            training_dao.insert(new Training(-1, "Training 17", lorem, 201512, 84, team_dao.getById(1), 0));
            if(exercise_dao.numberOfRows() == 0){
                new Exercise_TestData(context);
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
            System.err.println(Training_TestData.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(Training_TestData.class.getName()).log(Level.WARNING, null, ex);
            ex.printStackTrace();
        }
    }
}
