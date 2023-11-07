package experisdenmarkprojects.bugdet_planner.services;

import experisdenmarkprojects.bugdet_planner.exceptions.ExpenseNotFoundException;
import experisdenmarkprojects.bugdet_planner.models.Expense;
import experisdenmarkprojects.bugdet_planner.repositories.IExpenseRepository;
import experisdenmarkprojects.bugdet_planner.services.interfaces.IExpenseService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ExpenseService implements IExpenseService {

    private final IExpenseRepository expenseRepository;

    public ExpenseService(IExpenseRepository expenseRepository){

        this.expenseRepository = expenseRepository;
    }
    
    @Override
    public Expense create(Expense object) {
        return expenseRepository.save(object);
    }

    @Override
    public Expense findById(Integer id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException(id));
    }

    @Override
    public Collection<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense update(Expense object) {
        return expenseRepository.save(object);
    }

    @Override
    public void deleteById(Integer id) {
        expenseRepository.deleteById(id);
    }
}
