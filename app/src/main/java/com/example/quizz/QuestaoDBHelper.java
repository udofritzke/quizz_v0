package com.example.quizz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/*
GitHub: https://github.com/udofritzke/quizz_v0.git
 */

public class QuestaoDBHelper extends SQLiteOpenHelper {
    private static final int VERSAO = 1;
    private static final String NOME_DATABASE = "questoesDB";
    public QuestaoDBHelper(Context context) {
        super(context, NOME_DATABASE, null, VERSAO);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE Questoes (_id integer PRIMARY KEY autoincrement, UUID, correta, texto)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
        // Política de upgrade é simplesmente descartar o conteúdo e começar novamente
        db.execSQL("DROP TABLE IF EXISTS Questoes");
        onCreate(db);
    }
}
