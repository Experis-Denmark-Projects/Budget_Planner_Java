package experisdenmarkprojects.budget_planner.services;

import experisdenmarkprojects.budget_planner.services.interfaces.*;
import lombok.Getter;

@org.springframework.stereotype.Service
@Getter
public class Service implements IService {
    //uses facade pattern
    private final IUserService userService;
    private final ICategoryService categoryService;
    private final IExpenseService expenseService;
    private final ICategorySharingService categorySharingService;

    public Service(IUserService userService,
                   ICategoryService categoryService,
                   IExpenseService expenseService,
                   ICategorySharingService categorySharingService){

        this.userService = userService;
        this.categoryService = categoryService;
        this.expenseService = expenseService;
        this.categorySharingService = categorySharingService;
    }
}
