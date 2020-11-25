package org.example.springb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private StudentService student;

    @GetMapping("say")
    public String say() {
        return student.say();
    }

}
