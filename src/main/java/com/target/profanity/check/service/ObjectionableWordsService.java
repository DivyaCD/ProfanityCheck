package com.target.profanity.check.service;

import com.target.profanity.check.exception.TargetApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class ObjectionableWordsService {
    private static final Logger logger = LoggerFactory.getLogger(ObjectionableWordsService.class);

    @Value("${objectionableWords.file.path}")
    String filePath;

    public Set<String> getAllObjectionableWords() {
        return ProfanityCheckService.objectionableWords;
    }

    public void addObjectionableWord(List<String> objectionableWords) {
        List<String> existingWords=new ArrayList<>();
        List<String> newWords=new ArrayList<>();

        Consumer<String> checkObjectionableWord=word->{
            if(ProfanityCheckService.objectionableWords.contains(word))
                existingWords.add(word);
            else
                newWords.add(word);
        };
        objectionableWords.stream().forEach(checkObjectionableWord);
        ProfanityCheckService.objectionableWords.addAll(newWords);



        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for(String word:newWords) {
                out.println(word.toLowerCase());
            }
        } catch (IOException e) {
            throw new TargetApplicationException("Error writing objectionable words in the File");

        }finally {
            if (!existingWords.isEmpty()) {
                logger.info("Objectionable words that already existed in the file:" + existingWords);
            }
        }
    }
}
