package pt.ulisboa.tecnico.cmu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

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
        ResponseEntity err = status(HttpStatus.BAD_REQUEST).body(msg);
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
            return status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/all/scores")
    public List<User> getAllScores() {
        return userRepository.findAllByOrderByTotalScoreDesc();
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
    public ResponseEntity startQuiz(@PathVariable("id") String id, @RequestParam("ssid") String ssid, @RequestParam("sessionId") String sessionId){
        Optional<User> user = userRepository.findById(id);
        Optional<Monument> monument = monumentRepository.findBySsid(ssid);

        if(user.isPresent() && user.get().getSessionId().equals(sessionId)){
            if(monument.isPresent() && !user.get().getScores().containsKey(monument.get().getId())){
                Long timestamp = System.currentTimeMillis();
                user.get().getTimestamps().putIfAbsent(monument.get().getId(), timestamp);
                userRepository.save(user.get());
                return ok(monument);
            } else if(monument.isPresent()) {
                String msg = "Duplicated quiz request.";
                return status(HttpStatus.CONFLICT).body(msg);
            } else {
                String msg = "Invalid quiz request.";
                return status(HttpStatus.BAD_REQUEST).body(msg);
            }
        } else {
            String msg = "No permission.";
            return status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }

    @PostMapping("/{id}/quiz")
    public ResponseEntity submitQuiz(@PathVariable("id") String id, @RequestParam("sessionId") String sessionId, @RequestBody List<QuizAnswers> quizAnswersList){
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent() && user.get().getSessionId().equals(sessionId)) {
            user.ifPresent(u -> {
                quizAnswersList.forEach(qA -> {
                    Optional<Monument> monument = monumentRepository.findById(qA.getMonumentId());

                    monument.ifPresent(m -> {
                        List<Integer> solution = m.getQuiz().getSolution();

                        Long userTime = System.currentTimeMillis() - u.getTimestamps().get(qA.getMonumentId());
                        Long duration = m.getQuiz().getDuration();
                        Long time = Math.max(0, duration - userTime);

                        qA.setTime(time);
                        qA.setSolution(solution);
                        qA.calcScore();

                        u.getScores().put(m.getId(), qA);
                    });
                });

                u.calcTotalScore();
                userRepository.save(u);
            });

            return ok(userRepository.findAllByOrderByTotalScoreDesc());
        }

        String msg = "No permission.";
        return status(HttpStatus.BAD_REQUEST).body(msg);
    }

}
