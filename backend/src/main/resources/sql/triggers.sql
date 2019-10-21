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