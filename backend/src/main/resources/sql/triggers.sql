# ********************** meetup_attendees
DROP TRIGGER IF EXISTS bfr_join_meetup;
CREATE TRIGGER bfr_join_meetup BEFORE INSERT ON meetup_attendees
FOR EACH ROW
BEGIN
    SET @FREE_PLACES = (SELECT max_attendees - registered_attendees FROM meetup WHERE uid = NEW.meetup_id);
    IF @FREE_PLACES > 0 THEN
        UPDATE meetup
        SET registered_attendees = registered_attendees + 1
        WHERE uid = NEW.meetup_id;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Meetup is fully booked';
    END IF;
END;


DROP TRIGGER IF EXISTS bfr_leave_meetup;
CREATE TRIGGER bfr_leave_meetup BEFORE DELETE ON meetup_attendees
FOR EACH ROW
BEGIN
    UPDATE meetup
    SET registered_attendees = registered_attendees - 1
    WHERE uid = OLD.meetup_id;
END;


DROP TRIGGER IF EXISTS bfr_change_attendees;
CREATE TRIGGER bfr_change_attendees BEFORE UPDATE ON meetup_attendees
FOR EACH ROW
BEGIN
    IF OLD.meetup_id <> NEW.meetup_id THEN
        SET @FREE_PLACES = (SELECT max_attendees - registered_attendees FROM meetup WHERE uid = NEW.meetup_id);
        IF @FREE_PLACES > 0 THEN
            UPDATE meetup
            SET registered_attendees = registered_attendees + 1
            WHERE uid = NEW.meetup_id;
            UPDATE meetup
            SET registered_attendees = registered_attendees - 1
            WHERE uid = OLD.meetup_id;
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Meetup is fully booked';
        END IF;
    END IF;
END;


# ********************** meetup
DROP TRIGGER IF EXISTS bfr_change_meetup;
CREATE TRIGGER bfr_change_meetup BEFORE UPDATE ON meetup
    FOR EACH ROW
BEGIN
    IF NEW.max_attendees < NEW.registered_attendees THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Max number of attendees cannot be less then current number of registered attendees';
    END IF;
END;


# ********************** user
DROP TRIGGER IF EXISTS afr_deactivated_user;
CREATE TRIGGER afr_deactivated_user AFTER UPDATE ON user
    FOR EACH ROW
BEGIN
    IF NEW.is_deactivated = 1 THEN

        SET @FUTURE_MEETUPS = (SELECT GROUP_CONCAT(uid) FROM meetup
                               WHERE state_id IN (SELECT uid FROM state
                                                  WHERE state.name = 'Booked'
                                                     OR state.name = 'Scheduled'));
        DELETE FROM meetup_attendees
        WHERE user_id = OLD.uid
          AND FIND_IN_SET(meetup_id, @FUTURE_MEETUPS);

    END IF;
END;


DROP TRIGGER IF EXISTS afr_deactivated_user_cancel_meetups;
CREATE TRIGGER afr_deactivated_user_cancel_meetups AFTER UPDATE ON user
    FOR EACH ROW
BEGIN
    IF NEW.is_deactivated = 1 THEN

        SET @FUTURE_MEETUPS = (SELECT GROUP_CONCAT(uid) FROM meetup
                               WHERE speaker_id = OLD.uid
                                 AND state_id IN (SELECT uid FROM state
                                                  WHERE state.name = 'Booked'
                                                     OR state.name = 'Scheduled'));

        SET @CANCELED_ID = (SELECT uid FROM state WHERE name = 'Canceled');

        UPDATE meetup
        SET state_id = @CANCELED_ID
        WHERE FIND_IN_SET(uid, @FUTURE_MEETUPS);

    END IF;
END;