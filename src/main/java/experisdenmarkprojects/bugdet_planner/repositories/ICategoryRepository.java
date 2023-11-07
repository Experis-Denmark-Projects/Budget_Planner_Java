package experisdenmarkprojects.bugdet_planner.repositories;

import experisdenmarkprojects.bugdet_planner.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
}
