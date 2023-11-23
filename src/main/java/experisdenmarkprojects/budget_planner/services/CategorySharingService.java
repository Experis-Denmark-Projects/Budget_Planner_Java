package experisdenmarkprojects.budget_planner.services;

import experisdenmarkprojects.budget_planner.exceptions.CategorySharingNotFoundException;
import experisdenmarkprojects.budget_planner.models.CategorySharing;
import experisdenmarkprojects.budget_planner.repositories.ICategorySharingRepository;
import experisdenmarkprojects.budget_planner.services.interfaces.ICategorySharingService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategorySharingService implements ICategorySharingService {

    private final ICategorySharingRepository categorySharingRepository;

    public CategorySharingService(ICategorySharingRepository categorySharingRepository){
        this.categorySharingRepository = categorySharingRepository;
    }

    @Override
    public CategorySharing create(CategorySharing object) {
        return categorySharingRepository.save(object);
    }

    @Override
    public CategorySharing findById(Integer i) {
        return categorySharingRepository.findById(i).orElseThrow(()-> new CategorySharingNotFoundException(i));
    }

    @Override
    public Collection<CategorySharing> findAll() {
        return categorySharingRepository.findAll();
    }

    @Override
    public CategorySharing update(CategorySharing object) {
        return categorySharingRepository.save(object);
    }

    @Override
    public void deleteById(Integer i) {
        categorySharingRepository.deleteById(i);
    }
}
