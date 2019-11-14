Profanity check API

Problem Statement:
target.com allows our customers to post comment/feedback on specific products in the product details page. These comments are visible to all other customers.

Develop a REST API to moderate/validate the comment text to prevent customers from posting objectionable content. The REST API is expected to take a piece of text as an input and respond with feedback regarding objectionable content


Deliverable:
1.	The REST API contract
2.	Working API demo
3.	Code in git


System Design

1) Maintain a file with all the objectionable words
2) Load the objectionable words in HashSet at the application startup
3) Validate the comment by filtering out all stop words, single character words and check against the HashSet with objectionable words
4) Objectionable words can be added to the file with Objectionable words and in HashSet

API details:
GET API: profanityCheck/<Comment>
Description: Returns if the given comment is objectionable or not
Result:
{
    "comment": <Comment>,
    "objectionable": true/false,
    "objectionableWords": null
}

GET API: profanityCheck/getAllObjectionableWords/<Comment>
Description: Returns if the given comment is objectionable or not and gives the list of words that are objectionable
Result:
{
    "comment": <Comment>,
    "objectionable": true/false,
    "objectionableWords": <List of objectionable words in the comment>
}

GET API: objectionableWords/
Description: Returns all the objectionable words that the comments are checked against
Result:
<Objectionable words list>

POST API: objectionableWords/
Body: List of words that are to be added in the objectionable list  (eg: ["word4","word3","word5"])



