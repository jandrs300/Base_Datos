package com.example.juanandres.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Juan Andres on 14/03/2016.
 */
public class DateBaseManager {

    public static final String TABLE_NAME = "contactos";

    public static final String CN_ID = "_id";
    public static final String CN_NAME = "nombre";
    public static final String CN_PHONE = "telefono";


    //  create table contactos(
    //                          _id integer primary key autoincrement,
    //                          nombre text not null,
    //                          telefono text);


    public static final String CREATE_TABLE  = "create table " + TABLE_NAME+"("
            + CN_ID +" integer primary key autoincrement,"
            + CN_NAME + " text not null,"
            + CN_PHONE + " text);";


    private DbHelper helper;
    private SQLiteDatabase db;

    public DateBaseManager(Context context) {

        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    private ContentValues generarContenido(String nombre, String telefono){
        ContentValues valores = new ContentValues();
        valores.put(CN_NAME,nombre);
        valores.put(CN_PHONE, telefono);
        return valores;

    }

    public void insertar(String nombre, String telefono){

        // db.insert(TABLA, NullColumBack, ContentValues);
        // TABLA ===> nombre de la tabla.
        // NullColumBack ===> columna o campo que puede estar vacio o nulo. (o se especifica que campo o se pone mejor null)
        //
        db.insert(TABLE_NAME, null, generarContenido(nombre, telefono));
    }

    public void eliminar(String nombre){
        // db.delete(Tabla, Clausula Where, Argumentos where);
        db.delete(TABLE_NAME, CN_NAME + "=?", new String[]{nombre});
    }

    public void eliminarMultiples(String nomb1, String nomb2){
        db.delete(TABLE_NAME, CN_NAME + "IN (?,?)", new String[]{nomb1,nomb2});
    }

    public void modificarTelefono(String nombre, String nuevoTelefono){
        // db.update(Tabla, ContenValues, Clausula Where, Argumentos Where);
        db.update(TABLE_NAME, generarContenido(nombre, nuevoTelefono), CN_NAME + "=?", new String[]{nombre});
    }

    public Cursor cargarCursorContactos() {
        String[] columnas = new String[]{CN_ID,CN_NAME,CN_PHONE};
        // query (String Table, String[] columns, String Selection, String[] selectionArg, String having, String orderBy)
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);
    }

    public Cursor buscarContacto(String nombre){

        // esperar 7 segundos. Simular que carga muchos datos.
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /////////////
        String[] columnas = new String[]{CN_ID,CN_NAME,CN_PHONE};
        return  db.query(TABLE_NAME,columnas,CN_NAME + "=?",new String[]{nombre},null,null,null);
    }


}
