package life.bareun.diary.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "tree_color")
public class TreeColor {

    @Id
    @Column(name = "id")
    private Integer id;


    @Column(name = "name")
    @Length(min = 1, max = 30)
    private String name;

    @JoinColumn(name = "grade_id")
    @ManyToOne
    private TreeColorGrade treeColorGrade;
}
