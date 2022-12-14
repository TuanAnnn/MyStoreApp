package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.IImageClickListenner;
import com.example.appbanhang.R;
import com.example.appbanhang.model.EventBus.TinhTongEvent;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {

    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        GioHang gioHang= gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        // so luong Interger loi hay gap phai
        holder.item_giohang_soluong.setText(gioHang.getSoluong()+"");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format((gioHang.getGiasp())));
        long gia =gioHang.getSoluong()*gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Utils.mangmuahang.add(gioHang);
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }else {
                    for(int i=0;i<Utils.mangmuahang.size();i++){
                        if(Utils.mangmuahang.get(i).getIdsp()==gioHang.getIdsp()){
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    }
                }
            }
        });
        holder.setListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if(giatri==1){
                    if(gioHangList.get(pos).getSoluong()>1){
                        // sluong>1 cho phep tru
                        int soluongmoi= gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong()+"");
                        long gia =gioHangList.get(pos).getSoluong()*gioHang.getGiasp();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                        // dung thu vien Eventbus gui du kien
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else if(gioHangList.get(pos).getSoluong()==1){
                        AlertDialog.Builder builder= new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Th??ng b??o");
                        builder.setMessage("B???n c?? mu???n x??a s???n ph???m kh???i gi??? h??ng");
                        builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                // gui tinh tong tien
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();

                    }

                }else if(giatri==2){
                    if(gioHangList.get(pos).getSoluong()<10){
                        int soluongmoi= gioHangList.get(pos).getSoluong()+1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                    holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong()+"");
                    long gia =gioHangList.get(pos).getSoluong()*gioHang.getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                    // dung thu vien Eventbus gui du kien
                    EventBus.getDefault().postSticky(new TinhTongEvent());

                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView item_giohang_image,img_tru,img_cong;
        TextView item_giohang_tensp,item_giohang_gia,item_giohang_soluong,item_giohang_giasp2;
        IImageClickListenner listenner;
        CheckBox checkBox;

        //tincoder
        TextView tvName;
        LinearLayout foreGround;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_giohang_image =itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp =itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia =itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong =itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 =itemView.findViewById(R.id.item_giohang_giasp2);
            img_tru =itemView.findViewById(R.id.item_giohang_tru);
            img_cong =itemView.findViewById(R.id.item_giohang_cong);
            checkBox=itemView.findViewById(R.id.item_giohang_check);
            //tincoder
           /* tvName=itemView.findViewById(R.id.tv_xoa);
            foreGround=itemView.findViewById(R.id.layout_foreground);*/


            // event click
            img_cong.setOnClickListener(this);
            img_tru.setOnClickListener(this);


        }

        public void setListenner(IImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if(view == img_tru){
                listenner.onImageClick(view,getAdapterPosition(),1);
                // 1 la tru

            }else if(view ==img_cong){
                listenner.onImageClick(view,getAdapterPosition(),2);
                // 2 la cong
            }
        }
    }
}
