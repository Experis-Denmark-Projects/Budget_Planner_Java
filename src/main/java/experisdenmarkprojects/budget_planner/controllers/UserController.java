package experisdenmarkprojects.budget_planner.controllers;

import experisdenmarkprojects.budget_planner.mappers.IMapper;
import experisdenmarkprojects.budget_planner.models.Category;
import experisdenmarkprojects.budget_planner.models.CategorySharing;
import experisdenmarkprojects.budget_planner.models.Expense;
import experisdenmarkprojects.budget_planner.models.User;
import experisdenmarkprojects.budget_planner.models.dtos.CategoryDTO;
import experisdenmarkprojects.budget_planner.models.dtos.CategorySharingDTO;
import experisdenmarkprojects.budget_planner.models.dtos.ExpenseDTO;
import experisdenmarkprojects.budget_planner.models.dtos.UserDTO;
import experisdenmarkprojects.budget_planner.services.interfaces.IService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static com.nimbusds.jose.shaded.gson.internal.bind.util.ISO8601Utils.format;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(value = {"http://localhost:4200", "https://budget-planner-app.azurewebsites.net"})
public class UserController {
    private final IService service;
    private final IMapper mapper;

    public UserController(IService service, IMapper modelToDTOMapper){
        this.service = service;
        this.mapper = modelToDTOMapper;
    }

    /***** User Controller Mappings ****/
    @GetMapping("/private/user")
    public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal Jwt jwt){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            return ResponseEntity.ok(mapper.getUserToUserDTOMapper().userToUserDTO(user));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/private/user")
    public ResponseEntity<UserDTO> createUser(@AuthenticationPrincipal Jwt jwt){
        String uid = jwt.getSubject().split("\\|")[1];
        String username = jwt.getClaim("name");
        String email = jwt.getClaim("email");

        if(uid == null || username == null || email == null){
            return ResponseEntity.badRequest().build();
        }

        // Return if user already exists in the database
        if(service.getUserService().findUserByUid(uid) != null){
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setUid(uid);
        user.setUsername(username);
        user.setEmail(email);
        user.setCategories(new HashSet<>());
        UserDTO userDTO = mapper.getUserToUserDTOMapper().userToUserDTO(service.getUserService().create(user));
        return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/private/user")
    public ResponseEntity<UserDTO> updateUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UserDTO userDTO){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null && userDTO.getId() == user.getId()){
            user = service.getUserService().update(mapper.getUserDTOToUserMapper().userDTOToUser(userDTO));
            return user != null ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/private/user")
    public ResponseEntity<UserDTO> deleteUser(@AuthenticationPrincipal Jwt jwt){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            service.getUserService().deleteById(user.getId());
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
    }

    /***** Category Controller Mappings ****/
    @GetMapping("/private/user/category")
    public ResponseEntity<Collection<CategoryDTO>> getCategories(@AuthenticationPrincipal Jwt jwt){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            Collection<Category> categories = user.getCategories();
            if(categories != null){
                List<Category> sortedCategories = new ArrayList<>(categories);

                return ResponseEntity.ok(mapper.getCategoryToCategoryDTOMapper().categoryToCategoryDTO(categories));
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/private/user/category/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@AuthenticationPrincipal Jwt jwt, @PathVariable int id){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            Category category = service.getCategoryService().findById(id);
            if(category != null){
                return ResponseEntity.ok(mapper.getCategoryToCategoryDTOMapper().categoryToCategoryDTO(category));
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/private/user/category")
    public ResponseEntity<CategoryDTO> createCategory(@AuthenticationPrincipal Jwt jwt, @RequestBody CategoryDTO categoryDTO){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            Category category = mapper.getCategoryDTOToCategoryMapper().categoryDTOToCategory(categoryDTO);
            category.setUser(user);
            Category newCategory = service.getCategoryService().create(category);
            CategoryDTO newCategoryDTO = mapper.getCategoryToCategoryDTOMapper().categoryToCategoryDTO(newCategory);

            return ResponseEntity.ok(newCategoryDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/private/user/category")
    public ResponseEntity<CategoryDTO> updateCategory(@AuthenticationPrincipal Jwt jwt, @RequestBody CategoryDTO categoryDTO){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null && user.getId() == categoryDTO.getUser()){
            Category category = mapper.getCategoryDTOToCategoryMapper().categoryDTOToCategory(categoryDTO);
            if(category != null){
                service.getCategoryService().update(category);
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/private/user/category/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@AuthenticationPrincipal Jwt jwt, @PathVariable int id){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){

            // Delete Expenses before deleting the category
            Category category = service.getCategoryService().findById(id);
            Collection<Expense> expenses = category.getExpenses();
            expenses.forEach(expense -> service.getExpenseService().deleteById(expense.getId()));
            Collection<CategorySharing> categorySharings = category.getSharedCatgories();
            categorySharings.forEach(categorySharing -> service.getCategorySharingService().deleteById(categorySharing.getId()));
            user.getCategories().remove(category);
            service.getCategoryService().deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }

    /***** Expense Controller Mappings ****/
    @GetMapping("/private/user/category/{id}/expense")
    public ResponseEntity<Collection<ExpenseDTO>> getExpenses(@AuthenticationPrincipal Jwt jwt, @PathVariable int id){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            Category category = service.getCategoryService().findById(id);
            if(category != null){
                Collection<ExpenseDTO> expenseDTOS = mapper.getExpenseToExpenseDTOMapper().expenseToExpenseDTO(category.getExpenses());
                if(expenseDTOS != null){
                    return ResponseEntity.ok(expenseDTOS);
                }
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/private/user/expense")
    public ResponseEntity<Collection<ExpenseDTO>> getExpenses(@AuthenticationPrincipal Jwt jwt){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            Collection<Category> categories = user.getCategories();

            if(categories != null){
                Collection<Expense> expenses = new HashSet<>();
                categories.forEach(category ->  expenses.addAll( category.getExpenses()));
                List<Expense> sortedExpenses = new ArrayList<>(expenses);
                sortedExpenses.sort(Comparator.comparing(Expense::getId));
                return ResponseEntity.ok(mapper.getExpenseToExpenseDTOMapper().expenseToExpenseDTO(sortedExpenses));
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/private/user/category/{id}/expense")
    public ResponseEntity<ExpenseDTO> createExpense(@AuthenticationPrincipal Jwt jwt, @PathVariable int id, @RequestBody ExpenseDTO expenseDTO){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){

            Category category = service.getCategoryService().findById(id);
            boolean isUser = user.getCategories().stream().anyMatch(c -> c.getId() == id);

            if(category != null && isUser){
                Expense expense = mapper.getExpenseDTOToExpenseMapper().expenseDTOToExpense(expenseDTO);
                expense = service.getExpenseService().create(expense);
                if(expense != null){
                    expense.setCategory(category);
                    expenseDTO = mapper.getExpenseToExpenseDTOMapper().expenseToExpenseDTO(service.getExpenseService().create(expense));
                    return expenseDTO != null ? ResponseEntity.ok(expenseDTO) : ResponseEntity.badRequest().build();
                }
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/private/user/category/{id}/expense")
    public ResponseEntity<ExpenseDTO> updateExpense(@AuthenticationPrincipal Jwt jwt, @PathVariable int id, @RequestBody ExpenseDTO expenseDTO){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            Category category = service.getCategoryService().findById(id);
            boolean isUser = user.getCategories().stream().anyMatch(c -> c.getId() == id);
            if(category != null && isUser){
                Expense expense = mapper.getExpenseDTOToExpenseMapper().expenseDTOToExpense(expenseDTO);
                if(expense != null && service.getExpenseService().findById(expense.getId()) != null){
                    service.getExpenseService().update(expense);
                    return ResponseEntity.noContent().build();
                }
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/private/user/category/expense/{id}")
    public ResponseEntity<ExpenseDTO> deleteExpense(@AuthenticationPrincipal Jwt jwt, @PathVariable int id){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null ){
            service.getExpenseService().deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }

    /* Category Sharing Controller Mappings */
    @GetMapping("/private/user/category-sharing")
    public ResponseEntity<Collection<CategorySharingDTO>> getCategorySharing(@AuthenticationPrincipal Jwt jwt){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null ){
            Collection<CategorySharing> categorySharings = user.getSharedCategories();
            Collection<CategorySharingDTO> categorySharingDTOS = mapper.getCategorySharingToCategorySharingDTOMapper().categorySharingToCategorySharingDTO(categorySharings);
            return ResponseEntity.ok(categorySharingDTOS);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/private/user/category-sharing")
    public ResponseEntity<CategorySharingDTO> createCategorySharing(@AuthenticationPrincipal Jwt jwt, @RequestBody CategorySharingDTO categorySharingDTO){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            User shareWithUser = service.getCategorySharingService().getUserByEmail(categorySharingDTO.getSharedUserEmail());
            CategorySharing categorySharing = mapper.getCategorySharingDTOToCategorySharingMapper().categorySharingDTOToCategorySharing(categorySharingDTO);
            if(categorySharing != null && shareWithUser != null){
                Set<CategorySharing> categorySharings = new HashSet<>(service.getCategorySharingService().findAll());

                boolean exists = categorySharings.contains(categorySharing); // Check if category already exists

                if(!exists){ // Only create new category sharing if not it does not exist
                    return ResponseEntity.ok(mapper.getCategorySharingToCategorySharingDTOMapper().categorySharingToCategorySharingDTO(service.getCategorySharingService().create(categorySharing)));
                }
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/private/user/category-sharing")
    public ResponseEntity<CategorySharingDTO> updateCategorySharing(@AuthenticationPrincipal Jwt jwt, @RequestBody CategorySharingDTO categorySharingDTO){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            CategorySharing categorySharing = mapper.getCategorySharingDTOToCategorySharingMapper().categorySharingDTOToCategorySharing(categorySharingDTO);
            if(categorySharing != null && (user.getId() == categorySharingDTO.getSharedWithUser() || user.getId() == categorySharing.getSharedCategory().getUser().getId())){
                service.getCategorySharingService().update(categorySharing);
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/private/user/category-sharing/{id}")
    public ResponseEntity<CategorySharingDTO> deleteCategorySharing(@AuthenticationPrincipal Jwt jwt, @PathVariable int id){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);

        if(user != null){

            CategorySharing categorySharing = service.getCategorySharingService().findById(id);
            boolean belongsToUser = categorySharing.getSharedCategory().getUser().getId() == user.getId();

            if(belongsToUser){ // Category must belong to authenticated user
                service.getCategorySharingService().deleteById(id);
                return ResponseEntity.noContent().build();
            }

        }

        return ResponseEntity.badRequest().build();
    }
}