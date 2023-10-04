package dev.yangsijun.gauth.sample.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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

    protected GAuthUserEntity() {
    }

    public GAuthUserEntity(Long id, String profileUrl, String email, String name, String gender, Integer grade, Integer classNum, Integer num, String role) {
        this.id = id;
        this.profileUrl = profileUrl;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.grade = grade;
        this.classNum = classNum;
        this.num = num;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Integer getGrade() {
        return grade;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public Integer getNum() {
        return num;
    }

    public String getRole() {
        return role;
    }
}
