package com.example.contactsapplication;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHandler>{
    private Context context;
    private ArrayList<ModelContact> contactList;
    private DbHelper dbHelper;

    //constructor

    public AdapterContact(Context context, ArrayList<ModelContact> contactList) {
        this.context = context;
        this.contactList = contactList;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public ContactViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contact_item,parent,false);
        ContactViewHandler vh = new ContactViewHandler(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHandler holder, int position) {
        ModelContact modelContact = contactList.get(position);
        //get data
        String id = modelContact.getId();
        String name= modelContact.getName();
        String phone = modelContact.getPhone();
        String note= modelContact.getNote();
        String email= modelContact.getEmail();
        String addedTime= modelContact.getAddedTime();
        String updateTime= modelContact.getUpdateTime();

        //set data
        holder.contactName.setText(name);

        //handle click listener
        if(holder.contactDial != null) {
            holder.contactDial.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });
        }

        //handler item click and show contact details
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create intent to move contact details activity with contact id as reference
                Intent intent = new Intent(context,ContactDetails.class);
                intent.putExtra("contactId",id);
                context.startActivity(intent);
            }
        });

        //handel edit btn
        holder.contactEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddEditContact.class);
                intent.putExtra("ID",id);
                intent.putExtra("NAME",name);
                intent.putExtra("PHONE",phone);
                intent.putExtra("NOTE",note);
                intent.putExtra("EMAIL",email);
                intent.putExtra("ADDEDTIME",addedTime);
                intent.putExtra("UPDATETIME",updateTime);

                intent.putExtra("isEditMode",true);
                context.startActivity(intent);

            }
        });
        //handel delete btn
        holder.contactDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteContact(id);
                ((MainActivity)context).onResume();

            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHandler extends RecyclerView.ViewHolder{

        //view for row contact item
        TextView contactName,contactEdit,contactDelete;
        ImageView contactImage, contactDial;
        RelativeLayout relativeLayout;

        public ContactViewHandler(@NonNull View itemView) {
            super(itemView);

            //init view
            contactImage = itemView.findViewById(R.id.content_image);
            contactDial = itemView.findViewById(R.id.contact_number_dial);
            contactName = itemView.findViewById(R.id.contact_name);
            contactDelete = itemView.findViewById(R.id.contact_delete);
            contactEdit = itemView.findViewById(R.id.contact_edit);
            relativeLayout = itemView.findViewById(R.id.mainLayout);

        }

    }

}
