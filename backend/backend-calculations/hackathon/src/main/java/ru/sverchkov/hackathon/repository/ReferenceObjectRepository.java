package ru.sverchkov.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverchkov.hackathon.model.entity.ReferenceObjectEntity;

@Repository
public interface ReferenceObjectRepository extends JpaRepository<ReferenceObjectEntity, Long> {
}
