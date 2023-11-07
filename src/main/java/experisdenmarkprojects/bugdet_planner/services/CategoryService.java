package experisdenmarkprojects.bugdet_planner.services;

import experisdenmarkprojects.bugdet_planner.exceptions.CategoryNotFoundException;
import experisdenmarkprojects.bugdet_planner.models.Category;
import experisdenmarkprojects.bugdet_planner.repositories.ICategoryRepository;
import experisdenmarkprojects.bugdet_planner.services.interfaces.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    public CategoryService(ICategoryRepository categoryRepository){
        this.categoryRepository =categoryRepository;
    }

    @Override
    public Category create(Category object) {
        return categoryRepository.save(object);
    }

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException(id)) ;
    }

    @Override
    public Collection<Category> findAll(){
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Category object){
        return categoryRepository.save(object);
    }

    @Override
    public void deleteById(Integer id){
        categoryRepository.deleteById(id);
    }

}
