package studentcompany.sportgest.Exercises;

import android.content.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.EventCategories.DisplayEventCategoryActivity;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
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
        try {
            exercise_dao.insert(new Exercise(-1, "Title1", "Description1", 10));
            exercise_dao.insert(new Exercise(-1, "Title2", "Description2", 10));
            exercise_dao.insert(new Exercise(-1, "Title3", "Description3", 10));
            exercise_dao.insert(new Exercise(-1, "Title4", "Description4", 10));
            exercise_dao.insert(new Exercise(-1, "Title5", "Description5", 10));
            exercise_dao.insert(new Exercise(-1, "Title6", "Description6", 10));
            exercise_dao.insert(new Exercise(-1, "Title7", "Description7", 10));
            exercise_dao.insert(new Exercise(-1, "Title8", "Description8", 10));
            exercise_dao.insert(new Exercise(-1, "Title9", "Description9", 10));
            exercise_dao.insert(new Exercise(-1, "Title10", "Description10", 10));
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
        }catch (GenericDAOException ex){
            System.err.println(DisplayEventCategoryActivity.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(DisplayEventCategoryActivity.class.getName()).log(Level.WARNING, null, ex);
            ex.printStackTrace();
        }
    }
}

