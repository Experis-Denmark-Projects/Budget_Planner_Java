package experisdenmarkprojects.bugdet_planner.mappers;

public interface IMapper {

    IUserToUserDTOMapper getUserToUserDTOMapper();
    ICategoryToCategoryDTOMapper getCategoryToCategoryDTOMapper();
    IExpenseToExpenseDTOMapper getExpenseToExpenseDTOMapper();

    UserDTOToUserMapper getUserDTOToUserMapper();
    CategoryDTOToCategoryMapper getCategoryDTOToCategoryMapper();
    ExpenseDTOToExpenseMapper getExpenseDTOToExpenseMapper();
}
