package by.training.pharmacy.domain.user;

/**
 * Created by vladislav on 14.06.16.
 */
public class UserDescription {
    private String specialization;
    private String description;

    @Override
    public String toString() {
        return "UserDescription{" +
                "specialization='" + specialization + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDescription that = (UserDescription) o;

        if (specialization != null ? !specialization.equals(that.specialization) : that.specialization != null)
            return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = specialization != null ? specialization.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public void setSpecialization(String specialization) {

        this.specialization = specialization;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }

    public String getSpecialization() {

        return specialization;
    }
}
