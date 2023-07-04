package codeFromBook;

import java.util.HashSet;
import java.util.Set;

/**
 * 在使用过程中保证非线程安全集合的线程安全
 * @Author niuzheju
 * @Date 16:04 2023/7/4
 */
public class PersonSet {

    private final Set<Person> mySet = new HashSet<>();

    public synchronized void addPerson(Person person) {
        mySet.add(person);
    }

    public synchronized boolean containsPerson(Person person) {
        return mySet.contains(person);
    }
}

class Person {
}
