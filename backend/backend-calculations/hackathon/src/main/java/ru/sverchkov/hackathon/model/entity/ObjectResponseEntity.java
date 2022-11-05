package ru.sverchkov.hackathon.model.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@Table(name = "object_responses", schema = "public")
public class ObjectResponseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String location;

    private Integer rooms;

    private String age;

    private Integer floors;

    private String walls;

    private Integer currentFloor;

    private Integer area;

    private Boolean balcony;

    private Integer subway;

    private String condition;

    private Float marketPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ObjectResponseEntity that = (ObjectResponseEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
