package experisdenmarkprojects.budget_planner.models.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import experisdenmarkprojects.budget_planner.serialization.CustomDateDeserializer;
import experisdenmarkprojects.budget_planner.serialization.CustomDateSerializer;
import lombok.Setter;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class CategoryDTO {
    private int id;
    private String name;
    private Integer user;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date created;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date lastModified;
    private Set<Integer> expenses;
    private Set<Integer> sharedCategories;
}
