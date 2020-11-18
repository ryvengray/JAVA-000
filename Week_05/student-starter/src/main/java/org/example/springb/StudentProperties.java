package org.example.springb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@ConfigurationProperties(prefix = "student")
public class StudentProperties {

    private int id;
    private String name;

    public void init(){
        System.out.println("hello...........");
    }

    public StudentProperties create(){
        return new StudentProperties(101,"KK101");
    }
}
