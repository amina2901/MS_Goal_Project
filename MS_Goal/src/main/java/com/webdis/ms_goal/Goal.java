

package com.webdis.ms_goal;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Goal implements Serializable {
    private static final long serialVersionUID=6;
    @Id
    @GeneratedValue
    private int id;
    private String goalName;

    private LocalDateTime targetTime;
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = Status.NotStarted;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /*public Goal(String goalName, LocalDateTime targetTime, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.goalName = goalName;
        this.targetTime = targetTime;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }*/
    public Goal(String goalName, LocalDateTime targetTime) {
        this.goalName = goalName;
        this.targetTime = targetTime;
    }

}
