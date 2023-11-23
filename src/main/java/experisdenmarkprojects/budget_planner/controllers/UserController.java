package experisdenmarkprojects.budget_planner.controllers;

import experisdenmarkprojects.budget_planner.mappers.IMapper;
import experisdenmarkprojects.budget_planner.models.Category;
import experisdenmarkprojects.budget_planner.models.Expense;
import experisdenmarkprojects.budget_planner.models.User;
import experisdenmarkprojects.budget_planner.models.dtos.CategoryDTO;
import experisdenmarkprojects.budget_planner.models.dtos.ExpenseDTO;
import experisdenmarkprojects.budget_planner.models.dtos.UserDTO;
import experisdenmarkprojects.budget_planner.services.interfaces.IService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        if(user != null){
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
            Collection<Category> categories = service.getCategoryService().findAll();
            if(categories != null){
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

    @Transactional
    @PostMapping("/private/user/category")
    public ResponseEntity<CategoryDTO> createCategory(@AuthenticationPrincipal Jwt jwt, @RequestBody CategoryDTO categoryDTO){

        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
            Category category = mapper.getCategoryDTOToCategoryMapper().categoryDTOToCategory(categoryDTO);
            //category.setUser(user);
            category.getUsers().add(user);
            Category newCategory = service.getCategoryService().create(category);
            user.getCategories().add(newCategory);
            CategoryDTO newCategoryDTO = mapper.getCategoryToCategoryDTOMapper().categoryToCategoryDTO(newCategory);

            return ResponseEntity.ok(newCategoryDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/private/user/category")
    public ResponseEntity<CategoryDTO> updateCategory(@AuthenticationPrincipal Jwt jwt, @RequestBody CategoryDTO categoryDTO){
        String uid = jwt.getSubject().split("\\|")[1];
        User user = service.getUserService().findUserByUid(uid);
        if(user != null){
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
        if(user != null){
            service.getExpenseService().deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }
}