package experisdenmarkprojects.budget_planner.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CategorySharingNotFoundException extends EntityNotFoundException {

    public CategorySharingNotFoundException(int id){
        super(String.format("Category does not exists with ID: %s", id));
    }
}
