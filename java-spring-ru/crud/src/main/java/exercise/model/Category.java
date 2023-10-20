package exercise.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "categories")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Category implements BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @CreatedDate
    private Date createdAt;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}
