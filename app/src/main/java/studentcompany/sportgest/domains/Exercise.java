package studentcompany.sportgest.domains;

import java.util.Comparator;

public class Exercise extends DomainPojo implements Comparator<Exercise>{
    private long id;
    private String title;
    private String description;
    private int duration;
    private int deleted;

    public Exercise(long id, String title, String description, int duration, int deleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.deleted = deleted;
    }

    public Exercise(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public int compare(Exercise lhs, Exercise rhs) {
        return lhs.getTitle().compareTo(rhs.getTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (id != exercise.id) return false;
        if (duration != exercise.duration) return false;
        if (deleted != exercise.deleted) return false;
        if (title != null ? !title.equals(exercise.title) : exercise.title != null) return false;
        return !(description != null ? !description.equals(exercise.description) : exercise.description != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + deleted;
        return result;
    }
}
