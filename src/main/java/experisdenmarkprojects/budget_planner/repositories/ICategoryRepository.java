package experisdenmarkprojects.budget_planner.repositories;

import experisdenmarkprojects.budget_planner.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
}
