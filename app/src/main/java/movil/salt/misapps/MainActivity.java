package movil.salt.misapps;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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

import static movil.salt.misapps.R.menu.menu_buscar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnQueryTextListener {

    PackageManager packageManager;
    List<ApplicationInfo> listaAppsInfo;
    List<ApplicationInfo> listAppsInfoBusqueda;
    List<String> listAppNames;
    List<String> listAppNamesBusqueda;
    ListView appNamesListView;

    boolean busqueda = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI
        packageManager = getPackageManager();

        listAppNames = new ArrayList<>();
        listAppNamesBusqueda = new ArrayList<>();
        listAppsInfoBusqueda = new ArrayList<>();
        appNamesListView = (ListView) findViewById(R.id.listAppsNames);
        cargarListaApps();
        appNamesListView.setOnItemClickListener(this);
    }

    private void cargarListaApps(){
        listaAppsInfo = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo app: listaAppsInfo){
            //region logs
            Log.i("haur","packageName: "+app.packageName);
            Log.i("haur","Name: "+app.name);
            Log.i("haur","ProcessName: "+app.processName);
            Log.i("haur","ClassName: "+app.className);
            Log.i("haur","bacupAgentName: "+app.backupAgentName);
            Log.i("haur","loadLabel: "+app.loadLabel(packageManager));//El que lo carga
            //endregion

            String appName = app.loadLabel(packageManager).toString();
            listAppNames.add(appName);

        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listAppNames);
        appNamesListView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String packageApp;
        ApplicationInfo app;
        if (!busqueda){
        app = listaAppsInfo.get(position);
        packageApp = app.packageName;
        }else {
            app = listAppsInfoBusqueda.get(position);
            packageApp = app.packageName;
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
        listAppsInfoBusqueda= new ArrayList<>();
        listAppNamesBusqueda = new ArrayList<>();
        for (int i=0; i<listAppNames.size(); i++) {
            if (listAppNames.get(i).toUpperCase().contains(query.toUpperCase())) {
                listAppNamesBusqueda.add(listAppNames.get(i));
                listAppsInfoBusqueda.add(listaAppsInfo.get(i));
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listAppNamesBusqueda);
        appNamesListView.setAdapter(adapter);
        busqueda = true;
    }
}
