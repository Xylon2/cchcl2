-- :name create-question! :! :n
-- :doc creates a new question
INSERT INTO question
(question_text)
VALUES (:question_text)

-- :name get-questions :? :*
-- :doc retrieves all questions
SELECT * FROM question

-- :name create-choice! :! :n
-- :doc creates a choice
insert into choice
(question, choice_text)
values (:question, :choice_text)

-- :name get-choices :? :*
-- :doc retrieves all choices for a question
select * from choice
where question = :question
