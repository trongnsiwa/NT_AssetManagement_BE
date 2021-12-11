package com.nashtech.rootkies.model;

import com.nashtech.rootkies.enums.EAssignmentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "assignment_state")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EAssignmentState name;

}
