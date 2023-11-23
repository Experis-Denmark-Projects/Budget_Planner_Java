package experisdenmarkprojects.budget_planner.mappers;

public interface IMapper {

    IUserToUserDTOMapper getUserToUserDTOMapper();
    ICategoryToCategoryDTOMapper getCategoryToCategoryDTOMapper();
    IExpenseToExpenseDTOMapper getExpenseToExpenseDTOMapper();
    ICategorySharingToCategorySharingDTOMapper getCategorySharingToCategorySharingDTOMapper();

    UserDTOToUserMapper getUserDTOToUserMapper();
    CategoryDTOToCategoryMapper getCategoryDTOToCategoryMapper();
    ExpenseDTOToExpenseMapper getExpenseDTOToExpenseMapper();
    CategorySharingDTOToCategorySharingMapper getCategorySharingDTOToCategorySharingMapper();
}
