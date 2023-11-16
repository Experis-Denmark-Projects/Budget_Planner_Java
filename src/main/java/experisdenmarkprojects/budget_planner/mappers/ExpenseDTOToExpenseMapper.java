package experisdenmarkprojects.budget_planner.mappers;

import experisdenmarkprojects.budget_planner.models.Expense;
import experisdenmarkprojects.budget_planner.models.dtos.ExpenseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ExpenseDTOToExpenseMapper {

    @Mapping(target = "category.id", source = "category")
    public abstract Expense expenseDTOToExpense(ExpenseDTO expenseDTO);
}
