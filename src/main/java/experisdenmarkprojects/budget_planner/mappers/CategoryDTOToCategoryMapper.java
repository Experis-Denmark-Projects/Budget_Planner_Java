package experisdenmarkprojects.budget_planner.mappers;

import experisdenmarkprojects.budget_planner.models.Category;
import experisdenmarkprojects.budget_planner.models.Expense;
import experisdenmarkprojects.budget_planner.models.User;
import experisdenmarkprojects.budget_planner.models.dtos.CategoryDTO;
import experisdenmarkprojects.budget_planner.services.interfaces.IExpenseService;
import experisdenmarkprojects.budget_planner.services.interfaces.IUserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CategoryDTOToCategoryMapper {

    @Autowired
    IExpenseService expenseService;

    @Mapping(target = "user.id", source = "user")
    @Mapping(target = "expenses", qualifiedByName = "expenseIdsToExpenses")
    public abstract Category categoryDTOToCategory(CategoryDTO categoryDTO);

    @Named("expenseIdsToExpenses")
    public Set<Expense> mapExpenseIdsToExpenses(Collection<Integer> expenseIds){
        if(expenseIds == null) return null;
        return expenseIds.stream().map(expenseService::findById).collect(Collectors.toSet());
    }
}
