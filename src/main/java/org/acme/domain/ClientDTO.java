package org.acme.domain;

import javax.validation.constraints.Min;

public class ClientDTO {

    private int line;

    private String name;

    private String hash;

    @Min(message="A idade minima deve ser 1", value=1)
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "line=" + line +
                ", name='" + name + '\'' +
                ", hash='" + hash + '\'' +
                ", age=" + age +
                '}';
    }
}
