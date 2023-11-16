package experisdenmarkprojects.budget_planner.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ExpenseNotFoundException extends EntityNotFoundException {

    public ExpenseNotFoundException(int id){
        super(String.format("User does not exists with ID: %s", id));
    }
}
