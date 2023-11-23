package experisdenmarkprojects.budget_planner.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

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

    @ManyToMany(mappedBy = "categories")
    private Set<User> users;

    @OneToMany(mappedBy = "category")
    private Set<Expense> expenses;
}
