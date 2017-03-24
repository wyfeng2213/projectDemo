package com.cmcc.patient.im;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cmcc.patient.R;

import java.util.List;


public class ChatListViewAdapter extends BaseAdapter {

    public static final int TYPE_TEXT_LEFT = 0;
    public static final int TYPE_TEXT_RIGHT = 1;
    public static final int TYPE_IMAGE_LEFT = 2;
    public static final int TYPE_IMAGE_RIGHT = 3;

    private List<MessageModel> dataSource;
    private LayoutInflater inflater = null;
    private Context context = null;

    public ChatListViewAdapter(Context context) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public List<MessageModel> getDatasource() {
        return dataSource;
    }


    public void setDatasource(List<MessageModel> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        MessageModel model = dataSource.get(position);
        return model.getMsgUiType();
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 4;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataSource == null ? 0 : dataSource.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return dataSource.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        MessageModel model = this.dataSource.get(position);
        int type = getItemViewType(position);
        ViewHolderText holderText = null;
        ViewHolderImage holderImage = null;
        if (convertView == null) {

            switch (type) {
                case TYPE_TEXT_LEFT:
                    convertView = inflater.inflate(R.layout.msg_text_left, null);
                    holderText = new ViewHolderText();
                    holderText.name = (TextView) convertView.findViewById(R.id.msg_text_left_user);
                    holderText.text = (TextView) convertView.findViewById(R.id.msg_text_left_content);
                    holderText.status = (TextView) convertView.findViewById(R.id.msg_text_left_status);
                    convertView.setTag(holderText);
                    break;
                case TYPE_TEXT_RIGHT:
                    convertView = inflater.inflate(R.layout.msg_text_right, null);
                    holderText = new ViewHolderText();
                    holderText.name = (TextView) convertView.findViewById(R.id.msg_text_right_user);
                    holderText.text = (TextView) convertView.findViewById(R.id.msg_text_right_content);
                    holderText.status = (TextView) convertView.findViewById(R.id.msg_text_right_status);
                    convertView.setTag(holderText);
                    break;
                case TYPE_IMAGE_LEFT:
                    convertView = inflater.inflate(R.layout.msg_image_left, null);
                    holderImage = new ViewHolderImage();
                    holderImage.name = (TextView) convertView.findViewById(R.id.msg_image_left_user);
                    holderImage.image = (ImageView) convertView.findViewById(R.id.msg_image_left_content);
                    holderImage.progressBar = (ProgressBar) convertView.findViewById(R.id.msg_image_left_progress);
                    holderImage.status = (TextView) convertView.findViewById(R.id.msg_image_left_status);
                    convertView.setTag(holderImage);
                    break;
                case TYPE_IMAGE_RIGHT:
                    convertView = inflater.inflate(R.layout.msg_image_right, null);
                    holderImage = new ViewHolderImage();
                    holderImage.name = (TextView) convertView.findViewById(R.id.msg_image_right_user);
                    holderImage.image = (ImageView) convertView.findViewById(R.id.msg_image_right_content);
                    holderImage.progressBar = (ProgressBar) convertView.findViewById(R.id.msg_image_right_progress);
                    holderImage.status = (TextView) convertView.findViewById(R.id.msg_image_right_status);
                    convertView.setTag(holderImage);
                    break;
            }


        } else {
            switch (type) {
                case TYPE_TEXT_LEFT:
                case TYPE_TEXT_RIGHT:
                    holderText = (ViewHolderText) convertView.getTag();
                    break;
                case TYPE_IMAGE_LEFT:
                case TYPE_IMAGE_RIGHT:
                    holderImage = (ViewHolderImage) convertView.getTag();
                    break;
            }

        }

        final String originalPath = model.getMsgOriginalPath();
        final String originalUri = model.getMsgOriginalUri();
        if (holderImage != null) {
            holderImage.image.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(context, OriginalActivity.class);
                    if (originalPath != null && originalPath.length() > 0)
                        intent.putExtra(OriginalActivity.ORIGINAL_PATH, originalPath);

                    if (originalUri != null && originalUri.length() > 0)
                        intent.putExtra(OriginalActivity.ORIGINAL_URI, originalUri);

                    context.startActivity(intent);
                }
            });
        }

        switch (type) {
            case TYPE_TEXT_LEFT:
            case TYPE_TEXT_RIGHT:
                holderText.name.setText(model.getMsgByUser());
                holderText.text.setText(model.getMsgText());
                holderText.status.setText(model.getMsgStatus());
                break;
            case TYPE_IMAGE_LEFT:
            case TYPE_IMAGE_RIGHT:
                holderImage.name.setText(model.getMsgByUser());
                holderImage.status.setText(model.getMsgStatus());
                holderImage.progressBar.setProgress(model.getProgress());

                holderImage.image.setDrawingCacheEnabled(true);
                Bitmap bmp = holderImage.image.getDrawingCache();
                holderImage.image.setDrawingCacheEnabled(false);
                if (bmp != null) {
                    bmp.recycle();
                    bmp = null;
                }
                String bmpPath = model.getMsgThumbPath();
                Bitmap bitmap = BitmapFactory.decodeFile(bmpPath);
                if (bitmap != null)
                    holderImage.image.setImageBitmap(bitmap);
                else
                    holderImage.image.setImageResource(R.drawable.ic_launcher);

                break;
        }

        return convertView;
    }

    public static class ViewHolderText {
        public TextView name;
        public TextView text;
        public TextView status;
    }

    public static class ViewHolderImage {
        public TextView name;
        public ImageView image;
        public ProgressBar progressBar;
        public TextView status;
    }
}
