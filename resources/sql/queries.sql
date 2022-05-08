-- :name create-question! :! :n
-- :doc creates a new question
INSERT INTO question
(question_text)
VALUES (:question_text)

-- :name get-questions :? :*
-- :doc retrieves all questions
SELECT * FROM question
