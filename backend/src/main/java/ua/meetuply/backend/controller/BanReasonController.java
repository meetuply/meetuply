package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.BanReason;
import ua.meetuply.backend.service.BanReasonService;

import javax.validation.Valid;

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
	
	@GetMapping("/{banReasonId}")
	public BanReason getOneBanReason(@PathVariable("banReasonId") Integer banReasonId) {
	    return banReasonService.get(banReasonId);
	}
	
	@PostMapping()
    public ResponseEntity createNewBanReason(@Valid @RequestBody BanReason banReason){
		banReasonService.create(banReason);
		return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{banReasonId}")
    public ResponseEntity updateBanReason(@PathVariable("banReasonId") Integer banReasonId, @RequestBody BanReason banReason) {
    	if (banReasonService.get(banReasonId) == null) {
            return ResponseEntity.notFound().build();
        }
		banReason.setBanReasonId(banReasonId);
    	banReasonService.update(banReason);
    	return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{banReasonId}")
    public ResponseEntity deleteBanReason(@PathVariable("banReasonId") Integer banReasonId){
    	if (banReasonService.get(banReasonId) == null) {
            return ResponseEntity.notFound().build();
        }
    	banReasonService.delete(banReasonId);
    	return ResponseEntity.ok().build();
    }
}
