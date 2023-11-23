package experisdenmarkprojects.budget_planner.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySharingDTO {
    private int id;
    private boolean accepted;
    private Integer sharedCategory;
    private Integer sharedWithUser;
}
