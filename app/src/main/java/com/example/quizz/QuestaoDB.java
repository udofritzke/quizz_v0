package com.example.quizz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuestaoDB {
    private Context mContext;
    private static Context mStaticContext;
    private SQLiteDatabase mDatabase;

    public QuestaoDB(Context contexto){
        mContext = contexto.getApplicationContext();
        mStaticContext = mContext;
        mDatabase = new QuestaoDBHelper(mContext).getWritableDatabase();
    }
    private static ContentValues getValoresConteudo(Questao q){
        ContentValues valores = new ContentValues();

        // CREATE TABLE Questoes (_id integer PRIMARY KEY autoincrement, UUID, correta, texto)
        // pares chave-valor: nomes das colunas - valores
        valores.put("UUID", q.getId().toString());
        valores.put("texto",
                mStaticContext.getString(q.getIdAfirmacao())); // recupera valor do recurso string pelo id
        valores.put("correta", q.isCorreta());
        return valores;
    }

    public void addQuestao(Questao q){
        ContentValues valores = getValoresConteudo(q);
        mDatabase.insert("Questoes", null, valores);
    }
/*
    public void updateQuestao(Questao q){
        String uuidString = q.getId().toString();
        ContentValues valores = getValoresConteudo(q);
        // mDatabase.update(QuestoesDbSchema.QuestoesTbl.NOME, valores, QuestoesDbSchema.QuestoesTbl.Cols.UUID +" = ?",
        //        new String[] {uuidString});
    }

    public Cursor queryQuestao(String clausulaWhere, String[] argsWhere){
        Cursor cursor = mDatabase.query("Questoes",
                null,  // todas as colunas
                clausulaWhere,
                argsWhere,
                null, // sem group by
                null, // sem having
                null  // sem order by
        );
        return cursor;
    }
    void removeBanco(){
        int delete;
        delete = mDatabase.delete(
                "Questoes",
                null, null);
    }
     */
}
