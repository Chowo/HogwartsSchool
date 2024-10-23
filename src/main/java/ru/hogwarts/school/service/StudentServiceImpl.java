package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotEnoughStudentsException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);


    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method for adding new student");

        return repository.save(student);
    }

    @Override
    public Student getStudentById(long id) {
        logger.info("Was invoked method for getting student by id");

        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Student updateStudent(long id, Student student) {
        logger.info("Was invoked method for updating new student");

        if (!repository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        student.setId(id);
        return repository.save(student);
    }

    @Override
    public Student deleteStudent(long id) {
        logger.info("Was invoked method for deleting student");

        Student tmp = getStudentById(id);
        repository.deleteById(id);
        return tmp;
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        logger.info("Was invoked method for getting students by age {}", age);

        return (List<Student>) repository.findStudentsByAge(age);
    }

    @Override
    public List<Student> getStudentsByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method for getting students by age between {} and {}", minAge, maxAge);

        return (List<Student>) repository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getStudentsFaculty(long id) {
        logger.info("Was invoked method for getting student faculty");

        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id)).getFaculty();
    }

    @Override
    public int getStudentsAmount() {
        logger.info("Was invoked method for getting students amount");

        return repository.getStudentsAmount();
    }

    @Override
    public double getAverageStudentsAge() {
        logger.info("Was invoked method for getting average age of students");

        return repository.getAverageAge();
    }

    @Override
    public Collection<Student> getLeastFiveListedStudents() {
        logger.info("Was invoked method for getting list of last five listed students");

        return repository.getFiveLastStudents();
    }

    @Override
    public Collection<Student> getAllStudentsNameStartsWithA() {
        return repository.findAll().stream().filter(e -> e.getName().toLowerCase().startsWith("A".toLowerCase())).toList();
    }

    @Override
    public double getAverageAgeWithStream() {
        return repository.findAll().stream().mapToDouble(Student::getAge).average().orElseThrow();
    }

    @Override
    public void printStudentsParallel() {
        if (repository.findAll().size() < 6) {
            throw new NotEnoughStudentsException();
        }
        List<Student> listOfStudents = repository.getSixStudents();
        printStudentsName(listOfStudents.get(0));
        printStudentsName(listOfStudents.get(1));

        new Thread(() -> {
            printStudentsName(listOfStudents.get(2));
            printStudentsName(listOfStudents.get(3));
        }).start();

        new Thread(() -> {
            printStudentsName(listOfStudents.get(4));
            printStudentsName(listOfStudents.get(5));
        }).start();

    }

    private void printStudentsName (Student student) {
        System.out.println(student.getName());
    }

    synchronized private void printStudentsNameSynchronized (Student student) {
        System.out.println(student.getName());
    }

    @Override
    public void printStudentsSynchronized() {
        if (repository.findAll().size() < 6) {
            throw new NotEnoughStudentsException();
        }
        List<Student> listOfStudents = repository.getSixStudents();
        printStudentsNameSynchronized(listOfStudents.get(0));
        printStudentsNameSynchronized(listOfStudents.get(1));

        new Thread(() -> {
            printStudentsNameSynchronized(listOfStudents.get(2));
            printStudentsNameSynchronized(listOfStudents.get(3));
        }).start();

        new Thread(() -> {
            printStudentsNameSynchronized(listOfStudents.get(4));
            printStudentsNameSynchronized(listOfStudents.get(5));
        }).start();

    }


}
