package experisdenmarkprojects.budget_planner.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @Column(name = "category_name")
    private String name;

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "category")
    private Set<Expense> expenses;

    @OneToMany(mappedBy = "category")
    private List<CategorySharing> sharings;
}
