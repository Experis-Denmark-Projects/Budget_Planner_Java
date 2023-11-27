package experisdenmarkprojects.budget_planner.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import experisdenmarkprojects.budget_planner.serialization.CustomDateDeserializer;
import experisdenmarkprojects.budget_planner.serialization.CustomDateSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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

    @Column
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date created;

    @Column
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date lastModified;

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "category")
    private Set<Expense> expenses;

    @OneToMany(mappedBy = "sharedCategory")
    private Set<CategorySharing> sharedCatgories;
}
