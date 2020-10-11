package ir.maktab.repository;

import ir.maktab.base.repository.BaseRepository;
import ir.maktab.domains.User;

public interface UserRepository extends BaseRepository<User,Long> {
    User findByUserName(String userName);

    User userLogin(String userName, String password);
}
