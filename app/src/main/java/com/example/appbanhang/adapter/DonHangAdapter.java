package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.DonHang;
import com.example.appbanhang.utils.Utils;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool= new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> listdonhang;

    public DonHangAdapter(Context context, List<DonHang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang= listdonhang.get(position);
        holder.txtdonhang.setText("Đơn hàng: "+donHang.getId());

//        holder.txttenuserdonhang.setText(Utils.user_current.getUsername());
//        holder.txtsodienthoaidonhang.setText(Utils.user_current.getMobile());
//        holder.txtemaildonhang.setText(Utils.user_current.getEmail());
//        holder.txtdiachidonhang.setText(Utils.user_current.getDiachi());

        LinearLayoutManager layoutManager= new LinearLayoutManager(
                holder.reChitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        // adapter chi tiet
        ChitietAdapter chitietAdapter= new ChitietAdapter(context,donHang.getItem());
        holder.reChitiet.setLayoutManager(layoutManager);
        holder.reChitiet.setAdapter(chitietAdapter);
        // vi RecycledView nam trong RecycledView
        holder.reChitiet.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtdonhang,txttenuserdonhang,txtsodienthoaidonhang,txtdiachidonhang,txtemaildonhang;
        RecyclerView reChitiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang=itemView.findViewById(R.id.iddonhang);

//            txttenuserdonhang=itemView.findViewById(R.id.tenuserdonhang);
//            txtsodienthoaidonhang=itemView.findViewById(R.id.sodienthoaidonhang);
//            txtemaildonhang=itemView.findViewById(R.id.emaildonhang);
//            txtdiachidonhang=itemView.findViewById(R.id.diachidonhang);
            reChitiet=itemView.findViewById(R.id.recyclerview_chitiet);

        }
    }
}
