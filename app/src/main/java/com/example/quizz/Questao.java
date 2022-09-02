package com.example.quizz;

import java.util.UUID;

public class Questao {
    private UUID mId;
    private int mIdAfirmacao;
    private boolean mCorreta;

    public Questao(int idAfirmacao, boolean correta) {
        mIdAfirmacao = idAfirmacao;
        mCorreta = correta;
        mId = UUID.randomUUID();
    }

    public UUID getId(){return mId;};
    
    public int getIdAfirmacao() {
        return mIdAfirmacao;
    }

    public void setIdAfirmacao(int idAfirmacao) {
        mIdAfirmacao = idAfirmacao;
    }

    public boolean isCorreta() {
        return mCorreta;
    }

    public void setCorreta(boolean correta) {
        mCorreta = correta;
    }
}
