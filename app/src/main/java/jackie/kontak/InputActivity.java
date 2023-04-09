package jackie.kontak;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import jackie.kontak.databinding.ActivityInputBinding;
import jackie.kontak.db.User;
import jackie.kontak.loaders.InsertLoader;

public class InputActivity extends AppCompatActivity {
    private static final int INSERT_LOADER = 121;
    private static final int UPDATE_LOADER = 122;
    private ActivityInputBinding binding;
    private boolean editMode;
    private int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        editMode = getIntent().getBooleanExtra("edit", false);
        if (editMode) {
            User user = getIntent().getParcelableExtra("user");
            userId = user.getId();
            setDetails(user);
        }
    }
    private void setDetails(User user) {
        binding.etName.setText(user.getName());
        binding.etEmail.setText(user.getEmail());
        binding.etPhone.setText(user.getPhone());
        binding.btnAdd.setText("Update");
    }
    public  void insertUser(){
        User user = new User();
        user.setName(binding.etName.getText().toString());
        user.setEmail(binding.etEmail.getText().toString());
        user.setPhone(binding.etPhone.getText().toString());
        showProgressBar();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        LoaderManager.getInstance(this).restartLoader(INSERT_LOADER, args, new LoaderManager.LoaderCallbacks<Boolean>() {
            @NonNull
            @Override
            public Loader<Boolean> onCreateLoader(int id, @Nullable Bundle args) {
                return new InsertLoader(InputActivity.this, args.getParcelable("user"));
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean data) {
                hideProgressBar();
                if (data){
                    userAdded();
                }

            }

            @Override
            public void onLoaderReset(@NonNull Loader<Boolean> loader) {

            }
        }).forceLoad();
        
    }

    private void userAdded() {
        binding.etName.setText("");
        binding.etEmail.setText("");
        binding.etPhone.setText("");
        Toast.makeText(this, "User added to database!", Toast.LENGTH_SHORT).show();
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }
}