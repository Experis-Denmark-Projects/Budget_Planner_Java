package experisdenmarkprojects.budget_planner.repositories;

import experisdenmarkprojects.budget_planner.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseRepository extends JpaRepository<Expense, Integer> {
}
