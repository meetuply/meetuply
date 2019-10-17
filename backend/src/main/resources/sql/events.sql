DROP EVENT IF EXISTS `delete_expired_confirmation_token`;

CREATE EVENT `delete_expired_confirmation_token`
ON SCHEDULE EVERY 1 HOUR STARTS NOW()
ON COMPLETION PRESERVE ENABLE
DO
BEGIN
    DECLARE done INTEGER DEFAULT 0;
    DECLARE userID MEDIUMINT UNSIGNED;
    DECLARE tokenID MEDIUMINT UNSIGNED;

    DECLARE TokenCursor CURSOR FOR
    SELECT user_id, uid FROM confirmation_token WHERE create_date < DATE_SUB(NOW(), INTERVAL 24 HOUR);

    DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done=1;
    OPEN TokenCursor;

    WHILE done = 0 DO
    FETCH TokenCursor INTO userID, tokenID;
        DELETE FROM confirmation_token WHERE uid = tokenID;
        DELETE FROM user WHERE uid = userID;
    END WHILE;
END;