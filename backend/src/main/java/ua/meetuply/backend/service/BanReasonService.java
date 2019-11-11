package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.BanReasonDAO;
import ua.meetuply.backend.model.BanReason;

import java.util.List;

@Component
public class BanReasonService {
	
	@Autowired
    BanReasonDAO banReasonDAO;

    public BanReason get(Integer id){
        return banReasonDAO.get(id);
    }

    public List<BanReason> getByName(String term) {
        return banReasonDAO.getByName(term);
    }

    public void create(BanReason banReason){
    	banReasonDAO.save(banReason);
    }

    public void update(BanReason banReason){
    	banReasonDAO.update(banReason);
    }

    public void delete(Integer id){
    	banReasonDAO.delete(id);
    }

    public List<BanReason> getAll(){
        return banReasonDAO.getAll();
    }
}
