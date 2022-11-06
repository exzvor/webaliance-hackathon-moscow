package ru.sverchkov.hackathon.model.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "requests", schema = "public")
public class RequestEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(
            mappedBy = "requestEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true

    )
    @ToString.Exclude
    private List<ObjectEntity> objects;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RequestEntity that = (RequestEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
