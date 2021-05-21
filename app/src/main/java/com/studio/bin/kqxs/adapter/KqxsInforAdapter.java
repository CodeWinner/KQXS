package com.studio.bin.kqxs.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.bin.kqxs.R;
import com.studio.bin.kqxs.entity.KqxsEntity;

public class KqxsInforAdapter extends RecyclerView.Adapter {

    private KqxsEntity kqxsEntity;
    private Context context;

    private int countTypeMNAndMT = 0;
    public KqxsInforAdapter(KqxsEntity kqxsEntity, Context context) {
        this.kqxsEntity = kqxsEntity;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        Log.i("VIEW_TYPE", viewType + "");
        Log.i("VIEW_TYPE", "Count : " + getItemCount());
        switch (viewType) {
            case 0:
                View kqxsViewMB = inflater.inflate(R.layout.adapter_kqxs_mien_bac, parent, false);
                return new ViewHolderKqxsMB(kqxsViewMB);
            case 1:
                View kqxsViewMT = inflater.inflate(R.layout.adapter_kqxs_mien_trung, parent, false);
                return new ViewHolderKqxsMT(kqxsViewMT);
            case 2:
                View kqxsViewMN = inflater.inflate(R.layout.adapter_kqxs_mien_nam, parent, false);
                return new ViewHolderKqxsMN(kqxsViewMN);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i("VIEW_TYPE_2", holder.getItemViewType() + "");
        Log.i("VIEW_TYPE", "position : " + position);
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolderKqxsMB viewHolderKqxsMB = (ViewHolderKqxsMB) holder;
                String dateKqxsMB = kqxsEntity.getKqxsmb().getData().get(0).get(0).getDateKqxs();
                if (dateKqxsMB != null) {
                    dateKqxsMB = dateKqxsMB.substring(dateKqxsMB.length() - 10, dateKqxsMB.length());
                }
                ((ViewHolderKqxsMB) holder).titleKqxsmb.setText("Kết quả xổ số miền bắc ngày : " + dateKqxsMB);
                ((ViewHolderKqxsMB) holder).kqxsmb0.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(0).getValues()));
                ((ViewHolderKqxsMB) holder).kqxsmb1.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(1).getValues()));
                ((ViewHolderKqxsMB) holder).kqxsmb2.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(2).getValues()));
                ((ViewHolderKqxsMB) holder).kqxsmb3.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(3).getValues()));
                ((ViewHolderKqxsMB) holder).kqxsmb4.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(4).getValues()));
                ((ViewHolderKqxsMB) holder).kqxsmb5.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(5).getValues()));
                ((ViewHolderKqxsMB) holder).kqxsmb6.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(6).getValues()));
                ((ViewHolderKqxsMB) holder).kqxsmb7.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(7).getValues()));

                ((ViewHolderKqxsMB) holder).txtMb0.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(0).getName()));
                ((ViewHolderKqxsMB) holder).txtMb1.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(1).getName()));
                ((ViewHolderKqxsMB) holder).txtMb2.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(2).getName()));
                ((ViewHolderKqxsMB) holder).txtMb3.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(3).getName()));
                ((ViewHolderKqxsMB) holder).txtMb4.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(4).getName()));
                ((ViewHolderKqxsMB) holder).txtMb5.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(5).getName()));
                ((ViewHolderKqxsMB) holder).txtMb6.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(6).getName()));
                ((ViewHolderKqxsMB) holder).txtMb7.setText(replaceComma(kqxsEntity.getKqxsmb().getData().get(0).get(7).getName()));

                countTypeMNAndMT = holder.getItemViewType();
                break;
            case 1:
                Log.i("VIEW_TYPE_2", holder.getItemViewType() + "");
                ViewHolderKqxsMT viewHolderKqxsMT = (ViewHolderKqxsMT) holder;
                String dateKqxsMT = kqxsEntity.getKqxsmt().getData().get(0).get(0).getDateKqxs();
                if (dateKqxsMT != null) {
                    dateKqxsMT = dateKqxsMT.substring(dateKqxsMT.length() - 10, dateKqxsMT.length());
                }

                int positionMt = position - 1;
                if (countTypeMNAndMT != holder.getItemViewType()) {
                    ((ViewHolderKqxsMT) holder).titleKqxsmt.setText("Kết quả xổ số miền trung ngày : " + dateKqxsMT);
                }
                ((ViewHolderKqxsMT) holder).locationName.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(0).getLocation()));
                ((ViewHolderKqxsMT) holder).kqxsmt0.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(0).getValues()));
                ((ViewHolderKqxsMT) holder).kqxsmt1.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(1).getValues()));
                ((ViewHolderKqxsMT) holder).kqxsmt2.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(2).getValues()));
                ((ViewHolderKqxsMT) holder).kqxsmt3.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(3).getValues()));
                ((ViewHolderKqxsMT) holder).kqxsmt4.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(4).getValues()));
                ((ViewHolderKqxsMT) holder).kqxsmt5.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(5).getValues()));
                ((ViewHolderKqxsMT) holder).kqxsmt6.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(6).getValues()));
                ((ViewHolderKqxsMT) holder).kqxsmt7.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(7).getValues()));
                ((ViewHolderKqxsMT) holder).kqxsmt8.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(8).getValues()));

                ((ViewHolderKqxsMT) holder).txtMt0.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(0).getName()));
                ((ViewHolderKqxsMT) holder).txtMt1.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(1).getName()));
                ((ViewHolderKqxsMT) holder).txtMt2.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(2).getName()));
                ((ViewHolderKqxsMT) holder).txtMt3.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(3).getName()));
                ((ViewHolderKqxsMT) holder).txtMt4.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(4).getName()));
                ((ViewHolderKqxsMT) holder).txtMt5.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(5).getName()));
                ((ViewHolderKqxsMT) holder).txtMt6.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(6).getName()));
                ((ViewHolderKqxsMT) holder).txtMt7.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(7).getName()));
                ((ViewHolderKqxsMT) holder).txtMt8.setText(replaceComma(kqxsEntity.getKqxsmt().getData().get(positionMt).get(8).getName()));

                countTypeMNAndMT = holder.getItemViewType();
                break;
            case 2:
                Log.i("VIEW_TYPE_3", holder.getItemViewType() + "");
                ViewHolderKqxsMN viewHolderKqxsMN = (ViewHolderKqxsMN) holder;
                String dateKqxsMN = kqxsEntity.getKqxsmn().getData().get(0).get(0).getDateKqxs();
                if (dateKqxsMN != null) {
                    dateKqxsMN = dateKqxsMN.substring(dateKqxsMN.length() - 10, dateKqxsMN.length());
                }

                int positionMN = position - (1 + kqxsEntity.getKqxsmt().getCount());
                if (countTypeMNAndMT != holder.getItemViewType()) {
                    ((ViewHolderKqxsMN) holder).titleKqxsmn.setText("Kết quả xổ số miền nam ngày : " + dateKqxsMN);
                }
                ((ViewHolderKqxsMN) holder).locationName.setText(kqxsEntity.getKqxsmn().getData().get(positionMN).get(0).getLocation());
                ((ViewHolderKqxsMN) holder).kqxsmn0.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(0).getValues()));
                ((ViewHolderKqxsMN) holder).kqxsmn1.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(1).getValues()));
                ((ViewHolderKqxsMN) holder).kqxsmn2.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(2).getValues()));
                ((ViewHolderKqxsMN) holder).kqxsmn3.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(3).getValues()));
                ((ViewHolderKqxsMN) holder).kqxsmn4.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(4).getValues()));
                ((ViewHolderKqxsMN) holder).kqxsmn5.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(5).getValues()));
                ((ViewHolderKqxsMN) holder).kqxsmn6.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(6).getValues()));
                ((ViewHolderKqxsMN) holder).kqxsmn7.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(7).getValues()));
                ((ViewHolderKqxsMN) holder).kqxsmn8.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(8).getValues()));

                ((ViewHolderKqxsMN) holder).txtMn0.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(0).getName()));
                ((ViewHolderKqxsMN) holder).txtMn1.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(1).getName()));
                ((ViewHolderKqxsMN) holder).txtMn2.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(2).getName()));
                ((ViewHolderKqxsMN) holder).txtMn3.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(3).getName()));
                ((ViewHolderKqxsMN) holder).txtMn4.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(4).getName()));
                ((ViewHolderKqxsMN) holder).txtMn5.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(5).getName()));
                ((ViewHolderKqxsMN) holder).txtMn6.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(6).getName()));
                ((ViewHolderKqxsMN) holder).txtMn7.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(7).getName()));
                ((ViewHolderKqxsMN) holder).txtMn8.setText(replaceComma(kqxsEntity.getKqxsmn().getData().get(positionMN).get(8).getName()));

                countTypeMNAndMT = holder.getItemViewType();
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            // Type Kqxs MB
            return 0;
        } else {
            if (position > 0 && position < (getItemCount() - kqxsEntity.getKqxsmn().getCount())) {
                // Kqxs MT
                return 1;
            }
        }

        // Kqxs MN
        return 2;
    }

    @Override
    public int getItemCount() {
        return (kqxsEntity.getKqxsmb().getCount()
                + kqxsEntity.getKqxsmt().getCount()
                + kqxsEntity.getKqxsmn().getCount());
    }

    public String replaceComma(String rep) {
        if (rep != null && rep.contains(",")) {
            return rep.replace(",", " - ");
        }

        return rep;
    }

    public class ViewHolderKqxsMB extends RecyclerView.ViewHolder {
        private TextView titleKqxsmb, kqxsmb0, kqxsmb1, kqxsmb2, kqxsmb3, kqxsmb4, kqxsmb5, kqxsmb6, kqxsmb7;
        private TextView txtMb0, txtMb1, txtMb2, txtMb3, txtMb4, txtMb5, txtMb6, txtMb7;
        public ViewHolderKqxsMB(@NonNull View itemView) {
            super(itemView);
            titleKqxsmb = itemView.findViewById(R.id.titleKqxsmb);
            kqxsmb0 = itemView.findViewById(R.id.kqxsmb0);
            kqxsmb1 = itemView.findViewById(R.id.kqxsmb1);
            kqxsmb2 = itemView.findViewById(R.id.kqxsmb2);
            kqxsmb3 = itemView.findViewById(R.id.kqxsmb3);
            kqxsmb4 = itemView.findViewById(R.id.kqxsmb4);
            kqxsmb5 = itemView.findViewById(R.id.kqxsmb5);
            kqxsmb6 = itemView.findViewById(R.id.kqxsmb6);
            kqxsmb7 = itemView.findViewById(R.id.kqxsmb7);

            txtMb0 = itemView.findViewById(R.id.txtMb0);
            txtMb1 = itemView.findViewById(R.id.txtMb1);
            txtMb2 = itemView.findViewById(R.id.txtMb2);
            txtMb3 = itemView.findViewById(R.id.txtMb3);
            txtMb4 = itemView.findViewById(R.id.txtMb4);
            txtMb5 = itemView.findViewById(R.id.txtMb5);
            txtMb6 = itemView.findViewById(R.id.txtMb6);
            txtMb7 = itemView.findViewById(R.id.txtMb7);

        }
    }

    public class ViewHolderKqxsMT extends RecyclerView.ViewHolder {
        private TextView titleKqxsmt, kqxsmt0, kqxsmt1, kqxsmt2, kqxsmt3, kqxsmt4, kqxsmt5, kqxsmt6, kqxsmt7, kqxsmt8, locationName;
        private TextView txtMt0, txtMt1, txtMt2, txtMt3, txtMt4, txtMt5, txtMt6, txtMt7, txtMt8;
        public ViewHolderKqxsMT(@NonNull View itemView) {
            super(itemView);
            titleKqxsmt = itemView.findViewById(R.id.titleKqxsmt);
            locationName = itemView.findViewById(R.id.locationName);
            kqxsmt0 = itemView.findViewById(R.id.kqxsmt0);
            kqxsmt1 = itemView.findViewById(R.id.kqxsmt1);
            kqxsmt2 = itemView.findViewById(R.id.kqxsmt2);
            kqxsmt3 = itemView.findViewById(R.id.kqxsmt3);
            kqxsmt4 = itemView.findViewById(R.id.kqxsmt4);
            kqxsmt5 = itemView.findViewById(R.id.kqxsmt5);
            kqxsmt6 = itemView.findViewById(R.id.kqxsmt6);
            kqxsmt7 = itemView.findViewById(R.id.kqxsmt7);
            kqxsmt8 = itemView.findViewById(R.id.kqxsmt8);

            txtMt0 = itemView.findViewById(R.id.txtMt0);
            txtMt1 = itemView.findViewById(R.id.txtMt1);
            txtMt2 = itemView.findViewById(R.id.txtMt2);
            txtMt3 = itemView.findViewById(R.id.txtMt3);
            txtMt4 = itemView.findViewById(R.id.txtMt4);
            txtMt5 = itemView.findViewById(R.id.txtMt5);
            txtMt6 = itemView.findViewById(R.id.txtMt6);
            txtMt7 = itemView.findViewById(R.id.txtMt7);
            txtMt8 = itemView.findViewById(R.id.txtMt8);
        }
    }

    public class ViewHolderKqxsMN extends RecyclerView.ViewHolder {
        private TextView titleKqxsmn, kqxsmn0, kqxsmn1, kqxsmn2, kqxsmn3, kqxsmn4, kqxsmn5, kqxsmn6, kqxsmn7, kqxsmn8, locationName;
        private TextView txtMn0, txtMn1, txtMn2, txtMn3, txtMn4, txtMn5, txtMn6, txtMn7, txtMn8;
        public ViewHolderKqxsMN(@NonNull View itemView) {
            super(itemView);
            titleKqxsmn = itemView.findViewById(R.id.titleKqxsmn);
            locationName = itemView.findViewById(R.id.locationName);
            kqxsmn0 = itemView.findViewById(R.id.kqxsmn0);
            kqxsmn1 = itemView.findViewById(R.id.kqxsmn1);
            kqxsmn2 = itemView.findViewById(R.id.kqxsmn2);
            kqxsmn3 = itemView.findViewById(R.id.kqxsmn3);
            kqxsmn4 = itemView.findViewById(R.id.kqxsmn4);
            kqxsmn5 = itemView.findViewById(R.id.kqxsmn5);
            kqxsmn6 = itemView.findViewById(R.id.kqxsmn6);
            kqxsmn7 = itemView.findViewById(R.id.kqxsmn7);
            kqxsmn8 = itemView.findViewById(R.id.kqxsmn8);

            txtMn0 = itemView.findViewById(R.id.txtMn0);
            txtMn1 = itemView.findViewById(R.id.txtMn1);
            txtMn2 = itemView.findViewById(R.id.txtMn2);
            txtMn3 = itemView.findViewById(R.id.txtMn3);
            txtMn4 = itemView.findViewById(R.id.txtMn4);
            txtMn5 = itemView.findViewById(R.id.txtMn5);
            txtMn6 = itemView.findViewById(R.id.txtMn6);
            txtMn7 = itemView.findViewById(R.id.txtMn7);
            txtMn8 = itemView.findViewById(R.id.txtMn8);
        }
    }
}
