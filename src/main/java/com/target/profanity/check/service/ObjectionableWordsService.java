package com.target.profanity.check.service;

import com.target.profanity.check.exception.TargetApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ObjectionableWordsService {
    private static final Logger logger = LoggerFactory.getLogger(ObjectionableWordsService.class);


    public Set<String> getAllObjectionableWords() {
        return ProfanityCheckService.objectionableWords;
    }

    public void addObjectionableWord(List<String> objectionableWords) {
        List<String> existingWords = new ArrayList<>();
        List<String> newWords = new ArrayList<>();

        for (String word : objectionableWords) {
            int currentSize = ProfanityCheckService.objectionableWords.size();
            ProfanityCheckService.objectionableWords.add(word.toLowerCase());
            int updatedSize = ProfanityCheckService.objectionableWords.size();

            if (currentSize == updatedSize) {
                existingWords.add(word);
            } else {
                newWords.add(word);
            }
        }

        try (FileWriter fw = new FileWriter("/Users/d0c01cs/Documents/basics_learn/Target-profanityCheck/src/main/resources/objectionableWords.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for(String word:newWords) {
                out.println(word.toLowerCase());
            }
        } catch (IOException e) {
            throw new TargetApplicationException("Error writing objectionable words in the File");

        }

        if (!existingWords.isEmpty()) {
            logger.info("Objectionable words that already existed in the file:" + existingWords);
        }
    }
}
