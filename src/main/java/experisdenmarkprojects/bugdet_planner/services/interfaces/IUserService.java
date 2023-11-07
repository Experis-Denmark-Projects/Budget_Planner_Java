package experisdenmarkprojects.bugdet_planner.services.interfaces;

import experisdenmarkprojects.bugdet_planner.models.User;

public interface IUserService extends ICrudService<User, Integer>{

    User findUserByUid(String uid);
}
