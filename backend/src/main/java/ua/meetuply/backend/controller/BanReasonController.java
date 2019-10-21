package ua.meetuply.backend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ua.meetuply.backend.model.BanReason;
import ua.meetuply.backend.service.BanReasonService;

@RequestMapping("api/ban_reasons")
@Transactional
@RestController
public class BanReasonController {
	
	@Autowired
	private BanReasonService banReasonService;
	
	@GetMapping()
    public @ResponseBody Iterable<BanReason> getAllBanReasons() {
        return banReasonService.getAll();
    }
	
	@GetMapping("/{ban_reasonId}")
	public BanReason getOneBanReason(@PathVariable("ban_reasonId") Integer banReasonId) {
	    return banReasonService.get(banReasonId);
	}
	
	@PostMapping("/create")
    public ResponseEntity<BanReason> createNewBanReason(@Valid @RequestBody BanReason banReason){
		banReasonService.create(banReason);
		return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{ban_reasonId}")
    public ResponseEntity<BanReason> updateBanReason(@PathVariable("ban_reasonId") Integer banReasonId, @RequestBody BanReason banReason) {
    	if (banReasonService.get(banReasonId) == null) {
            ResponseEntity.badRequest().build();
        }
		banReason.setBanReasonId(banReasonId);
    	banReasonService.update(banReason);
    	return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ban_reasonId}")
    public ResponseEntity<BanReason> deleteBanReason(@PathVariable("ban_reasonId") Integer banReasonId){
    	if (banReasonService.get(banReasonId) == null) {
            ResponseEntity.badRequest().build();
        }
    	banReasonService.delete(banReasonId);
    	return ResponseEntity.ok().build();
    }
}
