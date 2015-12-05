package movil.salt.misapps;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuItemImpl;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import movil.salt.misapps.adapters.AppAdapter;
import movil.salt.misapps.model.App;

import static movil.salt.misapps.R.menu.menu_buscar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnQueryTextListener {

    PackageManager packageManager;
    List<ApplicationInfo> listaAppsInfo;
    ListView appNamesListView;

    List<App> apps;
    List<App> appsBusqueda;

    boolean busqueda = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI
        packageManager = getPackageManager();


        apps = new ArrayList<>();
        appsBusqueda = new ArrayList<>();
        appNamesListView = (ListView) findViewById(R.id.listAppsNames);
        cargarListaApps();
        appNamesListView.setOnItemClickListener(this);
    }

    private void cargarListaApps(){
        listaAppsInfo = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo appInfo: listaAppsInfo){

            String appName = appInfo.loadLabel(packageManager).toString();
            Drawable icono = appInfo.loadIcon(packageManager);
            String paquete = appInfo.packageName;

            App app = new App();
            app.setNombre(appName);
            app.setIcono(icono);
            app.setPaquete(paquete);

            apps.add(app);

        }

        AppAdapter adapter = new AppAdapter(apps,this);
        appNamesListView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String packageApp;
        App app;
        if (!busqueda){
        app = apps.get(position);
        packageApp = app.getPaquete();
        }else {
            app = appsBusqueda.get(position);
            packageApp = app.getPaquete();
        }

        abrirApp(packageApp);
    }



    private void abrirApp(String packageApp){
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageApp);
            startActivity(intent);
        }catch (Exception exe){
            Toast.makeText(this,"Esta APP no se puede abrir",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buscar,menu);

        MenuItem searchItem = menu.findItem(R.id.menu_buscar);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Nombre de la aplicación");
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        actualizarVista(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        actualizarVista(newText);
        return false;
    }

    private void actualizarVista(String query){
        appsBusqueda = new ArrayList<>();
        for (int i=0; i<apps.size(); i++) {
            if (apps.get(i).getNombre().toUpperCase().contains(query.toUpperCase())) {
                appsBusqueda.add(apps.get(i));
            }
        }

        AppAdapter adapter = new AppAdapter(appsBusqueda,this);
        appNamesListView.setAdapter(adapter);
        busqueda = true;
    }
}
