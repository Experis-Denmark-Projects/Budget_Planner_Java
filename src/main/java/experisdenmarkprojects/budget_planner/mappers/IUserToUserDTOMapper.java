package experisdenmarkprojects.budget_planner.mappers;

import experisdenmarkprojects.budget_planner.models.Category;
import experisdenmarkprojects.budget_planner.models.User;
import experisdenmarkprojects.budget_planner.models.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IUserToUserDTOMapper {

    @Mapping(target = "categories", qualifiedByName = "categoriesToCategoryIds")
    UserDTO userToUserDTO(User user);

    Collection<UserDTO> userToUserDTO(Collection<User> users);

    @Named(value = "categoriesToCategoryIds")
    default Set<Integer> mapCategories(Set<Category> categories){
        if(categories == null) return null;

        return categories.stream().map(Category::getId).collect(Collectors.toSet());
    }
}
