package net.dereva.taxi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.dereva.taxi.R;
import net.dereva.taxi.helper.DateHelper;
import net.dereva.taxi.interfaces.OnOrderClickListener;
import net.dereva.taxi.model.Order;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<Order> orderList;
    private OnOrderClickListener orderClickListener;

    public void setOrderClickListener(OnOrderClickListener orderClickListener) {
        this.orderClickListener = orderClickListener;
    }

    public OrderAdapter(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new OrderViewHolder(view, orderClickListener);
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView startAddress, endAddress, orderDate, amount, currency;

        OrderViewHolder(@NonNull View viewHolder, final OnOrderClickListener onOrderClickListener) {
            super(viewHolder);
            startAddress = viewHolder.findViewById(R.id.item_start_address_tv);
            endAddress = viewHolder.findViewById(R.id.item_end_address_tv);
            orderDate = viewHolder.findViewById(R.id.item_trip_date_tv);
            amount = viewHolder.findViewById(R.id.item_order_amount_tv);
            currency = viewHolder.findViewById(R.id.item_currency_tv);

            viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        onOrderClickListener.onOrderClicked(position);

                    }
                }
            });
        }

        void bind(Order order){
            startAddress.setText(order.getStartAddress().getAddress());
            endAddress.setText(order.getEndAddress().getAddress());
            orderDate.setText(DateHelper.showOrderDate(order));
            amount.setText(String.valueOf(order.getPrice().getAmount()).substring(0,3));
            currency.setText(order.getPrice().getCurrency());

        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public Order getOrder(int position){
        return orderList.get(position);
    }

}