package jackie.kontak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import jackie.kontak.databinding.ActivityMainBinding;
import jackie.kontak.db.User;
import jackie.kontak.loaders.DeleteLoader;
import jackie.kontak.loaders.GetDataLoader;

public class MainActivity extends AppCompatActivity {
    private static final int DATA_LOADER_CODE = 123;
    private static final int DELETE_LOADER_CODE = 124;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        showProgressBar();
        LoaderManager.getInstance(this).restartLoader(DATA_LOADER_CODE, null, new LoaderManager.LoaderCallbacks<List<User>>() {
            @NonNull
            @Override
            public Loader<List<User>> onCreateLoader(int id, @Nullable Bundle args) {
                return new GetDataLoader(MainActivity.this);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<List<User>> loader, List<User> data) {
                hideProgressBar();
                initAdapter(data);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<List<User>> loader) {

            }
        }).forceLoad();
    }

    private void initAdapter(List<User> data) {
        KontakViewAdapter kontakViewAdapter = new KontakViewAdapter();
        binding.rvKontak.setLayoutManager(new LinearLayoutManager(this));
        binding.rvKontak.setAdapter(kontakViewAdapter);
        kontakViewAdapter.setData(data);
        kontakViewAdapter.setOnClickListener(new KontakViewAdapter.OnClickListener() {
            @Override
            public void onEditClicked() {

            }

            @Override
            public void onDeleteClicked(int userId) {
                deleteUser(userId);
            }
        });
    }

    private void deleteUser(int userId) {
        showProgressBar();
        Bundle args = new Bundle();
        args.putInt("id", userId);
        LoaderManager.getInstance(this).restartLoader(DELETE_LOADER_CODE, args, new LoaderManager.LoaderCallbacks<Integer>() {
            @NonNull
            @Override
            public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
                return new DeleteLoader(MainActivity.this, args.getInt("id"));
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
                hideProgressBar();
                if (data != -1){
                    itemDelete();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Integer> loader) {

            }
        }).forceLoad();
    }

    private void itemDelete() {
        Toast.makeText(this, "User Deleted !", Toast.LENGTH_SHORT).show();
        getData();
    }

    private void hideProgressBar(){
        binding.progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
    }
}