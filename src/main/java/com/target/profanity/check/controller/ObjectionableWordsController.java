package com.target.profanity.check.controller;

import com.target.profanity.check.service.ObjectionableWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/objectionableWords")
public class ObjectionableWordsController {

    @Autowired
    ObjectionableWordsService objectionableWordsService;

    @GetMapping
    public ResponseEntity getAllObjectionableWords() {
        Set<String> users = objectionableWordsService.getAllObjectionableWords();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addObjectionableWord(@RequestBody List<String> objectionableWord) {
        objectionableWordsService.addObjectionableWord(objectionableWord);
        return new ResponseEntity("Added objectionable word", HttpStatus.OK);
    }

}
