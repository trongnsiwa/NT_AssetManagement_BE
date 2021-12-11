package com.nashtech.rootkies.dto.category;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryDTO {

  @NotBlank
  private String name;

  @NotBlank
  private String prefix;

}
