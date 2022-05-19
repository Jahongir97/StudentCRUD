package com.example.studentcrud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class AttachmentContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  byte[] mainContent;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private  Attachment attachment;

}
