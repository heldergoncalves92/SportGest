package studentcompany.sportgest.domains;

public class TrainingExercise extends DomainPojo{
    int id;
    Training training;
    Exercise exercise;
    int repetitions;

    public TrainingExercise(int id, Training training, Exercise exercise, int repetitions) {
        this.id = id;
        this.training = training;
        this.exercise = exercise;
        this.repetitions = repetitions;
    }

    @Override
    public int getId() {
        return id;
    }

    public Training getTraining() {
        return training;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    @Override
    public String toString() {
        return "TrainingExercise{" +
                "id=" + id +
                ", training=" + training +
                ", exercise=" + exercise +
                ", repetitions=" + repetitions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingExercise that = (TrainingExercise) o;

        if (id != that.id) return false;
        if (repetitions != that.repetitions) return false;
        if (training != null ? !training.equals(that.training) : that.training != null)
            return false;
        return !(exercise != null ? !exercise.equals(that.exercise) : that.exercise != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (training != null ? training.hashCode() : 0);
        result = 31 * result + (exercise != null ? exercise.hashCode() : 0);
        result = 31 * result + repetitions;
        return result;
    }
}
