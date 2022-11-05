package ru.sverchkov.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sverchkov.hackathon.model.entity.ObjectEntity;

@Repository
public interface ObjectRepository extends JpaRepository<ObjectEntity,Long> {
}
