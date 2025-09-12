package com.nishtha.ExpenseSplitter.service;

import com.nishtha.ExpenseSplitter.entity.Group;
import com.nishtha.ExpenseSplitter.entity.User;
import com.nishtha.ExpenseSplitter.repository.groupRepository;
import com.nishtha.ExpenseSplitter.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class groupService {

    @Autowired
    private groupRepository groupRepository;

    @Autowired
    private userRepository userRepository;

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(String id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Group addMemberToGroup(String groupId,String userId){
        Group group=getGroupById(groupId);
        User user=userRepository.findById(userId).orElse(null);

        if(group!=null && user!=null && !group.getMemberIds().contains(userId)){
            group.getMemberIds().add(userId);
            return groupRepository.save(group);
        }

        return group;
    }

    public Group removeMemberFromGroup(String groupId,String userId){
        Group group=getGroupById(groupId);

        if(group!=null && group.getMemberIds().contains(userId)){
            group.getMemberIds().remove(userId);
            return groupRepository.save(group);
        }

        return group;
    }

    public Group updateGroupInfo(String groupId,Group updatedGroup){
        Group existingGroup=getGroupById(groupId);
        existingGroup.setGroupName(updatedGroup.getGroupName());
        existingGroup.setMemberIds(updatedGroup.getMemberIds());
        return groupRepository.save(updatedGroup);
    }

    public void deleteGroup(String groupId){
        if(!groupRepository.existsById(groupId)){
            throw new RuntimeException("Group Not Found");
        }
        groupRepository.deleteById(groupId);

    }


}