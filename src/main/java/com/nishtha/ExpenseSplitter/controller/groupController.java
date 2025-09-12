package com.nishtha.ExpenseSplitter.controller;

import com.nishtha.ExpenseSplitter.entity.Group;
import com.nishtha.ExpenseSplitter.entity.User;
import com.nishtha.ExpenseSplitter.repository.groupRepository;
import com.nishtha.ExpenseSplitter.service.groupService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/split/group")
public class groupController {

    @Autowired
    private groupService groupService;

    @Autowired
    private groupRepository groupRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable String groupId){
        return ResponseEntity.ok(groupService.getGroupById(groupId));
    }

    @PostMapping("/create-group")
    public ResponseEntity<Group> createGroup(@RequestBody Group group){
        return ResponseEntity.ok(groupService.createGroup(group));
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups(){
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable String groupId, @RequestBody Group updatedGroup) {
        return ResponseEntity.ok(groupService.updateGroupInfo(groupId, updatedGroup));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String groupId){
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/add-member/{member-id}")
    public ResponseEntity<Group> addMember(@PathVariable String groupId,@PathVariable String userId){
        return ResponseEntity.ok(groupService.addMemberToGroup(groupId,userId));
    }

    @DeleteMapping("{id}/delete-member/{member-id}")
    public ResponseEntity<Group> deleteMember(@PathVariable String groupId,@PathVariable String userId){
        return ResponseEntity.ok(groupService.removeMemberFromGroup(groupId,userId));

    }

}
