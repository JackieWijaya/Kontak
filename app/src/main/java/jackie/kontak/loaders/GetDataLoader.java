package jackie.kontak.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

import jackie.kontak.db.User;
import jackie.kontak.db.UserDataBase;

public class GetDataLoader extends AsyncTaskLoader<List<User>> {

    private UserDataBase db;

    public GetDataLoader(@NonNull Context context) {
        super(context);
        db = UserDataBase.getInstance(context);
    }

    @Nullable
    @Override
    public List<User> loadInBackground() {
        return db.userDao().getAllUser();
    }
}
