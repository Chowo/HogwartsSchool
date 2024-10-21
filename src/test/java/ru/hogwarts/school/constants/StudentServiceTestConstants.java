package ru.hogwarts.school.constants;

import ru.hogwarts.school.model.Student;

import java.util.List;

public class StudentServiceTestConstants {

    public static final Student HARRY = new Student(1L, "Harry", 11);

    public static final Student RON = new Student(4L, "Ron", 11);

    public static final Student ALEX = new Student(5L, "Alex", 14);
    public static final Student ALAN = new Student(6L, "Alan", 16);


    public static final Student FRED = new Student(2L, "Fred", 13);

    public static final Student GEORGE = new Student(3L, "George", 13);


    public static final List<Student> STUDENTS = List.of(FRED, GEORGE);

    public static final List<Student> STUDENTS2 = List.of(HARRY, RON, FRED, GEORGE);

    public static final List<Student> STUDENTS3 = List.of(HARRY, RON, FRED, GEORGE, ALAN, ALEX);

}
