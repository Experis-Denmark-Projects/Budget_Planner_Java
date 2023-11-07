package experisdenmarkprojects.bugdet_planner.mappers;

import experisdenmarkprojects.bugdet_planner.models.Category;
import experisdenmarkprojects.bugdet_planner.models.Expense;
import experisdenmarkprojects.bugdet_planner.models.dtos.CategoryDTO;
import experisdenmarkprojects.bugdet_planner.services.interfaces.IExpenseService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
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
        return expenseIds.stream().map(expenseService::findById).collect(Collectors.toSet());
    }
}
