package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.BanReasonDAO;
import ua.meetuply.backend.model.BanReason;

import java.util.ArrayList;
import java.util.List;

@Component
public class BanReasonService {
	
	@Autowired
    BanReasonDAO banReasonDAO;

    public BanReason get(Integer id){
        return banReasonDAO.get(id);
    }

    public List<BanReason> getByName(String name){
        List<BanReason> banReasons = banReasonDAO.getAll();
        List<BanReason> result = new ArrayList<BanReason>();
        for (BanReason banReason : banReasons) {
            if(banReason.getName().contains(name)) result.add(banReason);
        }
        return result;
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
