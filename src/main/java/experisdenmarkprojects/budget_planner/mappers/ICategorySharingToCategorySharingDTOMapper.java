package experisdenmarkprojects.budget_planner.mappers;
import experisdenmarkprojects.budget_planner.models.CategorySharing;
import experisdenmarkprojects.budget_planner.models.dtos.CategorySharingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ICategorySharingToCategorySharingDTOMapper {

    @Mapping(target = "sharedWithUser", source = "sharedWithUser.id")
    @Mapping(target = "sharedCategory", source = "sharedCategory.id")
    CategorySharingDTO categorySharingToCategorySharingDTO(CategorySharing categorySharing);

    Collection<CategorySharingDTO> categorySharingToCategorySharingDTO(Collection<CategorySharing> categorySharings);
}
