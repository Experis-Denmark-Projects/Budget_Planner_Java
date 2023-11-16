package experisdenmarkprojects.budget_planner.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {

    public CategoryNotFoundException(int id){
        super(String.format("Category does not exists with ID: %s", id));
    }
}
