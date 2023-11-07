package experisdenmarkprojects.bugdet_planner.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private int id;

    @Column(name = "expense_name")
    private String expenseName;

    @Column()
    private int amount;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
}
