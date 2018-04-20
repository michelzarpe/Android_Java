package com.michel.zarpelon.lappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture[] passarinho;
    private Texture[] cano;
    private Random espacoRandomico;
    private Texture planoFundo;
    private Texture gameOver;
    private int movimento;
    private float velocidadeQueda;
    private float passaroPosicaoInicialVertical;
    private float passaroPosicaoInicialHorizontal;
    private int LARGURA_TELA;
    private int ALTURA_TELA;
    private float variacaoImagensPassaros;
    private float canoPosicaoInicialVerticalTop;
    private float canoPosicaoInicialVerticalDown;
    private float canoPosicaoInicialHorizontal;
    private float espacamentoEntreCanos;
    private float alturaEntreCanos;
    private int pontuacao;
    private int estadoJogo; /*0 parado, 1 jogando, 2 game over */
    private BitmapFont placar;
    private BitmapFont mensagem;
    private Texture feitoPor;
    private boolean marcouPonto;
    private Circle passaroCircle;
    private Rectangle rectangleTOP;
    private Rectangle rectangleDOWN;
    private ShapeRenderer shapeRenderer;


    @Override
    public void create() {
        batch = new SpriteBatch();
        passarinho = new Texture[3];
        passarinho[0] = new Texture("passaro1.png");
        passarinho[1] = new Texture("passaro2.png");
        passarinho[2] = new Texture("passaro3.png");
        gameOver = new Texture("game_over.png");
        feitoPor = new Texture("mz.PNG");
        espacoRandomico = new Random();

        passaroCircle = new Circle();
        rectangleDOWN = new Rectangle();
        rectangleTOP = new Rectangle();
        shapeRenderer = new ShapeRenderer();


        cano = new Texture[2];
        cano[0] = new Texture("cano_topo_maior.png");
        cano[1] = new Texture("cano_baixo_maior.png");

        planoFundo = new Texture("fundo.png");


        pontuacao = 0;
        ALTURA_TELA = Gdx.graphics.getHeight();/*largura da tela do celular*/
        LARGURA_TELA = Gdx.graphics.getWidth();/*altura da tela do celular*/
        movimento = 0;
        velocidadeQueda = 20;
        variacaoImagensPassaros = 0;
        estadoJogo = 0;

        passaroPosicaoInicialVertical = ALTURA_TELA / 2;
        passaroPosicaoInicialHorizontal = 240;
        espacamentoEntreCanos = 300;

        canoPosicaoInicialHorizontal = LARGURA_TELA;
        canoPosicaoInicialVerticalDown = ALTURA_TELA / 2 - cano[1].getHeight(); // ponto de onde vai subir
        canoPosicaoInicialVerticalTop = ALTURA_TELA / 2;// ponto de onde vai subir
        placar = new BitmapFont();
        placar.setColor(Color.WHITE);
        placar.getData().setScale(6);

        mensagem = new BitmapFont();
        mensagem.setColor(Color.RED);
        mensagem.getData().setScale(3);
        marcouPonto = false;


    }

    @Override
    public void render() {
        /*pintado a tela-------------------------------------*/
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*----------------------------------------------------*/

        /*Batendo as asas*/
        variacaoImagensPassaros = variacaoImagensPassaros + Gdx.graphics.getDeltaTime() * 10;
        if (variacaoImagensPassaros > 2)
            variacaoImagensPassaros = 0;

        /*LIBERANDO PASSARO*/
        if (estadoJogo==0) {
            if (Gdx.input.justTouched()) {
                estadoJogo = 1;
            }
        } else{
            if (estadoJogo==1){
                velocidadeQueda++;
                /*movimentacao dos canos*/
                canoPosicaoInicialHorizontal = canoPosicaoInicialHorizontal - (Gdx.graphics.getDeltaTime() * 300);
                if (canoPosicaoInicialHorizontal < -cano[0].getWidth()) {
                    canoPosicaoInicialHorizontal = LARGURA_TELA;
                    alturaEntreCanos = espacoRandomico.nextInt((int) espacamentoEntreCanos) - 200;
                    marcouPonto = false;
                }

                /*faz a pontuacao*/
                if (canoPosicaoInicialHorizontal < passaroPosicaoInicialHorizontal) {
                    if (!marcouPonto) {
                        pontuacao++;
                        marcouPonto = true;
                    }
                }


                /*velocidade Da queda do passaro*/
                if (passaroPosicaoInicialVertical > 0 || velocidadeQueda < 0)
                    passaroPosicaoInicialVertical = passaroPosicaoInicialVertical - velocidadeQueda;

                /*click na tela*/
                if (Gdx.input.justTouched()) {
                    velocidadeQueda = -20;
                }

            }else{
                if (Gdx.input.justTouched()) {
                    inicializar();
                }
            }
        }

        passaroCircle.set(passaroPosicaoInicialHorizontal, passaroPosicaoInicialVertical, (passarinho[0].getWidth() / 2));
        rectangleTOP.set(canoPosicaoInicialHorizontal, canoPosicaoInicialVerticalTop + espacamentoEntreCanos / 2 + (alturaEntreCanos), cano[0].getWidth(), cano[0].getHeight());
        rectangleDOWN.set(canoPosicaoInicialHorizontal, canoPosicaoInicialVerticalDown - espacamentoEntreCanos / 2 + (alturaEntreCanos), cano[1].getWidth(), cano[1].getHeight());


        desenharTextura();
        //desenharObjetos();

        if (Intersector.overlaps(passaroCircle, rectangleTOP) || Intersector.overlaps(passaroCircle, rectangleDOWN)
                || passaroPosicaoInicialHorizontal <=0 || passaroPosicaoInicialHorizontal>=LARGURA_TELA
                || passaroPosicaoInicialVertical <=0 || passaroPosicaoInicialVertical >=ALTURA_TELA){
            estadoJogo=2;
        }


    }

    @Override
    public void dispose() {
        batch.dispose();
        passarinho[0].dispose();
        passarinho[1].dispose();
        passarinho[2].dispose();
        planoFundo.dispose();
    }

    private void desenharTextura() {
        /*desenho da textura*/
        batch.begin();

        batch.draw(planoFundo, 0, 0, LARGURA_TELA, ALTURA_TELA);
        batch.draw(cano[0], canoPosicaoInicialHorizontal, canoPosicaoInicialVerticalTop + espacamentoEntreCanos / 2 + (alturaEntreCanos));//top
        batch.draw(cano[1], canoPosicaoInicialHorizontal, canoPosicaoInicialVerticalDown - espacamentoEntreCanos / 2 + (alturaEntreCanos));//down
        batch.draw(passarinho[(int) variacaoImagensPassaros], passaroPosicaoInicialHorizontal, passaroPosicaoInicialVertical);
        if(estadoJogo==2){
               batch.draw(gameOver,LARGURA_TELA/2 - gameOver.getWidth()/2,ALTURA_TELA/2);
               mensagem.draw(batch, "Click para Reiniciar!", (LARGURA_TELA / 2) - 160, ALTURA_TELA/2);
           }

        placar.draw(batch, String.valueOf(pontuacao), LARGURA_TELA / 2, ALTURA_TELA - 100);
        batch.draw(feitoPor, 0, -2);
        batch.end();
    }



    private void desenharObjetos() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(passaroCircle.x + (passarinho[0].getWidth() / 2), passaroCircle.y + (passarinho[0].getHeight() / 2), passaroCircle.radius);
        shapeRenderer.rect(rectangleTOP.x, rectangleTOP.y, rectangleTOP.width, rectangleTOP.height);
        shapeRenderer.rect(rectangleDOWN.x, rectangleDOWN.y, rectangleDOWN.width, rectangleDOWN.height);
        shapeRenderer.end();
    }


    private void inicializar(){
        pontuacao = 0;
        ALTURA_TELA = Gdx.graphics.getHeight();/*largura da tela do celular*/
        LARGURA_TELA = Gdx.graphics.getWidth();/*altura da tela do celular*/
        movimento = 0;
        velocidadeQueda = 20;
        variacaoImagensPassaros = 0;
        estadoJogo = 0;
        passaroPosicaoInicialVertical = ALTURA_TELA / 2;
        passaroPosicaoInicialHorizontal = 240;
        espacamentoEntreCanos = 300;
        canoPosicaoInicialHorizontal = LARGURA_TELA;
        canoPosicaoInicialVerticalDown = ALTURA_TELA / 2 - cano[1].getHeight(); // ponto de onde vai subir
        canoPosicaoInicialVerticalTop = ALTURA_TELA / 2;// ponto de onde vai subi

        marcouPonto = false;
    }

}
