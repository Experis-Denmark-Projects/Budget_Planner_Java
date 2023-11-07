package experisdenmarkprojects.bugdet_planner.repositories;

import experisdenmarkprojects.bugdet_planner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.uid = :uid")
    User getUserByUid(String uid);
}
