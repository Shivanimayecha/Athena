package com.athena.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.OrderDetailsTruckModel;
import com.athena.group.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDisplayAdapter extends RecyclerView.Adapter<OrderDisplayAdapter.MyViewHolder> {

    Context mContext;
    List<OrderDetailsTruckModel.OrderDetail> orderDetails;

    public OrderDisplayAdapter(Context mContext, List<OrderDetailsTruckModel.OrderDetail> orderDetails) {
        this.mContext = mContext;
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public OrderDisplayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDisplayAdapter.MyViewHolder holder, int position) {

        /*holder.img_productdup.setOnClickListener(view -> {
            if (holder.ll_expandable_product.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.card_product, new AutoTransition());
                holder.ll_expandable_product.setVisibility(View.VISIBLE);
                holder.img_productdup.setBackgroundResource(R.drawable.uparrow);
            } else {
                TransitionManager.beginDelayedTransition(holder.card_product, new AutoTransition());
                holder.ll_expandable_product.setVisibility(View.GONE);
                holder.img_productdup.setBackgroundResource(R.drawable.downarrow);
            }
        });*/

        holder.txt_productname.setText(orderDetails.get(position).getProductName());
        holder.txt_qty.setText(orderDetails.get(position).getOrdQty());
        if (!orderDetails.get(position).getOrdNotes().equals("")) {
            holder.card_notes.setVisibility(View.VISIBLE);
            holder.txt_notes.setText(orderDetails.get(position).getOrdNotes());
        } else {
            holder.card_notes.setVisibility(View.GONE);
        }

        if (orderDetails.get(position).getProductName().equals("Concrete")) {
            holder.txt_type.setVisibility(View.GONE);
            holder.txt_concrete_dringe.setVisibility(View.GONE);
            holder.txt_mesh.setVisibility(View.GONE);
            holder.txt_gnrlmtrl.setVisibility(View.GONE);
            holder.txt_stools.setVisibility(View.GONE);
            holder.txt_ppe.setVisibility(View.GONE);
            holder.txt_pdp.setVisibility(View.GONE);
            holder.txt_pr.setVisibility(View.GONE);
            holder.txt_pdw.setVisibility(View.GONE);
            holder.txt_claydrinage.setVisibility(View.GONE);
            holder.txt_kerbs.setVisibility(View.GONE);
            holder.txt_flags.setVisibility(View.GONE);
            holder.txt_blockpaves.setVisibility(View.GONE);
            holder.txt_aggsize.setVisibility(View.VISIBLE);
            holder.txt_slump.setVisibility(View.VISIBLE);
            holder.txt_mix.setVisibility(View.VISIBLE);

            holder.txt_mix.setText("Mix / " + orderDetails.get(position).getOrdAttributes().getMix());
            holder.txt_aggsize.setText("Aggregate Size / " + orderDetails.get(position).getOrdAttributes().getAggregateSize());
            holder.txt_slump.setText("Slump / " + orderDetails.get(position).getOrdAttributes().getSlump());

        } else if (orderDetails.get(position).getProductName().equals("Aggregates")) {
            holder.txt_type.setVisibility(View.VISIBLE);
            holder.txt_aggsize.setVisibility(View.GONE);
            holder.txt_slump.setVisibility(View.GONE);
            holder.txt_mix.setVisibility(View.GONE);
            holder.txt_concrete_dringe.setVisibility(View.GONE);
            holder.txt_mesh.setVisibility(View.GONE);
            holder.txt_gnrlmtrl.setVisibility(View.GONE);
            holder.txt_stools.setVisibility(View.GONE);
            holder.txt_ppe.setVisibility(View.GONE);
            holder.txt_pdp.setVisibility(View.GONE);
            holder.txt_pr.setVisibility(View.GONE);
            holder.txt_pdw.setVisibility(View.GONE);
            holder.txt_claydrinage.setVisibility(View.GONE);
            holder.txt_kerbs.setVisibility(View.GONE);
            holder.txt_flags.setVisibility(View.GONE);
            holder.txt_blockpaves.setVisibility(View.GONE);

            holder.txt_type.setText("Type / " + orderDetails.get(position).getOrdAttributes().getType());

        } else if (orderDetails.get(position).getProductName().equals("Materials")) {
            holder.txt_type.setVisibility(View.GONE);
            holder.txt_aggsize.setVisibility(View.GONE);
            holder.txt_slump.setVisibility(View.GONE);
            holder.txt_mix.setVisibility(View.GONE);

            if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("Concrete drainage")) {

                holder.txt_concrete_dringe.setVisibility(View.VISIBLE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_concrete_dringe.setText("Concrete Drainage :- " + orderDetails.get(position).getOrdAttributes().getOrdLabel() + " : " + orderDetails.get(position).getOrdAttributes().getOrdConcrete());

            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("Mesh and accessories")) {

                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.VISIBLE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_mesh.setText("Mesh :- " + orderDetails.get(position).getOrdAttributes().getOrdMesh());

            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("General building materials")) {
                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.VISIBLE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setText("GeneralBuilding Material :- " + orderDetails.get(position).getOrdAttributes().getGeneralbuldingMaterial());

            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("PPE")) {

                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.VISIBLE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_ppe.setText("PPE :- " + orderDetails.get(position).getOrdAttributes().getOrdPpe());

            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("Small tools")) {

                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.VISIBLE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_stools.setText("Small tools :- " + orderDetails.get(position).getOrdAttributes().getOrdSmalltools());

            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("Plastic Drainage Polypipe")) {

                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.VISIBLE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_pdp.setText("Plastic Drainage Polypipe :- " + orderDetails.get(position).getOrdAttributes().getOrdPlasticDrainagePolypipe());

            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("Polysewer / Ridgidrain")) {

                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.VISIBLE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_pr.setText("Polysewer / Ridgidrain :- " + orderDetails.get(position).getOrdAttributes().getOrdPolysewer());

            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("Plastic Drainage Wavin")) {

                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.VISIBLE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_pdw.setText("Plastic Drainage Wavin :- " + orderDetails.get(position).getOrdAttributes().getOrdPlasticDrainageWavin());
            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("Clay Drainage")) {

                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.VISIBLE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_claydrinage.setText("Clay Drainage :- " + orderDetails.get(position).getOrdAttributes().getOrdClayDrainage());
            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("Kerbs")) {

                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.VISIBLE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_kerbs.setText("Kerbs :- " + orderDetails.get(position).getOrdAttributes().getOrdKerbs());

            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("Flags")) {

                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.VISIBLE);
                holder.txt_blockpaves.setVisibility(View.GONE);
                holder.txt_flags.setText("Flags :- " + orderDetails.get(position).getOrdAttributes().getOrdFlags());
            } else if (orderDetails.get(position).getOrdAttributes().getOrdProductLabel().equals("Block Paves")) {

                holder.txt_concrete_dringe.setVisibility(View.GONE);
                holder.txt_mesh.setVisibility(View.GONE);
                holder.txt_gnrlmtrl.setVisibility(View.GONE);
                holder.txt_stools.setVisibility(View.GONE);
                holder.txt_ppe.setVisibility(View.GONE);
                holder.txt_pdp.setVisibility(View.GONE);
                holder.txt_pr.setVisibility(View.GONE);
                holder.txt_pdw.setVisibility(View.GONE);
                holder.txt_claydrinage.setVisibility(View.GONE);
                holder.txt_kerbs.setVisibility(View.GONE);
                holder.txt_flags.setVisibility(View.GONE);
                holder.txt_blockpaves.setVisibility(View.VISIBLE);
                holder.txt_blockpaves.setText("Block Paves :- " + orderDetails.get(position).getOrdAttributes().getOrdBlockpaves());
            }

        }
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_concrete_dringe)
        TextView txt_concrete_dringe;
        @BindView(R.id.txt_mesh)
        TextView txt_mesh;
        @BindView(R.id.txt_gnrlmtrl)
        TextView txt_gnrlmtrl;
        @BindView(R.id.txt_stools)
        TextView txt_stools;
        @BindView(R.id.txt_ppe)
        TextView txt_ppe;
        @BindView(R.id.txt_pdp)
        TextView txt_pdp;
        @BindView(R.id.txt_pr)
        TextView txt_pr;
        @BindView(R.id.txt_pdw)
        TextView txt_pdw;
        @BindView(R.id.txt_claydrinage)
        TextView txt_claydrinage;
        @BindView(R.id.txt_kerbs)
        TextView txt_kerbs;
        @BindView(R.id.txt_flags)
        TextView txt_flags;
        @BindView(R.id.txt_blockpaves)
        TextView txt_blockpaves;
        @BindView(R.id.txt_productname)
        TextView txt_productname;
        @BindView(R.id.txt_type)
        TextView txt_type;
        @BindView(R.id.txt_mix)
        TextView txt_mix;
        @BindView(R.id.txt_slump)
        TextView txt_slump;
        @BindView(R.id.txt_aggsize)
        TextView txt_aggsize;
        @BindView(R.id.txt_qty)
        TextView txt_qty;
        @BindView(R.id.txt_notes)
        TextView txt_notes;
        @BindView(R.id.card_notes)
        CardView card_notes;
        /*@BindView(R.id.ll_expandable_product)
        LinearLayout ll_expandable_product;
        @BindView(R.id.img_productdup)
        ImageView img_productdup;
        @BindView(R.id.card_product)
        CardView card_product;*/

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

