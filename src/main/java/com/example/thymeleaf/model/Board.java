package com.example.thymeleaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=2,max=30,message = "제목은 2자이상 30자 이하입니다.")
    private String title;
    private String content;
//    @Size(max=10 , message = "이름은 10자 이하입니다.")
//    private String writer;
    private LocalDateTime create_date;
    @ManyToOne
    @JoinColumn(name = "user_id")//,referencedColumnName = "id" 생략해도 됨)
    @JsonIgnore
    private User user;



    //@OneToOne 일대일 연결 user-user_Detail
    //@OneToMany 일대다 연결 user-board
    //@ManyToMany 다대다 연결 user-role
    //@ManyToOne 다대일 연결 board-user
}
