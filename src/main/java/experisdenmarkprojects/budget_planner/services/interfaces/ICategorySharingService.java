package experisdenmarkprojects.budget_planner.services.interfaces;

import experisdenmarkprojects.budget_planner.models.CategorySharing;
import experisdenmarkprojects.budget_planner.models.User;

public interface ICategorySharingService extends ICrudService<CategorySharing, Integer>{
    User getUserByEmail(String email);
}
