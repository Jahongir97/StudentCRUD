package com.example.studentcrud.repository;

import com.example.studentcrud.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Long> {

    Optional<AttachmentContent> findByAttachmentId(Long attachmentId);

    @Query(value = "delete from attachment_content where attachment_id=:attachment_id",nativeQuery = true)
    void deleteByAttachment_Id(Long attachment_id);

    boolean existsByAttachment_Id(Long attachment_id);
}
