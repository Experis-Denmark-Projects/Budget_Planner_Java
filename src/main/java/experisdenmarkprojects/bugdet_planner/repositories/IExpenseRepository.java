package experisdenmarkprojects.bugdet_planner.repositories;

import experisdenmarkprojects.bugdet_planner.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseRepository extends JpaRepository<Expense, Integer> {
}
