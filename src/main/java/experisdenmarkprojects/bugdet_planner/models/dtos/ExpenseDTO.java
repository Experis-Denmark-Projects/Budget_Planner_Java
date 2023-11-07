package experisdenmarkprojects.bugdet_planner.models.dtos;

import experisdenmarkprojects.bugdet_planner.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDTO {
    private int id;
    private String expenseName;
    private int amount;
    private int categoryId;
}
