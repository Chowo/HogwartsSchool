package ru.hogwarts.school.constants;

import ru.hogwarts.school.model.Student;

import java.util.List;

public class StudentServiceTestConstants {

    public static final Student HARRY = new Student(1L,"Harry", 11);

    public static final Student FRED = new Student(2L, "Fred", 13);

    public static final Student GEORGE = new Student(3L, "George", 13);


    public static final List<Student> STUDENTS = List.of(FRED, GEORGE);

}
