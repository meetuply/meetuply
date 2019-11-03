package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    public Ban get(Integer banReasonId, Integer banAuthorId, Integer reportedUserId){
        return banDAO.get(banReasonId, banAuthorId, reportedUserId);
    }

    public List<Ban> getByReason(Integer banReasonId) { return banDAO.getByReason(banReasonId); }

    public List<Ban> getByAuthor(Integer banAuthorId) { return banDAO.getByAuthor(banAuthorId); }

    public List<Ban> getByReported(Integer reportedUserId) { return banDAO.getByReported(reportedUserId); }

    public void create(Ban ban, Integer banReasonId, Integer reportedUserId) {
        ban.setBanReason(banReasonDAO.get(banReasonId));
        ban.setAuthor(appUserService.getUser(appUserService.getCurrentUserID()));
        ban.setReported(appUserService.getUser(reportedUserId));
        ban.setTime(LocalDateTime.now());
        banDAO.save(ban);
    }

    public void update(Ban ban){
        banDAO.update(ban);
    }

    public void delete(Integer banReasonId, Integer banAuthorId, Integer reportedUserId){
        banDAO.delete(banReasonId, banAuthorId, reportedUserId);
    }

    public List<Ban> getAll(){
        return banDAO.getAll();
    }
}
