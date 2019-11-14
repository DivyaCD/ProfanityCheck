package com.target.profanity.check.controller;

import com.target.profanity.check.entity.CommentRequest;
import com.target.profanity.check.entity.CommentResponse;
import com.target.profanity.check.exception.TargetApplicationException;
import com.target.profanity.check.service.ProfanityCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/profanityCheck")
public class ProfanityCheckController {

    @Autowired
    ProfanityCheckService profanityCheck;

    @GetMapping("{comment}")
    public ResponseEntity checkIfCommentIsObjectionable(@PathVariable("comment") String comment) {
        try {
            CommentResponse commentVerified = profanityCheck.checkIfCommentIsObjectionable(comment);
            return new ResponseEntity(commentVerified, HttpStatus.OK);
        } catch (TargetApplicationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("getAllObjectionableWords/{comment}")
    public ResponseEntity getAllObjectionableWords(@PathVariable("comment") String comment) {
        try {
            CommentResponse commentVerified = profanityCheck.getAllObjectionableWords(comment);
            return new ResponseEntity(commentVerified, HttpStatus.OK);
        } catch (TargetApplicationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity checkIfCommentIsObjectionable(@RequestBody CommentRequest comment) {
        try {
            CommentResponse commentResponseVerified = profanityCheck.processComment(comment);
            return new ResponseEntity(commentResponseVerified, HttpStatus.OK);
        } catch (TargetApplicationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
