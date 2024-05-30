Задача: спроектировать и разработать API для системы опросов пользователей.

Функционал для администратора системы:

- авторизация в системе (регистрация не нужна)
- добавление/изменение/удаление опросов. Атрибуты опроса: название, дата старта, дата окончания, описание. После создания поле "дата старта" у опроса менять нельзя
- добавление/изменение/удаление вопросов в опросе. Атрибуты вопросов: текст вопроса, тип вопроса (ответ текстом, ответ с выбором одного варианта, ответ с выбором нескольких вариантов)

Функционал для пользователей системы:

- получение списка активных опросов
- прохождение опроса: опросы можно проходить анонимно, в качестве идентификатора пользователя в API передаётся числовой ID, по которому сохраняются ответы пользователя на вопросы; один пользователь может участвовать в любом количестве опросов
- получение пройденных пользователем опросов с детализацией по ответам (что выбрано) по ID уникальному пользователя

Использовать следующие технологии: Spring Framework.

Результат выполнения задачи:
- исходный код приложения в github (только на github, публичный репозиторий)
- инструкция по разворачиванию приложения (в docker или локально)
- документация по API

TODO:
- documentation
- timezone
- exception handling
- db index
- optimistic locking
- all hashCode and equals
- all sonarLint problems
- get questions public other operations admin
- check all answers answered before submit questionnaire
- active questionnaire pagination

ex:
 - unauthorized
 - changed start date
 - end date after start date
 - 

features:
- active questionary by start date and are not submitted by user
- constructor injection with Lombok
- Postgres jsonb for question options
- Enum as String with Hibernate
- N+1 fetch joining
- optimistic locking
- validation
- OneToMany-ManyToOne cascade and orphan removal
- id generation type sequence
- api versioning
- Dates as LocalDateTime, UTC db and server
- dto - record - mapper
- flyway
- MockMVCTest - testing web layer
- compose
- pagination
- validation
- end time after start
- authorization via api-key
- question order, unique constraint