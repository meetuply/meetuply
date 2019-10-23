package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.NoHandlerFoundException;
import ua.meetuply.backend.dao.BanDAO;
import ua.meetuply.backend.dao.BanReasonDAO;
import ua.meetuply.backend.model.Ban;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BanService {

    @Autowired
    BanDAO banDAO;

    @Autowired
    BanReasonDAO banReasonDAO;

    @Autowired
    AppUserService appUserService;

    public Ban get(Integer reason_id, Integer by_user_id, Integer reported_user_id){
        return banDAO.get(reason_id, by_user_id, reported_user_id);
    }

    public List<Ban> getByReason(Integer reason_id) { return banDAO.getByReason(reason_id); }

    public List<Ban> getByAuthor(Integer by_user_id) { return banDAO.getByAuthor(by_user_id); }

    public List<Ban> getByReported(Integer reported_user_id) { return banDAO.getByReported(reported_user_id); }

    public void create(Ban ban, Integer ban_reason_id, Integer reported_user_id) {
        ban.setBanReason(banReasonDAO.get(ban_reason_id));
        ban.setAuthor(appUserService.getUser(appUserService.getCurrentUserID()));
        ban.setReported(appUserService.getUser(reported_user_id));
        ban.setTime(LocalDateTime.now());
        banDAO.save(ban);
    }

    public void update(Ban ban){
        banDAO.update(ban);
    }

    public void delete(Integer reason_id, Integer by_user_id, Integer reported_user_id){
        banDAO.delete(reason_id, by_user_id, reported_user_id);
    }

    public List<Ban> getAll(){
        return banDAO.getAll();
    }
}
