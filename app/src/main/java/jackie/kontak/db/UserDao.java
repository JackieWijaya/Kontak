package jackie.kontak.db;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT*FROM users")
    List<User> getAllUser();

    @Update
    int updateUser(User user);

    @Query("DELETE FROM users WHERE id=:userId")
    int deleteUser(int userId);
}
