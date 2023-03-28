package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
GitHub:
https://github.com/udofritzke/quizz_v0

Comentário segundo commit
 */

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewAfirmacao;
    private Button mBotaoVerdadeiro;
    private Button mBotaoFalso;
    private Button mBotaoProximo;
    boolean haQuestoes;

    QuestaoDB mQuestoesDb;

    private int mIndiceQuestaoAtual = 0;

    private Questao[] mBancoDeQuestoes = new Questao[]{
            new Questao(R.string.afirmacao_terra_plana, false),
            new Questao(R.string.afirmacao_linux, true),
            new Questao(R.string.afirmacao_boca, true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Atualiza visão de texto */
        atualizaTextoAfirmacao();

        // recupera referência para o banco de dados
        if (mQuestoesDb == null) {
            mQuestoesDb = new QuestaoDB(getBaseContext());
        }
        mQuestoesDb.esvaziaTabela();
        mQuestoesDb = new QuestaoDB(getBaseContext());

        int indice = 0;
        mQuestoesDb.addQuestao(mBancoDeQuestoes[indice++]);
        mQuestoesDb.addQuestao(mBancoDeQuestoes[indice++]);
        mQuestoesDb.addQuestao(mBancoDeQuestoes[indice++]);

        /* define tratador do botão Verdadeiro */
        mBotaoVerdadeiro = (Button) findViewById(R.id.botao_verdadeiro);
        // utilização de classe anônima interna
        mBotaoVerdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaResposta(true);
            }
        });

        /* define tratador do botão Falso */
        mBotaoFalso = (Button) findViewById(R.id.botao_falso);
        // utilização de classe anônima interna
        mBotaoFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaResposta(false);
            }
        });
        mBotaoProximo = (Button) findViewById(R.id.botao_proximo);
        mBotaoProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIndiceQuestaoAtual = (mIndiceQuestaoAtual+1) % mBancoDeQuestoes.length;
                atualizaTextoAfirmacao();
            }
        });

        /*
          Imprime as questões cadastradas no BD
         */
        Cursor cursor = mQuestoesDb.queryQuestao(null, null);
        if (cursor.getCount() > 0) {
            haQuestoes = true;
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                String afirmacao = cursor.getString(3);
                int ehcorreta = cursor.getInt(2);
                cursor.moveToNext();
                Log.d("main", afirmacao + " " + ehcorreta);
            }
        } else haQuestoes = false;
    }

    void mostraToastAcertou() {
        int id_toast = R.string.texto_acertou;
        Toast.makeText(this, id_toast, Toast.LENGTH_SHORT).show();
    }

    void mostraToastErrou() {
        int id_toast = R.string.texto_errou;
        Toast.makeText(this, id_toast, Toast.LENGTH_SHORT).show();
    }

    void atualizaTextoAfirmacao() {
        Questao q = mBancoDeQuestoes[mIndiceQuestaoAtual];
        mTextViewAfirmacao = (TextView) findViewById(R.id.view_texto_da_afirmação);
        mTextViewAfirmacao.setText(q.getIdAfirmacao());
    }

    private void verificaResposta(boolean respostaPressionada) {
        boolean respostaCorreta = mBancoDeQuestoes[mIndiceQuestaoAtual].isCorreta();
        if (respostaPressionada == respostaCorreta) {
            mostraToastAcertou();
        } else
            mostraToastErrou();
    }
}