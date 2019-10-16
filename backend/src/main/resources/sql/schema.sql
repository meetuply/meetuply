-- ************************************** `achievement`
CREATE TABLE `achievement`
(
    `uid`              integer unsigned NOT NULL AUTO_INCREMENT,
    `title`            varchar(100)     NOT NULL,
    `description`      varchar(400)     NOT NULL,
    `icon`             varchar(500)     NOT NULL,
    `followers_number` integer          NULL,
    `posts_number`     integer          NULL,
    `rating`           float            NULL,

    PRIMARY KEY (`uid`)
);

-- ************************************** `ban_reason`
CREATE TABLE `ban_reason`
(
    `name` varchar(100)     NOT NULL,
    `uid`  integer unsigned NOT NULL AUTO_INCREMENT,

    PRIMARY KEY (`uid`)
);


-- ************************************** `chat_room`
CREATE TABLE `chat_room`
(
    `uid` integer unsigned NOT NULL AUTO_INCREMENT,

    PRIMARY KEY (`uid`)
);

-- ************************************** `language`
CREATE TABLE `language`
(
    `name` varchar(100)     NOT NULL,
    `uid`  integer unsigned NOT NULL AUTO_INCREMENT,

    PRIMARY KEY (`uid`)
);

-- ************************************** `notification_template`
CREATE TABLE `notification_template`
(
    `uid`        integer      NOT NULL AUTO_INCREMENT,
    `name`       varchar(100) NOT NULL,
    `html_text`  varchar(700) NOT NULL,
    `plain_text` varchar(400) NOT NULL,

    PRIMARY KEY (`uid`)
);


-- ************************************** `role`
CREATE TABLE `role`
(
    `name` varchar(45)      NOT NULL,
    `uid`  integer unsigned NOT NULL AUTO_INCREMENT,

    PRIMARY KEY (`uid`),
    UNIQUE (`name`)
);

-- ************************************** `state`
CREATE TABLE `state`
(
    `name` varchar(20)       NOT NULL,
    `uid`  smallint unsigned NOT NULL AUTO_INCREMENT,

    PRIMARY KEY (`uid`)
);

-- ************************************** `topic`
CREATE TABLE `topic`
(
    `name` varchar(100)     NOT NULL,
    `uid`  integer unsigned NOT NULL AUTO_INCREMENT,

    PRIMARY KEY (`uid`)
);

-- ************************************** `user`
CREATE TABLE `user`
(
    `uid`                    integer unsigned NOT NULL AUTO_INCREMENT,
    `email`                  varchar(200)     NOT NULL,
    `password`               varchar(72)      NOT NULL,
    `firstname`              varchar(100)     NOT NULL,
    `surname`                varchar(100)     NOT NULL,
    `registration_confirmed` tinyint(1)       NOT NULL,
    `is_deactivated`         tinyint(1)       NOT NULL,
    `allow_notifications`    tinyint(1)       NOT NULL,
    `role_id`                integer unsigned NOT NULL,

    PRIMARY KEY (`uid`),
    UNIQUE (`email`),
    FOREIGN KEY (`role_id`) REFERENCES `role` (`uid`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- ************************************** `user_chat_room`
CREATE TABLE `user_chat_room`
(
    `room_id` integer unsigned NOT NULL,
    `uid`     integer unsigned NOT NULL,

    PRIMARY KEY (`room_id`, `uid`),
    FOREIGN KEY (`room_id`) REFERENCES `chat_room` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `user_achievement`
CREATE TABLE `user_achievement`
(
    `achievement_id` integer unsigned NOT NULL,
    `user_id`        integer unsigned NOT NULL,

    PRIMARY KEY (`achievement_id`, `user_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (`achievement_id`) REFERENCES `achievement` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `user_language`
CREATE TABLE `user_language`
(
    `user_id`     integer unsigned NOT NULL,
    `language_id` integer unsigned NOT NULL,

    PRIMARY KEY (`user_id`, `language_id`),
    FOREIGN KEY (`language_id`) REFERENCES `language` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `achievement_topic`
CREATE TABLE `achievement_topic`
(
    `achievement_id` integer unsigned NOT NULL,
    `topic_id`       integer unsigned NOT NULL,
    `quantity`       integer          NOT NULL,

    PRIMARY KEY (`achievement_id`, `topic_id`),
    KEY (`topic_id`),
    FOREIGN KEY (`topic_id`) REFERENCES `topic` (`uid`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    KEY (`achievement_id`),
    FOREIGN KEY (`achievement_id`) REFERENCES `achievement` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- ************************************** `ban`
CREATE TABLE `ban`
(
    `reason_id`        integer unsigned NOT NULL,
    `by_user_id`       integer unsigned NOT NULL,
    `reported_user_id` integer unsigned NOT NULL,
    `description`      varchar(200)     NULL,
    `date_time`        datetime         NOT NULL,

    PRIMARY KEY (`reason_id`, `by_user_id`, `reported_user_id`),
    FOREIGN KEY (`by_user_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    FOREIGN KEY (`reported_user_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (`reason_id`) REFERENCES `ban_reason` (`uid`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);


-- ************************************** `feedback`
CREATE TABLE `feedback`
(
    `uid`             integer unsigned NOT NULL AUTO_INCREMENT,
    `date_time`       datetime         NOT NULL,
    `content`         varchar(500)     NOT NULL,
    `author_id`       integer unsigned NOT NULL,
    `forwarded_to_id` integer unsigned NOT NULL,

    PRIMARY KEY (`uid`),
    KEY (`author_id`),
    FOREIGN KEY (`author_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    KEY (`forwarded_to_id`),
    FOREIGN KEY (`forwarded_to_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- ************************************** `followers`
CREATE TABLE `followers`
(
    `followed_user_id` integer unsigned NOT NULL,
    `follower_id`      integer unsigned NOT NULL,

    PRIMARY KEY (`followed_user_id`, `follower_id`),
    KEY (`follower_id`),
    FOREIGN KEY (`follower_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    KEY (`followed_user_id`),
    FOREIGN KEY (`followed_user_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `meetup`
CREATE TABLE `meetup`
(
    `title`                varchar(300)      NOT NULL,
    `uid`                  integer unsigned  NOT NULL AUTO_INCREMENT,
    `start_date_time`      datetime          NOT NULL,
    `finish_date_time`     datetime          NOT NULL,
    `place`                varchar(500)      NOT NULL,
    `registered_attendees` integer           NOT NULL,
    `min_attendees`        integer           NOT NULL,
    `max_attendees`        integer           NOT NULL,
    `state_id`             smallint unsigned NOT NULL,
    `speaker_id`           integer unsigned  NOT NULL,
    `description`          varchar(500)      NOT NULL,

    PRIMARY KEY (`uid`),
    FOREIGN KEY (`state_id`) REFERENCES `state` (`uid`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    KEY (`speaker_id`),
    FOREIGN KEY (`speaker_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `meetup_attendees`
CREATE TABLE `meetup_attendees`
(
    `meetup_id` integer unsigned NOT NULL,
    `user_id`   integer unsigned NOT NULL,

    PRIMARY KEY (`meetup_id`, `user_id`),
    KEY (`user_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (`meetup_id`) REFERENCES `meetup` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `meetup_topic`
CREATE TABLE `meetup_topic`
(
    `topic_id`  integer unsigned NOT NULL,
    `meetup_id` integer unsigned NOT NULL,

    PRIMARY KEY (`topic_id`, `meetup_id`),
    FOREIGN KEY (`topic_id`) REFERENCES `topic` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    KEY (`meetup_id`),
    FOREIGN KEY (`meetup_id`) REFERENCES `meetup` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `meetup_language`
CREATE TABLE `meetup_language`
(
    `language_id` integer unsigned NOT NULL,
    `meetup_id`   integer unsigned NOT NULL,

    PRIMARY KEY (`language_id`, `meetup_id`),
    FOREIGN KEY (`language_id`) REFERENCES `language` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (`meetup_id`) REFERENCES `meetup` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `notification`
CREATE TABLE `notification`
(
    `uid`         integer          NOT NULL AUTO_INCREMENT,
    `date_time`   datetime         NOT NULL,
    `is_read`     tinyint(1)       NOT NULL,
    `receiver_id` integer unsigned NOT NULL,
    `template_id` integer          NOT NULL,

    PRIMARY KEY (`uid`),
    KEY (`receiver_id`),
    FOREIGN KEY (`receiver_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (`template_id`) REFERENCES `notification_template` (`uid`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

-- ************************************** `message`
CREATE TABLE `message`
(
    `uid`        integer unsigned NOT NULL AUTO_INCREMENT,
    `date_time`  datetime         NOT NULL,
    `content`    varchar(256)     NOT NULL,
    `from`       integer unsigned NOT NULL,
    `to_user_id` integer unsigned NULL,
    `to_room_id` integer unsigned NULL,

    PRIMARY KEY (`uid`),
    KEY (`from`),
    FOREIGN KEY (`from`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    KEY (`to_user_id`),
    FOREIGN KEY (`to_user_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    KEY (`to_room_id`),
    FOREIGN KEY (`to_room_id`) REFERENCES `chat_room` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- ************************************** `post`
CREATE TABLE `post`
(
    `uid`       integer unsigned NOT NULL AUTO_INCREMENT,
    `title`     varchar(150)     NOT NULL,
    `date_time` datetime         NOT NULL,
    `content`   varchar(2000)    NOT NULL,
    `author_id` integer unsigned NOT NULL,

    PRIMARY KEY (`uid`),
    KEY (`author_id`),
    FOREIGN KEY (`author_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- ************************************** `rating`
CREATE TABLE `rating`
(
    `rated_user_id` integer unsigned NOT NULL,
    `rated_by`      integer unsigned NOT NULL,
    `value`         smallint         NOT NULL,
    `date_time`     datetime         NOT NULL,

    PRIMARY KEY (`rated_user_id`, `rated_by`),
    KEY (`rated_by`),
    FOREIGN KEY (`rated_by`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    KEY (`rated_user_id`),
    FOREIGN KEY (`rated_user_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- ************************************** `saved_filter`
CREATE TABLE `saved_filter`
(
    `uid`            integer unsigned NOT NULL AUTO_INCREMENT,
    `name`           varchar(100)     NOT NULL,
    `rating`         float            NULL,
    `date_time_from` datetime         NULL,
    `date_time_to`   datetime         NULL,
    `owner_id`       integer unsigned NOT NULL,

    PRIMARY KEY (`uid`),
    FOREIGN KEY (`owner_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `comment`
CREATE TABLE `comment`
(
    `uid`       integer unsigned NOT NULL AUTO_INCREMENT,
    `date_time` datetime         NOT NULL,
    `content`   varchar(400)     NOT NULL,
    `post_id`   integer unsigned NOT NULL,
    `author`    integer unsigned NOT NULL,

    PRIMARY KEY (`uid`),
    KEY (`post_id`),
    FOREIGN KEY (`post_id`) REFERENCES `post` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    KEY (`author`),
    FOREIGN KEY (`author`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `filter_topic`
CREATE TABLE `filter_topic`
(
    `topic_id`  integer unsigned NOT NULL,
    `filter_id` integer unsigned NOT NULL,

    PRIMARY KEY (`topic_id`, `filter_id`),
    FOREIGN KEY (`filter_id`) REFERENCES `saved_filter` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (`topic_id`) REFERENCES `topic` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ************************************** `confirmation_token`
CREATE TABLE `confirmation_token`
(
    `uid`         integer unsigned NOT NULL AUTO_INCREMENT,
    `token`       varchar(255)     NULL,
    `create_date` DATETIME         NULL,
    `user_id`     integer unsigned NOT NULL,

    PRIMARY KEY (`uid`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);