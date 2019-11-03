package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.Ban;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.BanReasonService;
import ua.meetuply.backend.service.BanService;

import javax.validation.Valid;

@RequestMapping("api/bans")
@Transactional
@RestController
public class BanController {

    @Autowired
    private BanService banService;

    @Autowired
    private BanReasonService banReasonService;

    @Autowired
    private AppUserService appUserService;

    @GetMapping()
    public @ResponseBody Iterable<Ban> getAllBans() {
        return banService.getAll();
    }

    @GetMapping("/one/author={banAuthorId}&reported={reportedUserId}&reason={banReasonId}")
    public Ban getOneBan(@PathVariable("banReasonId") Integer banReasonId, @PathVariable("banAuthorId") Integer banAuthorId, @PathVariable("reportedUserId") Integer reportedUserId) {
        return banService.get(banReasonId, banAuthorId, reportedUserId);
    }

    @GetMapping("/reason={banReasonId}")
    public Iterable<Ban> getBansWithOneReason(@PathVariable("banReasonId") Integer banReasonId) {
        return banService.getByReason(banReasonId);
    }

    @GetMapping("/author={banAuthorId}")
    public Iterable<Ban> getBansWithOneAuthor(@PathVariable("banAuthorId") Integer banAuthorId) {
        return banService.getByAuthor(banAuthorId);
    }

    @GetMapping("/reported={reportedUserId}")
    public Iterable<Ban> getBansWithOneReported(@PathVariable("reportedUserId") Integer reportedUserId) {
        return banService.getByReported(reportedUserId);
    }

    @PostMapping("/reported={reportedUserId}&reason={banReasonId}")
    public ResponseEntity createNewBan(@PathVariable("banReasonId") Integer banReasonId,
                                            @PathVariable("reportedUserId") Integer reportedUserId, @Valid @RequestBody Ban ban){
        if(banReasonService.get(banReasonId) == null || appUserService.getUser(reportedUserId) == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            banService.create(ban, banReasonId, reportedUserId);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/reported={reportedUserId}&reason={banReasonId}")
    public ResponseEntity updateBan(@PathVariable("banReasonId") Integer banReasonId,
                                         @PathVariable("reportedUserId") Integer reportedUserId, @RequestBody Ban ban) {
        Integer banAuthorId = appUserService.getCurrentUserID();
        if (banService.get(banReasonId, banAuthorId, reportedUserId) == null) {
            return ResponseEntity.notFound().build();
        }
 /*       if(banReasonService.get(banReasonId) == null || appUserService.getUser(reportedUserId) == null) {
            return ResponseEntity.notFound().build();
        }*/
        else {
            ban.setBanReason(banReasonService.get(banReasonId));
            ban.setAuthor(appUserService.getUser(banAuthorId));
            ban.setReported(appUserService.getUser(reportedUserId));
            banService.update(ban);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/author={banAuthorId}&reported={reportedUserId}&reason={banReasonId}")
    public ResponseEntity deleteBan(@PathVariable("banReasonId") Integer banReasonId,
                                         @PathVariable("banAuthorId") Integer banAuthorId, @PathVariable("reportedUserId") Integer reportedUserId){
        if (banService.get(banReasonId, banAuthorId, reportedUserId) == null) {
            return ResponseEntity.notFound().build();
        }
        banService.delete(banReasonId, banAuthorId, reportedUserId);
        return ResponseEntity.ok().build();
    }
}
