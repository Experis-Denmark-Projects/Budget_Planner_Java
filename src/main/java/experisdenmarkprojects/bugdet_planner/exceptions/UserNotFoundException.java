package experisdenmarkprojects.bugdet_planner.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(int id){
        super(String.format("User does not exists with ID: %s", id));
    }
}
