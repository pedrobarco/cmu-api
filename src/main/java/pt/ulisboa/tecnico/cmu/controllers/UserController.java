package pt.ulisboa.tecnico.cmu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.cmu.models.Code;
import pt.ulisboa.tecnico.cmu.models.Monument;
import pt.ulisboa.tecnico.cmu.models.QuizAnswers;
import pt.ulisboa.tecnico.cmu.models.User;
import pt.ulisboa.tecnico.cmu.repositories.CodeRepository;
import pt.ulisboa.tecnico.cmu.repositories.MonumentRepository;
import pt.ulisboa.tecnico.cmu.repositories.UserRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserRepository userRepository;

    private final CodeRepository codeRepository;

    private final MonumentRepository monumentRepository;

    @Autowired
    public UserController(UserRepository userRepository, CodeRepository codeRepository, MonumentRepository monumentRepository) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
        this.monumentRepository = monumentRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody User user){
        StringBuilder msg = new StringBuilder();
        ResponseEntity err = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        ResponseEntity success = ok(user);
        ResponseEntity response;

        Optional<Code> code = codeRepository.findBySecret(user.getCode().getSecret());
        Optional<User> userUsingSameUsername = userRepository.findByUsername(user.getUsername());
        Optional<User> userUsingSameCode = userRepository.findByCode(user.getCode());

        if(userUsingSameUsername.isPresent()) {
            msg.append("Username already exists.");
            response = err;
        } else if(code.isPresent() && !userUsingSameCode.isPresent()) {
            user.setCode(code.get());
            userRepository.save(user);
            response = success;
        } else {
            msg.append("Invalid password.");
            response = err;
        }

        return response;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        Optional<User> actualUser = userRepository.findByUsername(user.getUsername());

        if(actualUser.isPresent() && actualUser.get().getCode().getSecret().equals(user.getCode().getSecret())){
            User u = actualUser.get();
            String sessionId = actualUser.get().generateSessionId();
            u.setSessionId(sessionId);
            userRepository.save(u);
            return ok(u);
        } else {
            String msg = "Invalid username or password.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/all/scores")
    public List<User> getAllScores() {
        return userRepository.findAllByOrderByScoresDesc();
    }

    @DeleteMapping("/all")
    public String deleteAll(){
        userRepository.deleteAll();
        return "All users deleted successfully!";
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable("id") String id){
        return userRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") String id){
        userRepository.deleteById(id);
        return "User deleted successfully!";
    }

    @GetMapping("/{id}/quiz")
    public Optional<Monument> startQuiz(@PathVariable("id") String id, @RequestParam("ssid") String ssid){
        Optional<User> user = userRepository.findById(id);

        user.ifPresent(u -> {
            Long timestamp = System.currentTimeMillis();
            u.setTimestamp(timestamp);
            userRepository.save(u);
        });

        return monumentRepository.findBySsid(ssid);
    }

    @PostMapping("/{id}/quiz")
    public Optional<User> submitQuiz(@PathVariable("id") String id, @RequestBody List<QuizAnswers> quizAnswersList){
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            for (QuizAnswers qA : quizAnswersList){
                Optional<Monument> monument = monumentRepository.findById(qA.getMonumentId());

                if(monument.isPresent()) {
                    List<Integer> solution = monument.get().getQuiz().getSolution();

                    Long userTime = System.currentTimeMillis() - user.get().getTimestamp();
                    Long duration = monument.get().getQuiz().getDuration();
                    Long time = Math.max(0, duration - userTime);

                    qA.setTime(time);
                    qA.setSolution(solution);
                    qA.calcScore();

                    user.get().getScores().put(monument.get().getId(), qA);
                }
            }

            user.get().calcTotalScore();
            userRepository.save(user.get());

            return user;
        } else {
            return Optional.empty();
        }
    }

}
