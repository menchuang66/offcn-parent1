package mc.user.service;

import mc.user.pojo.TMember;

public interface UserService {
    public void registerUser(TMember member);
    public TMember login(String username,String password);
}
