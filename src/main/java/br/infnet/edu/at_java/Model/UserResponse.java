package br.infnet.edu.at_java.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

public class UserResponse {

    @Getter
    @JsonProperty("results")
    public List<User> results;
    private Info info;

    public void setResults(List<User> results) {
        this.results = results;
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        @JsonProperty("gender")
        private String gender;
        @JsonProperty("name")
        private Name name;
        @JsonProperty("location")
        private Location location;
        @JsonProperty("email")
        private String email;
        @JsonProperty("Gender")
        private  String Gender;

    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Name {
        @JsonProperty("title")
        private String title;
        @JsonProperty("first")
        private String first;
        @JsonProperty("last")
        private String last;

    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        @JsonProperty("street")
        private Street street;
        @JsonProperty("city")
        private String city;
        @JsonProperty("state")
        private String state;
        @JsonProperty("country")
        private String country;
        @JsonProperty("postcode")
        private String postcode;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info {
        @JsonProperty("seed")
        private String seed;
        @JsonProperty("results")
        private int results;
        @JsonProperty("page")
        private int page;
        @JsonProperty("version")
        private String version;


    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Street  {
        @JsonProperty("number")
        private int number;
        @JsonProperty("name")
        private String name;

    }
}
