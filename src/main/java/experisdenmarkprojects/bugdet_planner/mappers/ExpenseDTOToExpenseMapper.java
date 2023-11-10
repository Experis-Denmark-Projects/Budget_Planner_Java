package experisdenmarkprojects.bugdet_planner.mappers;

import experisdenmarkprojects.bugdet_planner.models.Expense;
import experisdenmarkprojects.bugdet_planner.models.dtos.ExpenseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ExpenseDTOToExpenseMapper {

    @Mapping(target = "category.id", source = "category")
    public abstract Expense expenseDTOToExpense(ExpenseDTO expenseDTO);
}
