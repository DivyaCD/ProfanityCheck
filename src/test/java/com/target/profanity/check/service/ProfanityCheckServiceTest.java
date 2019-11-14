package com.target.profanity.check.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProfanityCheckServiceTest {

    @InjectMocks
    ProfanityCheckService profanityCheckService;

    @BeforeClass
    public static void setup(){
        String[] objectionableWordsArray = {"word1", "word2"};
        ProfanityCheckService.objectionableWords.addAll(Arrays.asList(objectionableWordsArray));
    }

    @Test
    public void testRemovePunctuations() {
        String comment = profanityCheckService.removePunctuations("hello!!world");
        assertEquals("hello  world", comment);
    }

    @Test
    public void testFilterStopWords() {
        List<String> result = profanityCheckService.filterStopWords("the a ");
        assertEquals(0, result.size());

    }

    @Test
    public void testFilterStopWordsWithNonStopWords() {
        List<String> result = profanityCheckService.filterStopWords("hello the a word");
        assertEquals(2, result.size());

    }

    @Test
    public void testValidateComment() {

        System.out.println(ProfanityCheckService.objectionableWords);
        boolean result = profanityCheckService.validateComment(Arrays.asList("word1", "friends", "hello"));
        assertEquals(true, result);
    }

    @Test
    public void testGetListOfObjectionableWords() {
        System.out.println(ProfanityCheckService.objectionableWords);
        List<String> result = profanityCheckService.getListOfObjectionableWords(Arrays.asList("word1", "friends", "hello"));
        assertEquals(1, result.size());
        assertEquals("word1", result.get(0));
    }
}