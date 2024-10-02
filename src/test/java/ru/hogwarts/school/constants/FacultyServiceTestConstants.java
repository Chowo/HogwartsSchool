package ru.hogwarts.school.constants;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public class FacultyServiceTestConstants {

    public static final Faculty FACULTY1 = new Faculty("Hufflepuff", "Yellow");
    public static final Faculty FACULTY2 = new Faculty("Ravenclaw", "blue");
    public static final Faculty FACULTY3 = new Faculty("Slytherin", "green");

    public static final List<Faculty> FACULTIES = List.of(FACULTY1, FACULTY2, FACULTY3);

}
