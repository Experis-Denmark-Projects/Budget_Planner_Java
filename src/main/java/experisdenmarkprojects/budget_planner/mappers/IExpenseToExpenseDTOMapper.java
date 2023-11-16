package experisdenmarkprojects.budget_planner.mappers;

import experisdenmarkprojects.budget_planner.models.Expense;
import experisdenmarkprojects.budget_planner.models.dtos.ExpenseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface IExpenseToExpenseDTOMapper {

    @Mapping(target = "category", source = "category.id")
    ExpenseDTO expenseToExpenseDTO(Expense expense);

    Collection<ExpenseDTO> expenseToExpenseDTO(Collection<Expense> expenses);
}
