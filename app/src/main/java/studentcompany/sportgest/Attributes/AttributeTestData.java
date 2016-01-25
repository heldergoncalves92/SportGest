package studentcompany.sportgest.Attributes;

import android.content.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;

public class AttributeTestData {
    Attribute_DAO attribute_dao;

    public AttributeTestData(Context context) {
        attribute_dao = new Attribute_DAO(context);

        try {
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Resistencia", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Aceleracao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Condicao fisica natural", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Equilibrio", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Forca", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Impulsao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Velocidade", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Agressividade", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Antecipacao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Bravura", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Compustura", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Concentracao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Decisoes", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Determincacao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Imprevisibilidade", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Indice de trabalho", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Lideranca", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Posicionamento", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Sem bola", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Trabalho de equipa", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Visao de jogo", 0));

            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Comando de area", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Comunicacao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Exentricidade", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Jogo aereo", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Jogo maos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Lancamentos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Livres", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Marcacao penalties", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Pontape", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Primeiro toque", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUALITATIVE, "Reflexos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Saidas", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Tendencia sair punhos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Um para um", 0));

            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Cabeceamento", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Cantos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Cruzamentos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Desarme", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Finalizacao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Finta", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Lancamentos longos", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Marcacao", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Passe", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.RATIO, "Remates de longe", 0));
            attribute_dao.insert(new Attribute(-1, Attribute.QUANTITATIVE, "Tecnica", 0));
        } catch (GenericDAOException ex){
            System.err.println(AttributeTestData.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(AttributeTestData.class.getName()).log(Level.WARNING, null, ex);
            ex.printStackTrace();
        }
    }
}

