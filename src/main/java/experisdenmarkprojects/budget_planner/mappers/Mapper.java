package experisdenmarkprojects.budget_planner.mappers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Mapper implements IMapper {
    private final IUserToUserDTOMapper userToUserDTOMapper;
    private final ICategoryToCategoryDTOMapper categoryToCategoryDTOMapper;
    private final IExpenseToExpenseDTOMapper expenseToExpenseDTOMapper;
    private final ICategorySharingToCategorySharingDTOMapper categorySharingToCategorySharingDTOMapper;
    private final UserDTOToUserMapper userDTOToUserMapper;
    private final CategoryDTOToCategoryMapper categoryDTOToCategoryMapper;
    private final ExpenseDTOToExpenseMapper expenseDTOToExpenseMapper;
    private final CategorySharingDTOToCategorySharingMapper categorySharingDTOToCategorySharingMapper;

    @Autowired
    public Mapper(IUserToUserDTOMapper userToUserDTOMapper,
                  ICategoryToCategoryDTOMapper categoryToCategoryDTOMapper,
                  IExpenseToExpenseDTOMapper expenseToExpenseDTOMapper,
                  ICategorySharingToCategorySharingDTOMapper categorySharingToCategorySharingDTOMapper,
                  UserDTOToUserMapper userDTOToUserMapper,
                  CategoryDTOToCategoryMapper categoryDTOToCategoryMapper,
                  ExpenseDTOToExpenseMapper expenseDTOToExpenseMapper,
                  CategorySharingDTOToCategorySharingMapper categorySharingDTOToCategorySharingMapper){
        this.userToUserDTOMapper = userToUserDTOMapper;
        this.categoryToCategoryDTOMapper = categoryToCategoryDTOMapper;
        this.expenseToExpenseDTOMapper = expenseToExpenseDTOMapper;
        this.categorySharingToCategorySharingDTOMapper = categorySharingToCategorySharingDTOMapper;

        this.userDTOToUserMapper = userDTOToUserMapper;
        this.categoryDTOToCategoryMapper = categoryDTOToCategoryMapper;
        this.expenseDTOToExpenseMapper = expenseDTOToExpenseMapper;
        this.categorySharingDTOToCategorySharingMapper = categorySharingDTOToCategorySharingMapper;
    }
}
