package experisdenmarkprojects.budget_planner.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDTO {
    private int id;
    private String name;
    private float amount;
    private int category;
}
