package aplacelikethis.com.hungerstrikedbdemo;

        import android.os.Bundle;
        import android.app.Activity;
        import android.app.AlertDialog;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.Cursor;

        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;

        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class DisplayRecipe extends Activity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;

    TextView name ;
    TextView calories;
    TextView ingredient1;
    TextView ingredient2;
    TextView ingredient3;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);
        name = (TextView) findViewById(R.id.editTextName);
        calories = (TextView) findViewById(R.id.editTextCalories);
        ingredient1 = (TextView) findViewById(R.id.editTextIngredient1);
        ingredient2 = (TextView) findViewById(R.id.editTextIngredient2);
        ingredient3 = (TextView) findViewById(R.id.editTextIngredient3);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add recipe part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.RECIPES_COLUMN_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.RECIPES_COLUMN_CALORIES));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.RECIPES_COLUMN_INGREDIENT1));
                String stree = rs.getString(rs.getColumnIndex(DBHelper.RECIPES_COLUMN_INGREDIENT2));
                String plac = rs.getString(rs.getColumnIndex(DBHelper.RECIPES_COLUMN_INGREDIENT3));

                if (!rs.isClosed())
                {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                calories.setText((CharSequence) phon);
                calories.setFocusable(false);
                calories.setClickable(false);

                ingredient1.setText((CharSequence) emai);
                ingredient1.setFocusable(false);
                ingredient1.setClickable(false);

                ingredient2.setText((CharSequence) stree);
                ingredient2.setFocusable(false);
                ingredient2.setClickable(false);

                ingredient3.setText((CharSequence) plac);
                ingredient3.setFocusable(false);
                ingredient3.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_recipe, menu);
            }

            else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.Edit_Recipe:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                calories.setEnabled(true);
                calories.setFocusableInTouchMode(true);
                calories.setClickable(true);

                ingredient1.setEnabled(true);
                ingredient1.setFocusableInTouchMode(true);
                ingredient1.setClickable(true);

                ingredient2.setEnabled(true);
                ingredient2.setFocusableInTouchMode(true);
                ingredient2.setClickable(true);

                ingredient3.setEnabled(true);
                ingredient3.setFocusableInTouchMode(true);
                ingredient3.setClickable(true);

                return true;
            case R.id.Delete_Recipe:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_recipe)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteRecipe(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure?");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateRecipe(id_To_Update, name.getText().toString(), calories.getText().toString(), ingredient1.getText().toString(), ingredient2.getText().toString(), ingredient3.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(mydb.insertRecipe(name.getText().toString(), calories.getText().toString(), ingredient1.getText().toString(), ingredient2.getText().toString(), ingredient3.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "Not Done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}