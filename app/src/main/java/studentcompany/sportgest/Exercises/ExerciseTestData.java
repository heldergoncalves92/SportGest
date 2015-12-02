package studentcompany.sportgest.Exercises;

import android.content.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.EventCategories.DisplayEventCategoryActivity;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;

public class ExerciseTestData {
    Exercise_DAO exercise_dao;
    Attribute_DAO attribute_dao;
    Attribute_Exercise_DAO attribute_exercise_dao;

    ExerciseTestData(Context context) {
        exercise_dao = new Exercise_DAO(context);
        attribute_dao = new Attribute_DAO(context);
        attribute_exercise_dao = new Attribute_Exercise_DAO(context);
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
            exercise_dao.insert(new Exercise(-1, "Title1", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Title2", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Title3", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Title4", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Title5", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Title6", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Title7", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Title8", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Title9", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Title10",lorem, 10));
            attribute_dao.insert(new Attribute(-1, "Type1", "Name1", 0));
            attribute_dao.insert(new Attribute(-1, "Type2", "Name2", 0));
            attribute_dao.insert(new Attribute(-1, "Type3", "Name3", 0));
            attribute_dao.insert(new Attribute(-1, "Type4", "Name4", 0));
            attribute_dao.insert(new Attribute(-1, "Type5", "Name5", 0));
            attribute_dao.insert(new Attribute(-1, "Type6", "Name6", 0));
            attribute_dao.insert(new Attribute(-1, "Type7", "Name7", 0));
            attribute_dao.insert(new Attribute(-1, "Type8", "Name8", 0));
            attribute_dao.insert(new Attribute(-1, "Type9", "Name9", 0));
            attribute_dao.insert(new Attribute(-1, "Type10", "Name10", 0));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(1), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(2), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(3), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(4), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(5), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(1), exercise_dao.getById(2)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(8), exercise_dao.getById(3)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(1), exercise_dao.getById(4)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(2), exercise_dao.getById(4)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(3), exercise_dao.getById(4)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(1), exercise_dao.getById(5)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(2), exercise_dao.getById(5)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(1), exercise_dao.getById(6)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(5), exercise_dao.getById(6)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(7), exercise_dao.getById(7)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(4), exercise_dao.getById(8)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(5), exercise_dao.getById(9)));
        }catch (GenericDAOException ex){
            System.err.println(DisplayEventCategoryActivity.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(DisplayEventCategoryActivity.class.getName()).log(Level.WARNING, null, ex);
            ex.printStackTrace();
        }
    }
}

