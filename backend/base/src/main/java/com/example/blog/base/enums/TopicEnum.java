package com.example.blog.base.enums;

public enum TopicEnum {
    ALL("All"),
    PROGRAMMING_LANGUAGE("Programming Language"),
    MACHINE_LEARNING("Machine Learning"),
    CYBER_SECURITY("Cyber Security"),
    DISTRIBUTED_ALGORITHMS("Distributed Algorithms"),
    INFORMATION_SYSTEMS("Information Systems"),
    CLOUD_COMPUTING("Cloud Computing"),
    DATA_SCIENCE("Data Science");
    private String value;

    TopicEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
