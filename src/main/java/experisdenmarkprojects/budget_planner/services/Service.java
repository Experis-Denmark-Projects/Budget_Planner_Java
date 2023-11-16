package experisdenmarkprojects.budget_planner.services;

import experisdenmarkprojects.budget_planner.services.interfaces.ICategoryService;
import experisdenmarkprojects.budget_planner.services.interfaces.IExpenseService;
import experisdenmarkprojects.budget_planner.services.interfaces.IService;
import experisdenmarkprojects.budget_planner.services.interfaces.IUserService;
import lombok.Getter;

@org.springframework.stereotype.Service
@Getter
public class Service implements IService {
    //uses facade pattern
    private final IUserService userService;
    private final ICategoryService categoryService;
    private final IExpenseService expenseService;

    public Service(IUserService userService,
                   ICategoryService categoryService,
                   IExpenseService expenseService){

        this.userService = userService;
        this.categoryService = categoryService;
        this.expenseService = expenseService;
    }
}