package experisdenmarkprojects.budget_planner.services;

import experisdenmarkprojects.budget_planner.exceptions.UserNotFoundException;
import experisdenmarkprojects.budget_planner.models.User;
import experisdenmarkprojects.budget_planner.repositories.IUserRepository;
import experisdenmarkprojects.budget_planner.services.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository){

        this.userRepository = userRepository;
    }

    @Override
    public User create(User object) {
        return userRepository.save(object);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User object) {
        return userRepository.save(object);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByUid(String uid) {
        return userRepository.getUserByUid(uid);
    }
}
