package experisdenmarkprojects.budget_planner.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CategorySharing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sharing_id")
    private int id;

    @Column
    private boolean accepted;

    @ManyToOne()
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Category sharedCategory;

    @ManyToOne()
    @JoinColumn(name = "shared_with_user_id", referencedColumnName = "user_id", nullable = false)
    private User sharedWithUser;
}
