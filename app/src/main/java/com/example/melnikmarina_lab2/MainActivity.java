package com.example.melnikmarina_lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String name="";
    List<String> products;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products = new LinkedList<>();
        products.add("ShoppingList");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter(products);
        recyclerView.setAdapter(adapter);

        context = this;

        findViewById(R.id.addbutton).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Adding");

            final EditText input = new EditText(this);

            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    name = input.getText().toString();
                    products.add(name);
                    adapter.notifyItemInserted(products.size()-1);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });
    }
    public class Adapter extends RecyclerView.Adapter<VHolder>{
        List<String> products;

        public Adapter(List<String> products) {
            this.products = products;
        }
        @NonNull
        @Override
        public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
            return new VHolder(view).link(this);
        }

        @Override
        public void onBindViewHolder(@NonNull VHolder holder, int pos) {
            holder.text.setText(products.get(pos));
        }

        @Override
        public int getItemCount() {
            return products.size();
        }
    }

    class VHolder extends RecyclerView.ViewHolder{
        TextView text;
        private Adapter adapter;
        public VHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.name);
            itemView.findViewById(R.id.deletebutton).setOnClickListener(view -> {
                adapter.products.remove(getAbsoluteAdapterPosition());
                adapter.notifyItemRemoved(getAbsoluteAdapterPosition());
            });
            itemView.findViewById(R.id.editbutton).setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Editing");

                final EditText input = new EditText(context);
                input.setText(adapter.products.get(getAbsoluteAdapterPosition()));

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name = input.getText().toString();
                        adapter.products.set(getAbsoluteAdapterPosition(), name);
                        adapter.notifyItemChanged(getAbsoluteAdapterPosition());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            });

        }

        public VHolder link(Adapter adapter){
            this.adapter=adapter;
            return this;
        }
    }



}
