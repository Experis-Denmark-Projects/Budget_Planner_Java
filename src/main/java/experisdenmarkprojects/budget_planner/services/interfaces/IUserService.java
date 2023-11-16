package experisdenmarkprojects.budget_planner.services.interfaces;

import experisdenmarkprojects.budget_planner.models.User;

public interface IUserService extends ICrudService<User, Integer>{

    User findUserByUid(String uid);
}
