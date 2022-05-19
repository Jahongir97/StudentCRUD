package com.example.studentcrud.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NotNull
public class StudentDto {

    @NotNull
    private String firstName;

    private String lastName;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    private Long attachmentId;

    private double longitude;

    private double latitude;

    private Set<Long> skillIds;



}
