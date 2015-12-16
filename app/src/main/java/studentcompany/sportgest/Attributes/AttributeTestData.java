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
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Resistencia", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Aceleracao", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Condicao fisica natural", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Equilibrio", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Forca", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Impulsao", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Velocidade", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Agressividade", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Antecipacao", 0));
            attribute_dao.insert(new Attribute(-1, "Qualificativo", "Bravura", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Compustura", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Concentracao", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Decisoes", 0));
            attribute_dao.insert(new Attribute(-1, "Qualificativo", "Determincacao", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Imprevisibilidade", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Indice de trabalho", 0));
            attribute_dao.insert(new Attribute(-1, "Qualificativo", "Lideranca", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Posicionamento", 0));
            attribute_dao.insert(new Attribute(-1, "Qualificativo", "Sem bola", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Trabalho de equipa", 0));
            attribute_dao.insert(new Attribute(-1, "Qualificativo", "Visao de jogo", 0));

            attribute_dao.insert(new Attribute(-1, "Qualificativo", "Comando de area", 0));
            attribute_dao.insert(new Attribute(-1, "Qualificativo", "Comunicacao", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Exentricidade", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Jogo aereo", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Jogo maos", 0));
            attribute_dao.insert(new Attribute(-1, "Racio", "Lancamentos", 0));
            attribute_dao.insert(new Attribute(-1, "Racio", "Livres", 0));
            attribute_dao.insert(new Attribute(-1, "Racio", "Marcacao penalties", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Pontape", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Primeiro toque", 0));
            attribute_dao.insert(new Attribute(-1, "Qualificativo", "Reflexos", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Saidas", 0));
            attribute_dao.insert(new Attribute(-1, "Racio", "Tendencia sair punhos", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Um para um", 0));

            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Cabeceamento", 0));
            attribute_dao.insert(new Attribute(-1, "Racio", "Cantos", 0));
            attribute_dao.insert(new Attribute(-1, "Racio", "Cruzamentos", 0));
            attribute_dao.insert(new Attribute(-1, "Racio", "Desarme", 0));
            attribute_dao.insert(new Attribute(-1, "Racio", "Finalizacao", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Finta", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Lancamentos longos", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Marcacao", 0));
            attribute_dao.insert(new Attribute(-1, "Racio", "Passe", 0));
            attribute_dao.insert(new Attribute(-1, "Racio", "Remates de longe", 0));
            attribute_dao.insert(new Attribute(-1, "Quantitativo", "Tecnica", 0));
        } catch (GenericDAOException ex){
            System.err.println(AttributeTestData.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(AttributeTestData.class.getName()).log(Level.WARNING, null, ex);
            ex.printStackTrace();
        }
    }
}

