package experisdenmarkprojects.bugdet_planner.models.dtos;

import lombok.Setter;
import lombok.Getter;
import java.util.Set;

@Getter
@Setter
public class CategoryDTO {
    private int id;
    private String name;
    private int user;
    private Set<Integer> expenses;
}
