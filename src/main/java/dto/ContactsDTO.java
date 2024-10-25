package dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContactsDTO {
    private ContactDTOLombok [] contacts;
}
