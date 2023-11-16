package experisdenmarkprojects.budget_planner.services.interfaces;

import java.util.Collection;

public interface ICrudService<T, ID>{

    T create(T object);

    T findById(ID id);

    Collection<T> findAll();

    T update(T object);

    void deleteById(ID id);
}
