package com.target.profanity.check.controller;

import com.target.profanity.check.entity.Comment;
import com.target.profanity.check.exception.TargetApplicationException;
import com.target.profanity.check.service.ProfanityCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/profanityCheck")
public class ProfanityCheckController {

    @Autowired
    ProfanityCheckService profanityCheck;

    @GetMapping("{comment}")
    public ResponseEntity checkIfCommentIsObjectionable(@PathVariable("comment") String comment) {
        try {
            Comment commentVerified = profanityCheck.checkIfCommentIsObjectionable(comment);
            return new ResponseEntity(commentVerified, HttpStatus.OK);
        } catch (TargetApplicationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("getAllObjectionableWords/{comment}")
    public ResponseEntity getAllObjectionableWords(@PathVariable("comment") String comment) {
        try {
            Comment commentVerified = profanityCheck.getAllObjectionableWords(comment);
            return new ResponseEntity(commentVerified, HttpStatus.OK);
        } catch (TargetApplicationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
