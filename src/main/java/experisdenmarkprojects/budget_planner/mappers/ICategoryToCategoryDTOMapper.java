package experisdenmarkprojects.budget_planner.mappers;

import experisdenmarkprojects.budget_planner.models.Category;
import experisdenmarkprojects.budget_planner.models.Expense;
import experisdenmarkprojects.budget_planner.models.User;
import experisdenmarkprojects.budget_planner.models.dtos.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ICategoryToCategoryDTOMapper {

    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "expenses", qualifiedByName = "expensesToExpenseIds")
    CategoryDTO categoryToCategoryDTO(Category category);

    Collection<CategoryDTO> categoryToCategoryDTO(Collection<Category> categories);

    @Named(value = "expensesToExpenseIds")
    default Set<Integer> mapExpenses(Set<Expense> expenses){
        if(expenses == null ) return null;

        return expenses.stream().map(Expense::getId).collect(Collectors.toSet());
    }
}
