package movil.salt.misapps.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import movil.salt.misapps.R;
import movil.salt.misapps.model.App;

/**
 * Created by Hamilton Urbano on 5/12/2015.
 */
public class AppAdapter extends BaseAdapter {
    List<App> data;
    Context context;

    public AppAdapter(List<App> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
        if (convertView == null){
            v = View.inflate(context, R.layout.templatate_adapter_app,null);
        }else{
            v = convertView;
        }

        TextView nombre = (TextView) v.findViewById(R.id.txt_nombre_template);
        ImageView icono = (ImageView) v.findViewById(R.id.img_template);

        App app = data.get(position);

        nombre.setText(app.getNombre());
        icono.setImageDrawable(app.getIcono());

        return v;
    }
}
