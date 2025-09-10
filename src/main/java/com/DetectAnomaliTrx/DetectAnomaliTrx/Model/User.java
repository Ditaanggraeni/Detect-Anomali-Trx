package com.DetectAnomaliTrx.DetectAnomaliTrx.Model;

import jakarta.persistence.*;

@Entity
    @Table(name = "users")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String email;
        private String domicile;


        public User() {}

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDomicile() {
            return domicile;
        }

        public void setDomicile(String domicile) {
            this.domicile = domicile;
        }
}
