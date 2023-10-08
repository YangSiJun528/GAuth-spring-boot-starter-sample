package dev.yangsijun.gauth.sample.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class GAuthUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileUrl;

    private String email;

    private String name;

    private String gender;

    private Integer grade;

    private Integer classNum;

    private Integer num;

    private String role;
}
