package com.example.studentcrud.service;

import com.example.studentcrud.entity.Attachment;
import com.example.studentcrud.entity.AttachmentContent;
import com.example.studentcrud.payload.ApiResponse;
import com.example.studentcrud.repository.AttachmentContentRepository;
import com.example.studentcrud.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    public ApiResponse saveNewImage(MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            long fileSize = file.getSize();
            String contentType = file.getContentType();

            Attachment attachment = new Attachment();
            attachment.setFileOriginalName(originalFilename);
            attachment.setSize(fileSize);
            attachment.setContentType(contentType);

            Attachment savedAttachment = attachmentRepository.save(attachment);


            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setMainContent(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);

            attachmentContentRepository.save(attachmentContent);

            return new ApiResponse("File saved, file Id: " + savedAttachment.getId(), true);
        }
        return new ApiResponse("File not saved", false);
    }

    public void downloadFile(Long id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(id);
            if (optionalAttachmentContent.isPresent()) {
                AttachmentContent attachmentContent = optionalAttachmentContent.get();
                response.setHeader("Content-Disposition", "attachment: filename=\"" + attachment.getFileOriginalName() + "\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());

            }
        }
    }

    public ApiResponse deleteFileById(Long id) {
        boolean exists = attachmentContentRepository.existsByAttachment_Id(id);
        if (exists) {
            attachmentContentRepository.deleteByAttachment_Id(id);
        }
        attachmentRepository.deleteAttachmentById(id);
        boolean existsById = attachmentRepository.existsById(id);
        if (existsById) {
            return new ApiResponse("Cannot delete", false);
        }
        return new ApiResponse("Successfully deleted", true);

    }
}
