package experisdenmarkprojects.bugdet_planner.mappers;

import experisdenmarkprojects.bugdet_planner.models.Category;
import experisdenmarkprojects.bugdet_planner.models.User;
import experisdenmarkprojects.bugdet_planner.models.dtos.UserDTO;
import experisdenmarkprojects.bugdet_planner.services.Service;
import experisdenmarkprojects.bugdet_planner.services.interfaces.IService;
import experisdenmarkprojects.bugdet_planner.services.interfaces.ICategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserDTOToUserMapper {

    @Autowired
    ICategoryService categoryService;

    @Mapping(target = "categories", qualifiedByName = "categoryIdsToCategories")
    public abstract User userDTOToUser(UserDTO userDTO);

    @Named("categoryIdsToCategories")
    public Set<Category> mapCategoryIdsToCategories(Collection<Integer> categoryIds){
        return categoryIds.stream().map(categoryService::findById).collect(Collectors.toSet());
    }
}
