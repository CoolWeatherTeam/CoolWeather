package org.das.coolweather.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

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


	/**
	 * A�ade un nuevo mensaje a la conversacion con cierto usuario
	 * @param hablandoCon El usuario con el que estas hablando
	 * @param message el mensaje
	 * @param enviadoPor 1 = enviado por el movil, 0 = enviado por tu contacto
	 */
	public void addMarker(double lat, double lon) {
		
		ContentValues values = new ContentValues();
		values.put("lat", lat);
		values.put("lon", lon);
		db.insert("markers", "lat, lon", values);
	}

	public void removeMarker(double lat, double lon) {
		db.delete("markers", 
				"lat=? and lon=?", 
				new String[]{String.valueOf(lat), String.valueOf(lon)});
	}
	
}
