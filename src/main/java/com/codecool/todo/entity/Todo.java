package com.codecool.todo.entity;

import com.codecool.todo.model.Status;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Todo {

    @Column
    private String title;

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    public boolean isCompleted() {
        return this.status == Status.COMPLETE;
    }

}
