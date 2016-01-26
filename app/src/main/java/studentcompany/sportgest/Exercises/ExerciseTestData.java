package studentcompany.sportgest.Exercises;

import android.content.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.Attributes.AttributeTestData;
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

    public ExerciseTestData(Context context) {
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
            exercise_dao.insert(new Exercise(-1, "Corrida", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Lances sem bola", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Ataques de ala", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Defesa cerrada", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Marcacao de cantos 1", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Marcacao de cantos 2", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Marcacao de cantos 3", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Marcacao ao homem", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Passes longos", lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Passes curtos",lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Marcacoes de penalties",lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Remates de fora de area",lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Defesa em desvantagem numerica 3 para 2",lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Ataque em profundidade",lorem, 10));
            exercise_dao.insert(new Exercise(-1, "Ataque em largura de campo",lorem, 10));
            if(attribute_dao.numberOfRows() == 0){
                new AttributeTestData(context);
            }
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(1), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(2), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(3), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(4), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(5), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(9), exercise_dao.getById(1)));
            attribute_exercise_dao.insert(new Pair<Attribute, Exercise>(attribute_dao.getById(10), exercise_dao.getById(1)));
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
            System.err.println(ExerciseTestData.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(ExerciseTestData.class.getName()).log(Level.WARNING, null, ex);
            ex.printStackTrace();
        }
    }
}
