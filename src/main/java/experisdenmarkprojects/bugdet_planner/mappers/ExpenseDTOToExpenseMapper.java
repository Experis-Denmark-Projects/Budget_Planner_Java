package experisdenmarkprojects.bugdet_planner.mappers;

import experisdenmarkprojects.bugdet_planner.models.Expense;
import experisdenmarkprojects.bugdet_planner.models.dtos.ExpenseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ExpenseDTOToExpenseMapper {

    public abstract Expense expenseDTOToExpense(ExpenseDTO expenseDTO);
}
