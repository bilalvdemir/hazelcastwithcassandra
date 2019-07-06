package com.example.bilal.model.dto;

public class UserDtoBuilder {

    private String id;
    private String username;
    private String email;
    private String name;
    private String lastname;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public UserDtoBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserDtoBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public UserDtoBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDtoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserDtoBuilder setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserDto build() {
        return new UserDto(this.id, this.username, this.email, this.name, this.lastname);
    }

}
