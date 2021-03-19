package com.example.ateam_app.irdnt_list_package;

import android.content.Context;
import android.graphics.Color;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.irdnt_list_package.fragment.IrdntListFragment;

import java.util.ArrayList;

public class IrdntListAdapter extends RecyclerView.Adapter<IrdntListAdapter.ViewHolder>
        implements OnIrdntItemClickListener, OnIrdntItemLongClickListener, OnIrdntItemCheckListener {
    Context context;
    ArrayList<IrdntListDTO> items;
    static OnIrdntItemClickListener listener;
    static OnIrdntItemLongClickListener longClickListener;
    static OnIrdntItemCheckListener checkListener;
    ArrayList<Long> irdnt_ids, new_ids;
    boolean checkMode;

    public IrdntListAdapter(Context context, ArrayList<IrdntListDTO> items, ArrayList<Long> irdnt_ids, ArrayList<Long> new_ids, boolean checkMode) {
        this.context = context;
        this.items = items;
        this.irdnt_ids = irdnt_ids;
        this.new_ids = new_ids;
        this.checkMode = checkMode;
    }

    //화면 연결
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.irdntview, viewGroup, false);

        return new ViewHolder(itemView, listener, longClickListener, checkListener);
    }

    //데이터 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final IrdntListDTO item = items.get(position);

        // 먼저 체크박스의 리스너를 null로 초기화한다
        holder.checkBox.setOnCheckedChangeListener(null);

        // 모델 클래스의 getter로 체크 상태값을 가져온 다음, setter를 통해 이 값을 아이템 안의 체크박스에 set한다
        holder.checkBox.setChecked(item.isCheck());

        // 체크박스의 상태값을 알기 위해 리스너 부착
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                // 여기의 item은 final 키워드를 붙인 모델 클래스의 객체와 동일하다
                item.setCheck(isChecked);
            }
        });

        holder.irdnt_layout.setBackgroundColor(Color.parseColor("#D9D9D9"));
        holder.shelf_life_end.setTextColor(Color.parseColor("#000000"));
        if(irdnt_ids == null && new_ids == null){
            holder.setItem(item, checkMode);
        }if(irdnt_ids != null){
            holder.setItem(item, irdnt_ids, 0, checkMode);
        }if(new_ids != null){
            holder.setItem(item, new_ids, 1, checkMode);
        }
    }

    public IrdntListDTO getItem(int position) { return items.get(position); }

    public void setOnItemClickListener(OnIrdntItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnIrdntItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setOnItemCheckListender(OnIrdntItemCheckListener checkListener){
        this.checkListener = checkListener;
    }

    public void addItem(IrdntListDTO item){
        items.add(item);
    }

    public void setItems(ArrayList<IrdntListDTO> items){
        this.items = items;
    }

    public void setItem(int position, IrdntListDTO item){
        items.set(position, item);
    }

    @Override
    public int getItemCount() {
        return (items != null ? items.size() : 0);
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) listener.onItemClick(holder, view, position);
    }

    @Override
    public void onItemLongClick(ViewHolder holder, View view, int position) {
        if(longClickListener != null ) longClickListener.onItemLongClick(holder, view, position);
    }

    @Override
    public void onItemCheck(ViewHolder holder, View view, int position, CompoundButton buttonView, boolean isChecked) {
        if(checkListener != null) checkListener.onItemCheck(holder, view, position, buttonView, isChecked);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView content_list_id, content_nm, shelf_life_end;
        public ImageView content_ty;
        public LinearLayout irdnt_layout;
        public CheckBox checkBox;

        public ViewHolder(@NonNull View itemView, OnIrdntItemClickListener listener,
                          OnIrdntItemLongClickListener longClickListener,
                          OnIrdntItemCheckListener checkListener) {
            super(itemView);

            irdnt_layout = itemView.findViewById(R.id.irdnt_layout);
//            content_list_id = itemView.findViewById(R.id.content_list_id);
            content_nm = itemView.findViewById(R.id.content_nm);
            content_ty = itemView.findViewById(R.id.content_ty);
            shelf_life_end = itemView.findViewById(R.id.shelf_life_end);
            checkBox = itemView.findViewById(R.id.checkBox);

            //재료리스트에서 재료를 눌렀을 때 이벤트
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, items.get(position).getContent_nm() + ", " + items.get(position).getContent_ty(), Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, itemView, position);
                    }
                }
            });

            //아이템을 길게 눌렀을때 이벤트
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (longClickListener != null) {
                        longClickListener.onItemLongClick(ViewHolder.this, itemView, position);
                    }
                    return true;
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(buttonView.getId() == R.id.checkBox){
                        int position = getAdapterPosition();
                        if (checkListener != null) {
                            checkListener.onItemCheck(ViewHolder.this, itemView, position, buttonView, isChecked);
                        }
                    }
                }
            });
        }

        public void setItem(IrdntListDTO dto, ArrayList<Long> ids, int mode, boolean checkMode) {
            if (mode == 0){
                for (Long id : ids) {
                    if(id == dto.getContent_list_id()){
                        irdnt_layout.setBackgroundResource(R.drawable.redcard_background_drawer);
                        shelf_life_end.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }else{
                for (Long id : ids) {
                    if(id == dto.getContent_list_id()){
                        irdnt_layout.setBackgroundResource(R.drawable.yellowcard_background_drawer);
                        shelf_life_end.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }

            checkModeMethod(checkMode); //체크모드

            //content_list_id.setText(dto.getContent_list_id());
            content_nm.setText(dto.getContent_nm());
            if(dto.getContent_ty().equals("고기")) {
                content_ty.setImageResource(R.drawable.icon_meat);
            } else if (dto.getContent_ty().equals("수산물")) {
                content_ty.setImageResource(R.drawable.icon_fish);
            } else if (dto.getContent_ty().equals("채소")) {
                content_ty.setImageResource(R.drawable.icon_vegetable);
            } else if (dto.getContent_ty().equals("과일")) {
                content_ty.setImageResource(R.drawable.icon_fruit);
            } else if (dto.getContent_ty().equals("유제품")) {
                content_ty.setImageResource(R.drawable.icon_milk);
            } else if (dto.getContent_ty().equals("곡류")) {
                content_ty.setImageResource(R.drawable.icon_grain);
            } else if (dto.getContent_ty().equals("조미료/주류")) {
                content_ty.setImageResource(R.drawable.icon_seasoning);
            } else if (dto.getContent_ty().equals("음료/기타")) {
                content_ty.setImageResource(R.drawable.icon_beverage);
            } else if (dto.getContent_ty().equals("미분류")) {
                content_ty.setImageResource(R.drawable.icon_unknown);
            }
            shelf_life_end.setText(dto.getShelf_life_end());
        }
        public void setItem(IrdntListDTO dto, boolean checkMode) {
            checkModeMethod(checkMode); //체크모드

            //content_list_id.setText(dto.getContent_list_id());
            content_nm.setText(dto.getContent_nm());
//            content_ty.setText(dto.getContent_ty());
            shelf_life_end.setText(dto.getShelf_life_end());
        }

        public void checkModeMethod(boolean checkMode){
            if(checkMode) {
                checkBox.setVisibility(View.VISIBLE);
            } else  {
                checkBox.setVisibility(View.GONE);
                checkBox.setChecked(false);
            }
        }
    }
}
