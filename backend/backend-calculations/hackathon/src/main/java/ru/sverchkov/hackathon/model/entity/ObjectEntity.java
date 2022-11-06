package ru.sverchkov.hackathon.model.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Table(name = "objects", schema = "public")
public class ObjectEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String location;

    private Integer analog;

    private String age;

    private Boolean balcony;

    private Integer counter;

    private Integer currentFloor;

    private Integer floors;

    private Integer subway;

    private Boolean etalon;

    private Integer marketPrice;

    private Integer price;

    private String metroStation;

    private Integer rooms;

    private String walls;

    private Integer area;

    private String condition;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "request_entity_id")
    private RequestEntity requestEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ObjectEntity that = (ObjectEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
