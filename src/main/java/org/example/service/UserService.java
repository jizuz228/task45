package org.example.service;

import jakarta.annotation.PostConstruct;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void run() {
        createUser();
        deleteUser(Long.valueOf(2));

        Optional<User> userOpt = getUserById(Long.valueOf(3));
        userOpt.ifPresent(System.out::println);

        updateUser(Long.valueOf(1), "Alina", "alina@gmail.com");
        findByEmail("alina@gmail.com").ifPresent(System.out::println);

        System.out.println("Exists 'alina': " + existsByLogin("alina"));

        List<User> users = findAllByLogin("alina");
        users.forEach(System.out::println);
    }

    public void createUser() {
        userRepository.save(new User(null, "kolya", "kolya@mail.ru"));
        userRepository.save(new User(null, "alina", "alina@mail.ru"));
        userRepository.save(new User(null, "lexa", "lexa@mail.ru"));
        userRepository.save(new User(null, "danya", "danya@mail.ru"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, String login, String mail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLogin(login);
        user.setMail(mail);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByMail(email);
    }

    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public List<User> findAllByLogin(String login) {
        return userRepository.findAllByLogin(login);
    }
}