package org.das.coolweather.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LaBD extends SQLiteOpenHelper{

	private static LaBD miLaBD;
	private SQLiteDatabase db = getWritableDatabase();
	
	private LaBD(Context context, String name, CursorFactory factory, int version)  {
		super(context, name, factory, version);
		
	}
	
	public static LaBD getMiBD(Context context) {
		if(miLaBD == null) {
			miLaBD = new LaBD(context, "CoolWeatherBD", null, 1);
		}
		
		return miLaBD;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//creamos la tabla de mensajes
		db.execSQL("CREATE TABLE 'markers' "
					+ "('id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , "
					+ "'lat' REAL, "
					+ "'lon' REAL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE markers");
	}
	
	/**
	 * Metodo que devuelve todos los mensajes que tienes con un usuario
	 * @param user
	 * @return
	 */
	public Cursor getMarkers() {
		
		return db.query("markers", //tabla
				new String[] {"lat", "lon"},  //columnas
				null, //where nombreusuario
				null, //= user
				null, //groupby
				null, //having
				null); //orderby
	}

	public void addMarker(double lat, double lon) {
		
		ContentValues values = new ContentValues();
		values.put("lat", lat);
		values.put("lon", lon);
		db.insert("markers", "lat, lon", values);
	}

	public void removeMarker(double lat, double lon) {
		try {
			db.execSQL("delete from markers where lat="+lat+" and lon="+lon);
			
		} catch (SQLException e) {
			Log.e("Error", e.toString());
		}
	}
	
	public void vaciar() {
		db.delete("markers", null, null);
	}
	
}
