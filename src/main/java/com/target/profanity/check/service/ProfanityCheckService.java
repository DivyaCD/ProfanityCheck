package com.target.profanity.check.service;

import com.target.profanity.check.entity.Comment;
import com.target.profanity.check.exception.TargetApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    List<String> stopWords= Arrays.asList("this","the","that","him","her","there");

    public static Set<String> objectionableWords = new HashSet();

    static {
        loadObjectionableWordsInHashSet("/Users/d0c01cs/Documents/basics_learn/Target-profanityCheck/src/main/resources/objectionableWords.txt");
    }

    private static void loadObjectionableWordsInHashSet(String filename) {
        logger.info("Load objectionable words in HashSet");

        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            objectionableWords = stream
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new TargetApplicationException("Error loading objectionable words in HashSet");
        }

    }

    public Comment checkIfCommentIsObjectionable(String comment) {
        Comment commentObj=new Comment();
        commentObj.setComment(comment);

//        String removePunctuations(comment);
        List<String> filteredComment=filterStopWords(comment);
        logger.info("Filtered comment:"+filteredComment);
        logger.info("Validate comment");
        commentObj.setObjectionable(validateComment(filteredComment));
        return commentObj;
    }

    private boolean validateComment(List<String> filteredComment) {
        if(filteredComment.isEmpty())
            return false;
        return filteredComment.stream().anyMatch(word->objectionableWords.contains(word));
    }

    private List<String> filterStopWords(String comment) {
        List<String> words=Stream.of(comment.split("\\s+")).filter(word->word.length()>=2).map(String::toLowerCase).collect(Collectors.toList());
        words.removeAll(stopWords);
        return words;
    }

    public Comment getAllObjectionableWords(String comment) {
        Comment commentObj=new Comment();
        commentObj.setComment(comment);

//        String removePunctuations(comment);
        List<String> filteredComment=filterStopWords(comment);
        logger.info("Filtered comment:"+filteredComment);
        logger.info("Validate comment");

        List<String> objectionableWordsList=getListOfObjectionableWords(filteredComment);
        commentObj.setObjectionableWords(objectionableWordsList);

        boolean isObjectionable=objectionableWordsList.isEmpty()?false:true;
        commentObj.setObjectionable(isObjectionable);
        return commentObj;
    }

    private List<String> getListOfObjectionableWords(List<String> filteredComment) {
        return filteredComment.stream().filter(word->objectionableWords.contains(word)).collect(Collectors.toList());
    }
}
