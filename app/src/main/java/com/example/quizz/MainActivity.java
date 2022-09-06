package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    // insere questões no SQLite
        if (mQuestoesDb == null) {
            mQuestoesDb = new QuestaoDB(getBaseContext());
        }

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