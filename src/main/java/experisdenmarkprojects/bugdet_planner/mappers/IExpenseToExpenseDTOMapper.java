package experisdenmarkprojects.bugdet_planner.mappers;

import experisdenmarkprojects.bugdet_planner.models.Expense;
import experisdenmarkprojects.bugdet_planner.models.dtos.ExpenseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface IExpenseToExpenseDTOMapper {

    @Mapping(target = "category", source = "category.id")
    ExpenseDTO expenseToExpenseDTO(Expense expense);

    Collection<ExpenseDTO> expenseToExpenseDTO(Collection<Expense> expenses);
}
