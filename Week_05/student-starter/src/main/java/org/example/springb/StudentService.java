package org.example.springb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentService {

    private int id;

    private String name;

    public String say() {
        return "Here is " + name + "(" + id + ") Say";
    }

}
