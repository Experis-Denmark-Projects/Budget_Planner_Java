package experisdenmarkprojects.budget_planner.repositories;

import experisdenmarkprojects.budget_planner.models.CategorySharing;
import experisdenmarkprojects.budget_planner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategorySharingRepository extends JpaRepository<CategorySharing, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByEmail(String email);
}
