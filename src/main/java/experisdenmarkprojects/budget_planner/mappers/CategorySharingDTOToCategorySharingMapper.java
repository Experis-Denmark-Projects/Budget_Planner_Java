package experisdenmarkprojects.budget_planner.mappers;

import experisdenmarkprojects.budget_planner.models.CategorySharing;
import experisdenmarkprojects.budget_planner.models.dtos.CategorySharingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CategorySharingDTOToCategorySharingMapper {
    @Mapping(target = "sharedWithUser.id", source = "sharedWithUser")
    @Mapping(target = "sharedCategory.id", source = "sharedCategory")
    public abstract CategorySharing categorySharingDTOToCategorySharing(CategorySharingDTO categorySharingDTO);
}
