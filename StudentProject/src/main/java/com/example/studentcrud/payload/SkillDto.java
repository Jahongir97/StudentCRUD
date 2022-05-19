package com.example.studentcrud.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillDto {

    @NotNull
    private String name;

    @Range(min = 1, max = 5, message = "Minimum value should be no less than 1 and Maximum no more than 5")
    private Integer value;


}
