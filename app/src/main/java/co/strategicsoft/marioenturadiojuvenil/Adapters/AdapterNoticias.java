package co.strategicsoft.marioenturadiojuvenil.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.strategicsoft.marioenturadiojuvenil.R;
import co.strategicsoft.marioenturadiojuvenil.model.TwitterM;


public class AdapterNoticias extends BaseAdapter {

    private Activity mContexto;
    private List<TwitterM> mDatos;

    public AdapterNoticias(Activity mContexto, List<TwitterM> mDatos){
        this.mContexto = mContexto;
        this.mDatos = mDatos;
    }

    @Override
    public int getCount() {
        return mDatos.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContexto, R.layout.list_item_noticias, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.titulo.setText(mDatos.get(position).getNicke());
        holder.contenido.setText(mDatos.get(position).getBody());
        holder.image.setImageResource(R.mipmap.ic_launcher);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name= " + mDatos.get(position).getUrl()));
                    mContexto.startActivity(intent);
                } catch (Exception e) {
                    mContexto.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + mDatos.get(position).getUrl())));
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        public TextView titulo  = null;
        public TextView contenido = null;
        public ImageView image = null;

        public ViewHolder(View view) {
            titulo = (TextView) view.findViewById(R.id.txtTitulo);
            contenido = (TextView) view.findViewById(R.id.txtContenido);
            image = (ImageView) view.findViewById(R.id.listicon);
            view.setTag(this);
        }
    }
}
