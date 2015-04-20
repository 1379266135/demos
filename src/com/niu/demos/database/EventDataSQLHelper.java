package com.niu.demos.database;

import java.io.File;
import java.io.IOException;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.provider.BaseColumns;
import android.util.Log;

public class EventDataSQLHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "gxws_db.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_B = "black";
	public static final String TABLE_W = "white";
	public static final String COL_NUMBER = "number";
	public static final String COL_NAME = "name";

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE_B + "( " 
										+ BaseColumns._ID + " integer primary key autoincrement, " 
										+ COL_NUMBER + " text not null, "
										+ COL_NAME + " text);";
		Log.d("EventsData", "onCreate: " + sql);
		db.execSQL(sql);
		
		String sql1 = "create table " + TABLE_W + "( " 
										+ BaseColumns._ID + " integer primary key autoincrement, " 
										+ COL_NUMBER + " text not null, "
										+ COL_NAME + " text);";
		Log.d("EventsData", "onCreate: " + sql);
		db.execSQL(sql1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	
	private static final String TAG = "DatabaseHelper";
	
//    public static String DATABASE_PATH;
    private SQLiteDatabase myDataBase;
//    private final Context context;
    private String password = "";
    private String FULL_DB_Path;
    private static EventDataSQLHelper helper;
    
    public static EventDataSQLHelper getInstance(Context context){
    	if(helper == null){
    		return new EventDataSQLHelper(context);
    	}
    	return helper;
    }

	private EventDataSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
//		DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).getPath();
//        this.context = context;
//        SQLiteDatabase.loadLibs(context.getApplicationContext());//load SqlCipher libraries
//        FULL_DB_Path = DATABASE_PATH;//full database path
	}
	
//    public SQLiteDatabase open(String password) {
//        this.password = password;
//
//        if (!checkDataBase()) {// if Database Not Exist
//            copyDataBase();
//        }
//
//        myDataBase = getExistDataBaseFile();
//
//        return myDataBase;
//    }
    
//    private SQLiteDatabase getExistDataBaseFile() {// this function to open an Exist database 

//        SQLiteDatabaseHook hook = new SQLiteDatabaseHook() {
//            public void preKey(SQLiteDatabase database) {}
//
//            public void postKey(SQLiteDatabase database) {
//                database.rawExecSQL("PRAGMA cipher_migrate;");
//            }
//        };
//        return SQLiteDatabase.openOrCreateDatabase(FULL_DB_Path, password, null, hook);

//    }

    /** 检查数据库文件是否已经存在 */
    private boolean checkDataBase() {// Check database file is already exist or not
        boolean checkDB = false;
        try {
            File dbfile = new File(FULL_DB_Path);
            checkDB = dbfile.exists();
        } catch (SQLiteException e) {
        	Log.e("info",TAG + " check database is exist or not " + e.getLocalizedMessage());
        }
        return checkDB;
    }

    public void db_delete() {// delete database
        File file = new File(FULL_DB_Path);
        if (file.exists()) {
            file.delete();
            Log.d("info", TAG + "delete database file.");
        }
    }

    private void copyDataBase() {//make a sub folder for database location and copy the database
//        try {
//            File fofo = new File(DATABASE_PATH);
//            fofo.mkdirs();
//            extractAssetToDatabaseDirectory(DATABASE_NAME);
//        } catch (IOException e) {
//        	Log.e("info",TAG + " copyDatabase " + e.getMessage());
//        }

    }

    public synchronized void closeDataBase() throws SQLException {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    public void extractAssetToDatabaseDirectory(String fileName)
            throws IOException {// copy the database

//        int length;
//        InputStream sourceDatabase = context.getAssets().open(fileName);
//        File destinationPath = new File(FULL_DB_Path);
//        OutputStream destination = new FileOutputStream(destinationPath);
//
//        byte[] buffer = new byte[4096];
//        while ((length = sourceDatabase.read(buffer)) > 0) {
//            destination.write(buffer, 0, length);
//        }
//        sourceDatabase.close();
//        destination.flush();
//        destination.close();
    }

//    public boolean changePassword(String newPassword) {// DataBase must be
                                                        // opened before
                                                        // changing Password

//        try {
//            if (myDataBase != null && myDataBase.isOpen()) {
//
//                myDataBase.rawExecSQL("BEGIN IMMEDIATE TRANSACTION;");
//                myDataBase.rawExecSQL("PRAGMA rekey = '" + newPassword + "';");
//
//                this.close();
//                myDataBase.close();
//
//                return true;
//
//            } else {
//
//                Log.e("boolean changePassword()",
//                        "Change Password Error : DataBase is null or not opened  !!");
//                return false;
//            }
//        } catch (Exception e) {
//
//            Log.e("boolean changePassword()",
//                    "Change Password Error :ExecSQL Error !!");
//            return false;
//
//        }
//
//    }

}
