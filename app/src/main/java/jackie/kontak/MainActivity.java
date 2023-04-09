package jackie.kontak;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import jackie.kontak.databinding.ActivityMainBinding;
import jackie.kontak.db.User;
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
    }

    private void hideProgressBar(){
        binding.progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
    }
}