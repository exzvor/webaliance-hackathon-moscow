package ru.sverchkov.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverchkov.hackathon.model.entity.ObjectResponseEntity;

@Repository
public interface ObjectResponseRepository extends JpaRepository<ObjectResponseEntity, Long> {
}
