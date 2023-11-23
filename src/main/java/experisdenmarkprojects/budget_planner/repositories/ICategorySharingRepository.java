package experisdenmarkprojects.budget_planner.repositories;

import experisdenmarkprojects.budget_planner.models.Category;
import experisdenmarkprojects.budget_planner.models.CategorySharing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategorySharingRepository extends JpaRepository<CategorySharing, Integer> {
}
