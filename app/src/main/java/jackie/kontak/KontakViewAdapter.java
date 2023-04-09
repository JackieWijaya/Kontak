package jackie.kontak;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import jackie.kontak.databinding.ItemKontakBinding;
import jackie.kontak.db.User;

public class KontakViewAdapter extends RecyclerView.Adapter<KontakViewAdapter.ViewHolder> {
    private List<User> data = new ArrayList<>();

    public void setData(List<User> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KontakViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemKontakBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KontakViewAdapter.ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        User user = data.get(pos);
        holder.itemKontakBinding.tvName.setText(user.getName());
        holder.itemKontakBinding.tvEmail.setText(user.getEmail());
        holder.itemKontakBinding.tvPhone.setText(user.getPhone());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemKontakBinding itemKontakBinding;

        public ViewHolder(@NonNull ItemKontakBinding itemView) {
            super(itemView.getRoot());
            itemKontakBinding = itemView;
        }
    }
}
