-- :name create-question! :! :n
-- :doc creates a new question
INSERT INTO question
(question_text)
VALUES (:question_text)

-- :name get-questions :? :*
-- :doc retrieves all questions
SELECT * FROM question

-- :name question-name :? :1
-- :doc retrieves the name and id for a question
select id, question_text from question
where id = :id

-- :name create-choice! :! :n
-- :doc creates a choice
insert into choice
(question, choice_text)
values (:question, :choice_text)

-- :name get-choices :? :*
-- :doc retrieves all choices for a question
select * from choice
where question = :question

-- :name save-vote :! :n
-- :doc records the extra vote
update choice
set votes = votes + 1
where id = :id

-- :name create-user!* :! :n
-- :doc creates a new user with the provided login and hashed password
insert into users
(login, password)
values (:login, :password)

-- :name get-user-for-auth* :? :1
-- :doc selects a user for authentication
select * from users
where login = :login
