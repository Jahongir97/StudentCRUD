package com.example.studentcrud.repository;

import com.example.studentcrud.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Query(value = "delete from attachment where id=:id", nativeQuery = true)
    void deleteAttachmentById(Long id);
}
