package experisdenmarkprojects.budget_planner.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private int id;
    private String uid;
    private String username;
    private String email;
    private int totalBudget;
    private Set<Integer> categories;
}
