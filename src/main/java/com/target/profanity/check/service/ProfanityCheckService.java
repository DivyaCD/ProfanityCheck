package com.target.profanity.check.service;

import com.target.profanity.check.entity.CommentRequest;
import com.target.profanity.check.entity.CommentResponse;
import com.target.profanity.check.exception.TargetApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProfanityCheckService {
    private static final Logger logger = LoggerFactory.getLogger(ProfanityCheckService.class);
    public static Set<String> objectionableWords = new HashSet();
    List<String> stopWords = Arrays.asList("this", "the", "that", "him", "her", "there");

    @Value("${objectionableWords.file.path}")
    private String filePath;

    @PostConstruct
    public void init() {
        logger.info("Load objectionable words in HashSet");

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            objectionableWords = stream
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new TargetApplicationException("Error loading objectionable words in HashSet");
        }

    }

    public CommentResponse checkIfCommentIsObjectionable(String comment) {
        CommentResponse commentResponseObj = new CommentResponse();
        commentResponseObj.setComment(comment);

        comment=removePunctuations(comment);
        List<String> filteredComment = filterStopWords(comment);
        logger.info("Filtered comment:" + filteredComment);
        logger.info("Validate comment");
        commentResponseObj.setObjectionable(validateComment(filteredComment));
        return commentResponseObj;
    }

    public String removePunctuations(String comment) {
        return comment.replaceAll("\\p{Punct}", " ");
    }

    public boolean validateComment(List<String> filteredComment) {
        if (filteredComment.isEmpty())
            return false;
        return filteredComment.stream().anyMatch(word -> objectionableWords.contains(word));
    }

    public List<String> filterStopWords(String comment) {
        List<String> words = Stream.of(comment.split("\\s+")).filter(word -> word.length() >= 2).map(String::toLowerCase).collect(Collectors.toList());
        words.removeAll(stopWords);
        return words;
    }

    public CommentResponse getAllObjectionableWords(String comment) {
        CommentResponse commentResponseObj = new CommentResponse();
        commentResponseObj.setComment(comment);

        comment=removePunctuations(comment);
        List<String> filteredComment = filterStopWords(comment);
        logger.info("Filtered comment:" + filteredComment);
        logger.info("Validate comment");

        List<String> objectionableWordsList = getListOfObjectionableWords(filteredComment);
        commentResponseObj.setObjectionableWords(objectionableWordsList);

        boolean isObjectionable = objectionableWordsList.isEmpty() ? false : true;
        commentResponseObj.setObjectionable(isObjectionable);
        return commentResponseObj;
    }

    public List<String> getListOfObjectionableWords(List<String> filteredComment) {
        return filteredComment.stream().filter(word -> objectionableWords.contains(word)).collect(Collectors.toList());
    }

    public CommentResponse processComment(CommentRequest comment) {
        if (comment.isRequireObjectionableWords())
            return getAllObjectionableWords(comment.getComment());
        else
            return checkIfCommentIsObjectionable(comment.getComment());
    }
}
