package com.example.asus.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLData;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Expense_Tracker";

    private static final String TABLE_NAME = "transactionTable";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TRANSACTION  = "transType";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_FROM = "fromAcc";
    private static final String COLUMN_TO = "toAcc";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_REMARK = "remark";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_ENTRY_DATE = "dateOfEntry";


    public DatabaseManager(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTransaction = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT incomeTable_PK PRIMARY KEY AUTOINCREMENT," + COLUMN_TRANSACTION + " INTEGER NOT NULL, " + COLUMN_TYPE + " varchar(200) NOT NULL," + COLUMN_FROM + " varchar(200) ," + COLUMN_TO + " varchar(200), " + COLUMN_AMOUNT + " double NOT NULL," + COLUMN_DATE + " datetime NOT NULL," + COLUMN_ENTRY_DATE + " datetime NOT NULL," + COLUMN_REMARK + " varchar(200)" + ")";

//        Creating Expense Table Query
//        String createExpense = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_EXPENSE + "(" + COLUMN_ID_EXPENSE + " INTEGER NOT NULL CONSTRAINT expenseTable_PK PRIMARY KEY AUTOINCREMENT," + COLUMN_TYPE_EXPENSE + " varchar(200) NOT NULL," + COLUMN_AMOUNT_EXPENSE + " double NOT NULL," + COLUMN_EXPENSE_DATE + " datetime NOT NULL," + COLUMN_ENTRY_DATE_EXPENSE + " datetime NOT NULL," + COLUMN_REMARK_EXPENSE + " varchar(200)" + ")";

//       Creating Transfer Table Query
//        String createTransfer = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TRANSFER + "(" + COLUMN_ID_TRANSFER + " INTEGER NOT NULL CONSTRAINT transferTable_PK PRIMARY KEY AUTOINCREMENT," + COLUMN_TYPE_TRANSFER + " varchar(200) NOT NULL," + COLUMN_FROM_ACCOUNT + " varchar(200) NOT NULL," + COLUMN_TO_ACCOUNT + " varchar(200) NOT NULL," + COLUMN_AMOUNT_TRANSFER + " double NOT NULL," + COLUMN_TRANSFER_DATE + " datetime NOT NULL," + COLUMN_ENTRY_DATE_TRANSFER + " datetime NOT NULL," + COLUMN_REMARK_TRANSFER + " varchar(200)" + ")";


        db.execSQL(createTransaction);
//        db.execSQL(createExpense);
//        db.execSQL(createTransfer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String createTransaction = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
//        String createExpense = "DROP TABLE IF EXISTS " + TABLE_NAME_EXPENSE + ";";
//        String createTransfer = "DROP TABLE IF EXISTS " + TABLE_NAME_TRANSFER + ";";

        db.execSQL(createTransaction);
//        db.execSQL(createExpense);
//        db.execSQL(createTransfer);
        onCreate(db);
    }

    boolean addData(String transaction, String type,String fromAcc, String toAcc, double amount, String remark, String date, String dateOfEntry){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRANSACTION, transaction);
        contentValues.put(COLUMN_TYPE,type);
        contentValues.put(COLUMN_FROM,fromAcc);
        contentValues.put(COLUMN_TO, toAcc);
        contentValues.put(COLUMN_AMOUNT,amount);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_ENTRY_DATE, dateOfEntry);
        contentValues.put(COLUMN_REMARK, remark);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insertOrThrow(TABLE_NAME, null,contentValues) != -1;
    }

    Cursor getAllData(){
        SQLiteDatabase sqLiteDatabaseGetAll = getReadableDatabase();

        return sqLiteDatabaseGetAll.rawQuery("SELECT * from " + TABLE_NAME, null);
    }

    Cursor getIncome(){
        SQLiteDatabase sqLiteDatabaseGetIncome = getReadableDatabase();

        return sqLiteDatabaseGetIncome.rawQuery("SELECT * from " + TABLE_NAME + " WHERE " + COLUMN_TRANSACTION + " = 1 ",null);
    }

    Cursor getExpense(){
        SQLiteDatabase sqLiteDatabaseGetExpense = getReadableDatabase();

        return sqLiteDatabaseGetExpense.rawQuery("SELECT * from " + TABLE_NAME + " WHERE " + COLUMN_TRANSACTION + " = 2 ",null);
    }

    Cursor getTransfer(){
        SQLiteDatabase sqLiteDatabaseGetTransfer = getReadableDatabase();

        return sqLiteDatabaseGetTransfer.rawQuery("SELECT * from " + TABLE_NAME + " WHERE " + COLUMN_TRANSACTION + " = 3",null);
    }

    boolean update(int id, String type, String fromAcc, String toAcc, double amount, String date, String remark){
        SQLiteDatabase sqLiteDatabaseUpdate = getWritableDatabase();

        ContentValues contentValuesUpdate = new ContentValues();
        contentValuesUpdate.put(COLUMN_TYPE, type);
        contentValuesUpdate.put(COLUMN_FROM, fromAcc);
        contentValuesUpdate.put(COLUMN_TO, toAcc);
        contentValuesUpdate.put(COLUMN_AMOUNT, amount);
        contentValuesUpdate.put(COLUMN_DATE, date);
        contentValuesUpdate.put(COLUMN_REMARK, remark);

        return sqLiteDatabaseUpdate.update(TABLE_NAME, contentValuesUpdate, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    boolean delete(int id){
        SQLiteDatabase sqLiteDatabaseDelete = getWritableDatabase();

        return sqLiteDatabaseDelete.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    Cursor getIncomeTotal(){
        SQLiteDatabase sqLiteDatabaseIncomeTotal = getReadableDatabase();

        return sqLiteDatabaseIncomeTotal.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") as TotalIncome from " +TABLE_NAME + " WHERE " + COLUMN_TRANSACTION + " = 1",null);
    }

    Cursor getExpenseTotal(){
        SQLiteDatabase sqLiteDatabaseExpenseTotal = getReadableDatabase();

        return sqLiteDatabaseExpenseTotal.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") as TotalExpense from " + TABLE_NAME + " WHERE " + COLUMN_TRANSACTION + " =2 ", null);
    }

    Cursor getTransferTotal(){
        SQLiteDatabase sqLiteDatabaseTransferTotal = getReadableDatabase();

        return sqLiteDatabaseTransferTotal.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") as TotalTransfer from " + TABLE_NAME + " WHERE " + COLUMN_TRANSACTION + " =3 ", null);
    }

}
