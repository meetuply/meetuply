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

    @GetMapping("/one/author={ban_authorId}&reported={reported_userId}&reason={ban_reasonId}")
    public Ban getOneBan(@PathVariable("ban_reasonId") Integer reason_id, @PathVariable("ban_authorId") Integer by_user_id, @PathVariable("reported_userId") Integer reported_user_id) {
        return banService.get(reason_id, by_user_id, reported_user_id);
    }

    @GetMapping("/reason={ban_reasonId}")
    public Iterable<Ban> getBansWithOneReason(@PathVariable("ban_reasonId") Integer reason_id) {
        return banService.getByReason(reason_id);
    }

    @GetMapping("/author={ban_authorId}")
    public Iterable<Ban> getBansWithOneAuthor(@PathVariable("ban_authorId") Integer by_user_id) {
        return banService.getByAuthor(by_user_id);
    }

    @GetMapping("/reported={reported_userId}")
    public Iterable<Ban> getBansWithOneReported(@PathVariable("reported_userId") Integer reported_user_id) {
        return banService.getByReported(reported_user_id);
    }

    @PostMapping("/create/reported={reported_userId}&reason={ban_reasonId}")
    public ResponseEntity<Ban> createNewBan(@PathVariable("ban_reasonId") Integer ban_reason_id,
            @PathVariable("reported_userId") Integer reported_user_id, @Valid @RequestBody Ban ban){
        if(banReasonService.get(ban_reason_id) == null || appUserService.getUser(reported_user_id) == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            banService.create(ban, ban_reason_id, reported_user_id);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/reported={reported_userId}&reason={ban_reasonId}")
    public ResponseEntity<Ban> updateBan(@PathVariable("ban_reasonId") Integer ban_reason_id,
        @PathVariable("reported_userId") Integer reported_user_id, @RequestBody Ban ban) {
        Integer by_user_id = appUserService.getCurrentUserID();
        if (banService.get(ban_reason_id, by_user_id, reported_user_id) == null) {
            ResponseEntity.badRequest().build();
        }
        if(banReasonService.get(ban_reason_id) == null || appUserService.getUser(reported_user_id) == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            ban.setBanReason(banReasonService.get(ban_reason_id));
            ban.setAuthor(appUserService.getUser(by_user_id));
            ban.setReported(appUserService.getUser(reported_user_id));
            banService.update(ban);
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/author={ban_authorId}&reported={reported_userId}&reason={ban_reasonId}")
    public ResponseEntity<Ban> deleteBan(@PathVariable("ban_reasonId") Integer reason_id,
        @PathVariable("ban_authorId") Integer by_user_id, @PathVariable("reported_userId") Integer reported_user_id){
        if (banService.get(reason_id, by_user_id, reported_user_id) == null) {
            ResponseEntity.badRequest().build();
        }
        banService.delete(reason_id, by_user_id, reported_user_id);
        return ResponseEntity.ok().build();
    }
}
